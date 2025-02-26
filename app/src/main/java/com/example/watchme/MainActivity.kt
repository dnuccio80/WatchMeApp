package com.example.watchme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.watchme.app.ui.BottomBar
import com.example.watchme.app.ui.TopBar
import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
import com.example.watchme.app.ui.screens.HomeScreen
import com.example.watchme.app.ui.screens.MovieDetailsScreen
import com.example.watchme.app.ui.screens.PeopleDetailsScreen
import com.example.watchme.app.ui.screens.SeriesDetailsScreen
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

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomBar()
                    }
                ) { innerPadding ->
//                    NavHost(navController, startDestination = Routes.Home.route) {
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
//                            })
//                        ) { backStackEntry ->
//                            MovieDetailsScreen(
//                                innerPadding,
//                                viewModel,
//                                navController,
//                                backStackEntry.arguments?.getInt("movieId") ?: 0
//                            )
//                        }
//                        composable(Routes.SeriesDetails.route,
//                            arguments = listOf(navArgument(("seriesId")) {
//                                type = NavType.IntType
//                            })
//                        ) { backStackEntry ->
//                            SeriesDetailsScreen(
//                                innerPadding,
//                                viewModel,
//                                navController,
//                                backStackEntry.arguments?.getInt("seriesId") ?: 0
//                            )
//                        }
//                    }
                    PeopleDetailsScreen(
                        innerPadding = innerPadding,
                        viewModel = viewModel,
                        peopleId = 1
                    )
                }
            }
        }
    }
}

