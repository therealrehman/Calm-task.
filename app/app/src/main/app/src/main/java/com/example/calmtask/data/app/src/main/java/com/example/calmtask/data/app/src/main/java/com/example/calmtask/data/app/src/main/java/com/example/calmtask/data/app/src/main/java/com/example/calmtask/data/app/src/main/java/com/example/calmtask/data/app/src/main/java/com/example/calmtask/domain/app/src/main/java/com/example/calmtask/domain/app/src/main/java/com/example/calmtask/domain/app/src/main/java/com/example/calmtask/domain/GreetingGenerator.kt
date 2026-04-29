package com.example.calmtask.domain

import java.util.Calendar

object GreetingGenerator {
    fun timeOfDay(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11  -> "morning"
            in 12..16 -> "afternoon"
            in 17..20 -> "evening"
            else      -> "night"
        }
    }

    fun emoji(): String = when (timeOfDay()) {
        "morning"   -> "☀️"
        "afternoon" -> "🌤️"
        "evening"   -> "🌆"
        else        -> "🌙"
    }

    fun greetingText(name: String): String {
        val time = timeOfDay()
        val displayName = if (name.isBlank()) "" else ", $name"
        return when (time) {
            "morning"   -> "Good morning$displayName"
            "afternoon" -> "Good afternoon$displayName"
            "evening"   -> "Good evening$displayName"
            else        -> "Good night$displayName"
        }
    }
}
