package com.vsahin.volkytv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.vsahin.volkytv.ui.detail.DetailScreen
import com.vsahin.volkytv.ui.home.HomeScreen
import com.vsahin.volkytv.ui.player.PlayerScreen

const val HOME = "home"
const val DETAIL = "detail"
const val PLAYER = "player"

const val PARAM_ENTRY_ID = "entryId"

@ExperimentalPagerApi
@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME,
    ) {
        composable(HOME) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navigateToDetail = { entryId ->
                    navController.navigate("$DETAIL/$entryId")
                },
                navigateToPlayer = { entryId ->
                    navController.navigate("$PLAYER/$entryId")
                }
            )
        }
        composable("$DETAIL/{$PARAM_ENTRY_ID}") {
            DetailScreen(
                viewModel = hiltViewModel(),
                entryId = it.arguments?.getString(PARAM_ENTRY_ID) ?: "",
                navigateToPlayer = { entryId ->
                    navController.navigate("$PLAYER/$entryId")
                }
            )
        }
        composable("$PLAYER/{$PARAM_ENTRY_ID}") {
            PlayerScreen(
                viewModel = hiltViewModel(),
                entryId = it.arguments?.getString(PARAM_ENTRY_ID) ?: ""
            )
        }
    }
}