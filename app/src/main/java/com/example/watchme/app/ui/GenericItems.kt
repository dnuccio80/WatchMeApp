package com.example.watchme.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
