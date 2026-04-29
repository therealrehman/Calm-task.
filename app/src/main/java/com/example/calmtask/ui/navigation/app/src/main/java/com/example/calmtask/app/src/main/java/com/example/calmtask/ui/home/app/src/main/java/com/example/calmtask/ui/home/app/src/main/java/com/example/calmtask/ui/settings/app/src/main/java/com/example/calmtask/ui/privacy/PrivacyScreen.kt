package com.example.calmtask.ui.privacy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.calmtask.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(onBack: () -> Unit) {
    val items = listOf(
        "📱" to "Tasks stored in Room database — local only",
        "🎙️" to "Voice processed on device — never uploaded",
        "🚫" to "No Firebase, no server, no login required",
        "🤖" to "No AI APIs — rule-based local parser only",
        "🔕" to "No ads, no tracking, no data selling",
        "😊" to "Mood data stored locally, deletable anytime",
        "👤" to "Profile info (name, gender, age) stored on device only",
        "🌐" to "Language preference used only for voice settings"
    )

    Scaffold(
        containerColor = WarmBackground,
        topBar = {
            TopAppBar(
                title = { Text("Privacy") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmBackground)
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Your data stays on your phone",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text("CalmTask is private by design. Here's exactly what we do and don't do.",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
            }
            items(items) { (emoji, text) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardWhite)
                ) {
                    Row(Modifier.padding(16.dp)) {
                        Text(emoji, modifier = Modifier.padding(end = 12.dp))
                        Text(text)
                    }
                }
            }
        }
    }
}
