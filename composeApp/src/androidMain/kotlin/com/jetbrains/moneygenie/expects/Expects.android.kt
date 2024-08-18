package com.jetbrains.moneygenie.expects

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.jetbrains.moneygenie.R

/**
 * Created by Kundan on 18/08/24
 **/
actual fun provideFont(typefaceName: FontTypeFace): FontFamily {
    return when (typefaceName) {
        FontTypeFace.BOLD -> FontFamily(Font(resId = R.font.raleway_bold))
        FontTypeFace.BOLD_ITALIC -> FontFamily(Font(resId = R.font.raleway_bold_italic))
        FontTypeFace.ITALIC -> FontFamily(Font(resId = R.font.raleway_italic))
        FontTypeFace.LIGHT -> FontFamily(Font(resId = R.font.raleway_light))
        FontTypeFace.LIGHT_ITALIC -> FontFamily(Font(resId = R.font.raleway_light_italic))
        FontTypeFace.MEDIUM -> FontFamily(Font(resId = R.font.raleway_medium))
        FontTypeFace.MEDIUM_ITALIC -> FontFamily(Font(resId = R.font.raleway_medium_italic))
        FontTypeFace.REGULAR -> FontFamily(Font(resId = R.font.raleway_regular))
        FontTypeFace.SEMI_BOLD -> FontFamily(Font(resId = R.font.raleway_semi_bold))
        FontTypeFace.SEMI_BOLD_ITALIC -> FontFamily(Font(resId = R.font.raleway_semi_bold_italic))
    }
}