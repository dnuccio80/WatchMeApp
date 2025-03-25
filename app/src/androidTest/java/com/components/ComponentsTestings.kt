package com.components

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import com.example.watchme.app.ui.MediaSection
import com.example.watchme.app.ui.dataClasses.VideoDataClass
import com.example.watchme.app.ui.screens.SeriesOverviewSection
import com.example.watchme.app.ui.screens.SeriesRecommendationsSection
import org.junit.Rule
import org.junit.Test

class ComponentsTestings {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ifSeriesOverviewSectionGetEmptyOrNullOverview_ThenShowNoOverviewText() {
        composeTestRule.setContent {
            SeriesOverviewSection(null)
        }
        composeTestRule.onNodeWithTag("OverviewSeriesDetails")
            .assertTextContains("overview", substring = true, ignoreCase = true)
    }

    @Test
    fun ifSeriesRecommendationsSectionGetEmptyOrNullRecommendationsList_thenShowNoResultsFoundText() {
        composeTestRule.setContent {
            SeriesRecommendationsSection(
                null,
                onClick = { }
            )
        }

        composeTestRule.onNodeWithTag("SeriesRecommendationsBodyText").assertExists()
    }

    @Test
    fun ifMediaSectionGetEmptyOrNullImageListAndSeriesVideosList_thenShowNoResultsFoundText() {
        composeTestRule.setContent {
            MediaSection(
                null,
                null
            )
        }
        composeTestRule.onNodeWithTag("MediaSectionNoResultsFoundBodyText").assertExists()
    }

    @Test
    fun ifMediaSectionGetEmptyOrNullImageListButASeriesVideosList_thenShowOnlySeriesVideoList(){
        composeTestRule.setContent {
            MediaSection(
                null,
                listOf(VideoDataClass(
                    name = "testing",
                    key = "testing",
                    size = 2
                ))
            )
        }
        composeTestRule.onNodeWithTag("MediaSectionImageAndVideosColumn").assertExists()
    }

}