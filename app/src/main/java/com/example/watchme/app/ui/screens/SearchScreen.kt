package com.example.watchme.app.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.AsyncImage
import com.example.watchme.R
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor
import retrofit2.http.Body
import kotlin.math.truncate

@Composable
fun SearchScreen(innerPadding: PaddingValues) {

    var textFieldValue by rememberSaveable { mutableStateOf("") }

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
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.search_by_title)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "search icon") },
            )
            HorizontalDivider(Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(20) {
                    SearchingCardItem()
                }
            }
        }
    }
}

@Composable
fun SearchingCardItem() {
    Card(
        modifier = Modifier
            .width(190.dp)
            .clickable {
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardContainerColor
        )
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Image(painterResource(R.drawable.image_not_found), modifier = Modifier.fillMaxSize().height(max(200.dp,250.dp)), contentDescription = "test image", contentScale = ContentScale.Crop)
//            AsyncImage(
//                model = url,
//                contentDescription = stringResource(R.string.image_cast),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .height(max(200.dp, 250.dp)),
//                contentScale = ContentScale.Crop,
//                placeholder = painterResource(R.drawable.unknown_male),
//                error = painterResource(R.drawable.unknown_male)
//            )
            Column(
                Modifier
                    .background(CardContainerColor)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThirdTitleTextItem("Titulo de la serie / pelicula", TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis)
                BodyTextItem(
                    "AÑO LANZ - GENRES TYPES",TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}