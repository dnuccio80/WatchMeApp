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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.watchme.app.ui.AccountHeader
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.dataClasses.ListDataClass
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor
import com.example.watchme.ui.theme.DialogContainerColor
import com.example.watchme.ui.theme.ThumbColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController
) {

    val lists by viewModel.myLists.collectAsState()

    viewModel.getMyLists()

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
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    SecondTitleTextItem(stringResource(R.string.my_lists), textAlign = TextAlign.Start, hasMaxWidth = false)
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ThumbColor
                        ),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        BodyTextItem(stringResource(R.string.new_list))
                    }

                }
                Spacer(Modifier.size(0.dp))
                if(lists.isNullOrEmpty()) {
                    BodyTextItem(stringResource(R.string.no_results_found), textAlign = TextAlign.Center)
                } else {
                    FlowRow(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        lists?.forEach {
                            ListsCardItem(it) {  }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListsCardItem(list: ListDataClass, onClick: (Int) -> Unit) {
    val imageUrl = Constants.IMAGE_BASE_URL + list.posterPath

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(list.id)
            }, colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(R.string.movie_image),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.backdrop_path_not_found),
            )
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = CardContainerColor
            ),
                shape = RectangleShape
            ) {
                BodyTextItem(list.name.toString(), textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(vertical = 8.dp, horizontal =  4.dp))
            }
        }

    }
}