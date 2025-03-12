package com.example.watchme.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.AccountHeader
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.SubtitleBigBodyTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.core.Routes
import com.example.watchme.ui.theme.AppBackground

@Composable
fun AccountScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController
) {
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
            AccountHeader(viewModel) { navController.popBackStack() }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SecondTitleTextItem("Stats")
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    SubtitleBigBodyTextItem("Total Edits", "20")
                    SubtitleBigBodyTextItem("Total Ratings", "35")
                }
                ListSection(
                    stringResource(R.string.my_favorites),
                    stringResource(R.string.see_favorites),
                    painterResource(R.drawable.favorite_background),
                    onClick = { navController.navigate(Routes.WatchList.route) }
                )
                ListSection(
                    stringResource(R.string.watchlist),
                    stringResource(R.string.see_watchlist),
                    painterResource(R.drawable.watchlist_background),
                    onClick = { navController.navigate(Routes.WatchList.route) }
                )
                ListSection(
                    stringResource(R.string.my_lists),
                    stringResource(R.string.see_lists),
                    painterResource(R.drawable.lists_background),
                    onClick = { navController.navigate(Routes.Lists.route) }
                )
                ListSection(
                    stringResource(R.string.my_ratings),
                    stringResource(R.string.see_ratings),
                    painterResource(R.drawable.ratings_background),
                    onClick = { navController.navigate(Routes.Ratings.route) }
                )
            }
        }
    }
}

@Composable
fun ListSection(title: String, buttonText: String, imageBackground: Painter, onClick: () -> Unit) {
    Spacer(Modifier.size(0.dp))
    SecondTitleTextItem(title.uppercase(), textAlign = TextAlign.Start)
    ImageBackgroundWithButtonItem(
        imageBackground,
        buttonText,
        onClick = { onClick() }
    )
}

@Composable
fun ImageBackgroundWithButtonItem(imageUrl: Painter, buttonText: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            imageUrl,
            modifier = Modifier.fillMaxWidth(),
            contentDescription = "background rating image",
            contentScale = ContentScale.Crop,
            alpha = .5f
        )
        Button(
            onClick = { onClick() },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp)
        ) {
            ThirdTitleTextItem(buttonText.uppercase(), hasMaxWidth = false)
        }
    }
}