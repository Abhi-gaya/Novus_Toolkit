package com.example.novustoolkit.roomdb.expensemanager

import com.example.novustoolkit.roomdb.expensemanager.Category.category

sealed interface ExpenseEvent {
     object SaveExpense : ExpenseEvent
     data class SetPrice(val price: Int) :ExpenseEvent
     data class SetDesc(val description : String) :ExpenseEvent
     data class SetCategory(val category :category) : ExpenseEvent
     data class SetMonth(val month :Month) : ExpenseEvent
     data class SetBudget(val budget : Int) : ExpenseEvent
     object ShowBudgetDialog : ExpenseEvent
     object HideBudgetDialog : ExpenseEvent
     data class DeleteExpense(val expense:expense_table):ExpenseEvent
     data class SortExpenses(val sortTypeExpenses: SortTypeExpenses) :ExpenseEvent
}