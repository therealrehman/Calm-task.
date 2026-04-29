package com.example.calmtask.ui.splash

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calmtask.ui.theme.MutedGray
import com.example.calmtask.ui.theme.WarmBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onDone: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(2000)
        onDone()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter   = fadeIn() + scaleIn(initialScale = 0.8f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🌿", fontSize = 72.sp)
                Spacer(Modifier.height(12.dp))
                Text(
                    "CalmTask",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text("Your calm companion", color = MutedGray, fontSize = 16.sp)
            }
        }
    }
}
