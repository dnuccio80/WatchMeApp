package com.example.watchme.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.BackButton
import com.example.watchme.app.ui.BackdropImageItem
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.TitleTextItem
import com.example.watchme.app.ui.dataClasses.CollectionDetailsDataClass
import com.example.watchme.core.Routes
import com.example.watchme.ui.theme.AppBackground

@Composable
fun CollectionDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController,
    collectionId: Int
) {

    viewModel.getCollectionDetailsById(collectionId)

    val collectionDetails by viewModel.collectionDetails.collectAsState()

    if(collectionDetails == null) return

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                collectionDetails?.backdropPath?.let { BackdropImageItem(it) }
                BackButton()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(Modifier.size(0.dp)) // It gives an extra 16.dp spacer at the top
                collectionDetails?.name?.let { TitleTextItem(it) }
                collectionDetails?.overview?.let { BodyTextItem(it) }
                Spacer(Modifier.size(0.dp)) // It gives an extra 16.dp spacer at the top
                SecondTitleTextItem(stringResource(R.string.full_collection), textAlign = TextAlign.Center)
                if(collectionDetails!!.parts.isNotEmpty()) {
                    MoviesRecommendationsSection(collectionDetails!!.parts) { movieId ->
                        navController.navigate(Routes.MovieDetails.createRoute(movieId))
                    }
                }
            }
        }

    }
}