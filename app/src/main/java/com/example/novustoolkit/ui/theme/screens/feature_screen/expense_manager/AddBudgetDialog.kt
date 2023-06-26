package com.example.novustoolkit.ui.theme.screens.feature_screen.expense_manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.novustoolkit.roomdb.expensemanager.ExpenseEvent
import com.example.novustoolkit.roomdb.expensemanager.ExpenseState
import com.example.novustoolkit.ui.theme.LightGreen
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.viewModel.SharedViewModel

@Composable
fun AddBudgetDialog(
    state:ExpenseState,
    onEvent :(ExpenseEvent) -> Unit,
    viewmodel :SharedViewModel
){
    val restorebudget by viewmodel.restoreBudget.collectAsState(state.Budget)
    AlertDialog(
        onDismissRequest = {
            onEvent(ExpenseEvent.HideBudgetDialog)
        }, modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
       text = {
           Column(verticalArrangement = Arrangement.SpaceBetween,
           horizontalAlignment = Alignment.CenterHorizontally) {
               TextField(value = state.Budget.toString(),
                   onValueChange = {
                       onEvent(ExpenseEvent.SetBudget(it.toInt()))
                   },
                   label = {
                       Text(text = "Budget")
                   })
           }
       },
       buttons = {
           Box(
               contentAlignment = Alignment.Center,
               modifier = Modifier.fillMaxWidth()
           ) {
               Button(onClick = {
                   onEvent(ExpenseEvent.HideBudgetDialog)
               },
                   colors = ButtonDefaults.buttonColors(
                       backgroundColor = LightGreen
                   )
               ) {
                   Text(
                       text = "Add Budget",
                       color = Color.Black,
                       fontWeight = FontWeight.SemiBold
                   )
               }
           }
       }
    )
}