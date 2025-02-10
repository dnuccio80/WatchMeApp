package com.example.watchme.app.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.app.data.network.responses.Collection
import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.app.ui.BackButton
import com.example.watchme.app.ui.BackdropImageItem
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.PointSeparatorIcon
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.app.ui.TitleTextItem
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.Pink80
import com.example.watchme.ui.theme.PurpleGrey40

@Composable
fun MovieDetailsScreen(innerPadding: PaddingValues, viewModel: AppViewModel, movieId: Int) {

    viewModel.getMovieDetailsById(movieId)
    val movieDetails = viewModel.movieDetails.collectAsState()

    val runTime = viewModel.getRunTimeInHours(movieDetails.value?.runtime ?: 0)
    val scrollState = rememberScrollState()

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                BackdropImageItem(movieDetails)
                BackButton()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeaderInfo(movieDetails, runTime, Modifier.align(Alignment.CenterHorizontally))
                movieDetails.value?.let { SecondTitleTextItem(it.title) }
                // ESTO ES DE PRUEBA TODAVIA
                LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    items(10) {
                        TestTextLazyRow("Testing")
                    }
                }
                HorizontalDivider(Modifier.fillMaxWidth())
                SecondTitleTextItem("Overview", TextAlign.Start)
                movieDetails.value?.let { BodyTextItem(it.overview) }
                CollectionItem(movieDetails.value?.collection)
                movieDetails.value?.releaseDate?.let { TitleSubtitleItem("Release Date:", it) }
                movieDetails.value?.genres?.map { it.nameGenre }?.let {
                    TitleSubtitleItem(
                        "Genre:",
                        it.joinToString(separator = ", ")
                    )
                }
                movieDetails.value?.budget?.let { viewModel.formatPrice(it) }?.let {
                    TitleSubtitleItem(
                        "Budget:",
                        "$$it"
                    )
                }
                movieDetails.value?.revenue?.let { viewModel.formatPrice(it) }?.let {
                    TitleSubtitleItem("Revenue:", "$$it")
                }
                movieDetails.value?.homepage?.let {
                    if(it.isEmpty()) return
                    TitleSubtitleItem(
                        "Website:",
                        it,
                        isClickable = true
                    )
                }
            }
        }
    }
}

@Composable
fun CollectionItem(collection: Collection?) {
    if (collection == null) return

    val backgroundUrl = Constants.BASE_URL + collection.backdropCollection

    Box(
        Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(if(collection.backdropCollection != null) Color.Transparent else PurpleGrey40)
    ) {
        AsyncImage(
            model = backgroundUrl,
            contentDescription = "collection image",
            modifier = Modifier.matchParentSize(),
            alpha = 0.5f
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.align(
                Alignment.CenterStart
            ).padding(16.dp)
        ) {
            SecondTitleTextItem(collection.nameCollection, TextAlign.Start)
            Button(
                onClick = { /* SEARCH COLLECTION BY ID*/ }
            ) {
                BodyTextItem("VIEW THE COLLECTION")
            }
        }
    }
}


@Composable
fun HeaderInfo(
    movieDetails: State<DetailsMovieResponse?>,
    runtime: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        movieDetails.value?.genres?.map { it.nameGenre }?.let {
            BodyTextItem(it.joinToString(separator = ", "))
        }
        PointSeparatorIcon()
        Text(runtime, color = Color.White)
    }
}


@Composable
fun TestTextLazyRow(text: String) {
    Text(text, color = Color.White)
}