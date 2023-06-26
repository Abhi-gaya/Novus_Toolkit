package com.example.novustoolkit.roomdb.taskmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tasks(
    val title: String,
    val description: String,
    val priority : Priority,
    @PrimaryKey(autoGenerate =true)
    val Id :Int = 0
)
