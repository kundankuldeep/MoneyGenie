package com.jetbrains.moneygenie.expects

import androidx.compose.ui.text.font.FontFamily

/**
 * Created by Kundan on 18/08/24
 **/
expect fun provideFont(typefaceName: FontTypeFace): FontFamily

enum class FontTypeFace(val value: String) {
    BOLD("bold"),
    BOLD_ITALIC("bold_italic"),
    ITALIC("italic"),
    LIGHT("light"),
    LIGHT_ITALIC("light_italic"),
    MEDIUM("medium"),
    MEDIUM_ITALIC("medium_italic"),
    REGULAR("regular"),
    SEMI_BOLD("semi_bold"),
    SEMI_BOLD_ITALIC("semi_bold_italic")
}
