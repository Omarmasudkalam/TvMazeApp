package com.omk.tvmazeapp.data.remote.apiservice

import com.omk.tvmazeapp.data.remote.dto.ShowsDto
import retrofit2.http.GET

interface TvShowApi {

    @GET(SHOW_END)
    suspend fun getListings(
    ): ShowsDto

    companion object {
        const val BASE_URL = "https://api.tvmaze.com/"
        const val SHOW_END = "shows"
    }

}

