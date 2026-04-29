package com.example.calmtask.ui.onboarding

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calmtask.ui.theme.*

// ── Data helpers ──────────────────────────────────────────────────────────────

data class LanguageOption(val code: String, val nativeName: String, val englishName: String)
data class CountryOption(val code: String, val flag: String, val name: String)

val LANGUAGES = listOf(
    LanguageOption("en", "English",    "English"),
    LanguageOption("ur", "اردو",       "Urdu"),
    LanguageOption("ar", "العربية",    "Arabic"),
    LanguageOption("hi", "हिन्दी",     "Hindi"),
    LanguageOption("es", "Español",    "Spanish"),
    LanguageOption("fr", "Français",   "French"),
    LanguageOption("de", "Deutsch",    "German"),
    LanguageOption("zh", "中文",        "Chinese"),
    LanguageOption("pt", "Português",  "Portuguese"),
    LanguageOption("tr", "Türkçe",     "Turkish"),
    LanguageOption("ru", "Русский",    "Russian"),
    LanguageOption("bn", "বাংলা",      "Bengali"),
    LanguageOption("id", "Bahasa",     "Indonesian"),
    LanguageOption("ja", "日本語",      "Japanese"),
    LanguageOption("ko", "한국어",      "Korean")
)

val COUNTRIES = listOf(
    CountryOption("PK","🇵🇰","Pakistan"),
    CountryOption("US","🇺🇸","United States"),
    CountryOption("GB","🇬🇧","United Kingdom"),
    CountryOption("IN","🇮🇳","India"),
    CountryOption("SA","🇸🇦","Saudi Arabia"),
    CountryOption("AE","🇦🇪","UAE"),
    CountryOption("CA","🇨🇦","Canada"),
    CountryOption("AU","🇦🇺","Australia"),
    CountryOption("DE","🇩🇪","Germany"),
    CountryOption("FR","🇫🇷","France"),
    CountryOption("TR","🇹🇷","Turkey"),
    CountryOption("EG","🇪🇬","Egypt"),
    CountryOption("NG","🇳🇬","Nigeria"),
    CountryOption("BR","🇧🇷","Brazil"),
    CountryOption("ID","🇮🇩","Indonesia"),
    CountryOption("BD","🇧🇩","Bangladesh"),
    CountryOption("MX","🇲🇽","Mexico"),
    CountryOption("PH","🇵🇭","Philippines"),
    CountryOption("JP","🇯🇵","Japan"),
    CountryOption("KR","🇰🇷","South Korea"),
    CountryOption("ZA","🇿🇦","South Africa"),
    CountryOption("KE","🇰🇪","Kenya"),
    CountryOption("MA","🇲🇦","Morocco"),
    CountryOption("OTHER","🌍","Other")
)

val AGE_RANGES = listOf(
    "under18"  to "Under 18",
    "18-25"    to "18 – 25",
    "26-35"    to "26 – 35",
    "36-45"    to "36 – 45",
    "46-55"    to "46 – 55",
    "55+"      to "55 +"
)

// ── Main Composable ───────────────────────────────────────────────────────────

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
    vm: OnboardingViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    AnimatedContent(
        targetState = state.currentStep,
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
            slideOutHorizontally { -it } + fadeOut()
        }
    ) { step ->
        when (step) {
            OnboardingStep.GENDER      -> GenderStep(state, vm)
            OnboardingStep.LANGUAGE    -> LanguageStep(state, vm)
            OnboardingStep.COUNTRY     -> CountryStep(state, vm)
            OnboardingStep.AGE         -> AgeStep(state, vm)
            OnboardingStep.WELCOME     -> WelcomeStep(vm)
            OnboardingStep.HOW_IT_WORKS-> HowItWorksStep(vm)
            OnboardingStep.TRY_COMMAND -> TryCommandStep(state, vm)
            OnboardingStep.YOUR_NAME   -> YourNameStep(state, vm)
            OnboardingStep.PERMISSIONS -> PermissionsStep(state, vm)
            OnboardingStep.ALL_SET     -> AllSetStep(state, vm, onFinished)
        }
    }
}

// ── Step Scaffold ─────────────────────────────────────────────────────────────

@Composable
private fun StepScaffold(
    progress: Float,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(24.dp)
    ) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .padding(bottom = 8.dp),
            color = PrimaryBlue,
            trackColor = PrimaryBlue.copy(alpha = 0.2f)
        )
        Spacer(Modifier.height(16.dp))
        content()
    }
}

@Composable
private fun PrimaryButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

// ── STEP 1: GENDER ────────────────────────────────────────────────────────────

@Composable
fun GenderStep(state: OnboardingUiState, vm: OnboardingViewModel) {
    StepScaffold(progress = 0.10f) {
        Spacer(Modifier.height(24.dp))
        Text("👤", fontSize = 64.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.height(16.dp))
        Text(
            "Tell me about yourself",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "This helps me choose the right voice for you.",
            style = MaterialTheme.typography.bodyLarge,
            color = MutedGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(40.dp))

        // Gender cards
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            GenderCard(
                emoji = "👨",
                label = "Male",
                selected = state.gender == "MALE",
                modifier = Modifier.weight(1f),
                onClick = { vm.setGender("MALE") }
            )
            GenderCard(
                emoji = "👩",
                label = "Female",
                selected = state.gender == "FEMALE",
                modifier = Modifier.weight(1f),
                onClick = { vm.setGender("FEMALE") }
            )
        }

        Spacer(Modifier.weight(1f))
        PrimaryButton("Continue") { vm.nextStep() }
    }
}

@Composable
private fun GenderCard(
    emoji: String, label: String, selected: Boolean,
    modifier: Modifier, onClick: () -> Unit
) {
    val borderColor = if (selected) PrimaryBlue else Color.Transparent
    val bgColor     = if (selected) LightBlue   else CardWhite

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .border(2.dp, borderColor, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(if (selected) 6.dp else 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(emoji, fontSize = 48.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                label,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) PrimaryBlue else Charcoal
            )
        }
    }
}

// ── STEP 2: LANGUAGE ──────────────────────────────────────────────────────────

@Composable
fun LanguageStep(state: OnboardingUiState, vm: OnboardingViewModel) {
    StepScaffold(progress = 0.20f) {
        Text(
            "🌐",
            fontSize = 56.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Choose your language",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "I'll understand your voice commands in this language.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(LANGUAGES) { lang ->
                val selected = state.language == lang.code
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { vm.setLanguage(lang.code) }
                        .border(
                            1.5.dp,
                            if (selected) PrimaryBlue else Color.Transparent,
                            RoundedCornerShape(14.dp)
                        ),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) LightBlue else CardWhite
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(lang.nativeName, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                        Spacer(Modifier.weight(1f))
                        Text(lang.englishName, color = MutedGray, fontSize = 14.sp)
                        if (selected) {
                            Spacer(Modifier.width(8.dp))
                            Text("✓", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        PrimaryButton("Continue") { vm.nextStep() }
    }
}

// ── STEP 3: COUNTRY ───────────────────────────────────────────────────────────

@Composable
fun CountryStep(state: OnboardingUiState, vm: OnboardingViewModel) {
    StepScaffold(progress = 0.30f) {
        Text(
            "📍",
            fontSize = 56.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Where are you based?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Used only to set time format. Stored locally.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(COUNTRIES) { country ->
                val selected = state.country == country.code
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { vm.setCountry(country.code) }
                        .border(
                            1.5.dp,
                            if (selected) PrimaryBlue else Color.Transparent,
                            RoundedCornerShape(14.dp)
                        ),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) LightBlue else CardWhite
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(country.flag, fontSize = 22.sp)
                        Spacer(Modifier.width(12.dp))
                        Text(country.name, fontWeight = FontWeight.Medium)
                        Spacer(Modifier.weight(1f))
                        if (selected) Text("✓", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        PrimaryButton("Continue") { vm.nextStep() }
    }
}

// ── STEP 4: AGE ───────────────────────────────────────────────────────────────

@Composable
fun AgeStep(state: OnboardingUiState, vm: OnboardingViewModel) {
    StepScaffold(progress = 0.40f) {
        Spacer(Modifier.height(16.dp))
        Text(
            "🎂",
            fontSize = 56.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Your age range",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Helps me adjust how I talk with you. Always private.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))

        // 2-column grid of age chips
        val rows = AGE_RANGES.chunked(2)
        rows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            ) {
                row.forEach { (code, label) ->
                    val selected = state.ageRange == code
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clickable { vm.setAgeRange(code) }
                            .border(
                                2.dp,
                                if (selected) PrimaryBlue else Color.Transparent,
                                RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selected) LightBlue else CardWhite
                        )
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                label,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                color = if (selected) PrimaryBlue else Charcoal,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                // If odd row, fill empty space
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }

        Spacer(Modifier.weight(1f))
        PrimaryButton("Continue") { vm.nextStep() }
        Spacer(Modifier.height(8.dp))
        TextButton(
            onClick = { vm.nextStep() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Prefer not to say", color = MutedGray)
        }
    }
}

// ── STEP 5: WELCOME ───────────────────────────────────────────────────────────

@Composable
fun WelcomeStep(vm: OnboardingViewModel) {
    StepScaffold(progress = 0.50f) {
        Spacer(Modifier.weight(0.3f))
        Text("🌿", fontSize = 80.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.height(24.dp))
        Text(
            "Welcome to CalmTask",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Your calm, private voice task companion.\nLet's get you set up in 1 minute.",
            style = MaterialTheme.typography.bodyLarge,
            color = MutedGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.weight(0.7f))
        PrimaryButton("Let's go") { vm.nextStep() }
    }
}

// ── STEP 6: HOW IT WORKS ─────────────────────────────────────────────────────

@Composable
fun HowItWorksStep(vm: OnboardingViewModel) {
    StepScaffold(progress = 0.60f) {
        Spacer(Modifier.height(16.dp))
        Text("🎙️", fontSize = 72.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.height(16.dp))
        Text(
            "Talk to manage tasks",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Tap the mic and say things like \"Add buy milk\" or \"Done\" or \"What's next\".\nYour voice never leaves your phone.",
            style = MaterialTheme.typography.bodyLarge,
            color = MutedGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        val commands = listOf("add…", "done", "skip", "later", "what's next")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
        ) {
            commands.forEach { cmd ->
                Surface(
                    shape = RoundedCornerShape(50),
                    color = LightBlue,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        cmd,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
        PrimaryButton("Got it") { vm.nextStep() }
    }
}

// ── STEP 7: TRY COMMAND ───────────────────────────────────────────────────────

@Composable
fun TryCommandStep(state: OnboardingUiState, vm: OnboardingViewModel) {
    StepScaffold(progress = 0.70f) {
        Spacer(Modifier.height(16.dp))
        Text(
            "Try saying a command",
            style = MaterialTheme.typography.head
