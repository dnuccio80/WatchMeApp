package com.example.watchme.app.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.LabelSmallItem
import com.example.watchme.app.ui.PercentageVisualItem
import com.example.watchme.app.ui.TitleTextItem
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.core.Routes
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController
) {

    val popularMovies by viewModel.popularMovies.collectAsState()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val topRatedMovies by viewModel.topRatedMovies.collectAsState()
    val upcomingMovies by viewModel.upcomingMovies.collectAsState()
    val providers by viewModel.providers.collectAsState()
    val popularSeries by viewModel.popularSeries.collectAsState()
    val airingSeriesToday by viewModel.airingSeriesToday.collectAsState()
    val onTheAirSeries by viewModel.onTheAirSeries.collectAsState()
    val getTopRatedSeries by viewModel.topRatedSeries.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            PopularMoviesLazyRow(popularMovies) {
                navController.navigate(Routes.MovieDetails.createRoute(it))
            }
            Spacer(Modifier.size(16.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TitleTextItem(stringResource(R.string.providers))
                Spacer(Modifier.size(16.dp))
                ProvidersLazyRow(providers)
                Spacer(Modifier.size(32.dp))
                MoviesSection(
                    nowPlayingMovies,
                    viewModel,
                    topRatedMovies,
                    upcomingMovies
                ) { movieId -> navController.navigate(Routes.MovieDetails.createRoute(movieId)) }
                Spacer(Modifier.size(32.dp))
                TvSeriesSection(popularSeries, airingSeriesToday, onTheAirSeries, getTopRatedSeries)
            }
        }

    }
}

@Composable
fun TvSeriesSection(
    popularSeries: List<SeriesDataClass>?,
    airingSeriesToday: List<SeriesDataClass>?,
    onTheAirSeries: List<SeriesDataClass>?,
    getTopRatedSeries: List<SeriesDataClass>?
) {
    TitleTextItem(stringResource(R.string.tv_series))
    Spacer(Modifier.size(16.dp))
    SeriesLazyRow(popularSeries,stringResource(R.string.popular_series))
    Spacer(Modifier.size(32.dp))
    SeriesLazyRow(airingSeriesToday, stringResource(R.string.airing_today))
    Spacer(Modifier.size(32.dp))
    SeriesLazyRow(onTheAirSeries, stringResource(R.string.on_the_air))
    Spacer(Modifier.size(32.dp))
    SeriesLazyRow(getTopRatedSeries, stringResource(R.string.top_rated))

}

@Composable
fun SeriesLazyRow(seriesList: List<SeriesDataClass>?, label:String) {

    if (seriesList.isNullOrEmpty()) return

    LabelSmallItem(label)
    Spacer(Modifier.size(4.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(seriesList){
            DefaultSeriesCardItem(it) { }
        }
    }
}

@Composable
fun MoviesSection(
    nowPlayingMovies: List<MovieDataClass>,
    viewModel: AppViewModel,
    topRatedMovies: List<MovieDataClass>,
    upcomingMovies: List<MovieDataClass>,
    onClick: (Int) -> Unit
) {
    TitleTextItem(stringResource(R.string.movies))
    Spacer(Modifier.size(16.dp))
    LabelSmallItem(stringResource(R.string.now_playing))
    Spacer(Modifier.size(4.dp))
    NowPlayingMoviesLazyRow(nowPlayingMovies) { movieId ->
        onClick(movieId)
    }
    Spacer(Modifier.size(32.dp))
    LabelSmallItem(stringResource(R.string.top_rated))
    Spacer(Modifier.size(4.dp))
    TopRatedMoviesLazyRow(
        topRatedMovies,
        viewModel
    ) { movieId -> onClick(movieId) }
    Spacer(Modifier.size(32.dp))
    LabelSmallItem(stringResource(R.string.upcoming))
    Spacer(Modifier.size(4.dp))
    UpcomingMoviesLazyRow(upcomingMovies) { movieId -> onClick(movieId) }
}


@Composable
fun ProvidersLazyRow(providers: List<ProvidersDataClass>) {
    LazyRow(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(providers) {
            ProviderCardItem(it)
        }
    }
}

@Composable
fun ProviderCardItem(provider: ProvidersDataClass) {

    val imageUrl = Constants.IMAGE_BASE_URL + provider.logo

    Card(
        modifier = Modifier
            .size(80.dp), colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        ),
        elevation = CardDefaults.cardElevation(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.provider_image),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun NowPlayingMoviesLazyRow(movies: List<MovieDataClass>, onClick: (Int) -> Unit) {
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movies) {
            DefaultMovieCardItem(it) { movieId -> onClick(movieId) }
        }
    }
}

@Composable
fun PopularMoviesLazyRow(popularMovies: List<MovieDataClass>, onItemClick: (Int) -> Unit) {

    if (popularMovies.isEmpty()) return

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = Int.MAX_VALUE / 2)

    LazyRow(
        state = listState,
        modifier = Modifier
            .height(460.dp),
        flingBehavior = rememberSnapFlingBehavior(listState),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(horizontal = 50.dp)
    ) {
        items(Int.MAX_VALUE) { index -> //
            val realIndex = index % popularMovies.size
            val isCentered = listState.firstVisibleItemIndex == index
            PopularMovieCardItem(
                popularMovies[realIndex],
                isCentered
            ) { movieId -> onItemClick(movieId) }
        }
    }
}

@Composable
fun PopularMovieCardItem(movie: MovieDataClass, isCentered: Boolean, onClick: (Int) -> Unit) {
    val imageUrl = Constants.IMAGE_BASE_URL + movie.poster

    val cardHeight by animateDpAsState(
        targetValue = if (isCentered) 460.dp else 440.dp,
    )

    Card(
        Modifier
            .width(380.dp)
            .height(cardHeight)
            .clickable {
                onClick(movie.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(16.dp)
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
fun DefaultMovieCardItem(movie: MovieDataClass, onClick: (Int) -> Unit) {
    val imageUrl = Constants.IMAGE_BASE_URL + movie.poster

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(movie.id)
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
fun DefaultSeriesCardItem(serie: SeriesDataClass, onClick: (Int) -> Unit) {
    val imageUrl = Constants.IMAGE_BASE_URL + serie.posterPath

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(serie.id)
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
fun TopRatedMoviesLazyRow(
    movies: List<MovieDataClass>,
    viewModel: AppViewModel,
    onClick: (Int) -> Unit
) {
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movies) {
            TopRatedCardItem(it, viewModel) { movieId -> onClick(movieId) }
        }
    }
}

@Composable
fun TopRatedCardItem(movie: MovieDataClass, viewModel: AppViewModel, onClick: (Int) -> Unit) {

    val imageUrl = Constants.IMAGE_BASE_URL + movie.poster
    val votePercentage = (movie.voteAverage * 10).toInt()

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(movie.id)
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
            PercentageVisualItem(
                votePercentage,
                Modifier.align(Alignment.TopStart),
                viewModel.getPercentageColor(votePercentage)
            )
        }
    }
}


@Composable
fun UpcomingMoviesLazyRow(upcomingMovies: List<MovieDataClass>, onClick: (Int) -> Unit) {
    if (upcomingMovies.isEmpty()) return

    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(upcomingMovies) {
            DefaultMovieCardItem(it) { movieId -> onClick(movieId) }
        }
    }
}


