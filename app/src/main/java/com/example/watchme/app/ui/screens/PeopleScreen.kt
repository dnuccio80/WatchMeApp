package com.example.watchme.app.ui.screens

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor

@Composable
fun PeopleDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    peopleId: Int
) {

    val scrollState = rememberScrollState()

    val peopleDetails by viewModel.peopleDetails.collectAsState()

    viewModel.getPeopleDetailsById(peopleId)

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
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    elevation = CardDefaults.cardElevation(16.dp),
                    border = BorderStroke(4.dp, CardContainerColor)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = Constants.IMAGE_BASE_URL + peopleDetails?.profilePath,
                        contentDescription = "people image",
                        error = painterResource(R.drawable.unknown_male),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    peopleDetails?.name?.let {
                        SecondTitleTextItem(it, textAlign = TextAlign.Start)
                    }
                    peopleDetails?.knownForDepartment?.let {
                        TitleSubtitleItem(stringResource(R.string.known_for_department), it)
                    }
                    peopleDetails?.birthday?.let {
                        TitleSubtitleItem(stringResource(R.string.birthday), it)
                    }
                    peopleDetails?.deathDay?.let {
                        TitleSubtitleItem(stringResource(R.string.death_day), it )
                    }

                    peopleDetails?.placeOfBirth?.let {
                        TitleSubtitleItem(stringResource(R.string.place_of_birth), it)
                    }

                }

            }
        }
    }
}