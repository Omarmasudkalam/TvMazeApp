package com.omk.tvmazeapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.omk.tvmazeapp.presentation.home.ShowsHomeScreen
import com.omk.tvmazeapp.presentation.settings.SettingScreen
import com.omk.tvmazeapp.presentation.search_shows.ShowListScreen
import com.omk.tvmazeapp.presentation.search_shows.SearchShowListingsScreen
import com.omk.tvmazeapp.presentation.search_shows.SearchShowsViewModel
import com.omk.tvmazeapp.presentation.show_detail.ShowDetailScreen

@Composable
fun NavGraph(navController: NavHostController,
             showListingViewModel: SearchShowsViewModel = hiltViewModel()) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            ShowsHomeScreen(navController)
        }
        composable(BottomNavItem.Search.screen_route) {
            SearchShowListingsScreen(navController, viewModel = showListingViewModel)
        }
        composable(BottomNavItem.Setting.screen_route) {
            SettingScreen()
        }

        composable("$SHOW_DETAIL_SCREEN/{name}",
            arguments = listOf(navArgument("name"){type= NavType.StringType})
        ) {
            ShowDetailScreen( navController=navController)
        }
        composable(route= SHOWLIST_SCREEN){
            ShowListScreen(navController = navController)
        }

    }
}