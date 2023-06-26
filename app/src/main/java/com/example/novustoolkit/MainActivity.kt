package com.example.novustoolkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.novustoolkit.navigation.SetupNav
import com.example.novustoolkit.roomdb.DatabaseTask
import com.example.novustoolkit.ui.theme.NovusToolkitTheme
import com.example.novustoolkit.viewModel.SharedViewModel

class MainActivity : FragmentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            DatabaseTask::class.java,
            "toolkit.db"
        ).fallbackToDestructiveMigration().build()
    }
    private val viewModel by viewModels<SharedViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SharedViewModel(db.taskdao,db.passwordsDao,db.notesdao,db.expenseDao,db.budgetDao) as T
                }
            }
        }
            )
    private lateinit var navController:NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovusToolkitTheme {
                val state by viewModel.state.collectAsState()
                val passState by viewModel.password_State.collectAsState()
                val noteState by viewModel.note_State.collectAsState()
                val expenseSate by viewModel.expenseState.collectAsState()
                navController = rememberNavController()
                SetupNav(navController = navController,
                    taskState = state,
                    passwordState = passState,
                    noteState = noteState ,
                    expenseState = expenseSate,
                    onTaskEvent = viewModel::onTaskEvent,
                    onPassEvent = viewModel::onPassEvent,
                    onNoteEvent =  viewModel::onNoteEvent,
                    onExpenseEvent = viewModel::onExpenseEvent,
                    viewModel = viewModel)
            }
        }
    }
}

