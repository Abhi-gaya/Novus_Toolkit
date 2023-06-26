package com.example.novustoolkit.roomdb.expensemanager

import com.example.novustoolkit.roomdb.expensemanager.Category.category

data class ExpenseState(
    val expenses : List<expense_table> = emptyList(),
    val Budget : Int=0,
    val price : Int?=0,
    val description : String = "",
    val month: Month = Month.Jan,
    val Category: category = category.Food,
    val isAddingBudget : Boolean = false,
    val sortTypeExpenses: SortTypeExpenses = SortTypeExpenses.A_to_Z,
)
