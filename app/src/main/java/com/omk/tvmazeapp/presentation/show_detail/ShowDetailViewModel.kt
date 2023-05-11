package com.omk.tvmazeapp.presentation.show_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omk.tvmazeapp.domain.repository.ShowRepository
import com.omk.tvmazeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ShowRepository,
    dispatcher: CoroutineDispatcher
) : ViewModel() {
    var state by mutableStateOf(ShowDetailState())

    init {
        viewModelScope.launch(dispatcher) {
            val q = savedStateHandle.get<String>("name") ?: return@launch
            state = state.copy(isLoading = true)

            when (val result = repository.getShowInfo(q)) {
                is Resource.Success -> {
                    state = state.copy(
                        show = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        show = null,
                        error = result.message ?: "An Error Occurred",
                        isLoading = false
                    )
                }
                else -> {
                    state = state.copy(
                        show = null,
                        error = null,
                        isLoading = true
                    )
                }
            }
        }
    }
}