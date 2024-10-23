package com.jetbrains.moneygenie.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.raleway_bold
import moneygenie.composeapp.generated.resources.raleway_medium
import moneygenie.composeapp.generated.resources.raleway_regular
import moneygenie.composeapp.generated.resources.raleway_semi_bold
import org.jetbrains.compose.resources.Font

/**
 * Created by Kundan on 03/10/24
 **/

@Composable
fun raleway() = FontFamily(
    Font(Res.font.raleway_regular, FontWeight.Normal),
    Font(Res.font.raleway_medium, FontWeight.Medium),
    Font(Res.font.raleway_semi_bold, FontWeight.SemiBold),
    Font(Res.font.raleway_bold, FontWeight.Bold),
)

@Composable
fun defaultFont(): FontFamily {
    return raleway()
}

data class CustomTypography(
    val titleBoldXL: TextStyle,
    val titleBoldL: TextStyle,
    val titleBold: TextStyle,
    val titleSemiBoldXL: TextStyle,
    val titleSemiBoldL: TextStyle,
    val titleSemiBold: TextStyle,
    val headingBoldL: TextStyle,
    val headingBold: TextStyle,
    val headingSemiBoldL: TextStyle,
    val headingRegularL: TextStyle,
    val headingSemiBold: TextStyle,
    val bodyBoldL: TextStyle,
    val bodyBold: TextStyle,
    val bodyBoldS: TextStyle,
    val bodyBoldXS: TextStyle,
    val bodyBoldItalicS: TextStyle,
    val bodySemiBoldL: TextStyle,
    val bodySemiBold: TextStyle,
    val bodySemiBoldS: TextStyle,
    val bodySemiBoldXS: TextStyle,
    val bodyRegularL: TextStyle,
    val bodyRegular: TextStyle,
    val bodyRegularS: TextStyle,
    val bodyRegularXS: TextStyle,
    val bodyItalicXL: TextStyle,
    val bodyItalicL: TextStyle,
    val bodyItalicS: TextStyle
)

@Composable
fun MGTypography(): CustomTypography {
    val font = defaultFont()

    return CustomTypography(
        titleBoldXL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        ),
        titleBoldL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        ),
        titleBold = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        ),
        titleSemiBoldXL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp
        ),
        titleSemiBoldL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp
        ),
        titleSemiBold = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        ),
        headingBoldL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        headingBold = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        headingSemiBoldL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        ),
        headingSemiBold = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        ),
        headingRegularL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        bodyBoldL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        bodyBold = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        bodyBoldS = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        ),
        bodyBoldXS = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        ),
        bodyBoldItalicS = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp
        ),
        bodySemiBoldL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        bodySemiBold = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        ),
        bodySemiBoldS = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        ),
        bodySemiBoldXS = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp
        ),
        bodyRegularL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        bodyRegular = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        bodyRegularS = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        bodyRegularXS = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        ),
        bodyItalicXL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp
        ),
        bodyItalicL = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp
        ),
        bodyItalicS = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp
        )
    )
}