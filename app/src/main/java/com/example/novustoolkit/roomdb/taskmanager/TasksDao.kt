package com.example.novustoolkit.roomdb.taskmanager

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface tasksDao {

    @Upsert
    suspend fun upsertTask(task: tasks)

    @Delete
    suspend fun deletetask(task: tasks)

    @Query("Select * From tasks Order By Case When priority Like 'L%' Then 1 When priority Like 'M%' Then 2 When priority Like 'H%' Then 3 End")
    fun sortbyLowPriority(): Flow<List<tasks>>

    @Query("Select * From tasks Order By Case when priority Like 'H%' Then 1 When priority Like 'M%' Then 2 When priority Like 'L%' Then 3 End")
    fun sortbyHighPriority(): Flow<List<tasks>>

    @Query("Select * From tasks Order By Case when priority Like 'M%' Then 1 When priority Like 'L%' Then 2 When priority Like 'H%' Then 3 End")
    fun sortbyMediumPriority(): Flow<List<tasks>>
}