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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleTextItem
import com.example.watchme.core.Routes
import com.example.watchme.ui.theme.AppBackground

@Composable
fun EpisodesDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController,
    seriesId: Int,
    episodeId: Int,
    seasonNumber: Int
) {

    val episodeDetails by viewModel.episodeDetails.collectAsState()

    viewModel.getEpisodeDetailsById(seriesId, seasonNumber, episodeId)

    val runtime = episodeDetails?.runtime?.let { viewModel.getRunTimeInHours(it) }

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
                episodeDetails?.stillPath?.let { BackdropImageItem(it) }
                BackButton()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){

                episodeDetails?.name?.let { TitleTextItem(it) }
                ThirdTitleTextItem("${stringResource(R.string.season)} $seasonNumber - ${stringResource(R.string.episode)} ${episodeDetails?.episodeNumber} - $runtime")
                SecondTitleTextItem(stringResource(R.string.overview), textAlign = TextAlign.Start)
                Spacer(modifier = Modifier.height(0.dp)) // It gives an extra 16.dp space because of verticalArrangement
                if(episodeDetails?.overview?.isEmpty() == true){
                    BodyTextItem(stringResource(R.string.no_results_found))
                } else{
                    episodeDetails?.overview?.let { BodyTextItem(it) }
                }
                SecondTitleTextItem(stringResource(R.string.guest_stars))
                if(episodeDetails != null){
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(episodeDetails!!.guestStars){ castItem ->
                            CastCreditsItem(castItem) { personId -> navController.navigate(Routes.PeopleDetails.createRoute(personId))  }
                        }
                    }
                }
                SecondTitleTextItem(stringResource(R.string.crew))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    episodeDetails?.let {
                        items(it.crew){crewItem ->
                            CrewCreditsItem(crewItem) { personId -> navController.navigate(Routes.PeopleDetails.createRoute(personId))  }
                        }
                    }
                }
            }

        }
    }
}