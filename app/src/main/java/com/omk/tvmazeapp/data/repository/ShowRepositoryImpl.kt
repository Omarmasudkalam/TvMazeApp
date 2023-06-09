package com.omk.tvmazeapp.data.repository

import com.omk.tvmazeapp.data.local.datasource.LocalDataSource
import com.omk.tvmazeapp.data.local.entity.ShowListingEntity
import com.omk.tvmazeapp.core.mapper.toShowListing
import com.omk.tvmazeapp.core.mapper.toShowListingEntity
import com.omk.tvmazeapp.data.remote.datasource.RemoteDataSource
import com.omk.tvmazeapp.domain.model.ShowDetail
import com.omk.tvmazeapp.domain.repository.ShowRepository
import com.omk.tvmazeapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ShowRepository {

    override suspend fun getShowListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ShowDetail>>> {
        return flow {
            emit(Resource.Loading(true))
            val localList = getShowListingFromDb(query)
            emit(
                Resource.Success(
                data = localList.map { it.toShowListing() }
            ))
            val isDbEmpty = localList.isEmpty() && query.isBlank()
            val loadFromCache = !isDbEmpty && !fetchFromRemote

            if (loadFromCache) {
                emit(Resource.Loading(false))
                return@flow //we don't make request since we have data already on db and return flow
            } else {
                val remoteListings = try {
                    getShowListingFromApi()
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null // flow{null}
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null // flow{null}
                }
                remoteListings?.let { listings ->
                    clearShowListingsFromDb()
                    insertShowListingToDb(listings.map {it.toShowListing()
                        .toShowListingEntity() })
                    emit(
                        Resource.Success(
                        data = getShowListingFromDb("").map {it.toShowListing() }
                    ))
                    emit(Resource.Loading(false))
                }
            }
        }

    }

    override suspend fun getShowInfo(query: String): Resource<ShowDetail> {
        return try {
            val result = getSingleShowFromDB(query)
            Resource.Success(result.toShowListing())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load show info due to IO error"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't Load show info by Http error"
            )
        }
    }

    override suspend fun getShowListingFromDb(query: String) =
        localDataSource.getShowListingFromDb(query)

    override suspend fun getShowListingFromApi() = remoteDataSource.getShowListingFromApi()

    override suspend fun clearShowListingsFromDb() = localDataSource.clearShowListingsFromDb()

    override suspend fun insertShowListingToDb(showList: List<ShowListingEntity>) =
        localDataSource.insertShowListingToDb(showList)

    override suspend fun getSingleShowFromDB(query: String) =
        localDataSource.getSingleShowFromDB(query)
}