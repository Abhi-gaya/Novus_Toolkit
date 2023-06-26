package com.example.novustoolkit.roomdb.notemanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface notesDao {

    @Upsert
    suspend fun upsertNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("Select * From Notes Where id = :id")
    suspend fun getNoteById(id : Int) :Notes?

    @Query("Select * From Notes ORDER BY title ASC")
    fun getOrderedAtoZ(): Flow<List<Notes>>

    @Query("Select * From Notes ORDER BY title DESC")
    fun getOrderedZtoA(): Flow<List<Notes>>

    @Query("Select * From Notes Where title Like :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Notes>>
}