package com.example.calmtask.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.Locale

class SpeechToTextManager(private val context: Context) {

    private var recognizer: SpeechRecognizer? = null

    fun startListening(
        languageCode: String = "en",
        onResult: (String) -> Unit,
        onError: (Int) -> Unit
    ) {
        recognizer?.destroy()
        recognizer = SpeechRecognizer.createSpeechRecognizer(context)

        val locale = when (languageCode) {
            "ur" -> "ur-PK"
            "ar" -> "ar-SA"
            "hi" -> "hi-IN"
            "zh" -> "zh-CN"
            "fr" -> "fr-FR"
            "de" -> "de-DE"
            "es" -> "es-ES"
            else -> "en-US"
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        recognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partial: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val text = matches?.firstOrNull() ?: ""
                onResult(text)
            }
            override fun onError(error: Int) { onError(error) }
        })
        recognizer?.startListening(intent)
    }

    fun stop() = recognizer?.stopListening()

    fun destroy() {
        recognizer?.destroy()
        recognizer = null
    }
}
