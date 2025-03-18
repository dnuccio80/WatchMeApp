package com.example.watchme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.watchme.app.ui.BottomBar
import com.example.watchme.app.ui.TopBar
import com.example.watchme.app.ui.screens.AccountScreen
import com.example.watchme.app.ui.screens.CollectionDetailsScreen
import com.example.watchme.app.ui.screens.EpisodesDetailsScreen
import com.example.watchme.app.ui.screens.FavoritesScreen
import com.example.watchme.app.ui.screens.HomeScreen
import com.example.watchme.app.ui.screens.ListDetailsScreen
import com.example.watchme.app.ui.screens.ListsScreen
import com.example.watchme.app.ui.screens.MovieDetailsScreen
import com.example.watchme.app.ui.screens.PeopleDetailsScreen
import com.example.watchme.app.ui.screens.RatingScreen
import com.example.watchme.app.ui.screens.SearchScreen
import com.example.watchme.app.ui.screens.SeriesDetailsScreen
import com.example.watchme.app.ui.screens.WatchListScreen
import com.example.watchme.core.Routes
import com.example.watchme.ui.theme.WatchMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WatchMeTheme {

                val navController = rememberNavController()
                val startDestination = Routes.Home.route
                var destination by rememberSaveable { mutableStateOf(startDestination) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomBar(destination) { newDestination ->
                            destination = newDestination
                            navController.navigate(newDestination)
                        }
                    }
                ) { innerPadding ->
//                    NavHost(navController,
//                        startDestination = startDestination,
//                        enterTransition = { slideInHorizontally { it } },
//                        exitTransition = { slideOutHorizontally { it } }) {
//                        composable(Routes.Home.route) {
//                            HomeScreen(
//                                innerPadding,
//                                viewModel,
//                                navController
//                            )
//                        }
//                        composable(
//                            Routes.MovieDetails.route,
//                            arguments = listOf(navArgument("movieId") {
//                                type = NavType.IntType
//                            }),
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }
//                        ) { backStackEntry ->
//                            MovieDetailsScreen(
//                                innerPadding,
//                                viewModel,
//                                navController,
//                                backStackEntry.arguments?.getInt("movieId") ?: 0
//                            )
//                        }
//                        composable(
//                            Routes.SeriesDetails.route,
//                            arguments = listOf(navArgument("seriesId") {
//                                type = NavType.IntType
//                            }),
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }
//                        ) { backStackEntry ->
//                            SeriesDetailsScreen(
//                                innerPadding,
//                                viewModel,
//                                navController,
//                                backStackEntry.arguments?.getInt("seriesId") ?: 0
//                            )
//                        }
//
//                        composable(
//                            Routes.PeopleDetails.route,
//                            arguments = listOf(navArgument("personId") {
//                                type = NavType.IntType
//                            }),
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }
//                        ) { backStackEntry ->
//                            PeopleDetailsScreen(
//                                innerPadding,
//                                viewModel,
//                                navController,
//                                backStackEntry.arguments?.getInt("personId") ?: 0
//                            )
//                        }
//
//                        composable(
//                            Routes.EpisodeDetails.route,
//                            arguments = listOf(
//                                navArgument("seriesId") { type = NavType.IntType },
//                                navArgument("episodeId") { type = NavType.IntType },
//                                navArgument("seasonNumber") { type = NavType.IntType }),
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }
//                        ) { backStackEntry ->
//                            EpisodesDetailsScreen(
//                                innerPadding,
//                                viewModel,
//                                navController,
//                                backStackEntry.arguments?.getInt("seriesId") ?: 0,
//                                backStackEntry.arguments?.getInt("episodeId") ?: 0,
//                                backStackEntry.arguments?.getInt("seasonNumber") ?: 0
//                            )
//                        }
//
//                        composable(
//                            Routes.CollectionDetails.route,
//                            arguments = listOf(navArgument("collectionId") {
//                                type = NavType.IntType
//                            }),
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }
//                        ) { backStackEntry ->
//                            CollectionDetailsScreen(
//                                innerPadding,
//                                viewModel,
//                                navController,
//                                backStackEntry.arguments?.getInt("collectionId") ?: 0
//                            )
//                        }
//                        composable(
//                            Routes.Search.route,
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }
//                        ) {
//                            SearchScreen(innerPadding, viewModel, navController)
//                        }
//                        composable(Routes.Profile.route,
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }) {
//                            AccountScreen(
//                                innerPadding,
//                                viewModel,
//                                navController
//                            )
//                        }
//                        composable(Routes.Ratings.route,
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }) {
//                            RatingScreen(
//                                innerPadding,
//                                viewModel,
//                                navController
//                            )
//                        }
//                        composable(Routes.Lists.route,
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }) {
//                            ListsScreen(
//                                innerPadding,
//                                viewModel,
//                                navController
//                            )
//                        }
//                        composable(Routes.WatchList.route,
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }) {
//                            WatchListScreen(
//                                innerPadding,
//                                viewModel,
//                                navController
//                            )
//                        }
//                        composable(
//                            Routes.ListDetails.route,
//                            arguments = listOf(navArgument("listId") {
//                                type = NavType.IntType
//                            }),
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }
//                        ) {
//                            ListDetailsScreen(
//                                innerPadding,
//                                viewModel,
//                                it.arguments?.getInt("listId") ?: 0,
//                                navController
//                            )
//                        }
//                        composable(Routes.Favorites.route,
//                            enterTransition = { slideInHorizontally { it } },
//                            exitTransition = { slideOutHorizontally { it } }) {
//                            FavoritesScreen(
//                                innerPadding,
//                                viewModel,
//                                navController
//                            )
//                        }
//                    }
                    MovieDetailsScreen(innerPadding, viewModel, navController, 15)

                }
            }
        }
    }
}

