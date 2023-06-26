package com.example.novustoolkit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.novustoolkit.roomdb.expensemanager.ExpenseEvent
import com.example.novustoolkit.roomdb.expensemanager.ExpenseState
import com.example.novustoolkit.roomdb.notemanager.NoteState
import com.example.novustoolkit.roomdb.notemanager.NotesEvent
import com.example.novustoolkit.roomdb.passwordmanager.PasswordEvent
import com.example.novustoolkit.roomdb.passwordmanager.PasswordState
import com.example.novustoolkit.roomdb.taskmanager.TaskEvent
import com.example.novustoolkit.roomdb.taskmanager.TaskState
import com.example.novustoolkit.ui.theme.screens.feature_screen.expense_manager.Expense_manager
import com.example.novustoolkit.ui.theme.screens.feature_screen.expense_manager.category_screens.Food_Category
import com.example.novustoolkit.ui.theme.screens.feature_screen.notes_manager.Notes_manager
import com.example.novustoolkit.ui.theme.screens.feature_screen.password_manager.Password_manager
import com.example.novustoolkit.ui.theme.screens.feature_screen.task_manager.Task_manager
import com.example.novustoolkit.ui.theme.screens.home_screen.HomeScreen
import com.example.novustoolkit.ui.theme.screens.home_screen.novus
import com.example.novustoolkit.ui.theme.screens.splash_screen.Splash_screen
import com.example.novustoolkit.viewModel.SharedViewModel

@Composable
fun SetupNav(navController : NavHostController,
             taskState : TaskState,
             passwordState: PasswordState,
             noteState : NoteState,
             expenseState : ExpenseState,
             onTaskEvent : (TaskEvent)-> Unit,
             onPassEvent : (PasswordEvent) -> Unit,
             onNoteEvent : (NotesEvent)-> Unit,
             onExpenseEvent : (ExpenseEvent)->Unit,
             viewModel : SharedViewModel
){
     NavHost(navController = navController,
     startDestination = novus.splash_screen.name
     ){
         composable(route = novus.splash_screen.name){
             Splash_screen( navController = navController,
             novus = novus.home,
             novus2 = novus.splash_screen)
         }
        composable(route = novus.home.name){
            HomeScreen(onTaskButtonClicked = {
                navController.navigate(novus.task_home.name)
            },
            onPassButtonClicked = {
                navController.navigate(novus.pass_home.name)
            },
            onExpenseButtonClicked = {
                navController.navigate(novus.expense_home.name)
            },
            onNotesButtonClicked = {
                navController.navigate(novus.notes_home.name)
            }
            )
        }
        composable(novus.task_home.name){
             Task_manager(
                 onHomeClicked = {
                     navController.navigate(novus.home.name){
                         popUpTo(novus.home.name){
                             inclusive = true
                         }
                     }
                 },
                 state = taskState,
                 onEvent = onTaskEvent
             )
        }
        composable(novus.pass_home.name){
            Password_manager(
                onHomeClicked = {
                    navController.navigate(novus.home.name){
                        popUpTo(novus.home.name){
                            inclusive = true
                        }
                    }
                },
                state = passwordState,
                onEvent = onPassEvent
            )
        }
         composable(novus.expense_home.name){
             Expense_manager(
                 onHomeClicked = {
                     navController.navigate(novus.home.name){
                         popUpTo(novus.home.name){
                             inclusive = true
                         }
                     }
                 },
                 onFoodClicked = {
                     navController.navigate(novus.expense_list.name)
                 },
                 state = expenseState,
                 onEvent = onExpenseEvent,
                 viewModel = viewModel
             )
         }
         composable(novus.notes_home.name){
             Notes_manager(
                 onHomeClicked = {
                     navController.navigate(novus.home.name){
                         popUpTo(novus.home.name){
                             inclusive = true
                         }
                     }
                 },
                 state = noteState,
                 onEvent = onNoteEvent
             )
         }
         composable(novus.expense_list.name){
             Food_Category(
                 onHomeClicked = {
                     navController.navigate(novus.expense_home.name){
                         popUpTo(novus.expense_home.name){
                             inclusive=true
                         }
                     }
                 },
                 state = expenseState,
                 viewModel = viewModel,
                 onEvent = onExpenseEvent
             )
         }
     }
}