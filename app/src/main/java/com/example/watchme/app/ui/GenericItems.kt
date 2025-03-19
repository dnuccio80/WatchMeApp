package com.example.watchme.app.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.watchme.AppViewModel
import com.example.watchme.R
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.CreditsDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.example.watchme.app.ui.dataClasses.TypeProviderDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
import com.example.watchme.app.ui.screens.CastCreditsItem
import com.example.watchme.app.ui.screens.CrewCreditsItem
import com.example.watchme.app.ui.screens.ProviderCardItem
import com.example.watchme.core.Categories
import com.example.watchme.core.MediaItem
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AlphaButtonColor
import com.example.watchme.ui.theme.ButtonContainerColor
import com.example.watchme.ui.theme.CardContainerColor
import com.example.watchme.ui.theme.DialogContainerColor
import com.example.watchme.ui.theme.LightBlueColor
import com.example.watchme.ui.theme.NegativeVoteColor
import com.example.watchme.ui.theme.Pink40
import com.example.watchme.ui.theme.ThumbColor

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
fun SecondTitleTextItem(
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    hasMaxWidth: Boolean = true
) {
    Text(
        text,
        modifier = if (hasMaxWidth) Modifier.fillMaxWidth() else Modifier,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
    )
}


@Composable
fun ThirdTitleTextItem(
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    hasMaxWidth: Boolean = true,
    color: Color = Color.White
) {
    Text(
        text,
        modifier = if (hasMaxWidth) modifier.fillMaxWidth() else Modifier,
        style = MaterialTheme.typography.titleMedium,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun BigBodyItem(body: String, color: Color = Color.Cyan) {
    Text(
        body,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp,
        color = color
    )
}

@Composable
fun SubtitleBigBodyTextItem(subtitle: String, body: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ThirdTitleTextItem(subtitle, hasMaxWidth = false)
        BigBodyItem(body)
    }
}

@Composable
fun LazyRowItemText(
    text: String,
    selectedText: String,
    onClick: (String, Int, Float) -> Unit
) {

    val isSelectedText = text.lowercase() == selectedText.lowercase()

    var textWidth by rememberSaveable { mutableStateOf(0) }
    var xPos by rememberSaveable { mutableStateOf(0f) }

    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        fontWeight = if (isSelectedText) FontWeight.ExtraBold else FontWeight.Normal,
        modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
                textWidth = layoutCoordinates.size.width
                xPos = layoutCoordinates.positionInParent().x

                if (isSelectedText) {
                    onClick(text, textWidth, xPos)
                }
            }
            .clickable {
                onClick(text, textWidth, xPos)
            }
    )
}

@Composable
fun BodyTextItem(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    color: Color = Color.White
) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = if (textAlign != TextAlign.Start) modifier.fillMaxWidth() else modifier,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        color = color
    )
}

@Composable
fun LabelSmallItem(text: String, textAlign: TextAlign = TextAlign.Start) {
    Text(text, style = MaterialTheme.typography.labelSmall, textAlign = textAlign)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeaderInfo(
    genres: List<String>,
    secondValue: String,
    modifier: Modifier = Modifier
) {

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = (Arrangement.Center),
    ) {
        BodyTextItem(
            genres.joinToString(separator = ", "),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(Modifier.size(8.dp))
        PointSeparatorIcon(Modifier.align(Alignment.CenterVertically))
        Spacer(Modifier.size(8.dp))
        Text(
            secondValue,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }

}

@Composable
fun BackButton(onClick: () -> Unit) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .size(60.dp)
            .padding(16.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = AlphaButtonColor,
            contentColor = Color.White
        )
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            modifier = Modifier.fillMaxSize(),
            contentDescription = stringResource(R.string.back_button_icon)
        )
    }
}

@Composable
fun CircularButtonIcon(icon: ImageVector, tint: Color, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonContainerColor
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(16.dp)
    ) {
        Icon(icon, contentDescription = "icon button", tint = tint)
    }
}

@Composable
fun RoundedButtonWithText(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonContainerColor
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(16.dp)
    ) {
        BodyTextItem(text, color = Color.White)
    }
}

@Composable
fun RateDialog(
    show: Boolean,
    percentage: Int,
    mediaItem: MediaItem,
    viewModel: AppViewModel,
    onDismiss: () -> Unit,
    onRatedButtonClicked: () -> Unit,
    onDeleteRateButtonClicked: () -> Unit
) {

    var sliderValue by rememberSaveable { mutableStateOf(7.0f) }

    val sliderValueInt = sliderValue.times(10).toInt()
    val activeTrackColor = viewModel.getPercentageColor(sliderValueInt)

    if (!show) return

    var isSeriesEpisode = false

    val rateCall = when (mediaItem.category) {
        is Categories.Movies -> {
            viewModel::rateMovie
        }

        is Categories.TvSeries -> {
            viewModel::rateSeries
        }

        is Categories.TvEpisodes -> {
            isSeriesEpisode = true
            viewModel::rateSeries // this does not a function, it is only because for avoid errors
        }

        else -> {
            return
        }
    }

    val deleteRateCall = when (mediaItem.category) {
        is Categories.Movies -> {
            viewModel::deleteRateMovie
        }

        is Categories.TvSeries -> {
            viewModel::deleteRateSeries
        }

        is Categories.TvEpisodes -> {
            viewModel::deleteRateSeries
        }

        else -> {
            return
        }
    }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardContainerColor
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ThirdTitleTextItem(stringResource(R.string.rating), hasMaxWidth = false)
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "close icon",
                        modifier = Modifier.clickable { onDismiss() })
                }
                HorizontalDivider(color = Color.White)
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleSubtitleItem(
                        "${stringResource(R.string.question_rating)} ${mediaItem.title}?",
                        "$percentage% ${stringResource(R.string.user_score)}"
                    )
                }
                Slider(
                    value = sliderValue,
                    onValueChange = { newValue ->
                        val valueFormated = String.format("%.1f", newValue).toFloat()
                        sliderValue = valueFormated
                    },
                    valueRange = 0.5f..10f,
                    steps = 18,
                    colors = SliderDefaults.colors(
                        thumbColor = activeTrackColor,
                        activeTrackColor = activeTrackColor,
                        inactiveTrackColor = AlphaButtonColor
                    )
                )
                ThirdTitleTextItem("$sliderValueInt - ${getRatingText(sliderValueInt)}")
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (isSeriesEpisode) {
                                viewModel.rateSeriesEpisodes(
                                    sliderValue,
                                    seriesId = mediaItem.id,
                                    episodeNumber = mediaItem.episodeNumber,
                                    seasonNumber = mediaItem.seasonNumber
                                )
                            } else {
                                rateCall(sliderValue, mediaItem.id)
                            }
                            onRatedButtonClicked()
                            onDismiss()
                        },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ThumbColor
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(16.dp)
                    ) {
                        BodyTextItem(stringResource(R.string.rate))
                    }
                    Button(
                        onClick = {
                            if (isSeriesEpisode) {
                                viewModel.deleteRateSeriesEpisodes(
                                    mediaItem.id,
                                    mediaItem.episodeNumber,
                                    mediaItem.seasonNumber
                                )
                            } else {
                                deleteRateCall(mediaItem.id)
                            }
                            onDeleteRateButtonClicked()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),

                        ) {
                        ThirdTitleTextItem(
                            stringResource(R.string.clear_rating),
                            color = NegativeVoteColor,
                            hasMaxWidth = false
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun getRatingText(percentage: Int): String {
    return when (percentage) {
        in 0..9 -> {
            stringResource(R.string.dumpster_fire)
        }

        in 10..19 -> {
            stringResource(R.string.absolute_trash)
        }

        in 20..29 -> {
            stringResource(R.string.garbage)
        }

        in 30..39 -> {
            stringResource(R.string.truly_bad)
        }

        in 40..49 -> {
            stringResource(R.string.not_good)
        }

        in 50..59 -> {
            stringResource(R.string.passable)
        }

        in 60..69 -> {
            stringResource(R.string.its_alright)
        }

        in 70..79 -> {
            stringResource(R.string.pretty_decent)
        }

        in 80..89 -> {
            stringResource(R.string.really_good)
        }

        in 90..99 -> {
            stringResource(R.string.greatness)
        }

        else -> {
            stringResource(R.string.champion)
        }
    }
}


@Composable
fun BackdropImageItem(imageUrl: String) {

    val fullUrl = Constants.IMAGE_BASE_URL + imageUrl

    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RectangleShape
    ) {
        AsyncImage(
            model = fullUrl,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.movie_image),
            error = painterResource(R.drawable.backdrop_path_not_found)
        )
    }
}

@Composable
fun PointSeparatorIcon(modifier: Modifier = Modifier) {
    Icon(
        painterResource(R.drawable.ic_point),
        tint = Color.White,
        contentDescription = stringResource(R.string.point_separator),
        modifier = modifier.size(6.dp)
    )
}

@Composable
fun PercentageVisualItem(
    percentage: Int,
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    cardColor: Color,
) {

    Card(
        modifier = modifier.size(size),
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
    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    Column {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        if (isClickable) {
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Pink40,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    showConfirmDialog = true
                }
            )
        } else {
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
    ConfirmDeclineDialog(
        show = showConfirmDialog,
        text = stringResource(R.string.open_website),
        onDismiss = { showConfirmDialog = false },
        onConfirm = {
            showConfirmDialog = false
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(subtitle))
            context.startActivity(intent)
        }
    )
}

@Composable
fun TitleSubtitleItemWithNullability(title: String, subtitle: String?) {

    if (subtitle == null) return

    if (subtitle.contains("null")) {
        TitleSubtitleItem(title, stringResource(R.string.unknown))
        return
    } else {
        TitleSubtitleItem(title, subtitle)
    }
}

@Composable
fun NextPreviousButtonsRow(
    index: Int,
    size: Int,
    modifier: Modifier,
    onButtonClick: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Card(
            modifier = Modifier.size(30.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = AlphaButtonColor,
                contentColor = Color.White
            ),
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.show_previous_image),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        val currentIndex = (index - 1 + size) % size
                        onButtonClick(currentIndex)
                    }
            )
        }
        Card(
            modifier = Modifier.size(30.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = AlphaButtonColor,
                contentColor = Color.White
            )
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.show_next_image),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        val currentIndex = (index + 1) % size
                        onButtonClick(currentIndex)
                    }
            )
        }
    }
}

@Composable
fun ConfirmDeclineDialog(
    show: Boolean,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    if (!show) return
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DialogContainerColor
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painterResource(R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.app_logo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 64.dp)
                )
                BodyTextItem(text, textAlign = TextAlign.Center)
                ConfirmDeclineButtons(onDecline = { onDismiss() }, onConfirm = { onConfirm() })
            }
        }
    }
}

@Composable
fun ConfirmDeclineButtons(onDecline: () -> Unit, onConfirm: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = (Arrangement.Center)) {
        Button(
            onClick = { onDecline() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonContainerColor
            )
        ) {
            BodyTextItem(stringResource(R.string.decline))
        }
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = { onConfirm() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonContainerColor
            )
        ) {
            BodyTextItem(stringResource(R.string.confirm))
        }
    }
}

@Composable
fun RedCloseButton(modifier: Modifier, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = NegativeVoteColor,
            contentColor = Color.White
        ),
        modifier = modifier.clickable {
            onClick()
        },
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Icon(
            Icons.Filled.Close,
            tint = Color.White,
            contentDescription = "Delete list button",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun VideosSection(
    videos: List<VideoDataClass>?,
    imagesList: List<BackdropImageDataClass>?
) {

    if (videos.isNullOrEmpty()) return

    val context = LocalContext.current
    var index by rememberSaveable { mutableIntStateOf(0) }
    val size = videos.size
    val videoUrl = Constants.YOUTUBE_BASE_URL + videos[index].key
    val imageUrl = Constants.IMAGE_BASE_URL + imagesList?.get(index)?.filePath
    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SecondTitleTextItem(stringResource(R.string.videos))

        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    showConfirmDialog = true
                },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(16.dp),
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(R.string.image_cast),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.loading_image),
                    error = painterResource(R.drawable.film_not_found)
                )
                NextPreviousButtonsRow(
                    index = index, size = size, modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) { newIndex -> index = newIndex }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    colors = CardDefaults.cardColors(
                        containerColor = CardContainerColor
                    ),
                    shape = RectangleShape
                ) {
                    BodyTextItem(
                        videos[index].name,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                    )
                }
                ConfirmDeclineDialog(
                    show = showConfirmDialog,
                    text = stringResource(R.string.open_youtube),
                    onDismiss = { showConfirmDialog = false },
                    onConfirm = {
                        showConfirmDialog = false
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                        intent.setPackage("com.google.android.youtube")
                        context.startActivity(intent)
                    })
            }
        }
    }
}

@Composable
fun ImageListItem(imagesList: List<BackdropImageDataClass>?) {
    Box(Modifier.fillMaxWidth()) {

        val size = imagesList?.size ?: 0

        if (size == 0) return
        var index by rememberSaveable { mutableIntStateOf(0) }
        val url = Constants.IMAGE_BASE_URL + imagesList?.get(index)?.filePath
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SecondTitleTextItem(stringResource(R.string.images))
            Box() {
                AsyncImage(
                    model = url,
                    contentDescription = stringResource(R.string.image_cast),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(R.drawable.loading_image),
                    error = painterResource(R.drawable.loading_image)
                )
                NextPreviousButtonsRow(
                    index = index, size = size, modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Center)
                ) { newIndex -> index = newIndex }
            }
        }

    }
}

@Composable
fun CreditsSection(credits: CreditsDataClass?, onClick: (Int) -> Unit) {

    if (credits == null) return

    if (credits.cast.isEmpty() && credits.crew.isEmpty()) {
        BodyTextItem(stringResource(R.string.no_results_found))
        return
    }

    if (credits.cast.isNotEmpty()) {
        SecondTitleTextItem(stringResource(R.string.cast))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(credits.cast) {
                CastCreditsItem(it) { personId -> onClick(personId) }

            }
        }
    }

    if (credits.crew.isNotEmpty()) {
        SecondTitleTextItem(stringResource(R.string.crew))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(credits.crew) {
                CrewCreditsItem(it) { personId -> onClick(personId) }
            }
        }
    }
}


@Composable
fun SectionSelectionItem(lazyList: List<String>, onItemClicked: (String) -> Unit) {

    var selectedText by rememberSaveable { mutableStateOf(lazyList[0]) }
    var textWidth by rememberSaveable { mutableStateOf(223) }
    var xPos by rememberSaveable { mutableStateOf(0f) }

    val lazyListState = rememberLazyListState()

    Column {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(32.dp), state = lazyListState) {
            items(lazyList) {
                LazyRowItemText(
                    it.uppercase(),
                    selectedText,
                ) { clickedText, newTextWidth, newXPos ->
                    selectedText = clickedText.lowercase()
                    onItemClicked(selectedText)
                    textWidth = newTextWidth
                    xPos = newXPos
                }
            }
        }

        Spacer(Modifier.size(4.dp))
        LazyHorizontalDividerItem(textWidth, xPos)
    }
}

@Composable
fun LazyHorizontalDividerItem(textWidth: Int, xPos: Float) {

    val cardWidth by animateDpAsState(
        targetValue = with(LocalDensity.current) { textWidth.toDp() },
        animationSpec = TweenSpec(200),
        label = "card width"
    )

    val xOffset by animateDpAsState(
        targetValue = with(LocalDensity.current) { xPos.toDp() },
        animationSpec = TweenSpec(durationMillis = 200),
        label = "x offset"
    )

    Column(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .height(6.dp)
                .width(cardWidth)
                .offset(x = xOffset),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) { }
        HorizontalDivider(Modifier.fillMaxWidth(), color = Color.White, thickness = 1.dp)
    }
}

@Composable
fun RatingSectionWithLists(
    mediaItem: MediaItem,
    viewModel: AppViewModel,
    isRated: Boolean,
    myRate: Float,
    addedToFavorites: Boolean,
    addedToWatchLater: Boolean,
    onFavoriteButtonClicked: () -> Unit,
    onWatchlistButtonClicked: () -> Unit,
    onRatedButtonClicked: () -> Unit,
    onDeleteRateButtonClicked: () -> Unit,
) {

    val percentage = (mediaItem.voteAverage.times(10)).toInt()
    val context = LocalContext.current

    var showDialog by rememberSaveable { mutableStateOf(false) }
    val ratingResponse by viewModel.rating.collectAsState()

    LaunchedEffect(ratingResponse) {
        viewModel.updateRatedMovies()
        viewModel.updateRatedSeries()
        ratingResponse?.let {
            if (it.success) {
                Toast.makeText(context, it.statusMessage, Toast.LENGTH_SHORT).show()
                viewModel.clearRatingResponse()
            }
        }
    }

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PercentageVisualItem(
                    percentage = percentage,
                    cardColor = viewModel.getPercentageColor(percentage),
                    size = 50.dp
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    ThirdTitleTextItem(stringResource(R.string.user), hasMaxWidth = false)
                    ThirdTitleTextItem(stringResource(R.string.score), hasMaxWidth = false)
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(
                    onClick = { showDialog = true },
                    elevation = ButtonDefaults.elevatedButtonElevation(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRated) ThumbColor else ButtonContainerColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    BodyTextItem(if (isRated) stringResource(R.string.rated) else stringResource(R.string.rate).uppercase())
                }
                if (isRated) {
                    val intRate = (myRate * 10).toInt()
                    BodyTextItem(
                        "${stringResource(R.string.your_rate)} $intRate%",
                        color = ThumbColor
                    )
                }
            }

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CircularButtonIcon(Icons.AutoMirrored.Filled.List, Color.White) { }
            CircularButtonIcon(
                Icons.Filled.Favorite,
                if (addedToFavorites) Color.Red else Color.White
            ) { onFavoriteButtonClicked() }
            CircularButtonIcon(
                Icons.Filled.Star,
                if (addedToWatchLater) Color.Yellow else Color.White
            ) { onWatchlistButtonClicked() }
        }

        RateDialog(
            show = showDialog,
            percentage = percentage,
            viewModel = viewModel,
            mediaItem = mediaItem,
            onRatedButtonClicked = { onRatedButtonClicked() },
            onDeleteRateButtonClicked = { onDeleteRateButtonClicked() },
            onDismiss = { showDialog = false },
        )
    }
}

@Composable
fun SimpleRatingSection(
    mediaItem: MediaItem,
    viewModel: AppViewModel,
    isRated: Boolean,
    myRate: Float,
    onRatedButtonClicked: () -> Unit,
    onDeleteRateButtonClicked: () -> Unit,
) {
    val percentage = (mediaItem.voteAverage.times(10)).toInt()
    val context = LocalContext.current

    var showDialog by rememberSaveable { mutableStateOf(false) }
    val ratingResponse by viewModel.rating.collectAsState()

    LaunchedEffect(ratingResponse) {
        ratingResponse?.let {
            if (it.success) {
                Toast.makeText(context, it.statusMessage, Toast.LENGTH_SHORT).show()
                viewModel.clearRatingResponse()
            }
            viewModel.updateRatedEpisodes()
        }
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PercentageVisualItem(
                percentage = percentage,
                cardColor = viewModel.getPercentageColor(percentage),
                size = 50.dp
            )
            ThirdTitleTextItem(stringResource(R.string.user_score), hasMaxWidth = false)
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Button(
                onClick = { showDialog = true },
                elevation = ButtonDefaults.elevatedButtonElevation(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRated) ThumbColor else ButtonContainerColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                BodyTextItem(if (isRated) stringResource(R.string.rated) else stringResource(R.string.rate).uppercase())
            }
            if (isRated) {
                val intRate = (myRate * 10).toInt()
                BodyTextItem(
                    "${stringResource(R.string.your_rate)} $intRate%",
                    color = ThumbColor
                )
            }
        }

    }

    RateDialog(
        show = showDialog,
        percentage = percentage,
        viewModel = viewModel,
        mediaItem = mediaItem,
        onRatedButtonClicked = { onRatedButtonClicked() },
        onDeleteRateButtonClicked = { onDeleteRateButtonClicked() },
        onDismiss = { showDialog = false },
    )

}

@Composable
fun AccountHeader(viewModel: AppViewModel, onClick: () -> Unit) {

    val accountDetails by viewModel.accountDetails.collectAsState()

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
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton() { onClick() }
            Image(
                painterResource(R.drawable.ic_logo),
                contentDescription = stringResource(R.string.app_logo),
                Modifier.width(128.dp)
            )
        }

        Row(
            Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
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
                        accountDetails?.name?.first().toString(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                accountDetails?.name?.let { SecondTitleTextItem(it, hasMaxWidth = false) }
                accountDetails?.username?.let {
                    BodyTextItem(
                        "${stringResource(R.string.username)}: $it",
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun ProvidersSection(title: String, providers: TypeProviderDataClass?) {

    if (title.isEmpty()) return

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SecondTitleTextItem("${stringResource(R.string.watch_providers_title)} $title?")
        if(providers?.buy.isNullOrEmpty() && providers?.rent.isNullOrEmpty()){
            BodyTextItem(stringResource(R.string.no_results_found))
            return
        }
        if (!providers?.buy.isNullOrEmpty()) {
            val providersList = providers?.buy?.map {
                it?.let { provider ->
                    ProvidersDataClass(
                        logo = provider.logo,
                        providerName = provider.providerName,
                        providerId = provider.providerId
                    )
                }
            }
            ThirdTitleTextItem(stringResource(R.string.for_buy), textAlign = TextAlign.Start)
            if (providersList?.isNotEmpty() == true) FlowRowProviders(providersList)
        }
        if (!providers?.rent.isNullOrEmpty()) {
            val providersList = providers?.rent?.map {
                it?.let { provider ->
                    ProvidersDataClass(
                        logo = provider.logo,
                        providerName = provider.providerName,
                        providerId = provider.providerId
                    )
                }
            }
            ThirdTitleTextItem(stringResource(R.string.for_rent), textAlign = TextAlign.Start)
            if (providersList?.isNotEmpty() == true) FlowRowProviders(providersList)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowProviders(providers: List<ProvidersDataClass?>?) {

    if(providers.isNullOrEmpty()) return

    FlowRow(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        providers.forEach {
            if(it == null) return@forEach
            ProviderCardItem(it)
        }
    }
}

