package com.example.balanceme

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object SignUp : Routes("Sign Up")
    object ForgotPassword: Routes("Forgot Password")
    object MenuItem: Routes("Menu Item")
    object Calculator: Routes("Calculator")
    object Expert: Routes("Expert")
    object Profile: Routes("Profile")
    object App: Routes("App")
}