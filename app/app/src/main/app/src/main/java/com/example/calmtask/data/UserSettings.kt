package com.example.calmtask.data

data class UserSettings(
    // ── Profile (NEW) ──────────────────────────────────
    val userName: String = "",
    val gender: String = "FEMALE",          // "MALE" or "FEMALE"
    val language: String = "en",            // BCP-47 language code e.g. "en", "ur", "ar"
    val country: String = "PK",             // ISO 3166-1 alpha-2 country code
    val ageRange: String = "18-25",         // "under18", "18-25", "26-35", "36-45", "46-55", "55+"

    // ── Voice ───────────────────────────────────────────
    val voiceRepliesEnabled: Boolean = true,
    val morningGreetingEnabled: Boolean = true,
    val nightSummaryEnabled: Boolean = true,
    val voiceSpeed: Float = 1.0f,           // TTS speed 0.5 – 2.0
    val voicePitch: Float = 1.0f,           // TTS pitch 0.5 – 2.0

    // ── Mood ────────────────────────────────────────────
    val moodAwareModeEnabled: Boolean = false,
    val moodListeningMode: String = "taponly",
    val saveMoodHistory: Boolean = false,
    val currentMood: String = "UNKNOWN",

    // ── Reminders ───────────────────────────────────────
    val reminderIntensity: String = "normal",
    val morningStartMinutes: Int = 420,     // 7:00 AM
    val morningEndMinutes: Int = 660,       // 11:00 AM
    val nightReviewMinutes: Int = 1260,     // 9:00 PM
    val quietStartMinutes: Int = 1380,      // 11:00 PM
    val quietEndMinutes: Int = 420,         // 7:00 AM
    val assistantPausedUntilMillis: Long = 0L,

    // ── Onboarding ──────────────────────────────────────
    val onboardingCompleted: Boolean = false
)
