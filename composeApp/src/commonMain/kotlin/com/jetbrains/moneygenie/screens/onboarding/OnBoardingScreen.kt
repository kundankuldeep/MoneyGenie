package com.jetbrains.moneygenie.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.CustomPagerIndicator
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.screens.authentication.SignUpScreen
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural100
import com.jetbrains.moneygenie.theme.Natural500
import com.jetbrains.moneygenie.theme.Primary700
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.logo_green
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Created by Kundan on 18/08/24
 **/
class OnBoardingScreen : Screen {
    @Composable
    override fun Content() {
        OnBoardingContent()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun OnBoardingContent() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: OnBoardingScreenModel = getScreenModel()

        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { viewModel.crawlers.size }
        )

        Box(modifier = Modifier.fillMaxSize().padding(vertical = 20.dp)) {
            // top title
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // logo icon
                    Image(
                        painter = painterResource(Res.drawable.logo_green), // Replace with your image
                        contentDescription = null,
                        modifier = Modifier.width(65.dp).height(35.dp)
                    )
                    VerticalSpace(30)

                    // screen Title
                    Text(
                        text = "Welcome to \nMoneyGenie!",
                        color = Primary700,
                        style = MGTypography().titleBoldL
                    )
                    VerticalSpace(10)

                    // screen Title
                    Text(
                        text = "Your personal finance assistant.",
                        color = Natural500,
                        style = MGTypography().bodyRegularL
                    )

                }

                // crawler
                Column(modifier = Modifier.wrapContentSize()) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.wrapContentHeight().fillMaxWidth(),
                        flingBehavior = PagerDefaults.flingBehavior(
                            state = pagerState,
                            pagerSnapDistance = PagerSnapDistance.atMost(1)
                        )
                    ) { page ->
                        CrawlerCard(viewModel.crawlers.getOrNull(page))
                    }
                    // Pager indicator
                    CustomPagerIndicator(
                        pagerState = pagerState,
                        spacing = 9.dp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }

                VerticalSpace(24)

                // bottom buttons
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MGButton(text = "Get Started", type = MGButtonType.SOLID, onClick = {
                        navigator.push(SignUpScreen())
                    })
                }
                VerticalSpace(20)
            }
        }
    }
}

@Composable
fun CrawlerCard(crawlerModel: CrawlerModel?) {
    if (crawlerModel == null) return
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set your desired background color here
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(20.dp)
            .border(1.dp, Natural100, RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 40.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(crawlerModel.icon),
                contentDescription = null,
                modifier = Modifier.width(280.dp).height(280.dp)
            )
            VerticalSpace(10)

            Text(
                text = stringResource(crawlerModel.title),
                color = Natural500,
                style = MGTypography().titleBold,
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpace(10)

            Text(
                text = stringResource(crawlerModel.subTitle),
                color = Natural500,
                style = MGTypography().bodyRegularL
            )
        }
    }
}