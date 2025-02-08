package com.example.watchme.app.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.app.data.network.responses.Movie
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.IntermediateVoteColor
import com.example.watchme.ui.theme.NegativeVoteColor
import com.example.watchme.ui.theme.PositiveVoteColor
import com.example.watchme.ui.theme.Purple40

@Composable
fun HomeScreen(innerPadding: PaddingValues, viewModel: AppViewModel) {

    val popularMovies by viewModel.popularMovies.collectAsState()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val topRatedMovies by viewModel.topRatedMovies.collectAsState()

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
            PopularMoviesLazyRow(popularMovies)
            Spacer(Modifier.size(16.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Now Playing", style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.size(4.dp))
                NowPlayingMoviesLazyRow(nowPlayingMovies)
                Spacer(Modifier.size(48.dp))
                Text("Top Rated", style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.size(4.dp))
                TopRatedMoviesLazyRow(topRatedMovies)
            }
        }

    }
}


@Composable
fun NowPlayingMoviesLazyRow(movies: MovieResponse) {
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movies.result) {
            NowPlayingCardItem(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMoviesLazyRow(popularMovies: MovieResponse) {

    if (popularMovies.result.isEmpty()) return

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = Int.MAX_VALUE / 2)

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .height(460.dp),
        flingBehavior = rememberSnapFlingBehavior(listState),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(horizontal = 80.dp)
    ) {
        items(Int.MAX_VALUE) { index -> //
            val realIndex = index % popularMovies.result.size
            val isCentered = listState.firstVisibleItemIndex == index
            PopularMovieCardItem(popularMovies.result[realIndex], isCentered)
        }
    }
}

@Composable
fun PopularMovieCardItem(movie: Movie, isCentered: Boolean) {
    val imageUrl = Constants.BASE_URL + movie.poster

    val cardHeight by animateDpAsState(
        targetValue = if (isCentered) 460.dp else 440.dp,
    )

    Card(
        Modifier
            .width(400.dp)
            .height(cardHeight),
        colors = CardDefaults.cardColors(
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
fun NowPlayingCardItem(movie: Movie) {
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
fun TopRatedMoviesLazyRow(movies: MovieResponse) {
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movies.result) {
            TopRatedCardItem(it)
        }
    }
}

@Composable
fun TopRatedCardItem(movie: Movie) {

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
            PercentageVisualItem(votePercentage, Modifier.align(Alignment.TopStart))
        }
    }
}

@Composable
fun PercentageVisualItem(percentage: Int, modifier: Modifier) {

    Card(
        modifier = modifier.size(38.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = getPercentageColor(percentage)
        ),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            )
        ) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(percentage.toString(), color = Color.White,fontWeight = FontWeight.Bold)
                    Text("%", textAlign = TextAlign.Start, color = Color.White, fontSize = 10.sp)
                }
            }
        }
    }
}

fun getPercentageColor(voteAverage:Int):Color{
    return when(voteAverage){
        in 0..40 -> NegativeVoteColor
        in 41..69 -> IntermediateVoteColor
        else -> PositiveVoteColor
    }
}