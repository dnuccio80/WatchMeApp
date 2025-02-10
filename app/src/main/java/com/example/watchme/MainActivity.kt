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
import com.example.watchme.app.ui.screens.HomeScreen
import com.example.watchme.app.ui.screens.MovieDetailsScreen
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
                    NavHost(navController, startDestination = Routes.Home.route) {
                        composable(Routes.Home.route) { HomeScreen(innerPadding, viewModel, navController) }
                        composable(
                            "detailsMovie/{movieId}",
                            arguments = listOf(navArgument("movieId") {
                                type = NavType.IntType
                            })) {backStackEntry -> MovieDetailsScreen(innerPadding, viewModel, backStackEntry.arguments?.getInt("movieId") ?: 0) }
                    }
//                    MovieDetailsScreen(innerPadding, viewModel, 939243)
                }
            }
        }
    }
}

