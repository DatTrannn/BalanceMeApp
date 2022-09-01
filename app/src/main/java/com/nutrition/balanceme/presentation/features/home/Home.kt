package com.nutrition.balanceme.presentation.features.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nutrition.balanceme.presentation.features.home.Screen.*
import com.nutrition.balanceme.presentation.features.home.explore.detailNavHost
import com.nutrition.balanceme.presentation.features.home.feed.Feed
import com.nutrition.balanceme.presentation.features.home.fridge.Fridge
import com.nutrition.balanceme.presentation.features.home.profile.Profile
import com.nutrition.balanceme.presentation.theme.BalanceMeTheme
import com.nutrition.balanceme.presentation.theme.Status

@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun Home(mainController: NavHostController) {
    val navController = rememberNavController()
    val screens = listOf(Feed, Explore, Fridge, Profile)

    BalanceMeTheme {
        Status()
        Surface {
            ProvideWindowInsets {
                Scaffold(
                    modifier = Modifier.systemBarsPadding(),
                    bottomBar = {
                        BottomNavigation(backgroundColor = colors.background) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            screens.forEach { screen ->
                                BottomNavigationItem(
                                    alwaysShowLabel = false,
                                    label = { Text(stringResource(screen.name)) },
                                    selectedContentColor = colors.primary,
                                    unselectedContentColor = colors.primaryVariant,
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    icon = {
                                        Icon(
                                            painter = painterResource(screen.icon!!),
                                            modifier = Modifier.size(22.dp),
                                            contentDescription = null
                                        )
                                    },
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            restoreState = true
                                            launchSingleTop = true

                                            val destination =
                                                navController.graph.findStartDestination().id
                                            popUpTo(destination) { saveState = true }
                                        }
                                    }
                                )
                            }
                        }
                    },
                    content = {
                        NavHost(navController,
                            startDestination = Feed.route,
                            Modifier.padding(it)) {
                            composable(Feed.route) { Feed(mainController) }
                            composable(Fridge.route) { Fridge(mainController) }
                            detailNavHost(navController)
                            composable(Profile.route) { Profile(mainController) }
                        }
                    }
                )
            }
        }
    }
}