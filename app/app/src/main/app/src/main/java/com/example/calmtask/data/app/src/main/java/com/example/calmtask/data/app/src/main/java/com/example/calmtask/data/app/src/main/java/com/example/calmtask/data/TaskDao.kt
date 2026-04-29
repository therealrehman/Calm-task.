package com.example.calmtask.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE status = 'active' ORDER BY isFocus DESC, createdAtMillis ASC")
    fun getActiveTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY createdAtMillis DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isFocus = 1 AND status = 'active' LIMIT 1")
    suspend fun getFocusTask(): TaskEntity?

    @Query("SELECT COUNT(*) FROM tasks WHERE status = 'completed' AND date(updatedAtMillis/1000,'unixepoch') = date('now')")
    suspend fun countTodayCompleted(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Query("UPDATE tasks SET status = 'completed', updatedAtMillis = :time WHERE id = :id")
    suspend fun markComplete(id: Long, time: Long = System.currentTimeMillis())

    @Query("UPDATE tasks SET status = 'skipped', updatedAtMillis = :time WHERE id = :id")
    suspend fun markSkipped(id: Long, time: Long = System.currentTimeMillis())

    @Query("UPDATE tasks SET isFocus = 0 WHERE isFocus = 1")
    suspend fun clearFocusTask()

    @Query("UPDATE tasks SET isFocus = 1 WHERE id = :id")
    suspend fun setFocusTask(id: Long)
}
