package com.example.calmtask.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {

    val activeTasks: Flow<List<TaskEntity>> = dao.getActiveTasks()
    val allTasks: Flow<List<TaskEntity>> = dao.getAllTasks()

    suspend fun addTask(title: String): Long =
        dao.insertTask(TaskEntity(title = title))

    suspend fun completeTask(id: Long) = dao.markComplete(id)

    suspend fun skipTask(id: Long) = dao.markSkipped(id)

    suspend fun getFocusTask(): TaskEntity? = dao.getFocusTask()

    suspend fun setFocusTask(id: Long) {
        dao.clearFocusTask()
        dao.setFocusTask(id)
    }

    suspend fun updateTask(task: TaskEntity) = dao.updateTask(task)

    suspend fun deleteTask(task: TaskEntity) = dao.deleteTask(task)

    suspend fun deleteAll() = dao.deleteAllTasks()

    suspend fun countTodayCompleted(): Int = dao.countTodayCompleted()
}
