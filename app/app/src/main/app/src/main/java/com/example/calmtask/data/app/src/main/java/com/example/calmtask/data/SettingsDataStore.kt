package com.example.calmtask.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class SettingsDataStore(private val context: Context) {

    companion object Keys {
        val USER_NAME                   = stringPreferencesKey("user_name")
        val GENDER                      = stringPreferencesKey("gender")
        val LANGUAGE                    = stringPreferencesKey("language")
        val COUNTRY                     = stringPreferencesKey("country")
        val AGE_RANGE                   = stringPreferencesKey("age_range")
        val VOICE_REPLIES_ENABLED       = booleanPreferencesKey("voice_replies_enabled")
        val MORNING_GREETING_ENABLED    = booleanPreferencesKey("morning_greeting_enabled")
        val NIGHT_SUMMARY_ENABLED       = booleanPreferencesKey("night_summary_enabled")
        val VOICE_SPEED                 = floatPreferencesKey("voice_speed")
        val VOICE_PITCH                 = floatPreferencesKey("voice_pitch")
        val MOOD_AWARE_MODE_ENABLED     = booleanPreferencesKey("mood_aware_mode_enabled")
        val MOOD_LISTENING_MODE         = stringPreferencesKey("mood_listening_mode")
        val SAVE_MOOD_HISTORY           = booleanPreferencesKey("save_mood_history")
        val CURRENT_MOOD                = stringPreferencesKey("current_mood")
        val REMINDER_INTENSITY          = stringPreferencesKey("reminder_intensity")
        val MORNING_START_MINUTES       = intPreferencesKey("morning_start_minutes")
        val MORNING_END_MINUTES         = intPreferencesKey("morning_end_minutes")
        val NIGHT_REVIEW_MINUTES        = intPreferencesKey("night_review_minutes")
        val QUIET_START_MINUTES         = intPreferencesKey("quiet_start_minutes")
        val QUIET_END_MINUTES           = intPreferencesKey("quiet_end_minutes")
        val ASSISTANT_PAUSED_UNTIL      = longPreferencesKey("assistant_paused_until")
        val ONBOARDING_COMPLETED        = booleanPreferencesKey("onboarding_completed")
    }

    val settingsFlow: Flow<UserSettings> = context.dataStore.data.map { prefs ->
        UserSettings(
            userName                = prefs[USER_NAME] ?: "",
            gender                  = prefs[GENDER] ?: "FEMALE",
            language                = prefs[LANGUAGE] ?: "en",
            country                 = prefs[COUNTRY] ?: "PK",
            ageRange                = prefs[AGE_RANGE] ?: "18-25",
            voiceRepliesEnabled     = prefs[VOICE_REPLIES_ENABLED] ?: true,
            morningGreetingEnabled  = prefs[MORNING_GREETING_ENABLED] ?: true,
            nightSummaryEnabled     = prefs[NIGHT_SUMMARY_ENABLED] ?: true,
            voiceSpeed              = prefs[VOICE_SPEED] ?: 1.0f,
            voicePitch              = prefs[VOICE_PITCH] ?: 1.0f,
            moodAwareModeEnabled    = prefs[MOOD_AWARE_MODE_ENABLED] ?: false,
            moodListeningMode       = prefs[MOOD_LISTENING_MODE] ?: "taponly",
            saveMoodHistory         = prefs[SAVE_MOOD_HISTORY] ?: false,
            currentMood             = prefs[CURRENT_MOOD] ?: "UNKNOWN",
            reminderIntensity       = prefs[REMINDER_INTENSITY] ?: "normal",
            morningStartMinutes     = prefs[MORNING_START_MINUTES] ?: 420,
            morningEndMinutes       = prefs[MORNING_END_MINUTES] ?: 660,
            nightReviewMinutes      = prefs[NIGHT_REVIEW_MINUTES] ?: 1260,
            quietStartMinutes       = prefs[QUIET_START_MINUTES] ?: 1380,
            quietEndMinutes         = prefs[QUIET_END_MINUTES] ?: 420,
            assistantPausedUntilMillis = prefs[ASSISTANT_PAUSED_UNTIL] ?: 0L,
            onboardingCompleted     = prefs[ONBOARDING_COMPLETED] ?: false
        )
    }

    suspend fun saveSettings(settings: UserSettings) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME]                = settings.userName
            prefs[GENDER]                   = settings.gender
            prefs[LANGUAGE]                 = settings.language
            prefs[COUNTRY]                  = settings.country
            prefs[AGE_RANGE]                = settings.ageRange
            prefs[VOICE_REPLIES_ENABLED]    = settings.voiceRepliesEnabled
            prefs[MORNING_GREETING_ENABLED] = settings.morningGreetingEnabled
            prefs[NIGHT_SUMMARY_ENABLED]    = settings.nightSummaryEnabled
            prefs[VOICE_SPEED]              = settings.voiceSpeed
            prefs[VOICE_PITCH]              = settings.voicePitch
            prefs[MOOD_AWARE_MODE_ENABLED]  = settings.moodAwareModeEnabled
            prefs[MOOD_LISTENING_MODE]      = settings.moodListeningMode
            prefs[SAVE_MOOD_HISTORY]        = settings.saveMoodHistory
            prefs[CURRENT_MOOD]             = settings.currentMood
            prefs[REMINDER_INTENSITY]       = settings.reminderIntensity
            prefs[MORNING_START_MINUTES]    = settings.morningStartMinutes
            prefs[MORNING_END_MINUTES]      = settings.morningEndMinutes
            prefs[NIGHT_REVIEW_MINUTES]     = settings.nightReviewMinutes
            prefs[QUIET_START_MINUTES]      = settings.quietStartMinutes
            prefs[QUIET_END_MINUTES]        = settings.quietEndMinutes
            prefs[ASSISTANT_PAUSED_UNTIL]   = settings.assistantPausedUntilMillis
            prefs[ONBOARDING_COMPLETED]     = settings.onboardingCompleted
        }
    }

    suspend fun updateField(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { it[key] = value }
    }

    suspend fun updateField(key: Preferences.Key<Boolean>, value: Boolean) {
        context.dataStore.edit { it[key] = value }
    }

    suspend fun markOnboardingCompleted() {
        context.dataStore.edit { it[ONBOARDING_COMPLETED] = true }
    }
}
