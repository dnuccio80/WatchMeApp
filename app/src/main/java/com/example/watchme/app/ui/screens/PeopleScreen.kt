package com.example.watchme.app.ui.screens

import android.util.Log
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.SectionSelectionItem
import com.example.watchme.app.ui.TitleSubtitleItem
import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
import com.example.watchme.app.ui.dataClasses.PeopleMoviesInterpretationDataClass
import com.example.watchme.core.Interpretations
import com.example.watchme.core.Sections
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor

@Composable
fun PeopleDetailsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    personId: Int
) {

    val scrollState = rememberScrollState()
    var sectionSelected by rememberSaveable { mutableStateOf(Sections.Biography.title) }

    val peopleDetails by viewModel.peopleDetails.collectAsState()
    val movieInterpretations by viewModel.peopleMovieInterpretations.collectAsState()

    viewModel.getPeopleDetailsById(personId)
    viewModel.getPeopleMovieInterpretationsById(personId)

    val sectionList = listOf(
        Sections.Biography.title,
        Sections.Movies.title,
        Sections.Series.title,
        Sections.Media.title,
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
            PeopleHeader(peopleDetails)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SectionSelectionItem(sectionList) { newSectionSelected ->
                    sectionSelected = newSectionSelected
                }
//                BiographySection(peopleDetails)
                MoviesInterpretationsSection(movieInterpretations)
            }

        }
    }
}

@Composable
fun MoviesInterpretationsSection(movieInterpretations: PeopleMoviesInterpretationDataClass?) {

    if (movieInterpretations == null) {
        BodyTextItem(stringResource(R.string.no_results_found))
        return
    }

    var filterInterpretationText by rememberSaveable { mutableStateOf(Interpretations.All.interpretation) }
    var showDropDownMenu by rememberSaveable { mutableStateOf(false) }

    val interpretationsList = listOf(
        Interpretations.All.interpretation,
        Interpretations.Cast.interpretation,
        Interpretations.Crew.interpretation,
    )

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Column {
            Button(
                onClick = { showDropDownMenu = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CardContainerColor
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyTextItem(filterInterpretationText)
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "options button",
                        tint = Color.White
                    )
                }
            }
            DropdownMenuByList(
                showDropDownMenu,
                interpretationsList,
                onDismiss = { showDropDownMenu = false },
                onClick = {
                    showDropDownMenu = false
                    filterInterpretationText = it
                })
        }

        FilterInterpretations(movieInterpretations, filterInterpretationText)
    }

}

@Composable
fun FilterInterpretations(movieInterpretations: PeopleMoviesInterpretationDataClass, filter:String) {

    if(filter != Interpretations.Crew.interpretation) {
        SecondTitleTextItem(stringResource(R.string.cast), textAlign = TextAlign.Start)
        if (movieInterpretations.cast.isEmpty()) {
            BodyTextItem(stringResource(R.string.no_results_found))
        } else {
            MoviesRecommendationsSection(movieInterpretations.cast) { }
        }
    }

    if(filter == Interpretations.Cast.interpretation) return

    SecondTitleTextItem(stringResource(R.string.crew), textAlign = TextAlign.Start)
    if (movieInterpretations.crew.isEmpty()) {
        BodyTextItem(stringResource(R.string.no_results_found))
    } else {
        MoviesRecommendationsSection(movieInterpretations.crew) { }
    }
}

@Composable
fun DropdownMenuByList(
    show: Boolean,
    list: List<String>,
    onDismiss: () -> Unit,
    onClick: (String) -> Unit
) {
    if (!show) return
    DropdownMenu(
        expanded = true,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(CardContainerColor)
    ) {
        list.forEach { item ->
            DropdownMenuItem(
                text = { BodyTextItem(item) },
                onClick = { onClick(item) },
            )
        }
    }
}

@Composable
fun PeopleHeader(peopleDetails: PeopleDetailsDataClass?) {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                TitleSubtitleItem(stringResource(R.string.death_day), it)
            }

            peopleDetails?.placeOfBirth?.let {
                TitleSubtitleItem(stringResource(R.string.place_of_birth), it)
            }
        }
    }
}

@Composable
fun BiographySection(peopleDetails: PeopleDetailsDataClass?) {

    if (peopleDetails?.biography == null) return

    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SecondTitleTextItem(stringResource(R.string.biography))
        BodyTextItem(peopleDetails.biography)
    }
}