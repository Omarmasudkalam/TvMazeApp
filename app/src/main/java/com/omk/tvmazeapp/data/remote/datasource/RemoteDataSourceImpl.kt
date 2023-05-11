package com.omk.tvmazeapp.data.remote.datasource

import com.omk.tvmazeapp.data.remote.apiservice.TvShowApi
import com.omk.tvmazeapp.data.remote.dto.ShowsDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: TvShowApi
    ): RemoteDataSource
{
    override suspend fun getShowListingFromApi(): ShowsDto =api.getListings()
}