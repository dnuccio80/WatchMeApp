package com.example.watchme.app.ui.screens

import android.util.Log
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
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
import com.example.watchme.app.ui.HeaderInfo
import com.example.watchme.app.ui.LazyRowItemText
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.app.ui.TitleSubtitleItemWithNullability
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.SeasonDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDetailsDataClass
import com.example.watchme.core.Routes
import com.example.watchme.core.SeriesOptions
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

    val verticalScrollState = rememberScrollState()
    var seasonSelected by rememberSaveable { mutableIntStateOf(1) }

    viewModel.getSeriesDetailsById(seriesId)
    viewModel.getSeasonDetails(seriesId, seasonSelected)
    viewModel.getSeriesRecommendationsById(seriesId)

    val lazyList = listOf(
        SeriesOptions.Episodes.item,
        SeriesOptions.Suggested.item,
        SeriesOptions.Details.item,
        SeriesOptions.Media.item,
        SeriesOptions.Credits.item,
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
                seriesDetails?.backdropPath?.let { BackdropImageItem(it) }
                BackButton()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeaderInfo(
                    seriesDetails?.genres?.map { it.name }.orEmpty(),
                    "${seriesDetails?.numberOfSeasons} seasons",
                    Modifier.align(Alignment.CenterHorizontally)
                )
                seriesDetails?.name?.let { SecondTitleTextItem(it) }
                SeriesOverviewSection(seriesDetails)
                Spacer(Modifier.size(16.dp))
                LazyRowItem(lazyList)

                SeriesDetailsSection(seriesDetails)


                /*
                    SUGGESTION SECTION
                SeriesRecommendationsSection(seriesRecommendations) { navController.navigate(Routes.SeriesDetails.createRoute(it)) }
                */

                /*
                    EPISODES SECTION

                seriesDetails?.seasons?.let { seasons ->
                    EpisodesSection(
                        seasonSelected,
                        seasons,
                        seasonDetails,
                        onCurrentSeasonChange = { seasonNumber ->
                            seasonSelected = seasonNumber
                        })
                }*/
            }
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
            .let { TitleSubtitleItem(stringResource(R.string.genre), it) }
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
            "${stringResource(R.string.episode)} ${seriesDetails.lastEpisodeToAir?.episodeNumber} - ${stringResource(R.string.season)} ${seriesDetails.lastEpisodeToAir?.seasonNumber}: ${seriesDetails.lastEpisodeToAir?.name} "
        )
        TitleSubtitleItemWithNullability(
            stringResource(R.string.next_episode_to_air),
            "${stringResource(R.string.episode)} ${seriesDetails.nextEpisodeToAir?.episodeNumber} - ${stringResource(R.string.season)} ${seriesDetails.nextEpisodeToAir?.seasonNumber}: ${seriesDetails.nextEpisodeToAir?.name} "
        )


    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SeriesRecommendationsSection(
    seriesRecommendations: List<SeriesDataClass>?,
    onClick: (Int) -> Unit
) {

    if (seriesRecommendations == null) return

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
fun LazyRowItem(lazyList: List<String>) {

    var selectedText by rememberSaveable { mutableStateOf(lazyList[0]) }
    var textWidth by rememberSaveable { mutableStateOf(223) }
    var xPos by rememberSaveable { mutableStateOf(0f) }

    val lazyListState = rememberLazyListState()

    Column {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(32.dp), state = lazyListState) {
            items(lazyList) {
                LazyRowItemText(
                    it.uppercase(),
                    selectedText,
                ) { clickedText, newTextWidth, newXPos ->
                    selectedText = clickedText
                    textWidth = newTextWidth
                    xPos = newXPos
                }
            }
        }

        Spacer(Modifier.size(4.dp))
        LazyHorizontalDividerItem(textWidth, xPos)
    }
}

@Composable
fun LazyHorizontalDividerItem(textWidth: Int, xPos: Float) {

    val cardWidth by animateDpAsState(
        targetValue = with(LocalDensity.current) { textWidth.toDp() },
        animationSpec = TweenSpec(200),
        label = "card width"
    )

    val xOffset by animateDpAsState(
        targetValue = with(LocalDensity.current) { xPos.toDp() },
        animationSpec = TweenSpec(durationMillis = 200),
        label = "x offset"
    )

    Column(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .height(6.dp)
                .width(cardWidth)
                .offset(x = xOffset),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) { }
        HorizontalDivider(Modifier.fillMaxWidth(), color = Color.White, thickness = 1.dp)
    }
}

@Composable
fun SeriesOverviewSection(seriesDetails: SeriesDetailsDataClass?) {
    seriesDetails?.overview?.let { BodyTextItem(it) }
}

@Composable
fun EpisodesSection(
    seasonSelected: Int,
    seasonList: List<SeasonDataClass>,
    seasonDetails: List<EpisodeDetailsDataClass>?,
    onCurrentSeasonChange: (Int) -> Unit
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = CardContainerColor
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BodyTextItem(seasonList[seasonSelected].name)
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.show_season_dialog_menu),
                    tint = Color.White
                )
            }
        }
        EpisodesListItem(seasonDetails)
    }

    SeasonsDropdownMenuItem(showDialog, seasonList,
        onClick = { season ->
            showDialog = false
            season.seasonNumber?.let { onCurrentSeasonChange(it) }
        }, onDismiss = { showDialog = false })
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
fun SeasonsDropdownMenuItem(
    show: Boolean,
    seasonList: List<SeasonDataClass>,
    onClick: (SeasonDataClass) -> Unit,
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
                        onClick(it)
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
