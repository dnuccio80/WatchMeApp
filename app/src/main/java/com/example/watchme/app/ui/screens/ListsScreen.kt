package com.example.watchme.app.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.AccountHeader
import com.example.watchme.app.ui.BodyTextItem
import com.example.watchme.app.ui.SecondTitleTextItem
import com.example.watchme.app.ui.ThirdTitleTextItem
import com.example.watchme.app.ui.dataClasses.ListDataClass
import com.example.watchme.core.Routes
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AlphaContrastColor
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.CardContainerColor
import com.example.watchme.ui.theme.ThumbColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListsScreen(
    innerPadding: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController
) {

    val lists by viewModel.myLists.collectAsState()
    val listRequest by viewModel.createListRequest.collectAsState()

    var showDialog by rememberSaveable { mutableStateOf(false) }

    var listName by rememberSaveable { mutableStateOf("") }
    var listDescription by rememberSaveable { mutableStateOf("") }

    viewModel.getMyLists()
    val context = LocalContext.current

    LaunchedEffect(listRequest) {
        if (listRequest?.success == true) {
            Toast.makeText(
                context,
                context.getString(R.string.list_added_successfully),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearCreateListRequest()
        }
    }

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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SecondTitleTextItem(
                        stringResource(R.string.my_lists),
                        textAlign = TextAlign.Start,
                        hasMaxWidth = false
                    )
                    Button(
                        onClick = { showDialog = true },
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
                if (lists.isNullOrEmpty()) {
                    BodyTextItem(
                        stringResource(R.string.no_results_found),
                        textAlign = TextAlign.Center
                    )
                } else {
                    FlowRow(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        lists?.forEach {
                            ListsCardItem(it) { listId ->
                                navController.navigate(
                                    Routes.ListDetails.createRoute(
                                        listId
                                    )
                                )
                            }
                        }
                    }
                }
                CreateListDialog(showDialog, onDismiss = { showDialog = false },
                    listName = listName,
                    listDescription = listDescription,
                    onListNameChange = { listName = it },
                    onListDescriptionChange = { listDescription = it },
                    onCreateButtonClick = {
                        if (listName.isNotEmpty() && listDescription.isNotEmpty()) {
                            showDialog = false
                            viewModel.createList(name = listName, description = listDescription)
                        }
                    })
            }
        }
    }
}

@Composable
fun CreateListDialog(
    show: Boolean,
    listName: String,
    listDescription: String,
    onDismiss: () -> Unit,
    onListNameChange: (String) -> Unit,
    onListDescriptionChange: (String) -> Unit,
    onCreateButtonClick: () -> Unit
) {

    if (!show) return

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = CardContainerColor
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SecondTitleTextItem(
                        stringResource(R.string.create_list),
                        textAlign = TextAlign.Start,
                        hasMaxWidth = false
                    )
                    Icon(
                        Icons.Filled.Close,
                        modifier = Modifier.clickable {
                            onDismiss()
                        },
                        contentDescription = "close dialog icon",
                        tint = Color.White
                    )

                }

                Spacer(Modifier.size(4.dp))
                HorizontalDivider(Modifier.fillMaxWidth(), thickness = 2.dp, color = Color.White)
                Spacer(Modifier.size(16.dp))
                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ListTextField(listName, stringResource(R.string.list_name)) {
                        onListNameChange(
                            it
                        )
                    }
                    ListTextField(
                        listDescription,
                        stringResource(R.string.list_description)
                    ) { onListDescriptionChange(it) }
                    Spacer(Modifier.size(0.dp))
                    Button(
                        onClick = { onCreateButtonClick() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ThumbColor
                        ),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        ThirdTitleTextItem(stringResource(R.string.create_list).uppercase())
                    }
                }
            }
        }
    }
}

@Composable
fun ListTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { BodyTextItem(label, maxLines = 1) },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = AlphaContrastColor,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun ListsCardItem(listItem: ListDataClass, onClick: (Int) -> Unit) {
    val imageUrl = Constants.IMAGE_BASE_URL + listItem.posterPath

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable {
                onClick(listItem.id)
            }, colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(R.string.movie_image),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.backdrop_path_not_found),
            )
            Card(
                modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    containerColor = CardContainerColor
                ),
                shape = RectangleShape
            ) {
                BodyTextItem(
                    listItem.name.toString(),
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                )
            }
        }

    }
}