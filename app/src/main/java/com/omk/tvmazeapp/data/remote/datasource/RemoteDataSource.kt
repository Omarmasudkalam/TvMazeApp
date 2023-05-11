package com.omk.tvmazeapp.data.remote.datasource

import com.omk.tvmazeapp.data.remote.dto.ShowsDto


interface RemoteDataSource {

    suspend fun getShowListingFromApi(): ShowsDto
}