package com.example.calmtask.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmtask.data.AppDatabase
import com.example.calmtask.data.SettingsDataStore
import com.example.calmtask.data.TaskEntity
import com.example.calmtask.data.TaskRepository
import com.example.calmtask.data.UserSettings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo    = TaskRepository(AppDatabase.getInstance(app).taskDao())
    private val store   = SettingsDataStore(app)

    val settings: StateFlow<UserSettings> = store.settingsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, UserSettings())

    val activeTasks: StateFlow<List<TaskEntity>> = repo.activeTasks
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTask(title: String) = viewModelScope.launch { repo.addTask(title) }
    fun complete(id: Long)     = viewModelScope.launch { repo.completeTask(id) }
    fun skip(id: Long)         = viewModelScope.launch { repo.skipTask(id) }
    fun setFocus(id: Long)     = viewModelScope.launch { repo.setFocusTask(id) }
}
