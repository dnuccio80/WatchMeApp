package com.example.watchme.app.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.watchme.R
import com.example.watchme.core.constants.Constants
import com.example.watchme.ui.theme.AlphaButtonColor
import com.example.watchme.ui.theme.DialogContainerColor
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
fun ThirdTitleTextItem(
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier
) {
    Text(
        text,
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        textAlign = textAlign
    )
}

@Composable
fun LazyRowItemText(
    text: String,
    selectedText: String,
    onClick: (String, Int, Float) -> Unit
) {

    val isSelectedText = text.uppercase() == selectedText.uppercase()

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

                if(isSelectedText){
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
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = if (textAlign != TextAlign.Start) modifier.fillMaxWidth() else modifier,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
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
fun BackButton() {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .size(60.dp)
            .padding(16.dp),
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
            error = painterResource(R.drawable.image_not_found)
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

    if(subtitle == null) return

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
            shape = RoundedCornerShape(8.dp)
        ) {
            BodyTextItem(stringResource(R.string.decline))
        }
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = { onConfirm() },
            shape = RoundedCornerShape(8.dp)
        ) {
            BodyTextItem(stringResource(R.string.confirm))
        }
    }
}
