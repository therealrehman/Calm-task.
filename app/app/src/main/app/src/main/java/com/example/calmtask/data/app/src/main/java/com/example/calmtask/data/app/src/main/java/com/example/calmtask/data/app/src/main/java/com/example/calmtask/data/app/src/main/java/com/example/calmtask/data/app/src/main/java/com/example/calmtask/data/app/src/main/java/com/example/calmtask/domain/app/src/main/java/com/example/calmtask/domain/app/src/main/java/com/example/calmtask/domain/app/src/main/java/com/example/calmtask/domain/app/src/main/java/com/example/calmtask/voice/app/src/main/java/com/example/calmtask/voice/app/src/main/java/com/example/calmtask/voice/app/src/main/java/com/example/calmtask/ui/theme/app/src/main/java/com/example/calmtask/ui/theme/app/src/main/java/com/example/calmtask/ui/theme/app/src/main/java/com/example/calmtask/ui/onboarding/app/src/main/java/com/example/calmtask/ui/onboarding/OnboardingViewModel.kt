package com.example.calmtask.ui.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmtask.data.SettingsDataStore
import com.example.calmtask.data.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val currentStep: OnboardingStep = OnboardingStep.GENDER,
    val gender: String = "FEMALE",
    val language: String = "en",
    val country: String = "PK",
    val ageRange: String = "18-25",
    val userName: String = "",
    val heardText: String = "",
    val micPermissionGranted: Boolean = false,
    val notifPermissionGranted: Boolean = false
)

class OnboardingViewModel(app: Application) : AndroidViewModel(app) {

    private val store = SettingsDataStore(app)
    private val _state = MutableStateFlow(OnboardingUiState())
    val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

    private val stepOrder = OnboardingStep.values().toList()

    fun nextStep() {
        val idx = stepOrder.indexOf(_state.value.currentStep)
        if (idx < stepOrder.lastIndex) {
            _state.update { it.copy(currentStep = stepOrder[idx + 1]) }
        }
    }

    fun prevStep() {
        val idx = stepOrder.indexOf(_state.value.currentStep)
        if (idx > 0) {
            _state.update { it.copy(currentStep = stepOrder[idx - 1]) }
        }
    }

    fun setGender(g: String)       = _state.update { it.copy(gender = g) }
    fun setLanguage(l: String)     = _state.update { it.copy(language = l) }
    fun setCountry(c: String)      = _state.update { it.copy(country = c) }
    fun setAgeRange(a: String)     = _state.update { it.copy(ageRange = a) }
    fun setUserName(n: String)     = _state.update { it.copy(userName = n) }
    fun setHeardText(t: String)    = _state.update { it.copy(heardText = t) }
    fun setMicGranted(v: Boolean)  = _state.update { it.copy(micPermissionGranted = v) }
    fun setNotifGranted(v: Boolean)= _state.update { it.copy(notifPermissionGranted = v) }

    fun completeOnboarding() = viewModelScope.launch {
        val s = _state.value
        store.saveSettings(
            UserSettings(
                userName           = s.userName,
                gender             = s.gender,
                language           = s.language,
                country            = s.country,
                ageRange           = s.ageRange,
                onboardingCompleted= true
            )
        )
    }
}
