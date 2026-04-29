package com.example.calmtask.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calmtask.data.SettingsDataStore
import com.example.calmtask.data.UserSettings
import com.example.calmtask.ui.onboarding.LANGUAGES
import com.example.calmtask.ui.theme.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val store = SettingsDataStore(app)
    val settings = store.settingsFlow.stateIn(viewModelScope, SharingStarted.Lazily, UserSettings())
    fun save(s: UserSettings) = viewModelScope.launch { store.saveSettings(s) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onPrivacy: () -> Unit,
    vm: SettingsViewModel = viewModel()
) {
    val s by vm.settings.collectAsState()

    Scaffold(
        containerColor = WarmBackground,
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ── PROFILE SECTION ──────────────────────────────────
            item { SectionHeader("👤 Profile") }

            item {
                SettingsCard {
                    SettingsTextField("Your Name", s.userName) { vm.save(s.copy(userName = it)) }
                }
            }

            item {
                SettingsCard {
                    Text("Voice Gender", fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("MALE" to "👨 Male", "FEMALE" to "👩 Female").forEach { (code, label) ->
                            FilterChip(
                                selected = s.gender == code,
                                onClick  = { vm.save(s.copy(gender = code)) },
                                label    = { Text(label) }
                            )
                        }
                    }
                }
            }

            item {
                SettingsCard {
                    Text("Language", fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(8.dp))
                    var expanded by remember { mutableStateOf(false) }
                    val currentLang = LANGUAGES.find { it.code == s.language }?.nativeName ?: s.language
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                        OutlinedTextField(
                            value = currentLang,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            LANGUAGES.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text("${lang.nativeName}  (${lang.englishName})") },
                                    onClick = {
                                        vm.save(s.copy(language = lang.code))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // ── VOICE SECTION ────────────────────────────────────
            item { Spacer(Modifier.height(4.dp)); SectionHeader("🎙️ Voice") }

            item {
                SettingsCard {
                    ToggleRow("Voice Replies", s.voiceRepliesEnabled) { vm.save(s.copy(voiceRepliesEnabled = it)) }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    ToggleRow("Morning Greeting", s.morningGreetingEnabled) { vm.save(s.copy(morningGreetingEnabled = it)) }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    ToggleRow("Night Summary", s.nightSummaryEnabled) { vm.save(s.copy(nightSummaryEnabled = it)) }
                }
            }

            // ── MOOD SECTION ─────────────────────────────────────
            item { Spacer(Modifier.height(4.dp)); SectionHeader("🌈 Mood") }
            item {
                SettingsCard {
                    ToggleRow("Mood-Aware Mode", s.moodAwareModeEnabled) { vm.save(s.copy(moodAwareModeEnabled = it)) }
                }
            }

            // ── PRIVACY SECTION ──────────────────────────────────
            item { Spacer(Modifier.height(4.dp)); SectionHeader("🔒 Privacy") }
            item {
                SettingsCard {
                    TextButton(onClick = onPrivacy, modifier = Modifier.fillMaxWidth()) {
                        Text("View Privacy Info")
                    }
                }
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(text, fontWeight = FontWeight.Bold, color = MutedGray, modifier = Modifier.padding(vertical = 4.dp))
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(Modifier.padding(16.dp)) { content() }
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onToggle)
    }
}

@Composable
private fun SettingsTextField(label: String, value: String, onUpdate: (String) -> Unit) {
    var text by remember(value) { mutableStateOf(value) }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        trailingIcon = {
            if (text != value) {
                TextButton(onClick = { onUpdate(text) }) { Text("Save") }
            }
        }
    )
}
