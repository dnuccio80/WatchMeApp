package com.example.watchme.app.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.watchme.R
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AlphaButtonColor
import com.example.watchme.ui.theme.Pink40

@Composable
fun TitleTextItem(text: String) {
    Text(
        text.uppercase(),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SecondTitleTextItem(text: String, textAlign: TextAlign = TextAlign.Center) {
    Text(
        text,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        textAlign = textAlign
    )
}

@Composable
fun ThirdTitleTextItem(text:String, textAlign: TextAlign = TextAlign.Center){
    Text(
        text,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        textAlign = textAlign
    )
}

@Composable
fun BodyTextItem(text: String, textAlign: TextAlign = TextAlign.Start) {
    Text(text, style = MaterialTheme.typography.bodyMedium, modifier = if(textAlign != TextAlign.Start) Modifier.fillMaxWidth() else Modifier, textAlign = textAlign)
}

@Composable
fun BackButton() {
    Card(
        shape = CircleShape,
        modifier = Modifier.size(60.dp).padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AlphaButtonColor,
            contentColor = Color.White
        )
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "back button icon"
        )
    }
}

@Composable
fun BackdropImageItem(movieDetails: State<DetailsMovieDataClass?>) {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RectangleShape
    ) {
        AsyncImage(
            model = Constants.BASE_URL + movieDetails.value?.backdropImage,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = "movie image",
        )
    }
}

@Composable
fun PointSeparatorIcon(){
    Icon(
        painterResource(R.drawable.ic_point),
        tint = Color.White,
        contentDescription = "point separator",
        modifier = Modifier.size(6.dp)
    )
}

@Composable
fun PercentageVisualItem(
    percentage: Int,
    modifier: Modifier = Modifier,
    cardColor: Color
) {

    Card(
        modifier = modifier.size(38.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = cardColor
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
                    Text(percentage.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                    Text("%", textAlign = TextAlign.Start, color = Color.White, fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun TitleSubtitleItem(title: String, subtitle: String, isClickable: Boolean = false) {
    val context = LocalContext.current
    Column {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        if (isClickable) {
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Pink40,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(subtitle))
                    context.startActivity(intent)
                }
            )
        } else {
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


