package com.example.watchme.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.watchme.AppViewModel
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.ui.theme.AlphaButtonColor
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor

@Composable
fun TestScreen(innerPadding: PaddingValues, viewModel: AppViewModel) {

    val rating by viewModel.rating.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {

        var sliderValue by rememberSaveable { mutableStateOf(0.5f) }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Absolute.SpaceEvenly) {
                Button(
                    onClick = {
                        viewModel.rateSeriesEpisodes(
                            value = sliderValue,
                            seriesId = 1434,
                            episodeNumber = 1,
                            seasonNumber = 1
                        )
                    },
                ) {
                    BodyTextItem("SEND")
                }

                Button(
                    onClick = {
                        viewModel.deleteRateSeriesEpisodes(1434, 1, 1)
                    },
                ) {
                    BodyTextItem("DELETE")
                }
            }

            BodyTextItem(rating?.statusMessage.toString())
        }
    }
}