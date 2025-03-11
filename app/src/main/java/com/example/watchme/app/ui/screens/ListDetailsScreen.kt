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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.BackButton
import com.example.watchme.app.ui.BackdropImageItem
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.dataClasses.ItemsListDetailDataClass
import com.example.watchme.core.Categories
import com.example.watchme.core.Routes
import com.example.watchme.core.ShowListType
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor
import com.example.watchme.ui.theme.ThumbColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    listId: Int,
    navController: NavHostController
) {

    val listDetails by viewModel.listDetails.collectAsState()

    viewModel.getListDetails(listId)

    val filterList = listOf(
        ShowListType.All.type,
        ShowListType.Movies.type,
        ShowListType.Series.type
    )

    var showMenu by rememberSaveable { mutableStateOf(false) }
    var filterButtonText by rememberSaveable { mutableStateOf(filterList[0]) }

    val filteredList = when(filterButtonText){
        ShowListType.All.type -> listDetails?.items
        ShowListType.Movies.type -> listDetails?.items?.filter { it.mediaType == Categories.Movies.mediaType }
        ShowListType.Series.type -> listDetails?.items?.filter { it.mediaType == Categories.TvSeries.mediaType }
        else -> listDetails?.items
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                BackdropImageItem(listDetails?.posterPath.orEmpty())
                BackButton() { navController.popBackStack() }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    colors = CardDefaults.cardColors(
                        containerColor = CardContainerColor
                    ),
                    shape = RectangleShape
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        listDetails?.name?.let { SecondTitleTextItem(it) }
                    }
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listDetails?.description?.let {
                    SecondTitleTextItem(
                        it,
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    listDetails?.createdBy?.let {
                        ThirdTitleTextItem(
                            "${stringResource(R.string.created_by)} $it",
                            textAlign = TextAlign.Start,
                            hasMaxWidth = false,
                            color = Color.Gray
                        )
                    }
                }
                ListFilterButton(
                    showMenu,
                    filterButtonText,
                    filterList,
                    onDismiss = { showMenu = false },
                    onButtonClick = { showMenu = true },
                    onClick = {
                        showMenu = false
                        filterButtonText = it
                    })
                Spacer(Modifier.size(0.dp))
                if (filteredList?.isEmpty() == true) {
                    BodyTextItem(stringResource(R.string.no_results_found))
                } else {
                    FlowRow(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        filteredList?.forEach {
                            ListDetailCardItem(it) { id, mediaType ->
                                if (mediaType == Categories.Movies.mediaType) {
                                    navController.navigate(Routes.MovieDetails.createRoute(id))
                                } else if (mediaType == Categories.TvSeries.mediaType) {
                                    navController.navigate(Routes.SeriesDetails.createRoute(id))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListDetailCardItem(listItem: ItemsListDetailDataClass, onClick: (Int, String) -> Unit) {
    val imageUrl = Constants.IMAGE_BASE_URL + listItem.posterPath

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(listItem.id, listItem.mediaType)
            }, colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.movie_image),
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.backdrop_path_not_found),
        )
    }
}

@Composable
fun ListFilterButton(
    showMenu: Boolean,
    buttonText: String,
    list: List<String>,
    onDismiss: () -> Unit,
    onButtonClick: () -> Unit,
    onClick: (String) -> Unit
) {

    Column {
        Button(
            onClick = { onButtonClick() },
            shape = RoundedCornerShape(4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ThumbColor
            ),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BodyTextItem(buttonText)
                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "open menu button icon")
            }
        }
        FilterDropdownMenu(
            show = showMenu,
            list = list,
            onDismiss = { onDismiss() },
            onClick = {
                onClick(it)
            })
    }
}

@Composable
fun FilterDropdownMenu(
    show: Boolean,
    list: List<String>,
    onDismiss: () -> Unit,
    onClick: (String) -> Unit
) {

    if (!show) return

    DropdownMenu(
        expanded = true, onDismissRequest = { onDismiss() }, modifier = Modifier.background(
            ThumbColor
        )
    ) {
        list.forEach {
            DropdownMenuItem(text = { BodyTextItem(it) }, onClick = { onClick(it) })
        }
    }
}