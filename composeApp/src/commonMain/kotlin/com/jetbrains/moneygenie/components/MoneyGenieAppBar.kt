package com.jetbrains.moneygenie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Primary700
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.logo_green
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 03/10/24
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    navigator: Navigator,
    title: String,
    showNavigationIcon: Boolean = false,
    showProfileIcon: Boolean = false,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfileIcon) {
                    // Profile Icon
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(resource = Res.drawable.logo_green),
                            contentDescription = "Profile Icon",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Title Content
                Text(
                    title,
                    color = Color.White,
                    style = MGTypography().headingBold,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        },
        navigationIcon = {
            if (showNavigationIcon) {
                IconButton(onClick = {
                    navigator.pop()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        actions = actions ?: {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Primary700,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White,
            scrolledContainerColor = Primary700
        )
    )
}

