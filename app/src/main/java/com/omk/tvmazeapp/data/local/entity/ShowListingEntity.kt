package com.omk.tvmazeapp.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omk.tvmazeapp.data.remote.dto.Image
import com.omk.tvmazeapp.data.remote.dto.Rating

@Entity(tableName = "show_listing_table")
data class ShowListingEntity(
    val name:String,
    val type:String,
    val runtime: Int,
    val image : Image,
    val premiered: String,
    val status: String,
    val summary: String,
    val rating: Rating,
    @PrimaryKey val id: Int? = null
)
