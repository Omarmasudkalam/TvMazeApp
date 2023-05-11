package com.omk.tvmazeapp.data.local.datasource

import com.omk.tvmazeapp.data.local.dao.TvShowDao
import com.omk.tvmazeapp.data.local.entity.ShowListingEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val showDao : TvShowDao
): LocalDataSource {

    override suspend fun insertShowListingToDb(showList: List<ShowListingEntity>)=
        showDao.insertShowListings(showList)

    override suspend fun clearShowListingsFromDb() =
        showDao.clearShowListings()

    override suspend fun getSingleShowFromDB(query: String) =
        showDao.getSingleShowFromDB(query)

    override suspend fun getShowListingFromDb(query: String): List<ShowListingEntity> =
        showDao.searchShowListing(query)
}