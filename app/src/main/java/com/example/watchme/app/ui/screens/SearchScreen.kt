package com.example.watchme.app.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.dataClasses.SearchDataClass
import com.example.watchme.core.Routes
import com.example.watchme.core.Categories
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AlphaButtonColor
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor

@Composable
fun SearchScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController
) {

    val query by viewModel.query.collectAsState()
    val searchCollection by viewModel.searchCollection.collectAsState()
    val searchMovie by viewModel.searchMovie.collectAsState()
    val searchSeries by viewModel.searchSeries.collectAsState()
    val searchPeople by viewModel.searchPeople.collectAsState()
    val searchTypeSelected by viewModel.searchTypeSelected.collectAsState()

    val searchList = listOf(
        Categories.Collections,
        Categories.TvSeries,
        Categories.Movies,
        Categories.People,
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = query,
                onValueChange = { viewModel.onQueryChanged(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.search_by_title)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "search icon") },
            )
            HorizontalDivider(Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp)

            Column {
                ThirdTitleTextItem(
                    stringResource(R.string.select_search_type),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Start
                )
                SearchTypeSection(
                    searchList,
                    searchTypeSelected
                ) { newTypeSelected -> viewModel.onSearchTypeSelectedChange(newTypeSelected.title) }
            }
            if(query.isEmpty()){
                ThirdTitleTextItem(stringResource(R.string.try_type_something_search), textAlign = TextAlign.Center)
            }
            when (searchTypeSelected) {
                Categories.Collections.title -> SearchSection(searchCollection, searchTypeSelected) { id -> navController.navigate(Routes.CollectionDetails.createRoute(id)) }
                Categories.TvSeries.title -> SearchSection(searchSeries, searchTypeSelected) { id -> navController.navigate(Routes.SeriesDetails.createRoute(id)) }
                Categories.Movies.title -> SearchSection(searchMovie, searchTypeSelected) { id -> navController.navigate(Routes.MovieDetails.createRoute(id)) }
                Categories.People.title -> SearchSection(searchPeople, searchTypeSelected) { id -> navController.navigate(Routes.PeopleDetails.createRoute(id)) }
            }
        }
    }
}

@Composable
fun SearchTypeSection(
    searchList: List<Categories>,
    searchTypeSelected: String,
    onClick: (Categories) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(searchList) { searchType ->
            SearchTypeCardItem(searchType, searchTypeSelected) { type -> onClick(type) }
        }
    }
}

@Composable
fun SearchSection(searchList: List<SearchDataClass>?, searchTypeSelected: String, onClick: (Int) -> Unit) {
    if (!searchList.isNullOrEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(searchList) {
                SearchingCardItem(it, searchTypeSelected) {id -> onClick(id) }
            }
        }
    }
}

@Composable
fun SearchTypeCardItem(
    type: Categories,
    searchTitleSelected: String,
    onClick: (Categories) -> Unit
) {

    val isSelected = type.title == searchTitleSelected

    val cardColor by animateColorAsState(
        targetValue = if (isSelected) CardContainerColor else AlphaButtonColor,
        animationSpec = TweenSpec(300), label = ""
    )

    val cardSize by animateDpAsState(
        if (isSelected) 104.dp else 100.dp,
        animationSpec = TweenSpec(300), label = ""
    )

    Card(
        modifier = Modifier
            .size(cardSize)
            .clickable {
                onClick(type)
            },
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(id = type.icon),
                contentDescription = "card icon",
                tint = Color.White
            )
            Spacer(Modifier.size(12.dp))
            BodyTextItem(type.title)
        }
    }
}

@Composable
fun SearchingCardItem(
    searchData: SearchDataClass,
    searchTypeSelected: String,
    onClick: (Int) -> Unit
) {

    val url = Constants.IMAGE_BASE_URL + searchData.posterPath

    val errorImage = when (searchTypeSelected) {
        Categories.People.title -> R.drawable.unknown_male
        else -> R.drawable.film_not_found
    }

    Card(
        modifier = Modifier
            .width(190.dp)
            .clickable {
                onClick(searchData.id)
            },
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
                placeholder = painterResource(R.drawable.loading_image),
                error = painterResource(errorImage)
            )
            Column(
                Modifier
                    .background(CardContainerColor)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThirdTitleTextItem(
                    searchData.name,
                    TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}