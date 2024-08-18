package com.jetbrains.moneygenie.expects

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

/**
 * Created by Kundan on 18/08/24
 **/
actual fun provideFont(typefaceName: FontTypeFace): FontFamily {
    return FontFamily(
        Font(0, weight = FontWeight.Normal)
    )
}