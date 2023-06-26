package com.example.novustoolkit.roomdb.passwordmanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.novustoolkit.roomdb.notemanager.Notes
import kotlinx.coroutines.flow.Flow


@Dao
interface passwordDao {

    @Upsert
    suspend fun upsertPassword(passwords: Passwords)

    @Delete
    suspend fun deletePassword(passwords: Passwords)


    @Query("Select * From Passwords ORDER BY platform_name ASC")
    fun getOrderedAtoZ(): Flow<List<Passwords>>

    @Query("Select * From Passwords ORDER BY platform_name DESC")
    fun getOrderedZtoA(): Flow<List<Passwords>>

    @Query("Select * From Passwords Where username Like :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Passwords>>

}