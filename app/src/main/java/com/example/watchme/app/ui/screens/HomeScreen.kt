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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.app.ui.PercentageVisualItem
import com.example.watchme.app.ui.TitleTextItem
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
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
    val providers by viewModel.providers.collectAsState()

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
                navController.navigate("detailsMovie/$it")
            }
            Spacer(Modifier.size(16.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TitleTextItem("Providers")
                Spacer(Modifier.size(16.dp))
                ProvidersLazyRow(providers)
                Spacer(Modifier.size(32.dp))
                TitleTextItem("Movies")
                Spacer(Modifier.size(16.dp))
                Text("Now Playing", style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.size(4.dp))
                NowPlayingMoviesLazyRow(nowPlayingMovies)
                Spacer(Modifier.size(32.dp))
                Text("Top Rated", style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.size(4.dp))
                TopRatedMoviesLazyRow(topRatedMovies, viewModel)
            }
        }

    }
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

    val imageUrl = Constants.BASE_URL + provider.logo

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
            contentDescription = "provider image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun NowPlayingMoviesLazyRow(movies: List<MovieDataClass>) {
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movies) {
            NowPlayingCardItem(it)
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
    val imageUrl = Constants.BASE_URL + movie.poster

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
            contentDescription = "movie image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun NowPlayingCardItem(movie: MovieDataClass) {
    val imageUrl = Constants.BASE_URL + movie.poster

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp), colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "movie image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun TopRatedMoviesLazyRow(movies: List<MovieDataClass>, viewModel: AppViewModel) {
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movies) {
            TopRatedCardItem(it, viewModel)
        }
    }
}

@Composable
fun TopRatedCardItem(movie: MovieDataClass, viewModel: AppViewModel) {

    val imageUrl = Constants.BASE_URL + movie.poster
    val votePercentage = (movie.voteAverage * 10).toInt()

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp), colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "movie image",
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


