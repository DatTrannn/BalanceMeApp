package com.example.balanceme

import BalanceMeScaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.balanceme.ui.FoodDetail.FoodDetail
import com.example.balanceme.ui.home.BalanceMeBottomBar
import com.example.balanceme.ui.home.HomeSections
import com.example.balanceme.ui.home.addHomeGraph
import com.example.balanceme.ui.theme.BalanceMeTheme

@Composable
fun BalanceMeApp() {
    BalanceMeTheme {
        val appState = rememberBalanceMeAppState()
        BalanceMeScaffold(
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    BalanceMeBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute!!,
                        navigateToRoute = appState::navigateToBottomBarRoute
                    )
                }
            },
            scaffoldState = appState.scaffoldState
        ) { innerPaddingModifier ->
            NavHost(
                navController = appState.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                BalanceMeNavGraph(
                    onSnackSelected = appState::navigateToMenuDetail,
                    upPress = appState::upPress
                )
            }
        }
    }
}

private fun NavGraphBuilder.BalanceMeNavGraph(
    onSnackSelected: (Int, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.MENUITEM.route
    ) {
        addHomeGraph(onSnackSelected)
    }
    composable(
        "${MainDestinations.MENU_DETAIL_ROUTE}/{${MainDestinations.MENUITEM_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.MENUITEM_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val menuItemId = arguments.getLong(MainDestinations.MENUITEM_ID_KEY)
        FoodDetail(menuItemId, upPress)
    }
}