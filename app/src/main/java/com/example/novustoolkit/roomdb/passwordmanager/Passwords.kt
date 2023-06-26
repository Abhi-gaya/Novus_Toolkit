package com.example.novustoolkit.roomdb.passwordmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Passwords(
    val platform_name : String,
    val username : String,
    val password : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
)