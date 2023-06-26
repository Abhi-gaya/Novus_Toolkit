package com.example.novustoolkit.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.novustoolkit.roomdb.expensemanager.ExpenseDao
import com.example.novustoolkit.roomdb.expensemanager.budget
import com.example.novustoolkit.roomdb.expensemanager.budgetDao
import com.example.novustoolkit.roomdb.expensemanager.expense_table
import com.example.novustoolkit.roomdb.notemanager.Notes
import com.example.novustoolkit.roomdb.notemanager.notesDao
import com.example.novustoolkit.roomdb.passwordmanager.Passwords
import com.example.novustoolkit.roomdb.passwordmanager.passwordDao
import com.example.novustoolkit.roomdb.taskmanager.tasks
import com.example.novustoolkit.roomdb.taskmanager.tasksDao

@Database(
        entities = [tasks::class, Passwords::class, Notes::class,expense_table::class,budget::class],
        version = 6
)
abstract class DatabaseTask: RoomDatabase(){
    abstract val taskdao : tasksDao
    abstract val passwordsDao : passwordDao
    abstract val notesdao :notesDao
    abstract val expenseDao : ExpenseDao
    abstract val budgetDao :budgetDao
}