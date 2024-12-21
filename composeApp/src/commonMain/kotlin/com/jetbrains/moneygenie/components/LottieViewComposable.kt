package com.jetbrains.moneygenie.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import moneygenie.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Created by Kundan on 20/12/24
 **/

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LottieView(path: String, modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(path).decodeToString()
        )
    }
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 2
    )

    Image(
        painter = rememberLottiePainter(
            composition = composition,
            progress = { progress }
        ),
        contentDescription = "Lottie animation",
        modifier = modifier
    )
}