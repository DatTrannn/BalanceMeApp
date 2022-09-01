package com.nutrition.balanceme.presentation.features.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nutrition.balanceme.presentation.features.auth.AuthActivity
import com.nutrition.balanceme.presentation.features.navigation.NavigationActivity
import com.nutrition.balanceme.util.Preferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject


@AndroidEntryPoint
@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
class IntroActivity : ComponentActivity() {

    @Inject lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        redirect()
    }

    private fun redirect(){
        val token = preferences.getToken()

        val auth = Intent(this, AuthActivity::class.java)
        val navigation = Intent(this, NavigationActivity::class.java)
        val activity = if (token == null) auth else navigation

        lifecycleScope.launchWhenStarted {
            delay(2000)
            startActivity(activity)
            finish()
        }
    }
}