package com.omk.tvmazeapp.presentation.search_shows


sealed class SearchShowsEvent {
    object Refresh : SearchShowsEvent()
    object LoadShows : SearchShowsEvent()
    data class OnSearchQueryChange(val query: String) : SearchShowsEvent()

}
