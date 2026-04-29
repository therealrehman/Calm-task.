package com.example.calmtask.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calmtask.domain.GreetingGenerator
import com.example.calmtask.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateVoice: () -> Unit,
    onNavigateTasks: () -> Unit,
    onNavigateNight: () -> Unit,
    onNavigateSettings: () -> Unit,
    vm: HomeViewModel = viewModel()
) {
    val settings by vm.settings.collectAsState()
    val tasks    by vm.activeTasks.collectAsState()
    val focusTask= tasks.firstOrNull { it.isFocus } ?: tasks.firstOrNull()

    Scaffold(
        containerColor = WarmBackground,
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = onNavigateSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmBackground)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateVoice,
                containerColor = PrimaryBlue,
                shape = RoundedCornerShape(50)
            ) {
                Text("🎙️", fontSize = 24.sp)
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Greeting
            item {
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(GreetingGenerator.emoji(), fontSize = 28.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        GreetingGenerator.greetingText(settings.userName),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "${tasks.size} tasks today",
                    color = MutedGray,
                    fontSize = 14.sp
                )
            }

            // Focus card
            if (focusTask != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = LightBlue),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            Text("⭐ Focus", color = PrimaryBlue, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Spacer(Modifier.height(6.dp))
                            Text(focusTask.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { vm.complete(focusTask.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = CalmGreen),
                                    shape = RoundedCornerShape(10.dp)
                                ) { Text("✓ Done", color = Color.White) }
                                OutlinedButton(
                                    onClick = { vm.skip(focusTask.id) },
                                    shape = RoundedCornerShape(10.dp)
                                ) { Text("Skip") }
                            }
                        }
                    }
                }
            }

            // Task list
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("All Tasks", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    TextButton(onClick = onNavigateTasks) { Text("See all") }
                }
            }

            items(tasks.take(5)) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardWhite)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(task.title, Modifier.weight(1f))
                        IconButton(onClick = { vm.complete(task.id) }) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Complete",
                                tint = CalmGreen)
                        }
                    }
                }
            }

            // Bottom navigation shortcuts
            item {
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ShortcutCard("📋", "Tasks",  Modifier.weight(1f), onNavigateTasks)
                    ShortcutCard("🌙", "Review", Modifier.weight(1f), onNavigateNight)
                }
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun ShortcutCard(emoji: String, label: String, modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp).fillMaxWidth()
        ) {
            Text(emoji, fontSize = 28.sp)
            Text(label, fontSize = 13.sp, color = MutedGray)
        }
    }
}
