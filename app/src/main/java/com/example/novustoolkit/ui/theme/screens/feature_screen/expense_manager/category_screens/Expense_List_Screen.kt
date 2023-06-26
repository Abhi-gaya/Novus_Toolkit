package com.example.novustoolkit.ui.theme.screens.feature_screen.expense_manager.category_screens

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novustoolkit.roomdb.expensemanager.ExpenseEvent
import com.example.novustoolkit.roomdb.expensemanager.ExpenseState
import com.example.novustoolkit.roomdb.expensemanager.SortTypeExpenses
import com.example.novustoolkit.roomdb.notemanager.NotesEvent
import com.example.novustoolkit.roomdb.notemanager.SortTypeNotes
import com.example.novustoolkit.ui.theme.LightBlue
import com.example.novustoolkit.ui.theme.screens.feature_screen.task_manager.DefaultAppBar
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor
import com.example.novustoolkit.viewModel.SharedViewModel
import java.util.*

@Composable
fun Food_Category(
    state : ExpenseState,
    onEvent : (ExpenseEvent) -> Unit,
    onHomeClicked : () ->Unit,
    viewModel: SharedViewModel
) {
    val context = LocalContext.current
    val totalFoodCost by viewModel.totalExpense.collectAsState(initial = null)
    var expanded1 by remember{
        mutableStateOf(false)
    }
    val rotate :Float by animateFloatAsState(
        targetValue = if(expanded1) 180f else 0f)
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = LightBlue,
        topBar = {
            DefaultAppBar(onHomeClicked = { onHomeClicked() }, heading = "Expense List")
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(6f))
                    Surface(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(MaterialTheme.colors.topAppBarBackgroundColor)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colors.topAppBarContentColor
                            )
                            .weight(4f),
                        shape = RoundedCornerShape(25)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .clickable { expanded1 = true }
                                .background(MaterialTheme.colors.topAppBarBackgroundColor),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.sortTypeExpenses.name,
                                color = MaterialTheme.colors.topAppBarContentColor,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp)
                            )
                            IconButton(
                                onClick = {
                                    expanded1 = true
                                },
                                modifier = Modifier
                                    .alpha(ContentAlpha.high)
                                    .rotate(rotate)
                                    .weight(0.5f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "Arrow Down",
                                    tint = MaterialTheme.colors.topAppBarContentColor
                                )
                            }
                            DropdownMenu(
                                expanded = expanded1,
                                onDismissRequest = { expanded1 = false },
                            ) {
                                DropdownMenuItem(onClick =
                                {
                                    expanded1 = false
                                    onEvent(ExpenseEvent.SortExpenses(sortTypeExpenses = SortTypeExpenses.A_to_Z))
                                }) {
                                    Text(text = "A to Z")
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded1 = false
                                    onEvent(ExpenseEvent.SortExpenses(sortTypeExpenses = SortTypeExpenses.Z_to_A))
                                }) {
                                    Text(text = "Z to A")
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.2f))
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(state.expenses) { expense ->
                        var showUsername by remember {
                            mutableStateOf(value = false)
                        }
                        val rotate2: Float by animateFloatAsState(
                            targetValue = if (showUsername) 180f else 0f
                        )
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.topAppBarBackgroundColor,
                            shape = RoundedCornerShape(10),
                            elevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(9f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "-₹ " + expense.Price.toString(),
                                            modifier = Modifier
                                                .weight(6f)
                                                .padding(start = 24.dp),
                                            color = Color.Red,
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.h6
                                        )
                                        Text(
                                            text = expense.Category.name.toUpperCase(Locale.getDefault()),
                                            modifier = Modifier
                                                .weight(3f)
                                                .padding(end = 2.dp),
                                            color = MaterialTheme.colors.topAppBarContentColor,
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.subtitle1
                                        )
                                    }
                                    Row(modifier = Modifier.height(35.dp)) {
                                        Spacer(modifier = Modifier.weight(8f))
                                        IconButton(
                                            onClick = {
                                                showUsername = !showUsername
                                            },
                                            modifier = Modifier
                                                .alpha(ContentAlpha.medium)
                                                .rotate(rotate2)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowDropDown,
                                                contentDescription = "Arrow Down",
                                                tint = MaterialTheme.colors.topAppBarContentColor
                                            )
                                        }
                                    }
                                    if (showUsername) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Month: " + expense.month.name,
                                                color = MaterialTheme.colors.topAppBarContentColor,
                                                fontWeight = FontWeight.Normal,
                                                style = MaterialTheme.typography.subtitle2,
                                                modifier = Modifier.padding(
                                                    horizontal = 8.dp,
                                                    vertical = 5.dp
                                                )
                                            )
                                            Divider(modifier = Modifier.fillMaxWidth())
                                            Text(
                                                text = "Details: " + expense.Description,
                                                color = MaterialTheme.colors.topAppBarContentColor,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Normal,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(
                                                    horizontal = 8.dp,
                                                    vertical = 5.dp
                                                )
                                            )
                                        }
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        onEvent(
                                            ExpenseEvent.DeleteExpense(expense = expense)
                                        )
                                        Toast.makeText(
                                            context,
                                            "Expense Deleted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Expense",
                                        tint = MaterialTheme.colors.topAppBarContentColor
                                    )
                                }
                            }

                        }
                    }
                }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Column {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(horizontal = 10.dp),
                        color = MaterialTheme.colors.topAppBarBackgroundColor,
                        contentColor = MaterialTheme.colors.topAppBarContentColor,
                        elevation = 10.dp, shape = RoundedCornerShape(10)
                    ) {
                        Text(
                            text = "Total: ₹" + totalFoodCost.toString(),
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp).fillMaxWidth().padding(horizontal = 10.dp).background(LightBlue))
                }
            }
        }
    }
}