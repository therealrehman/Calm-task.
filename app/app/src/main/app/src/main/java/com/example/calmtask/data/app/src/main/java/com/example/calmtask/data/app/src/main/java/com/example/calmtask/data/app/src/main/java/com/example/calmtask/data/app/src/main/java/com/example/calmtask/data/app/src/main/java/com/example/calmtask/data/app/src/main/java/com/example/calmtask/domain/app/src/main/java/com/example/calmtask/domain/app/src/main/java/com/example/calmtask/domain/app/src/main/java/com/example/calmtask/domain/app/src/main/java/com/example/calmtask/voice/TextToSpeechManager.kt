package com.example.calmtask.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import java.util.Locale

class TextToSpeechManager(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false
    private val pendingQueue = mutableListOf<String>()

    // Called after settings are loaded to configure voice
    fun initialize(
        gender: String = "FEMALE",
        languageCode: String = "en",
        speed: Float = 1.0f,
        pitch: Float = 1.0f,
        onReady: (() -> Unit)? = null
    ) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isReady = true
                applyVoiceSettings(gender, languageCode, speed, pitch)
                pendingQueue.forEach { speak(it) }
                pendingQueue.clear()
                onReady?.invoke()
            } else {
                Log.e("TTS", "Initialization failed: $status")
            }
        }
    }

    fun applyVoiceSettings(
        gender: String,
        languageCode: String,
        speed: Float = 1.0f,
        pitch: Float = 1.0f
    ) {
        val engine = tts ?: return
        engine.setSpeechRate(speed)
        engine.setPitch(pitch)
        selectVoice(engine, gender, languageCode)
    }

    /**
     * Selects a TTS voice matching the user's gender and language.
     *
     * Android Google TTS voice names follow patterns:
     *   Female: contains "female", "x-sfg", "x-tpd", "x-iol", "x-nfy" etc.
     *   Male:   contains "male",   "x-sfr", "x-iob", "x-tpf" etc.
     *
     * We try to match by name pattern; fall back to first available locale voice.
     */
    private fun selectVoice(engine: TextToSpeech, gender: String, languageCode: String) {
        val locale = when (languageCode) {
            "ur" -> Locale("ur", "PK")
            "ar" -> Locale("ar", "SA")
            "hi" -> Locale("hi", "IN")
            "zh" -> Locale("zh", "CN")
            "fr" -> Locale("fr", "FR")
            "de" -> Locale("de", "DE")
            "es" -> Locale("es", "ES")
            "pt" -> Locale("pt", "BR")
            "tr" -> Locale("tr", "TR")
            "ru" -> Locale("ru", "RU")
            "bn" -> Locale("bn", "BD")
            "id" -> Locale("id", "ID")
            "ja" -> Locale("ja", "JP")
            "ko" -> Locale("ko", "KR")
            else  -> Locale.ENGLISH
        }

        // Set base locale first as fallback
        val langResult = engine.setLanguage(locale)
        if (langResult == TextToSpeech.LANG_NOT_SUPPORTED ||
            langResult == TextToSpeech.LANG_MISSING_DATA) {
            engine.setLanguage(Locale.ENGLISH)
        }

        // Try to select a gender-matched voice
        val availableVoices = engine.voices ?: return
        val lang = locale.language

        // Female voice name patterns from Google TTS
        val femalePatterns = listOf(
            "female", "-f-", "x-sfg", "x-tpd", "x-iol", "x-nfy",
            "x-sfc", "emma", "alice", "lisa", "sara"
        )
        // Male voice name patterns from Google TTS
        val malePatterns = listOf(
            "male", "-m-", "x-sfr", "x-iob", "x-tpf", "x-nfm",
            "x-sfb", "james", "john", "david", "michael"
        )

        val patterns = if (gender.uppercase() == "MALE") malePatterns else femalePatterns

        // Filter to correct language, prefer offline voices
        val langVoices = availableVoices
            .filter { it.locale.language == lang && !it.isNetworkConnectionRequired }
            .ifEmpty { availableVoices.filter { it.locale.language == lang } }

        val matched = langVoices.firstOrNull { voice ->
            val name = voice.name.lowercase()
            patterns.any { name.contains(it) }
        }

        when {
            matched != null -> engine.voice = matched
            langVoices.isNotEmpty() -> engine.voice = langVoices.first()
            // No voices for language, keep default
        }

        Log.d("TTS", "Selected voice: ${engine.voice?.name} for gender=$gender lang=$languageCode")
    }

    fun speak(text: String) {
        if (!isReady) {
            pendingQueue.add(text)
            return
        }
        tts?.speak(text, TextToSpeech.QUEUE_ADD, null, "calmtask_${System.currentTimeMillis()}")
    }

    fun stop() = tts?.stop()

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isReady = false
    }
}
