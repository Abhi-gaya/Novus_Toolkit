package com.example.novustoolkit.roomdb.expensemanager

import androidx.room.*
import com.example.novustoolkit.roomdb.expensemanager.Category.category
import com.example.novustoolkit.roomdb.passwordmanager.Passwords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Dao
interface ExpenseDao {

    @Upsert
    suspend fun AddExpense(expense : expense_table)

    @Delete
    suspend fun DeleteExpense(expense: expense_table)

//    @Query("Select * From expense_table Where Category = 'Food'")
//    suspend fun getAllFoodExpense() : Flow<List<expense_table>>
//
//    @Query("Select * From expense_table Where Category = 'Shopping'")
//    suspend fun getAllShoppingExpense() : Flow<List<expense_table>>
//
//    @Query("Select * From expense_table Where Category = 'Transport'")
//    suspend fun getAllTransportExpense() : Flow<List<expense_table>>
//
//    @Query("Select * From expense_table Where Category = 'Entertainment'")
//    suspend fun getAllEntertainmentExpense() : Flow<List<expense_table>>
//
//    @Query("Select * From expense_table Where Category = 'Others'")
//    suspend fun getAllOthersExpense() : Flow<List<expense_table>>

    @Query("Select SUM(Price) From expense_table")
    fun getTotalExpense() :Flow<Int?>

    @Query("Select SUM(Price) From expense_table Where category like 'Food'")
    fun getfoodCategoryTotal(): Flow<Int>

    @Query("Select SUM(Price) From expense_table Where category like  'Shopping'")
    fun getshopCategoryTotal(): Flow<Int>

    @Query("Select SUM(Price) From expense_table Where category like  'Transport'")
    fun gettransCategoryTotal(): Flow<Int>

    @Query("Select SUM(Price) From expense_table Where category like 'Entertainment'")
    fun getenterCategoryTotal(): Flow<Int>

    @Query("Select SUM(Price) From expense_table Where category like 'Others'")
    fun getotherCategoryTotal(): Flow<Int>

    //sorting categories start
    @Query("Select * From expense_table ORDER BY month ASC")
    fun getOrderedAtoZ(): Flow<List<expense_table>>

    @Query("Select * From expense_table ORDER BY month DESC")
    fun getOrderedZtoA(): Flow<List<expense_table>>

}

@Dao
interface budgetDao{

    @Upsert
    suspend fun addBudget(budget: budget)

//    @Query("Update budget Set budgetvalue = :value Where id=:0")
//    suspend fun updateBudget(value :Int) :Int
}