package com.example.calmtask.domain

sealed class VoiceCommand {
    data class AddTask(val title: String) : VoiceCommand()
    object CompleteFocusTask  : VoiceCommand()
    object SkipFocusTask      : VoiceCommand()
    object SnoozeFocusTask    : VoiceCommand()
    object MoveFocusToTomorrow: VoiceCommand()
    object ReadTodayTasks     : VoiceCommand()
    object PlanTomorrow       : VoiceCommand()
    object Unknown            : VoiceCommand()
}
