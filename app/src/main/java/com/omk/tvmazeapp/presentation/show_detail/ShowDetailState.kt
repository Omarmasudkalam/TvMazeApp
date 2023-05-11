package com.omk.tvmazeapp.presentation.show_detail

import com.omk.tvmazeapp.domain.model.ShowDetail

data class ShowDetailState(
    val show: ShowDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val id:Int=0,
)