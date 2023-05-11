package com.omk.tvmazeapp.presentation.search_shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omk.tvmazeapp.domain.use_case.GetShowListUseCase
import com.omk.tvmazeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchShowsViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val getShowListUseCase: GetShowListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchShowsState())

    val uiState: StateFlow<SearchShowsState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchShowsEvent) {
        when (event) {
            is SearchShowsEvent.Refresh -> {
                getShowListings(fetchFromRemote = true)
            }
            is SearchShowsEvent.OnSearchQueryChange -> {
                _uiState.update { it.copy(searchQuery = event.query) }

                searchJob = viewModelScope.launch {
                    delay(500L)
                    getShowListings()
                }
            }
            is SearchShowsEvent.LoadShows -> {
                getShowListings()
            }
        }
    }

    private fun getShowListings(
        query: String = _uiState.value.searchQuery.lowercase(),

        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch(dispatcher) {
            getShowListUseCase.getShowList(fetchFromRemote=fetchFromRemote, query=query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                _uiState.update { it.copy(shows=listings) }
                            }
                            _uiState.value = _uiState.value.copy()
                        }
                        is Resource.Error -> {
                            _uiState.update { it.copy(error = "Error message") }
                        }
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = result.isLoading) }
                        }
                    }
                }
        }
    }
}