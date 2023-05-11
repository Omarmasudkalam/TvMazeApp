package com.omk.tvmazeapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omk.tvmazeapp.data.local.dao.TvShowDao
import com.omk.tvmazeapp.data.local.entity.ShowListingEntity
import com.omk.tvmazeapp.data.local.util.Converter

@Database(
    entities = [ShowListingEntity::class],
    version = 12,
    exportSchema = false
)

@TypeConverters(Converter::class)
abstract class TvShowDatabase:RoomDatabase() {
    abstract fun showDao(): TvShowDao
}