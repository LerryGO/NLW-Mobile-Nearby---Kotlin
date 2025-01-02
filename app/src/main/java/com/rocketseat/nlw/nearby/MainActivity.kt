package com.rocketseat.nlw.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rocketseat.nlw.nearby.data.model.Market
import com.rocketseat.nlw.nearby.ui.screen.home.HomeScreen
import com.rocketseat.nlw.nearby.ui.screen.home.HomeViewModel
import com.rocketseat.nlw.nearby.ui.screen.market_details.MarketDetailsScreen
import com.rocketseat.nlw.nearby.ui.screen.splash.SplashScreen
import com.rocketseat.nlw.nearby.ui.screen.welcome.WelcomeScreen
import com.rocketseat.nlw.nearby.ui.route.Home
import com.rocketseat.nlw.nearby.ui.route.Splash
import com.rocketseat.nlw.nearby.ui.route.Welcome
import com.rocketseat.nlw.nearby.ui.theme.NearbyTheme
import androidx.compose.runtime.getValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()

                val homeViewModel by viewModels<HomeViewModel>()
                val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = Splash
                ) {
                    composable<Splash> {
                        SplashScreen(
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToWelcome = {
                                navController.navigate(Welcome)
                            }
                        )
                    }
                    composable<Welcome> {
                        WelcomeScreen(onNavigateToHome = {
                            navController.navigate(Home)
                        })
                    }
                    composable<Home> {
                        HomeScreen(onNavigateToMarketDetails = { selectedMarket ->
                            navController.navigate(selectedMarket)
                        },
                            uiState = homeUiState,
                            onEvent = homeViewModel::onEvent )
                    }
                    composable<Market> {
                        val selectedMarket = it.toRoute<Market>()
                        MarketDetailsScreen(
                            market = selectedMarket,
                            onNavigateBack = { navController.popBackStack() })
                    }

                }
            }
        }
    }
}

