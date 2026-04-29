package com.example.calmtask.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calmtask.data.SettingsDataStore
import com.example.calmtask.ui.home.HomeScreen
import com.example.calmtask.ui.night.NightReviewScreen
import com.example.calmtask.ui.onboarding.OnboardingScreen
import com.example.calmtask.ui.privacy.PrivacyScreen
import com.example.calmtask.ui.settings.SettingsScreen
import com.example.calmtask.ui.splash.SplashScreen
import com.example.calmtask.ui.tasks.TaskListScreen
import com.example.calmtask.ui.voice.VoiceScreen

object Routes {
    const val SPLASH     = "splash"
    const val ONBOARDING = "onboarding"
    const val HOME       = "home"
    const val VOICE      = "voice"
    const val TASKS      = "tasks"
    const val NIGHT      = "night"
    const val SETTINGS   = "settings"
    const val PRIVACY    = "privacy"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val context       = LocalContext.current
    val store         = remember { SettingsDataStore(context) }
    val settings      by store.settingsFlow.collectAsState(initial = null)

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen {
                val dest = if (settings?.onboardingCompleted == true)
                    Routes.HOME else Routes.ONBOARDING
                navController.navigate(dest) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
        }
        composable(Routes.ONBOARDING) {
            OnboardingScreen(onFinished = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.ONBOARDING) { inclusive = true }
                }
            })
        }
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateVoice    = { navController.navigate(Routes.VOICE) },
                onNavigateTasks    = { navController.navigate(Routes.TASKS) },
                onNavigateNight    = { navController.navigate(Routes.NIGHT) },
                onNavigateSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }
        composable(Routes.VOICE)    { VoiceScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.TASKS)    { TaskListScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.NIGHT)    { NightReviewScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack    = { navController.popBackStack() },
                onPrivacy = { navController.navigate(Routes.PRIVACY) }
            )
        }
        composable(Routes.PRIVACY) { PrivacyScreen(onBack = { navController.popBackStack() }) }
    }
}
