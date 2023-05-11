package com.omk.tvmazeapp.presentation.home

import com.omk.tvmazeapp.data.remote.dto.Image
import com.omk.tvmazeapp.data.remote.dto.Rating
import com.omk.tvmazeapp.domain.model.ShowDetail

data class ShowsState(
    val shows: List<ShowDetail> = emptyList(),
    val isLoading:Boolean =false,
    val isRefreshing:Boolean=false,
    val searchQuery:String="",
    val id:Int=0,
    val show: ShowDetail = ShowDetail(0,"","",0,image= Image(),"","","", rating = Rating()),
    val error:String=""
    )
