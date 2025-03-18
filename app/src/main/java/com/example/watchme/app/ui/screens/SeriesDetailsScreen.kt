package com.example.watchme.app.ui.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.BackButton
import com.example.watchme.app.ui.BackdropImageItem
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.CreditsSection
import com.example.watchme.app.ui.HeaderInfo
import com.example.watchme.app.ui.ImageListItem
import com.example.watchme.app.ui.ProvidersSection
import com.example.watchme.app.ui.RatingSectionWithLists
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.SectionSelectionItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.app.ui.TitleSubtitleItemWithNullability
import com.example.watchme.app.ui.VideosSection
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.SeasonDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
import com.example.watchme.core.Categories
import com.example.watchme.core.MediaItem
import com.example.watchme.core.Routes
import com.example.watchme.core.Sections
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor

@Composable
fun SeriesDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController,
    seriesId: Int
) {


    val seriesDetails by viewModel.seriesDetails.collectAsState()
    val seasonDetails by viewModel.seasonsDetails.collectAsState()
    val seriesRecommendations by viewModel.seriesRecommendations.collectAsState()
    val seriesImageList by viewModel.seriesImageList.collectAsState()
    val seriesVideosList by viewModel.seriesVideos.collectAsState()
    val seriesCredits by viewModel.seriesCredits.collectAsState()
    val addFavoritesRequest by viewModel.addFavoriteRequest.collectAsState()
    val watchlistRequest by viewModel.watchListRequest.collectAsState()
    val ratedSeries by viewModel.ratedSeries.collectAsState()
    val seriesProviders by viewModel.seriesProviders.collectAsState()


    val sectionInit = stringResource(Sections.Episodes.title)

    val verticalScrollState = rememberScrollState()
    var seasonSelected by rememberSaveable { mutableIntStateOf(1) }
    var sectionSelected by rememberSaveable { mutableStateOf(sectionInit) }

    var isFavorite by rememberSaveable { mutableStateOf(viewModel.seriesIsFavorite(seriesId)) }
    var isInWatchlist by rememberSaveable { mutableStateOf(viewModel.seriesIsInWatchlist(seriesId)) }
    var isRated by rememberSaveable { mutableStateOf(viewModel.isSeriesRated(seriesId)) }
    var myRate by rememberSaveable { mutableFloatStateOf(viewModel.getMySeriesRate(seriesId)) }
    var regionProvider by rememberSaveable { mutableStateOf(viewModel.getSeriesProvidersByRegion()) }


    viewModel.getSeriesDetailsById(seriesId)
    viewModel.getSeasonDetailsById(seriesId, seasonSelected)
    viewModel.getSeriesRecommendationsById(seriesId)
    viewModel.getSeriesImageListById(seriesId)
    viewModel.getSeriesVideosListById(seriesId)
    viewModel.getSeriesCreditsById(seriesId)
    viewModel.getSeriesProvidersBySeriesId(seriesId)

    val context = LocalContext.current

    LaunchedEffect(seriesProviders) {
        regionProvider = viewModel.getSeriesProvidersByRegion()
    }

    LaunchedEffect(ratedSeries) {
        isRated = viewModel.isSeriesRated(seriesId)
        myRate = viewModel.getMySeriesRate(seriesId)
    }

    LaunchedEffect(addFavoritesRequest) {
        if (addFavoritesRequest != null && addFavoritesRequest?.success == true) {
            Toast.makeText(
                context,
                if (isFavorite) context.getString(R.string.series_added_favorites) else context.getString(
                    R.string.series_removed_favorites
                ),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearFavoriteRequest()
            viewModel.updateFavoritesSeries()
        }
    }

    LaunchedEffect(watchlistRequest) {
        if (watchlistRequest != null && watchlistRequest?.success == true) {
            Toast.makeText(
                context,
                if (isInWatchlist) context.getString(R.string.series_added_watchlist) else context.getString(
                    R.string.series_removed_watchlist
                ),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearWatchlistRequest()
            viewModel.getWatchlistSeries()
        }
    }

    val sectionList = listOf(
        stringResource(Sections.Episodes.title),
        stringResource(Sections.Suggested.title),
        stringResource(Sections.Details.title),
        stringResource(Sections.Media.title),
        stringResource(Sections.Credits.title),
        stringResource(Sections.Watch.title),
    )

    Box(
        Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState)
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                BackdropImageItem(seriesDetails?.backdropPath.toString())
                BackButton() { navController.popBackStack() }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeaderInfo(
                    seriesDetails?.genres?.map { it.name }.orEmpty(),
                    "${seriesDetails?.numberOfSeasons} ${stringResource(R.string.seasons)}",
                    Modifier.align(Alignment.CenterHorizontally)
                )
                seriesDetails?.name?.let { SecondTitleTextItem(it) }
                if (seriesDetails != null) {
                    RatingSectionWithLists(
                        MediaItem(
                            id = seriesId,
                            title = seriesDetails!!.name,
                            voteAverage = seriesDetails!!.voteAverage ?: 0f,
                            category = Categories.TvSeries
                        ),
                        viewModel = viewModel,
                        isRated = isRated,
                        myRate = myRate,
                        addedToFavorites = isFavorite,
                        addedToWatchLater = isInWatchlist,
                        onFavoriteButtonClicked = {
                            isFavorite = !isFavorite
                            viewModel.onAddFavorite(
                                mediaId = seriesId,
                                mediaType = Categories.TvSeries.mediaType,
                                favorite = isFavorite
                            )
                        },
                        onWatchlistButtonClicked = {
                            isInWatchlist = !isInWatchlist
                            viewModel.onAddToWatchlist(
                                mediaId = seriesId,
                                mediaType = Categories.TvSeries.mediaType,
                                watchList = isInWatchlist
                            )
                        },
                        onRatedButtonClicked = { isRated = true },
                        onDeleteRateButtonClicked = { isRated = false }
                    )
                }
                SeriesOverviewSection(seriesDetails)
                Spacer(Modifier.size(16.dp))
                SectionSelectionItem(sectionList) { newSectionSelected ->
                    sectionSelected = newSectionSelected
                }
                when (sectionSelected.lowercase()) {
                    stringResource(Sections.Episodes.title).lowercase() -> seriesDetails?.seasons?.let {
                        EpisodesSection(
                            seasonDetails, it, seasonSelected,
                            onValueChange = { seasonNumber -> seasonSelected = seasonNumber },
                            onEpisodeClicked = { episodeId, seasonNumber ->
                                navController.navigate(
                                    Routes.EpisodeDetails.createRoute(
                                        seriesId,
                                        episodeId,
                                        seasonNumber
                                    )
                                )
                            }
                        )
                    }

                    stringResource(Sections.Suggested.title).lowercase()-> SeriesRecommendationsSection(seriesRecommendations) {
                        navController.navigate(
                            Routes.SeriesDetails.createRoute(it)
                        )
                    }

                    stringResource(Sections.Details.title).lowercase() -> SeriesDetailsSection(seriesDetails)
                    stringResource(Sections.Media.title).lowercase() -> MediaSection(seriesImageList, seriesVideosList)
                    stringResource(Sections.Credits.title).lowercase() -> CreditsSection(seriesCredits) { personId ->
                        navController.navigate(
                            Routes.PeopleDetails.createRoute(personId)
                        )
                    }
                    stringResource(Sections.Watch.title).lowercase() -> ProvidersSection(title = seriesDetails?.name.toString(), regionProvider)
                }
            }
        }
    }
}

@Composable
fun EpisodesSection(
    seasonDetails: List<EpisodeDetailsDataClass>?,
    seasons: List<SeasonDataClass>,
    seasonSelected: Int,
    onValueChange: (Int) -> Unit,
    onEpisodeClicked: (Int, Int) -> Unit
) {

    val seasonName: String = seasons.find {
        it.seasonNumber == seasonSelected
    }?.name.toString()

    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = { showDialog = true },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = CardContainerColor
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodyTextItem(seasonName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = "arrown down icon",
                    tint = Color.White
                )
            }
        }
        seasonDetails?.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onEpisodeClicked(it.episodeNumber, it.seasonNumber)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = CardContainerColor
                )
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = Constants.IMAGE_BASE_URL + it.imagePath,
                            contentDescription = stringResource(R.string.episode_image),
                            modifier = Modifier.size(140.dp),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.film_not_found)
                        )
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            ThirdTitleTextItem(
                                it.name,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            BodyTextItem(
                                it.overview,
                                textAlign = TextAlign.Start,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }

    SeasonsDialogItem(
        show = showDialog,
        seasonList = seasons,
        onClick = { seasonNumber ->
            showDialog = false
            onValueChange(seasonNumber)
        },
        onDismiss = { showDialog = false }
    )

}

@Composable
fun MediaSection(
    imagesList: List<BackdropImageDataClass>?,
    seriesVideosList: List<VideoDataClass>?
) {

    if (imagesList.isNullOrEmpty() && seriesVideosList.isNullOrEmpty()) {
        BodyTextItem(stringResource(R.string.no_results_found))
        return
    }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ImageListItem(imagesList)
        seriesVideosList?.let {
            VideosSection(
                it,
                imagesList = imagesList
            )
        }
    }
}

@Composable
fun SeriesDetailsSection(seriesDetails: SeriesDetailsDataClass?) {
    if (seriesDetails == null) {
        BodyTextItem(stringResource(R.string.no_results_found))
        return
    }
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ThirdTitleTextItem(text = seriesDetails.name, textAlign = TextAlign.Start)
        seriesDetails.overview?.let { BodyTextItem(it) }
        seriesDetails.genres.joinToString(separator = ", ") { it.name }
            .let {
                if (it.isNotEmpty()) {
                    TitleSubtitleItem(stringResource(R.string.genre), it)
                } else {
                    TitleSubtitleItem(
                        stringResource(R.string.genre),
                        stringResource(R.string.no_results_found)
                    )
                }
            }
        TitleSubtitleItem(
            stringResource(R.string.number_of_seasons),
            "${seriesDetails.numberOfSeasons} ${stringResource(R.string.seasons)}"
        )
        TitleSubtitleItem(
            stringResource(R.string.number_of_episodes),
            "${seriesDetails.numberOfEpisodes} ${stringResource(R.string.episodes)}"
        )

        TitleSubtitleItemWithNullability(
            stringResource(R.string.first_air_date),
            seriesDetails.firstAirDate
        )
        TitleSubtitleItemWithNullability(
            stringResource(R.string.last_episode_to_air),
            "${stringResource(R.string.episode)} ${seriesDetails.lastEpisodeToAir?.episodeNumber} - ${
                stringResource(
                    R.string.season
                )
            } ${seriesDetails.lastEpisodeToAir?.seasonNumber}: ${seriesDetails.lastEpisodeToAir?.name} "
        )
        TitleSubtitleItemWithNullability(
            stringResource(R.string.next_episode_to_air),
            "${stringResource(R.string.episode)} ${seriesDetails.nextEpisodeToAir?.episodeNumber} - ${
                stringResource(
                    R.string.season
                )
            } ${seriesDetails.nextEpisodeToAir?.seasonNumber}: ${seriesDetails.nextEpisodeToAir?.name} "
        )


    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SeriesRecommendationsSection(
    seriesRecommendations: List<SeriesDataClass>?,
    onClick: (Int) -> Unit
) {

    if (seriesRecommendations.isNullOrEmpty()) {
        BodyTextItem(stringResource(R.string.no_results_found))
        return
    }


    FlowRow(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        seriesRecommendations.forEach {
            RecommendationCardItem(it) { seriesId -> onClick(seriesId) }
        }
    }

}

@Composable
fun RecommendationCardItem(series: SeriesDataClass, onClick: (Int) -> Unit) {
    val imageUrl = Constants.IMAGE_BASE_URL + series.posterPath

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(series.id)
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
fun SeriesOverviewSection(seriesDetails: SeriesDetailsDataClass?) {
    seriesDetails?.overview?.let { BodyTextItem(it) }
}

@Composable
fun EpisodesListItem(seasonDetails: List<EpisodeDetailsDataClass>?) {

    if (seasonDetails.isNullOrEmpty()) return

    seasonDetails.forEach {
        val imageUrl = Constants.IMAGE_BASE_URL + it.imagePath

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardContainerColor
            ),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Row(Modifier.fillMaxSize()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(R.string.episode_image),
                    modifier = Modifier.size(140.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.image_not_available)
                )
                Column(
                    modifier = Modifier
                        .weight(.7f)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ThirdTitleTextItem(
                        "${it.episodeNumber}. ${it.name}",
                        textAlign = TextAlign.Start
                    )
                    BodyTextItem(
                        it.overview,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 4
                    )
                }
            }
        }
    }
}

@Composable
fun SeasonsDialogItem(
    show: Boolean,
    seasonList: List<SeasonDataClass>,
    onClick: (Int) -> Unit,
    onDismiss: () -> Unit
) {

    if (!show) return

    Dialog(
        onDismissRequest = { }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(seasonList) {
                    ThirdTitleTextItem(it.name, modifier = Modifier.clickable {
                        onClick(it.seasonNumber ?: 1)
                    })
                }
            }
            Icon(
                Icons.Filled.Close,
                contentDescription = stringResource(R.string.close_season_dialog_menu),
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        onDismiss()
                    }
            )
        }

    }
}
