package com.example.watchme.app.ui.screens

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.TitleTextItem
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor
import com.example.watchme.ui.theme.LightBlueColor
import org.w3c.dom.Text
import retrofit2.http.Body

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccountScreen(innerPadding: PaddingValues, viewModel: AppViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            RatingHeader()
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SecondTitleTextItem(
                    stringResource(R.string.my_ratings),
                    textAlign = TextAlign.Start
                )
//                if (moviesRecommendations == null) {
//                    BodyTextItem(stringResource(R.string.no_results_found))
//                    return
//                }
                FlowRow(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
//                    moviesRecommendations.forEach {
//                        RecommendationCardItem(it) { movieId -> onClick(movieId) }
//                    }
                }
            }
        }
    }
}

@Composable
fun RatingCardItem() {
//    val imageUrl = Constants.IMAGE_BASE_URL + movies.poster
//
//    Card(
//        modifier = Modifier
//            .width(120.dp)
//            .height(160.dp)
//            .clickable {
//                onClick(movies.id)
//            }, colors = CardDefaults.cardColors(
//            containerColor = Color.Black
//        ),
//        shape = RoundedCornerShape(4.dp),
//        elevation = CardDefaults.cardElevation(15.dp)
//    ) {
//        AsyncImage(
//            model = imageUrl,
//            contentDescription = stringResource(R.string.movie_image),
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier
//                .fillMaxSize()
//        )
//    }
}

@Composable
private fun RatingHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.background_account),
            contentDescription = "account background",
            contentScale = ContentScale.Crop
        )
        Row(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = LightBlueColor
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp
                ),
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "D",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    SecondTitleTextItem("Damian", hasMaxWidth = false)
                    BodyTextItem("Member since January 2025", color = Color.LightGray)
                }
                BodyTextItem(
                    "Apasionado por la programación, la naturaleza y el deporte",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}