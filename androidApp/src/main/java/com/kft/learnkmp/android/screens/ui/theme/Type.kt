package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kft.learnkmp.android.R

val AppTypography = Typography(

    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_bold)),
        fontSize = 50.sp,
        color = Color.White
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
        fontSize = 20.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
        fontSize = 18.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        fontSize = 22.sp,

    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_light)),
        fontSize = 17.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_light)),
        fontSize = 15.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_light)),
        fontSize = 13.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    )

)
