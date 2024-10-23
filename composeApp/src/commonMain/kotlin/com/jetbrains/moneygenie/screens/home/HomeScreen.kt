package com.jetbrains.moneygenie.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.composableCollection.LottieView
import com.jetbrains.moneygenie.composableCollection.MainAppBar
import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.screens.addRecipient.AddRecipientScreen
import com.jetbrains.moneygenie.screens.components.GradientIconButton
import com.jetbrains.moneygenie.theme.Color_N200
import com.jetbrains.moneygenie.theme.MGTypography
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.dashboard
import org.jetbrains.compose.resources.stringResource

/**
 * Created by Kundan on 26/09/24
 **/
data object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: HomeScreenModel = getScreenModel()
        HomeScreenComposable(screenModel, navigator)
    }
}

@Composable
fun HomeScreenComposable(screenModel: HomeScreenModel, navigator: Navigator) {
    Scaffold(
        topBar = {
            MainAppBar {
                Text(
                    stringResource(Res.string.dashboard),
                    color = Color.White,
                    style = MGTypography().headingBold,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.push(AddRecipientScreen()) },
                modifier = Modifier.padding(bottom = 30.dp),
                shape = CircleShape
            ) {
                GradientIconButton(
                    icon = Icons.Rounded.Add
                )
            }
        }

    ) {
        Box(Modifier.fillMaxSize().padding(it)) {
            SetHomeScreen(screenModel)
        }
    }
}

@Composable
fun SetHomeScreen(data: HomeScreenModel) {
    val dataItem = data.dataList.value
    if (dataItem.isNotEmpty()) {
        RecipientListView(dataItem)
    } else {
        NoContentDashboard()
    }
}

@Composable
fun RecipientListView(recipientList: ArrayList<Recipient>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(recipientList.size) { index ->
            Spacer(modifier = Modifier.height(8.dp))
            RecipientItem(recipientList[index])
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
        }
    }
}

@Composable
fun RecipientItem(recipient: Recipient) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Text(
                text = "Name: ${recipient.name ?: "Unknown"}",
                style = MGTypography().titleSemiBold
            )
            Text(
                text = "Contact: ${recipient.phone ?: "No contact"}",
                style = MGTypography().titleSemiBold
            )
            Text(
                text = "Note: ${recipient.note ?: "No notes"}",
                style = MGTypography().bodyRegularS
            )
        }
    }
}

@Composable
fun NoContentDashboard() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LottieView(
                "files/empty_dashboard.json",
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Click on + \n To add your first recipient",
                color = Color_N200,
                style = MGTypography().bodyRegularL
            )
        }
    }

}

