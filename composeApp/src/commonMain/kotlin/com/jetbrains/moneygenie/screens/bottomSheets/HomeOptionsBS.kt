package com.jetbrains.moneygenie.screens.bottomSheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.jetbrains.moneygenie.components.CommonBottomSheet
import com.jetbrains.moneygenie.components.TextWithIcon
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.screens.settings.SettingsScreen
import com.jetbrains.moneygenie.theme.Primary500
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.ic_rate_us
import moneygenie.composeapp.generated.resources.ic_share_icon
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 19/12/24
 **/
class HomeOptionsBS(private val navigator: Navigator) : Screen {
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        CommonBottomSheet {
            Column {
                TextWithIcon(
                    text = "Settings",
                    icon = Icons.Rounded.Settings,
                    iconTint = Primary500,
                    modifier = Modifier.clickable {
                        bottomSheetNavigator.hide()
                        navigator.push(SettingsScreen())
                    })
                VerticalSpace(24)
                TextWithIcon(
                    text = "Share App",
                    icon = painterResource(resource = Res.drawable.ic_share_icon),
                    iconTint = Primary500
                )
                VerticalSpace(24)
                TextWithIcon(
                    text = "Rate Us",
                    icon = painterResource(resource = Res.drawable.ic_rate_us),
                    iconTint = Primary500
                )
                VerticalSpace(24)
            }
        }
    }
}