package com.example.watchme.app.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.core.Categories
import com.example.watchme.core.RatedItem
import com.example.watchme.core.Routes
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor
import com.example.watchme.ui.theme.LightBlueColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccountScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController
) {

    val ratedMovies by viewModel.ratedMovies.collectAsState()
    val ratedSeries by viewModel.ratedSeries.collectAsState()
    val ratedEpisodes by viewModel.ratedSeriesEpisodes.collectAsState()

    viewModel.getRatedSeries()
    viewModel.getRatedMovies()
    viewModel.getRatedSeriesEpisodes()

    val rateList = listOf(
        Categories.Movies.title,
        Categories.TvSeries.title,
        Categories.TvEpisodes.title
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
            RatingHeader()
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
                        stringResource(R.string.my_ratings),
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
                Spacer(Modifier.size(0.dp)) // It gives me an extra 16.dp space

                FlowRow(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    when (selectedOption) {
                        Categories.TvSeries.title -> {
                            if (ratedSeries == null) {
                                BodyTextItem(stringResource(R.string.no_results_found))
                            } else {
                                ratedSeries!!.forEach {
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

                        Categories.Movies.title -> {
                            if (ratedMovies == null) {
                                BodyTextItem(stringResource(R.string.no_results_found))
                            } else {
                                ratedMovies!!.forEach {
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

                        Categories.TvEpisodes.title -> {
                            if (ratedEpisodes == null) {
                                BodyTextItem(stringResource(R.string.no_results_found))
                            } else {
                                ratedEpisodes!!.forEach {
                                    RatingEpisodesCardItem(
                                        ratedItem = RatedItem(
                                            id = it.showId,
                                            posterPath = it.stillPath.orEmpty(),
                                            episodeNumber = it.episodeNumber,
                                            seasonNumber = it.seasonNumber
                                        )
                                    ) { seriesId, episodeNumber, seasonNumber ->
                                        navController.navigate(
                                            Routes.EpisodeDetails.createRoute(
                                                seriesId = seriesId,
                                                episodeId = episodeNumber,
                                                seasonNumber = seasonNumber
                                            )
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
}

@Composable
fun RatingDropdownMenu(
    list: List<String>,
    showMenu: Boolean,
    onClick: (String) -> Unit,
    onDismiss: () -> Unit
) {

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(CardContainerColor)
    ) {
        list.forEach {
            DropdownMenuItem(
                text = { BodyTextItem(it) },
                onClick = { onClick(it) },
            )
        }
    }
}

@Composable
fun RatingCardItem(ratedItem: RatedItem, onClick: (Int) -> Unit) {

    val imageUrl = Constants.IMAGE_BASE_URL + ratedItem.posterPath

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(ratedItem.id)
            }, colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.movie_image),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun RatingEpisodesCardItem(ratedItem: RatedItem, onClick: (Int, Int, Int) -> Unit) {

    val imageUrl = Constants.IMAGE_BASE_URL + ratedItem.posterPath

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(120.dp)
            .clickable {
                onClick(ratedItem.id, ratedItem.episodeNumber, ratedItem.seasonNumber)
            }, colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(R.string.movie_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                colors = CardDefaults.cardColors(
                    containerColor = CardContainerColor
                ),
                shape = RectangleShape
            ) {
                BodyTextItem("Season ${ratedItem.seasonNumber} - Episode ${ratedItem.episodeNumber}", modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
private fun RatingHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.background_account),
            contentDescription = "account background",
            contentScale = ContentScale.Crop
        )
        Row(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = LightBlueColor
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp
                ),
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "D",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    SecondTitleTextItem("Damian", hasMaxWidth = false)
                    BodyTextItem("Member since January 2025", color = Color.LightGray)
                }
                BodyTextItem(
                    "Apasionado por la programación, la naturaleza y el deporte",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}