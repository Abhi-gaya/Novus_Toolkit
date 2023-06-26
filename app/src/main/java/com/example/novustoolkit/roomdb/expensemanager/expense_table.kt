package com.example.novustoolkit.roomdb.expensemanager

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.novustoolkit.roomdb.expensemanager.Category.category

@Entity
data class expense_table(
    val Price : Int = 0,
    val Description: String,
    val Category : category,
    val month: Month,
    @PrimaryKey(autoGenerate = true)
    val expenseId : Int = 0
)

@Entity
data class budget(
    val budgetvalue : Int = 0,
    @PrimaryKey(autoGenerate = true)
    val budgetId : Int = 0
)