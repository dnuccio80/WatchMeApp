package com.example.watchme.ui.theme

import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        color = TextColor,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 1.sp,
        color = Color.White,
        shadow = Shadow(color = Color.Black, offset = Offset(4.0f, 4.0f), blurRadius = 2f)
    ),

    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        color = Color.White,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    ),

    bodyMedium = TextStyle(
        color = Color.White,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),


    bodySmall = TextStyle(
        color = Color.White
    )
)

