package com.omk.tvmazeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.omk.tvmazeapp.core.navigation.BottomNav
import com.omk.tvmazeapp.core.navigation.NavGraph
import com.omk.tvmazeapp.ui.theme.TVShowsAppTheme
import com.omk.tvmazeapp.presentation.search_shows.SearchShowsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVShowsAppTheme {
                TvShows()
            }
        }
    }
}

@Composable
fun TvShows() {
    val navController = rememberNavController()
    val   showListingViewModel: SearchShowsViewModel = hiltViewModel()

    Scaffold(
        bottomBar = { BottomNav(navController = navController) },
        content = { padding -> Column(modifier = Modifier.padding(padding)){
            NavGraph(navController = navController,
                showListingViewModel=showListingViewModel
            )
        } },
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.fillMaxWidth()
    )
}

