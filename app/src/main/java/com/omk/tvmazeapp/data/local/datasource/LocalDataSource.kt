package com.omk.tvmazeapp.data.local.datasource

import com.omk.tvmazeapp.data.local.entity.ShowListingEntity

interface LocalDataSource {

    suspend fun insertShowListingToDb(showList: List<ShowListingEntity>)

    suspend fun clearShowListingsFromDb()

    suspend fun getSingleShowFromDB(query:String): ShowListingEntity

    suspend fun getShowListingFromDb(query: String): List<ShowListingEntity>


}