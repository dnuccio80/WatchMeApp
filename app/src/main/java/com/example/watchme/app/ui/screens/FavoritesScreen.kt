package com.example.watchme.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.AccountHeader
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.core.Categories
import com.example.watchme.core.RatedItem
import com.example.watchme.core.Routes
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FavoritesScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController
) {


    val favoritesMovies by viewModel.favoritesMovies.collectAsState()
    val favoritesSeries by viewModel.favoritesSeries.collectAsState()

    val rateList = listOf(
        stringResource(Categories.Movies.title),
        stringResource(Categories.TvSeries.title),
    )

    var selectedOption by rememberSaveable { mutableStateOf(rateList[0]) }
    var showDropdownMenu by rememberSaveable { mutableStateOf(false) }

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
            AccountHeader(viewModel) { navController.popBackStack() }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SecondTitleTextItem(
                        stringResource(R.string.my_favorites),
                        textAlign = TextAlign.Start,
                        hasMaxWidth = false
                    )
                    Column {
                        Card(
                            shape = RoundedCornerShape(4.dp),
                            elevation = CardDefaults.cardElevation(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = CardContainerColor
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    showDropdownMenu = true
                                }) {
                                BodyTextItem(
                                    selectedOption,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                                )
                                Icon(
                                    Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "icon menu",
                                    tint = Color.White
                                )
                            }

                        }
                        RatingDropdownMenu(rateList, showDropdownMenu,
                            onClick = {
                                selectedOption = it
                                showDropdownMenu = false
                            },
                            onDismiss = { showDropdownMenu = false })
                    }
                }

                FlowRow(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    when (selectedOption) {
                        stringResource(Categories.TvSeries.title) -> {
                            if (favoritesSeries == null) {
                                BodyTextItem(stringResource(R.string.no_results_found))
                            } else {
                                favoritesSeries!!.forEach {
                                    RatingCardItem(
                                        ratedItem = RatedItem(it.id, it.posterPath.orEmpty()),
                                        onClick = { seriesId ->
                                            navController.navigate(
                                                Routes.SeriesDetails.createRoute(
                                                    seriesId
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        stringResource(Categories.Movies.title) -> {
                            if (favoritesMovies == null) {
                                BodyTextItem(stringResource(R.string.no_results_found))
                            } else {
                                favoritesMovies!!.forEach {
                                    RatingCardItem(
                                        ratedItem = RatedItem(
                                            id = it.id,
                                            posterPath = it.posterPath.orEmpty()
                                        ),
                                        onClick = { moviesId ->
                                            navController.navigate(
                                                Routes.MovieDetails.createRoute(
                                                    moviesId
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}