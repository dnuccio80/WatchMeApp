package com.example.watchme.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.PercentageVisualItem
import com.example.watchme.ui.theme.AlphaButtonColor
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.NegativeVoteColor
import com.example.watchme.ui.theme.PositiveVoteColor
import com.example.watchme.ui.theme.TextColor

@Composable
fun MovieDetailsScreen(innerPadding: PaddingValues, viewModel: AppViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(innerPadding)
    ) {
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RectangleShape
                ) {
                }
                Card(
                    shape = CircleShape,
                    modifier = Modifier.padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AlphaButtonColor,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "back button icon"
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = (Arrangement.SpaceEvenly),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("09/20/1972", color = Color.White)
                    Icon(
                        painterResource(R.drawable.ic_point),
                        tint = Color.White,
                        contentDescription = "point separator",
                        modifier = Modifier.size(12.dp)
                    )
                    Text("Drama, Crime", color = Color.White)
                    Icon(
                        painterResource(R.drawable.ic_point),
                        tint = Color.White,
                        contentDescription = "point separator",
                        modifier = Modifier.size(12.dp)
                    )
                    Text("2h 55m", color = Color.White)
                }
                TitleTextItem("Movie / Serie Title")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    items(10){
                        TestTextLazyRow("Testing")
                    }
                }
                HorizontalDivider(Modifier.fillMaxWidth())
                Text(
                    "Text Description overview daslñasd kjladsñk dlkjdasjd dakjlasdkjl adskjlsalñk djklñdaskñjll",
                    color = Color.White,
                    fontSize = 14.sp
                )
                TitleSubtitleItem("Release Date:", "1972")
                TitleSubtitleItem("Genre:", "Drama, Crime")
                TitleSubtitleItem("Rating:", "12+")
            }
        }
    }
}

@Composable
fun TitleTextItem(text: String) {
    Text(
        text,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TitleSubtitleItem(title: String, subtitle: String) {
    Column {
        Text(title, color = Color.Gray, fontSize = 14.sp)
        Text(subtitle, color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun TestTextLazyRow(text:String){
    Text(text, color = Color.White)
}