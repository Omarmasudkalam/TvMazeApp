package com.omk.tvmazeapp.domain.repository

import com.omk.tvmazeapp.data.local.entity.ShowListingEntity
import com.omk.tvmazeapp.data.remote.dto.ShowsDto
import com.omk.tvmazeapp.domain.model.ShowDetail
import com.omk.tvmazeapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface ShowRepository {

    suspend fun getShowListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ShowDetail>>>

    suspend fun getShowListingFromDb(
        query: String
    ): List<ShowListingEntity>

    suspend fun getShowListingFromApi(): ShowsDto

    suspend fun clearShowListingsFromDb()

    suspend fun insertShowListingToDb(
        showList: List<ShowListingEntity>
    )
    suspend fun getShowInfo(query: String): Resource<ShowDetail>

    suspend fun getSingleShowFromDB(query: String): ShowListingEntity
}

