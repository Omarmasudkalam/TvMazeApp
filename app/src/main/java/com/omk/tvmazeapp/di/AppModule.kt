package com.omk.tvmazeapp.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.omk.tvmazeapp.data.local.dao.TvShowDao
import com.omk.tvmazeapp.data.local.datasource.LocalDataSource
import com.omk.tvmazeapp.data.local.datasource.LocalDataSourceImpl
import com.omk.tvmazeapp.data.local.db.TvShowDatabase
import com.omk.tvmazeapp.data.remote.apiservice.TvShowApi
import com.omk.tvmazeapp.data.remote.datasource.RemoteDataSource
import com.omk.tvmazeapp.data.remote.datasource.RemoteDataSourceImpl
import com.omk.tvmazeapp.data.repository.ShowRepositoryImpl
import com.omk.tvmazeapp.domain.repository.ShowRepository
import com.omk.tvmazeapp.domain.use_case.GetShowListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)

class AppModule {
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    @Provides
    fun providesRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(TvShowApi.BASE_URL)
        .build()

    @Provides
    fun providesShowsApi(retrofit: Retrofit): TvShowApi = retrofit.create(TvShowApi::class.java)

    @Provides
    fun providesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ShowRepository {
        return ShowRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideGetShowListUseCase(repository: ShowRepository): GetShowListUseCase {
        return GetShowListUseCase(repository)
    }

    @Provides
    fun providesDatabase(app: Application): TvShowDatabase {
        return Room.databaseBuilder(
            app,
            TvShowDatabase::class.java,
            "showdb.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideShowDao(database: TvShowDatabase) = database.showDao()

    @Provides
    fun getRemoteDS(showApi: TvShowApi): RemoteDataSource {
        return RemoteDataSourceImpl(showApi)
    }
    @Provides
    fun getLocalDS(showDao: TvShowDao
    ): LocalDataSource {
        return LocalDataSourceImpl(showDao)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}