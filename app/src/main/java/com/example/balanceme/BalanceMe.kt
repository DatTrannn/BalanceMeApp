package com.example.balanceme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.balanceme.ui.ForgotPassword
import com.example.balanceme.ui.LoginPage
import com.example.balanceme.ui.SignUp
import com.example.balanceme.ui.theme.BalanceMeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun BalanceMe() {
    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    BalanceMeTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.Login.route) {

            composable(Routes.Login.route) {
                LoginPage(navController = navController, auth)
            }

            composable(Routes.SignUp.route) {
                SignUp(navController = navController, auth)
            }

            composable(Routes.ForgotPassword.route) {
                ForgotPassword(navController = navController)
            }
        }
    }
}