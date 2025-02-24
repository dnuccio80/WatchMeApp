package com.example.watchme.app.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.app.ui.BackButton
import com.example.watchme.app.ui.BackdropImageItem
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.ConfirmDeclineDialog
import com.example.watchme.app.ui.HeaderInfo
import com.example.watchme.app.ui.NextPreviousButtonsRow
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.CastCreditDataClass
import com.example.watchme.app.ui.dataClasses.CollectionDataClass
import com.example.watchme.app.ui.dataClasses.CrewCreditDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.MovieCreditsDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.ReviewDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
import com.example.watchme.core.Routes
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.CardContainerColor
import com.example.watchme.ui.theme.PurpleGrey40

@Composable
fun MovieDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController,
    movieId: Int
) {

    viewModel.getMovieDetailsById(movieId)
    viewModel.getMovieCreditsById(movieId)
    viewModel.getMovieImageListById(movieId)
    viewModel.getRecommendationsById(movieId)
    viewModel.getReviewsById(movieId)
    viewModel.getVideosById(movieId)

    val movieDetails = viewModel.movieDetails.collectAsState()
    val movieCredits = viewModel.movieCredits.collectAsState()
    val movieListImages = viewModel.movieImageList.collectAsState()
    val recommendations = viewModel.movieRecommendations.collectAsState()
    val reviews = viewModel.reviews.collectAsState()
    val videos = viewModel.videos.collectAsState()

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
                movieDetails.value?.backdropImage?.let { BackdropImageItem(it) }
                BackButton()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeaderInfo(
                    movieDetails.value?.genres?.map { it.nameGenre }.orEmpty(),
                    runTime,
                    Modifier.align(Alignment.CenterHorizontally)
                )
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
                RecommendationsSection(recommendations) {movieId -> navController.navigate(Routes.MovieDetails.createRoute(movieId)) }
                ReviewsSection(reviews)
                VideosSection(videos, movieListImages)
            }
        }
    }
}


@Composable
fun ImageListItem(movieListImages: State<List<BackdropImageDataClass>?>) {
    Box(Modifier.fillMaxWidth()) {

        val size = movieListImages.value?.size ?: 0

        if (size == 0) return
        var index by rememberSaveable { mutableIntStateOf(0) }
        val url = Constants.IMAGE_BASE_URL + movieListImages.value?.get(index)?.filePath
        AsyncImage(
            model = url,
            contentDescription = stringResource(R.string.image_cast),
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            placeholder = painterResource(R.drawable.loading_image),
            error = painterResource(R.drawable.loading_image)
        )
        NextPreviousButtonsRow(
            index = index, size = size, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center)
        ) { newIndex -> index = newIndex }
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
    movieDetails.value?.releaseDate?.let {
        TitleSubtitleItem(
            stringResource(R.string.release_date),
            it
        )
    }
    movieDetails.value?.genres?.map { it.nameGenre }?.let {
        TitleSubtitleItem(
            stringResource(R.string.genre),
            it.joinToString(separator = ", ")
        )
    }
    TitleSubtitleItem("Runtime", runTime)
    movieDetails.value?.budget?.let { viewModel.formatPrice(it) }?.let {
        val budget = if (it == "0") stringResource(R.string.unknown) else "$$it"
        TitleSubtitleItem(
            stringResource(R.string.budget),
            budget
        )
    }
    movieDetails.value?.revenue?.let { viewModel.formatPrice(it) }?.let {
        val revenue = if (it == "0") stringResource(R.string.unknown) else "$$it"
        TitleSubtitleItem(stringResource(R.string.revenue), revenue)
    }
    movieDetails.value?.homepage?.let {
        if (it.isEmpty()) return
        TitleSubtitleItem(
            stringResource(R.string.website),
            it,
            isClickable = true
        )
    }
}

@Composable
fun CreditsSection(movieCredits: State<MovieCreditsDataClass?>) {
    if (movieCredits.value == null) return

    SecondTitleTextItem(stringResource(R.string.cast))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(movieCredits.value!!.cast) {
            CastCreditsItem(it)
        }
    }
    SecondTitleTextItem(stringResource(R.string.crew))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(movieCredits.value!!.crew) {
            CrewCreditsItem(it)
        }
    }

}

@Composable
fun CastCreditsItem(credit: CastCreditDataClass) {

    val url = Constants.IMAGE_BASE_URL + credit.profilePath

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
                contentDescription = stringResource(R.string.image_cast),
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
                BodyTextItem(
                    "${stringResource(R.string.character)} ${credit.character}",
                    TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun CrewCreditsItem(credit: CrewCreditDataClass) {

    val url = Constants.IMAGE_BASE_URL + credit.profilePath

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
                contentDescription = stringResource(R.string.image_cast),
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
                BodyTextItem(
                    "${stringResource(R.string.department)} ${credit.department}",
                    TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun CollectionItem(collection: CollectionDataClass?) {
    if (collection == null) return

    val backgroundUrl = Constants.IMAGE_BASE_URL + collection.backdropCollection

    Box(
        Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(if (collection.backdropCollection != null) Color.Transparent else PurpleGrey40)
    ) {
        AsyncImage(
            model = backgroundUrl,
            contentDescription = stringResource(R.string.collection_image),
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
fun TestTextLazyRow(text: String) {
    Text(text, color = Color.White)
}

@Composable
fun RecommendationsSection(recommendations: State<List<MovieDataClass>?>, onClick: (Int) -> Unit) {

    if (recommendations.value == null) return

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SecondTitleTextItem(stringResource(R.string.recommendations), textAlign = TextAlign.Start)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(recommendations.value!!) {
                RecommendationsCardItem(it) {movieId -> onClick(movieId) }
            }
        }
    }
}

@Composable
fun RecommendationsCardItem(movieDataClass: MovieDataClass, onClick: (Int) -> Unit) {

    val image = Constants.IMAGE_BASE_URL + movieDataClass.backdrop

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(140.dp)
            .clickable {
                onClick(movieDataClass.id)
            }
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.Cyan
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = image,
                contentDescription = stringResource(R.string.image_cast),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_image),
                error = painterResource(R.drawable.image_not_found)
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(
                    containerColor = CardContainerColor
                )
            ) {
                BodyTextItem(
                    movieDataClass.title,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ReviewsSection(reviews: State<List<ReviewDataClass>?>) {

    val size = reviews.value?.size ?: 0
    if (size == 0) return
    var index by rememberSaveable { mutableIntStateOf(0) }

    val avatar = Constants.IMAGE_BASE_URL + reviews.value?.get(index)?.authorDetails?.avatarPath

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SecondTitleTextItem(stringResource(R.string.reviews))
        Box(
            Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 28.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape
                        ) {
                            AsyncImage(
                                model = avatar,
                                contentDescription = stringResource(R.string.image_cast),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.unknown_male),
                                error = painterResource(R.drawable.unknown_male)
                            )
                        }
                        Column {
                            reviews.value?.get(index)?.author?.let {
                                SecondTitleTextItem(
                                    it,
                                    textAlign = TextAlign.Start
                                )
                            }
                            reviews.value?.get(index)?.createdAt.let {
                                BodyTextItem(
                                    "${
                                        stringResource(
                                            R.string.created_at
                                        )
                                    } ${it.toString()}"
                                )
                            }
                        }
                    }
                    reviews.value?.get(index)?.content.let {
                        BodyTextItem(
                            it.toString(),
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            NextPreviousButtonsRow(
                index = index, size = size, modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) { newIndex -> index = newIndex }
        }
    }
}

@Composable
fun VideosSection(
    videos: State<List<VideoDataClass>?>,
    movieListImages: State<List<BackdropImageDataClass>?>
) {

    if (videos.value.isNullOrEmpty()) return

    val context = LocalContext.current
    var index by rememberSaveable { mutableIntStateOf(0) }
    val size = videos.value?.size ?: 0
    val videoUrl = Constants.YOUTUBE_BASE_URL + videos.value?.get(index)?.key
    val imageUrl = Constants.IMAGE_BASE_URL + movieListImages.value?.get(index)?.filePath
    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SecondTitleTextItem(stringResource(R.string.videos))

        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    showConfirmDialog = true
                },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(16.dp),
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(R.string.image_cast),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.loading_image),
                    error = painterResource(R.drawable.image_not_found)
                )
                NextPreviousButtonsRow(
                    index = index, size = size, modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) { newIndex -> index = newIndex }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    colors = CardDefaults.cardColors(
                        containerColor = CardContainerColor
                    ),
                    shape = RectangleShape
                ) {
                    videos.value?.get(index)?.name?.let {
                        BodyTextItem(
                            it,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                        )
                    }
                }
                ConfirmDeclineDialog(
                    show = showConfirmDialog,
                    text = stringResource(R.string.open_youtube),
                    onDismiss = { showConfirmDialog = false },
                    onConfirm = {
                        showConfirmDialog = false
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                        intent.setPackage("com.google.android.youtube")
                        context.startActivity(intent)
                    })
            }
        }
    }
}

