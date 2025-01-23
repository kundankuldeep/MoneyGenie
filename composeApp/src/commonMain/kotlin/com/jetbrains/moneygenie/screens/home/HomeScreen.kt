package com.jetbrains.moneygenie.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.AccountStatusCard
import com.jetbrains.moneygenie.components.GradientIconButton
import com.jetbrains.moneygenie.components.LottieView
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.RoundedCornerSearchBar
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.theme.Error700
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural100
import com.jetbrains.moneygenie.theme.Natural300
import com.jetbrains.moneygenie.theme.Natural500
import com.jetbrains.moneygenie.theme.Natural600
import com.jetbrains.moneygenie.theme.Primary700
import com.jetbrains.moneygenie.utils.DateTimeUtils
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 26/09/24
 **/
data object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val viewModel: HomeScreenModel = getScreenModel()
        HomeScreenComposable(viewModel, navigator, bottomSheetNavigator)
        viewModel.initViewModel()
    }
}

@Composable
fun HomeScreenComposable(
    viewModel: HomeScreenModel,
    navigator: Navigator,
    bottomSheetNavigator: BottomSheetNavigator
) {

    MGScaffold(
        topBar = {
            MainAppBar(
                navigator,
                "Hi ${viewModel.userName.value}!",
                showProfileIcon = true,
                profileIconClicked = { viewModel.onProfileIconClicked(navigator) },
                actions = {
                    IconButton(onClick = {
                        viewModel.onMoreIconClick(bottomSheetNavigator, navigator)
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Icon",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddRecipientClick(navigator) },
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
            SetHomeScreen(viewModel, viewModel)
        }
    }
}

@Composable
fun SetHomeScreen(data: HomeScreenModel, screenModel: HomeScreenModel) {
    val dataItem = data.dataList.value
    if (dataItem.isNotEmpty()) {
        MainContentDashboard(dataItem, screenModel)
    } else {
        NoContentDashboard()
    }
}

@Composable
fun MainContentDashboard(dataItem: List<RecipientViewItem>, viewModel: HomeScreenModel) {
    var isSearchEnable by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(20.dp)) {
        AccountStatusCard(viewModel.totalLent.value, viewModel.totalBorrowed.value)
        VerticalSpace(24)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Recipients",
                style = MGTypography().bodyRegularL,
                color = Natural500
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    isSearchEnable = !isSearchEnable
                    viewModel.updateSearchText("")
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = Primary700
                    )
                }
            }
        }

        // Search view for recipients
        if (isSearchEnable) {
            VerticalSpace(20)
            RoundedCornerSearchBar(
                query = viewModel.searchText.value,
                onQueryChange = { value -> viewModel.updateSearchText(value) }
            )
            VerticalSpace(6)
        }

        VerticalSpace(12)
        RecipientListView(dataItem, viewModel)
    }
}

@Composable
fun RecipientListView(recipientList: List<RecipientViewItem>, screenModel: HomeScreenModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipientList.size) { index ->
            Spacer(modifier = Modifier.height(8.dp))
            RecipientItem(recipientList[index], screenModel)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RecipientItem(recipient: RecipientViewItem, screenModel: HomeScreenModel) {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { screenModel.onRecipientItemClick(recipient, navigator) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(width = 1.dp, color = Natural100)
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = recipient.recipientName,
                    style = MGTypography().headingSemiBold,
                    color = Natural600
                )
                Text(
                    text = "+ Add Transaction",
                    style = MGTypography().bodyRegularS,
                    modifier = Modifier.clickable {
                        screenModel.onAddTransactionClick(
                            recipient,
                            navigator
                        )
                    },
                    color = Primary700
                )
            }

            if (recipient.recipientNote.isNotEmpty()) {
                VerticalSpace(3)
                Text(
                    text = recipient.recipientNote,
                    style = MGTypography().bodyRegularS,
                    color = Natural300
                )
            }

            VerticalSpace(6)
            val amountText =
                if (recipient.amount == 0.0) "Account is settled" else "${recipient.status.value} - ₹${recipient.amount}"
            Text(
                text = amountText,
                style = MGTypography().bodyRegularL,
                color = if (recipient.status == OverAllStatus.YouOwe) Primary700 else Error700
            )
            VerticalSpace(6)
            Text(
                text = DateTimeUtils.getFormattedDate(recipient.lastUpdatedDate),
                style = MGTypography().bodyRegularS,
                color = Natural300
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
                color = Natural300,
                style = MGTypography().bodyRegularL
            )
        }
    }

}

