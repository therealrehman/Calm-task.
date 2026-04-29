package com.example.calmtask.voice

import com.example.calmtask.domain.MoodState

object AssistantPhrases {

    fun morningGreeting(
        name: String,
        taskCount: Int,
        focusTitle: String?,
        mood: MoodState
    ): String {
        val displayName = if (name.isBlank()) "" else " $name"
        return when {
            mood == MoodState.TIRED ->
                "Morning$displayName. Take it easy today. I'll keep your list small."
            mood == MoodState.STRESSED ->
                "Morning$displayName. Let's focus on just one thing today."
            taskCount == 0 ->
                "Good morning$displayName. Your list is clear. Want to add something for today?"
            focusTitle != null ->
                "Good morning$displayName. You have $taskCount tasks today. Your focus is: $focusTitle."
            else ->
                listOf(
                    "Morning$displayName. Let's keep it simple today. One thing at a time.",
                    "Good morning. You have $taskCount things waiting. Ready when you are.",
                    "Morning$displayName. I'll keep this brief. Here's what's on your list."
                ).random()
        }
    }

    fun nightSummary(name: String, completedCount: Int, pendingCount: Int): String {
        val displayName = if (name.isBlank()) "" else " $name"
        return when {
            pendingCount == 0 && completedCount > 0 ->
                "Amazing$displayName. Everything done today. Enjoy your evening."
            completedCount == 0 ->
                "Good evening$displayName. Rest well. Tomorrow's list is ready when you wake up."
            else ->
                listOf(
                    "Good evening$displayName. You completed $completedCount tasks today. Well done.",
                    "Evening$displayName. $completedCount done, $pendingCount moved to tomorrow. That's a solid day.",
                    "Good night$displayName. Rest well. Tomorrow is ready."
                ).random()
        }
    }

    fun taskAdded(title: String): String =
        listOf("Added: $title.", "Got it. $title is on your list.", "Done. I added: $title.").random()

    fun taskCompleted(): String =
        listOf(
            "Done. That's one less thing to carry.",
            "Marked complete.",
            "Nice. I cleared it."
        ).random()

    fun taskSkipped(): String =
        listOf("Skipped. Moving on.", "Okay, skipped.").random()

    fun taskSnoozed(): String =
        listOf("Okay. I'll bring it back later.", "No problem. Moved later.").random()

    fun unknownCommand(): String =
        listOf(
            "I missed that. Try a shorter version.",
            "Do you want to add that as a task?",
            "I'm not sure. Try: add, done, later, or skip."
        ).random()

    fun welcomeGreeting(name: String): String {
        val displayName = if (name.isBlank()) "" else "$name. "
        return "Welcome to CalmTask ${displayName}I'm ready when you are."
    }
}
