package com.example.calmtask.domain

object VoiceCommandParser {
    fun parse(input: String): VoiceCommand {
        val text = input.trim().lowercase()
        return when {
            text.startsWith("add ") ->
                VoiceCommand.AddTask(input.substring(4).trim())
            text.startsWith("remind me to ") ->
                VoiceCommand.AddTask(input.substring(13).trim())
            text in listOf("done", "complete", "completed", "finished", "finish") ->
                VoiceCommand.CompleteFocusTask
            text == "skip" ->
                VoiceCommand.SkipFocusTask
            text in listOf("later", "remind me later", "snooze") ->
                VoiceCommand.SnoozeFocusTask
            text in listOf("tomorrow", "move to tomorrow") ->
                VoiceCommand.MoveFocusToTomorrow
            text in listOf("what's next", "what is next", "read tasks", "my tasks") ->
                VoiceCommand.ReadTodayTasks
            text in listOf("plan tomorrow", "tomorrow's plan") ->
                VoiceCommand.PlanTomorrow
            else -> VoiceCommand.Unknown
        }
    }
}
