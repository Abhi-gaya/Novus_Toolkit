package com.example.novustoolkit.roomdb.notemanager

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Notes(
    val title : String,
    val content : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int =0
)
