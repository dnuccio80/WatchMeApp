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
import com.example.watchme.app.data.network.responses.Movie
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.app.ui.BackButton
import com.example.watchme.app.ui.BackdropImageItem
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.CreditsSection
import com.example.watchme.app.ui.HeaderInfo
import com.example.watchme.app.ui.ImageListItem
import com.example.watchme.app.ui.NextPreviousButtonsRow
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.SectionSelectionItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.app.ui.VideosSection
import com.example.watchme.app.ui.dataClasses.CastCreditDataClass
import com.example.watchme.app.ui.dataClasses.CollectionDataClass
import com.example.watchme.app.ui.dataClasses.CrewCreditDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.ReviewDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.core.Routes
import com.example.watchme.core.Sections
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

    val movieDetails by viewModel.movieDetails.collectAsState()
    val movieCredits by viewModel.movieCredits.collectAsState()
    val movieListImages by viewModel.movieImageList.collectAsState()
    val recommendations by viewModel.movieRecommendations.collectAsState()
    val reviews by viewModel.reviews.collectAsState()
    val videos by viewModel.movieVideos.collectAsState()

    val runTime = viewModel.getRunTimeInHours(movieDetails?.runtime ?: 0)
    val scrollState = rememberScrollState()
    var sectionSelected by rememberSaveable { mutableStateOf(Sections.Suggested.title) }

    val sectionList = listOf(
        Sections.Details.title,
        Sections.Suggested.title,
        Sections.Media.title,
        Sections.Credits.title,
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                movieDetails?.backdropImage?.let { BackdropImageItem(it) }
                BackButton()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeaderInfo(
                    movieDetails?.genres?.map { it.nameGenre }.orEmpty(),
                    runTime,
                    Modifier.align(Alignment.CenterHorizontally)
                )
                movieDetails?.let { SecondTitleTextItem(it.title) }
                SectionSelectionItem(sectionList) { newSectionSelected -> sectionSelected = newSectionSelected }
                when(sectionSelected.lowercase()){
                    Sections.Details.title -> OverviewSection(movieDetails, runTime, viewModel)
                    Sections.Suggested.title -> MoviesRecommendationsSection(recommendations) {  }
                    Sections.Media.title -> MediaSection(movieListImages, videos)
                    Sections.Credits.title -> CreditsSection(movieCredits)
                }

            }
        }
    }
}


@Composable
private fun OverviewSection(
    movieDetails: DetailsMovieDataClass?,
    runTime: String,
    viewModel: AppViewModel
) {
    SecondTitleTextItem(movieDetails?.title.toString(), TextAlign.Start)
    movieDetails?.let { BodyTextItem(it.overview) }
    CollectionItem(movieDetails?.collection)
    movieDetails?.releaseDate?.let {
        TitleSubtitleItem(
            stringResource(R.string.release_date),
            it
        )
    }
    movieDetails?.genres?.map { it.nameGenre }?.let {
        TitleSubtitleItem(
            stringResource(R.string.genre),
            it.joinToString(separator = ", ")
        )
    }
    TitleSubtitleItem("Runtime", runTime)
    movieDetails?.budget?.let { viewModel.formatPrice(it) }?.let {
        val budget = if (it == "0") stringResource(R.string.unknown) else "$$it"
        TitleSubtitleItem(
            stringResource(R.string.budget),
            budget
        )
    }
    movieDetails?.revenue?.let { viewModel.formatPrice(it) }?.let {
        val revenue = if (it == "0") stringResource(R.string.unknown) else "$$it"
        TitleSubtitleItem(stringResource(R.string.revenue), revenue)
    }
    movieDetails?.homepage?.let {
        if (it.isEmpty()) return
        TitleSubtitleItem(
            stringResource(R.string.website),
            it,
            isClickable = true
        )
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoviesRecommendationsSection(
    moviesRecommendations: List<MovieDataClass>?,
    onClick: (Int) -> Unit
) {

    if (moviesRecommendations == null) return

    FlowRow(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        moviesRecommendations.forEach {
            RecommendationCardItem(it) { seriesId -> onClick(seriesId) }
        }
    }

}

@Composable
fun RecommendationCardItem(movies: MovieDataClass, onClick: (Int) -> Unit) {
    val imageUrl = Constants.IMAGE_BASE_URL + movies.poster

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(movies.id)
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


//@Composable
//fun RecommendationsSection(recommendations: List<MovieDataClass>?, onClick: (Int) -> Unit) {
//
//    if (recommendations == null) return
//
//    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//        SecondTitleTextItem(stringResource(R.string.recommendations), textAlign = TextAlign.Start)
//        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            items(recommendations){
//                RecommendationsCardItem(it) {movieId -> onClick(movieId) }
//
//            }
//        }
//    }
//}
//
//@Composable
//fun RecommendationsCardItem(movieDataClass: MovieDataClass, onClick: (Int) -> Unit) {
//
//    val image = Constants.IMAGE_BASE_URL + movieDataClass.backdrop
//
//    Card(
//        modifier = Modifier
//            .width(200.dp)
//            .height(140.dp)
//            .clickable {
//                onClick(movieDataClass.id)
//            }
//        ,
//        colors = CardDefaults.cardColors(
//            containerColor = Color.Cyan
//        ),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(16.dp)
//    ) {
//        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
//            AsyncImage(
//                model = image,
//                contentDescription = stringResource(R.string.image_cast),
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop,
//                placeholder = painterResource(R.drawable.loading_image),
//                error = painterResource(R.drawable.image_not_found)
//            )
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                shape = RectangleShape,
//                colors = CardDefaults.cardColors(
//                    containerColor = CardContainerColor
//                )
//            ) {
//                BodyTextItem(
//                    movieDataClass.title,
//                    modifier = Modifier
//                        .padding(vertical = 8.dp)
//                        .fillMaxWidth(),
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
//    }
//}

@Composable
fun ReviewsSection(reviews: List<ReviewDataClass>?) {

    val size = reviews?.size ?: 0
    if (size == 0) return
    var index by rememberSaveable { mutableIntStateOf(0) }

    val avatar = Constants.IMAGE_BASE_URL + reviews?.get(index)?.authorDetails?.avatarPath

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
                            reviews?.get(index)?.author?.let {
                                SecondTitleTextItem(
                                    it,
                                    textAlign = TextAlign.Start
                                )
                            }
                            reviews?.get(index)?.createdAt.let {
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
                    reviews?.get(index)?.content.let {
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


