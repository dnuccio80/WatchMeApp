package com.example.watchme.app.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.app.ui.BackButton
import com.example.watchme.app.ui.BackdropImageItem
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.PointSeparatorIcon
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.CastCreditDataClass
import com.example.watchme.app.ui.dataClasses.CollectionDataClass
import com.example.watchme.app.ui.dataClasses.CrewCreditDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.MovieCreditsDataClass
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AlphaButtonColor
import com.example.watchme.ui.theme.CardContainerColor
import com.example.watchme.ui.theme.PurpleGrey40

@Composable
fun MovieDetailsScreen(innerPadding: PaddingValues, viewModel: AppViewModel, movieId: Int) {

    viewModel.getMovieDetailsById(movieId)
    viewModel.getMovieCreditsById(movieId)
    viewModel.getMovieImageListById(movieId)

    val movieDetails = viewModel.movieDetails.collectAsState()
    val movieCredits = viewModel.movieCredits.collectAsState()
    val movieListImages = viewModel.movieImageList.collectAsState()

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
                OverviewSection(movieDetails, runTime, viewModel)
                CreditsSection(movieCredits)
                ImageListItem(movieListImages)
            }
        }
    }
}

@Composable
fun ImageListItem(movieListImages: State<List<BackdropImageDataClass>?>) {
    Box(Modifier.fillMaxWidth()) {

        val size = movieListImages.value?.size ?: 0

        if(size == 0) return
        var index by rememberSaveable { mutableIntStateOf(0) }
        val url = Constants.BASE_URL + movieListImages.value?.get(index)?.filePath
        AsyncImage(
            model = url,
            contentDescription = "image cast",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            placeholder = painterResource(R.drawable.loading_image),
            error = painterResource(R.drawable.loading_image)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Card(
                modifier = Modifier.size(30.dp),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AlphaButtonColor,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "show previous image",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            val currentIndex = (index - 1 + size) % size
                            index = currentIndex
                        }
                )
            }
            Card(
                modifier = Modifier.size(30.dp),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AlphaButtonColor,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "show previous image",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            val currentIndex = (index + 1) % size
                            index = currentIndex
                        }
                )
            }

        }
    }
}

@Composable
private fun OverviewSection(
    movieDetails: State<DetailsMovieDataClass?>,
    runTime: String,
    viewModel: AppViewModel
) {
    SecondTitleTextItem(movieDetails.value?.title.toString(), TextAlign.Start)
    movieDetails.value?.let { BodyTextItem(it.overview) }
    CollectionItem(movieDetails.value?.collection)
    movieDetails.value?.releaseDate?.let { TitleSubtitleItem("Release Date:", it) }
    movieDetails.value?.genres?.map { it.nameGenre }?.let {
        TitleSubtitleItem(
            "Genre:",
            it.joinToString(separator = ", ")
        )
    }
    TitleSubtitleItem("Runtime", runTime)
    movieDetails.value?.budget?.let { viewModel.formatPrice(it) }?.let {
        val budget = if (it == "0") "Unknown" else "$$it"
        TitleSubtitleItem(
            "Budget:",
            budget
        )
    }
    movieDetails.value?.revenue?.let { viewModel.formatPrice(it) }?.let {
        val revenue = if (it == "0") "Unknown" else "$$it"
        TitleSubtitleItem("Revenue:", revenue)
    }
    movieDetails.value?.homepage?.let {
        if (it.isEmpty()) return
        TitleSubtitleItem(
            "Website:",
            it,
            isClickable = true
        )
    }
}

@Composable
fun CreditsSection(movieCredits: State<MovieCreditsDataClass?>) {
    if (movieCredits.value == null) return

    SecondTitleTextItem("Cast")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(movieCredits.value!!.cast) {
            CastCreditsItem(it)
        }
    }
    SecondTitleTextItem("Crew")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(movieCredits.value!!.crew) {
            CrewCreditsItem(it)
        }
    }

}

@Composable
fun CastCreditsItem(credit: CastCreditDataClass) {

    val url = Constants.BASE_URL + credit.profilePath

    Card(
        modifier = Modifier.width(190.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardContainerColor
        )
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = url,
                contentDescription = "image cast",
                modifier = Modifier
                    .fillMaxSize()
                    .height(max(200.dp, 250.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.unknown_male),
                error = painterResource(R.drawable.unknown_male)
            )
            Column(
                Modifier
                    .background(CardContainerColor)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThirdTitleTextItem(credit.name, TextAlign.Center)
                BodyTextItem("Character: ${credit.character}", TextAlign.Center)
            }
        }

    }
}

@Composable
fun CrewCreditsItem(credit: CrewCreditDataClass) {

    val url = Constants.BASE_URL + credit.profilePath

    Card(
        modifier = Modifier.width(190.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardContainerColor
        )
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = url,
                contentDescription = "image cast",
                modifier = Modifier
                    .fillMaxSize()
                    .height(max(200.dp, 250.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.unknown_male),
                error = painterResource(R.drawable.unknown_male)
            )
            Column(
                Modifier
                    .background(CardContainerColor)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThirdTitleTextItem(credit.name, TextAlign.Center)
                BodyTextItem("Department: ${credit.department}", TextAlign.Center)
            }
        }

    }
}

@Composable
fun CollectionItem(collection: CollectionDataClass?) {
    if (collection == null) return

    val backgroundUrl = Constants.BASE_URL + collection.backdropCollection

    Box(
        Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(if (collection.backdropCollection != null) Color.Transparent else PurpleGrey40)
    ) {
        AsyncImage(
            model = backgroundUrl,
            contentDescription = "collection image",
            modifier = Modifier.matchParentSize(),
            alpha = 0.5f
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
                .align(
                    Alignment.CenterStart
                )
                .padding(16.dp)
        ) {
            SecondTitleTextItem(collection.nameCollection!!, TextAlign.Start)
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
    movieDetails: State<DetailsMovieDataClass?>,
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