package com.example.watchme.app.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.BackButton
import com.example.watchme.app.ui.BackdropImageItem
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.LoadingDialog
import com.example.watchme.app.ui.RatingSectionWithLists
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.SimpleRatingSection
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleTextItem
import com.example.watchme.core.Categories
import com.example.watchme.core.MediaItem
import com.example.watchme.core.Routes
import com.example.watchme.ui.theme.AppBackground

@Composable
fun EpisodesDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController,
    seriesId: Int,
    episodeNumber: Int,
    seasonNumber: Int
) {

    val episodeDetails by viewModel.episodeDetails.collectAsState()
    val ratedEpisodes by viewModel.ratedSeriesEpisodes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    viewModel.getEpisodesDetailsScreen()

    val seriesName = viewModel.getSeriesName(seriesId)
    var isRated by rememberSaveable {
        mutableStateOf(
            viewModel.isEpisodeRated(
                seriesId = seriesId,
                episodeNumber = episodeNumber,
                seasonNumber = seasonNumber
            )
        )
    }
    var myRate by rememberSaveable {
        mutableFloatStateOf(
            viewModel.getMyEpisodeRate(
                seriesId = seriesId,
                episodeNumber = episodeNumber,
                seasonNumber = seasonNumber
            )
        )
    }

    viewModel.getEpisodeDetailsById(seriesId, seasonNumber, episodeNumber)

    val runtime = episodeDetails?.runtime?.let { viewModel.getRunTimeInHours(it) }

    if (episodeDetails == null) return

    LaunchedEffect(ratedEpisodes) {
        isRated = viewModel.isEpisodeRated(
            seriesId = seriesId,
            episodeNumber = episodeNumber,
            seasonNumber = seasonNumber
        )
        myRate = viewModel.getMyEpisodeRate(
            seriesId = seriesId,
            episodeNumber = episodeNumber,
            seasonNumber = seasonNumber
        )
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        LoadingDialog(isLoading)

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
                BackdropImageItem(episodeDetails?.stillPath.toString())
                BackButton() { navController.popBackStack() }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                episodeDetails?.name?.let { TitleTextItem(it) }
                ThirdTitleTextItem(
                    "${stringResource(R.string.season)} $seasonNumber - ${
                        stringResource(
                            R.string.episode
                        )
                    } ${episodeDetails?.episodeNumber} - $runtime"
                )
                BodyTextItem(
                    seriesName,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        viewModel.changeLoadingState(true)
                        navController.navigate(Routes.SeriesDetails.createRoute(seriesId))
                    })
                Spacer(Modifier.size(0.dp)) // Gives me an extra 16.dp vertical space
                SimpleRatingSection(
                    mediaItem = MediaItem(
                        id = seriesId,
                        title = episodeDetails!!.name,
                        voteAverage = episodeDetails!!.voteAverage ?: 0f,
                        seasonNumber = seasonNumber,
                        episodeNumber = episodeDetails!!.episodeNumber,
                        category = Categories.TvEpisodes
                    ),
                    viewModel = viewModel,
                    isRated = isRated,
                    myRate = myRate,
                    onRatedButtonClicked = { isRated = true },
                    onDeleteRateButtonClicked = { isRated = false },
                )

                if (episodeDetails?.overview.isNullOrEmpty() && episodeDetails?.guestStars?.isEmpty() == true && episodeDetails?.crew?.isEmpty() == true) {
                    BodyTextItem(stringResource(R.string.no_results_found))
                }

                if (episodeDetails?.overview?.isNotEmpty() == true) {
                    SecondTitleTextItem(
                        stringResource(R.string.overview),
                        textAlign = TextAlign.Start
                    )
                    episodeDetails?.overview?.let { BodyTextItem(it) }
                    Spacer(modifier = Modifier.height(0.dp)) // It gives an extra 16.dp space because of verticalArrangement
                }


                if (episodeDetails?.guestStars?.isNotEmpty() == true) {
                    SecondTitleTextItem(stringResource(R.string.guest_stars))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(episodeDetails!!.guestStars) { castItem ->
                            CastCreditsItem(castItem) { personId ->
                                viewModel.changeLoadingState(true)
                                navController.navigate(
                                    Routes.PeopleDetails.createRoute(
                                        personId
                                    )
                                )
                            }
                        }
                    }
                }

                if (episodeDetails?.crew?.isEmpty() == true) return

                SecondTitleTextItem(stringResource(R.string.crew))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    episodeDetails?.let {
                        items(it.crew) { crewItem ->
                            CrewCreditsItem(crewItem) { personId ->
                                viewModel.changeLoadingState(true)
                                navController.navigate(
                                    Routes.PeopleDetails.createRoute(
                                        personId
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