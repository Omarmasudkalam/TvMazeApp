package com.omk.tvmazeapp.domain.use_case

import com.omk.tvmazeapp.domain.model.ShowDetail
import com.omk.tvmazeapp.domain.repository.ShowRepository
import com.omk.tvmazeapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShowListUseCase @Inject constructor(
    private val repository: ShowRepository
){
    suspend fun getShowList(query: String, fetchFromRemote: Boolean): Flow<Resource<List<ShowDetail>>> {
        return  repository.getShowListings( fetchFromRemote = fetchFromRemote,query=query)
    }
}