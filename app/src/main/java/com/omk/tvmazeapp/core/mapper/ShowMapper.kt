package com.omk.tvmazeapp.core.mapper

import com.omk.tvmazeapp.data.local.entity.ShowListingEntity
import com.omk.tvmazeapp.data.remote.dto.ShowDetailDto
import com.omk.tvmazeapp.domain.model.ShowDetail


fun ShowListingEntity.toShowListing(): ShowDetail {
    return ShowDetail(
        id = id!!,
        name = name,
        type = type,
        runtime = runtime,
        image=image,
        status=status,
        summary =summary,
        premiered = premiered,
        rating = rating
    )
}

fun ShowDetail.toShowListingEntity(): ShowListingEntity {
    return ShowListingEntity(
        id=id,
        name = name,
        type = type,
        runtime = runtime,
        image= image,
        status=status,
        summary =summary,
        premiered = premiered,
        rating = rating
    )
}

fun ShowDetailDto.toShowListing(): ShowDetail {
    return ShowDetail(
        id = id,
        name = name,
        type = type,
        premiered= premiered,
        status = status,
        runtime = runtime ?:0,
        summary = summary,
        image = image,
        rating = rating
    )
}
