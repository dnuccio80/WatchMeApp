package com.example.watchme.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.ImageListItem
import com.example.watchme.app.ui.LoadingDialog
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.SectionSelectionItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
import com.example.watchme.app.ui.dataClasses.PeopleMovieInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.PeopleSeriesInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.core.Routes
import com.example.watchme.core.Sections
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor

@Composable
fun PeopleDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController,
    personId: Int
) {

    val sectionInit = stringResource(Sections.Biography.title)

    val scrollState = rememberScrollState()
    var sectionSelected by rememberSaveable { mutableStateOf(sectionInit) }

    val peopleDetails by viewModel.peopleDetails.collectAsState()
    val movieInterpretations by viewModel.peopleMovieInterpretations.collectAsState()
    val seriesInterpretations by viewModel.peopleSeriesInterpretations.collectAsState()
    val personMedia by viewModel.peopleMediaImages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    viewModel.getPeopleScreen(personId)

    val sectionList = listOf(
        stringResource(Sections.Biography.title),
        stringResource(Sections.Credits.title),
        stringResource(Sections.Media.title),
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LoadingDialog(isLoading)

            PeopleHeader(peopleDetails)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SectionSelectionItem(sectionList) { newSectionSelected ->
                    sectionSelected = newSectionSelected
                }
                when (sectionSelected.lowercase()) {
                    stringResource(Sections.Biography.title).lowercase() -> BiographySection(peopleDetails)
                    stringResource(Sections.Credits.title).lowercase()  -> PeopleCreditsSection(
                        movieInterpretations,
                        seriesInterpretations,
                        onSeriesClicked = { seriesId ->
                            viewModel.changeLoadingState(true)
                            navController.navigate(
                                Routes.SeriesDetails.createRoute(
                                    seriesId
                                )
                            )
                        },
                        onMovieClicked = { movieId ->
                            viewModel.changeLoadingState(true)
                            navController.navigate(Routes.MovieDetails.createRoute(movieId))
                        }
                    )
                    stringResource(Sections.Media.title).lowercase() -> PeopleMediaSection(personMedia)
                }
            }
        }
    }
}

@Composable
fun PeopleCreditsSection(
    movieInterpretations: PeopleMovieInterpretationDataClass?,
    seriesInterpretations: PeopleSeriesInterpretationDataClass?,
    onSeriesClicked: (Int) -> Unit,
    onMovieClicked: (Int) -> Unit
) {

    if (movieInterpretations == null && seriesInterpretations == null) {
        BodyTextItem(stringResource(R.string.no_results_found))
        return
    }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (movieInterpretations != null) {
            if (movieInterpretations.cast.isNotEmpty() || movieInterpretations.crew.isNotEmpty()) {
                SecondTitleTextItem(stringResource(R.string.movies), textAlign = TextAlign.Center)
                MovieInterpretations(movieInterpretations) { movieId -> onMovieClicked(movieId) }
            }
        }
        if (seriesInterpretations != null) {
            if (seriesInterpretations.cast.isNotEmpty() || seriesInterpretations.crew.isNotEmpty()) {
                SecondTitleTextItem(
                    stringResource(R.string.tv_series),
                    textAlign = TextAlign.Center
                )
                SeriesInterpretations(seriesInterpretations) { seriesId -> onSeriesClicked(seriesId) }
            }
        }
    }

}

@Composable
fun SeriesInterpretations(
    seriesInterpretations: PeopleSeriesInterpretationDataClass?,
    onSeriesClicked: (Int) -> Unit
) {

    if (seriesInterpretations == null) return

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (seriesInterpretations.cast.isNotEmpty()) ThirdTitleTextItem(
            stringResource(R.string.cast),
            textAlign = TextAlign.Start
        )
        LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(seriesInterpretations.cast) {
                SeriesInterpretationsCardItem(it) { seriesId -> onSeriesClicked(seriesId) }
            }
        }
        if (seriesInterpretations.crew.isNotEmpty()) ThirdTitleTextItem(
            stringResource(R.string.crew),
            textAlign = TextAlign.Start
        )
        LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(seriesInterpretations.crew) {
                SeriesInterpretationsCardItem(it) { seriesId -> onSeriesClicked(seriesId) }
            }
        }
    }
}

@Composable
fun MovieInterpretations(
    movieInterpretations: PeopleMovieInterpretationDataClass?,
    onMovieClicked: (Int) -> Unit
) {

    if (movieInterpretations == null) return


    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (movieInterpretations.cast.isNotEmpty()) ThirdTitleTextItem(
            stringResource(R.string.cast),
            textAlign = TextAlign.Start
        )
        LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(movieInterpretations.cast) {
                MoviesInterpretationsCardItem(it) { movieId -> onMovieClicked(movieId) }
            }
        }
        if (movieInterpretations.crew.isNotEmpty()) ThirdTitleTextItem(
            stringResource(R.string.crew),
            textAlign = TextAlign.Start
        )
        LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(movieInterpretations.crew) {
                MoviesInterpretationsCardItem(it) { movieId -> onMovieClicked(movieId) }
            }
        }
    }
}

@Composable
fun SeriesInterpretationsCardItem(seriesData: SeriesDataClass, onSeriesClicked: (Int) -> Unit) {

    val url = Constants.IMAGE_BASE_URL + seriesData.posterPath

    Card(
        modifier = Modifier
            .width(190.dp)
            .clickable { onSeriesClicked(seriesData.id) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardContainerColor
        ),
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = url,
                contentDescription = stringResource(R.string.image_cast),
                modifier = Modifier
                    .fillMaxSize()
                    .height(max(200.dp, 250.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_image),
                error = painterResource(R.drawable.film_not_found)
            )
            Column(
                Modifier
                    .background(CardContainerColor)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThirdTitleTextItem(seriesData.name, TextAlign.Center)
            }
        }
    }
}

@Composable
fun MoviesInterpretationsCardItem(movieData: MovieDataClass, onMovieClicked: (Int) -> Unit) {

    val url = Constants.IMAGE_BASE_URL + movieData.posterPath

    Card(
        modifier = Modifier
            .width(190.dp)
            .clickable { onMovieClicked(movieData.id) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardContainerColor
        ),
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = url,
                contentDescription = stringResource(R.string.image_cast),
                modifier = Modifier
                    .fillMaxSize()
                    .height(max(200.dp, 250.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_image),
                error = painterResource(R.drawable.film_not_found)
            )
            Column(
                Modifier
                    .background(CardContainerColor)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThirdTitleTextItem(movieData.title, TextAlign.Center)
            }
        }
    }
}

@Composable
fun PeopleMediaSection(personMedia: List<BackdropImageDataClass>?) {

    if (personMedia.isNullOrEmpty()) {
        BodyTextItem(stringResource(R.string.no_results_found))
        return
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        ImageListItem(personMedia)
    }
}

@Composable
fun PeopleHeader(peopleDetails: PeopleDetailsDataClass?) {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier.weight(1f),
            elevation = CardDefaults.cardElevation(16.dp),
            border = BorderStroke(4.dp, CardContainerColor)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(max(300.dp, 350.dp)),
                model = Constants.IMAGE_BASE_URL + peopleDetails?.profilePath,
                contentDescription = "people image",
                error = painterResource(R.drawable.unknown_male),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            peopleDetails?.name?.let {
                SecondTitleTextItem(it, textAlign = TextAlign.Start)
            }
            peopleDetails?.knownForDepartment?.let {
                TitleSubtitleItem(stringResource(R.string.known_for_department), it)
            }
            peopleDetails?.birthday?.let {
                TitleSubtitleItem(stringResource(R.string.birthday), it)
            }
            peopleDetails?.deathDay?.let {
                TitleSubtitleItem(stringResource(R.string.death_day), it)
            }

            peopleDetails?.placeOfBirth?.let {
                TitleSubtitleItem(stringResource(R.string.place_of_birth), it)
            }
        }
    }
}

@Composable
fun BiographySection(peopleDetails: PeopleDetailsDataClass?) {

    if (peopleDetails?.biography == null) return

    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SecondTitleTextItem(stringResource(R.string.biography))
        if (peopleDetails.biography.isNotEmpty() && peopleDetails.biography.isNotBlank()) {
            BodyTextItem(peopleDetails.biography)
        } else {
            BodyTextItem(stringResource(R.string.no_results_found))
        }
    }
}