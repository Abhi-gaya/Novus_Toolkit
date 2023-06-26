package com.example.novustoolkit.ui.theme.screens.feature_screen.expense_manager

import android.widget.Space
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.novustoolkit.roomdb.expensemanager.Category.category
import com.example.novustoolkit.roomdb.expensemanager.ExpenseEvent
import com.example.novustoolkit.roomdb.expensemanager.ExpenseState
import com.example.novustoolkit.roomdb.expensemanager.Month
import com.example.novustoolkit.ui.theme.LightGreen
import com.example.novustoolkit.ui.theme.Typography
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor


@Composable
fun AddFoodExpenseDialogBox(
    state: ExpenseState,
    onEvent : (ExpenseEvent) ->Unit,
    onMonthSelected :(Month) -> Unit,
    isAdd :MutableState<Boolean>,
    onCategorySelected : (category) ->Unit,
) {
    var expanded by remember{
        mutableStateOf(false)
    }
    var expanded2 by remember{
        mutableStateOf(false)
    }
    val rotate :Float by animateFloatAsState(
        targetValue = if(expanded) 180f else 0f
    )
    var otherSelected by remember{
        mutableStateOf(false)
    }
    AlertDialog(onDismissRequest = {
        isAdd.value=false
    }, modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        title = {
            Text(text = "Add Expense",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(bottom = 20.dp),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(12.dp),
                    content = {
                    item {
                        TextField(value = state.price.toString(),
                            onValueChange ={
                                onEvent(ExpenseEvent.SetPrice(it.toInt()))
                            },
                            placeholder = {
                                Text(text = "Price")
                            }, label ={
                                      Text(text = "Price")
                            } ,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = MaterialTheme.colors.topAppBarContentColor,
                                cursorColor = MaterialTheme.colors.topAppBarBackgroundColor
                            ), keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }
                    item {
                        TextField(value = state.description,
                            onValueChange ={
                                onEvent(ExpenseEvent.SetDesc(it))
                            },
                            label = {
                                    Text(text = "Description")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = MaterialTheme.colors.topAppBarContentColor,
                                cursorColor = MaterialTheme.colors.topAppBarBackgroundColor
                            )
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expanded = true }
                                .background(MaterialTheme.colors.background)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.onSurface.copy(
                                        alpha = ContentAlpha.disabled
                                    ), shape = MaterialTheme.shapes.small
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = state.Category.name,
                                color = MaterialTheme.colors.topAppBarContentColor,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(8f))
                            IconButton(
                                onClick = {
                                    expanded = true },
                                modifier = Modifier
                                    .alpha(ContentAlpha.medium)
                                    .rotate(rotate)
                                    .weight(1f)){
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "Arrow Down",
                                    tint = MaterialTheme.colors.topAppBarContentColor
                                )
                            }
                            DropdownMenu(expanded = expanded,
                                onDismissRequest = { expanded = false},
                                modifier = Modifier.fillMaxWidth(
                                    fraction = 0.98f
                                )) {
                                DropdownMenuItem(onClick =
                                {
                                    expanded = false
                                    onCategorySelected(category.Food)
                                }){
                                    CategoryIcon(category = category.Food)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded = false
                                    onCategorySelected(category.Shopping)
                                }){
                                    CategoryIcon(category = category.Shopping)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded = false
                                    onCategorySelected(category.Transport)
                                }){
                                    CategoryIcon(category = category.Transport)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded = false
                                    onCategorySelected(category.Entertainment)
                                }){
                                    CategoryIcon(category = category.Entertainment)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded = false
                                    onCategorySelected(category.Others)
                                    otherSelected=!otherSelected
                                }){
                                    CategoryIcon(category = category.Others)
                                }
                            } //for category
                        }
                    }
                    item{
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expanded2 = true }
                                .background(MaterialTheme.colors.background)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.onSurface.copy(
                                        alpha = ContentAlpha.disabled
                                    ), shape = MaterialTheme.shapes.small
                                ),
                            verticalAlignment = Alignment.CenterVertically,

                        ){
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = state.month.name,
                                color = MaterialTheme.colors.topAppBarContentColor,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(8f)
                            )
                            IconButton(
                                onClick = {
                                    expanded2 = true },
                                modifier = Modifier
                                    .alpha(ContentAlpha.medium)
                                    .rotate(rotate)
                                    .weight(1f)){
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "Arrow Down",
                                    tint = MaterialTheme.colors.topAppBarContentColor
                                )
                            }
                            DropdownMenu(expanded = expanded2,
                                onDismissRequest = { expanded2 = false},
                                modifier = Modifier.fillMaxWidth(
                                    fraction = 0.98f
                                )) {
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Jan)
                                }){
                                    MonthIcon(month = Month.Jan)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Feb)
                                }){
                                    MonthIcon(month = Month.Feb)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Mar)
                                }){
                                    MonthIcon(month = Month.Mar)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Apr)
                                }){
                                    MonthIcon(month = Month.Apr)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.May)
                                }){
                                    MonthIcon(month = Month.May)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Jun)
                                }){
                                    MonthIcon(month = Month.Jun)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Jul)
                                }){
                                    MonthIcon(month = Month.Jul)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Aug)
                                }){
                                    MonthIcon(month = Month.Aug)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Sep)
                                }){
                                    MonthIcon(month = Month.Sep)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Oct)
                                }){
                                    MonthIcon(month = Month.Oct)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Nov)
                                }){
                                    MonthIcon(month = Month.Nov)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded2 = false
                                    onMonthSelected(Month.Dec)
                                }){
                                    MonthIcon(month = Month.Dec)
                                }
                            }
                        }                 //for month
                    }
                })
            }
        }
    , buttons = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    onEvent(ExpenseEvent.SaveExpense)
                    isAdd.value=false
                },
                    colors = ButtonDefaults.buttonColors(
                    backgroundColor = LightGreen
                )
                ) {
                    Text(
                        text = "Add",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
    )
}


@Composable
fun CategoryIcon(
    category: category
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(modifier = Modifier.padding(start = 8.dp),
            text = category.name,
            style = Typography.subtitle1,
            color =MaterialTheme.colors.onSurface)
    }
}

@Composable
fun MonthIcon(
    month: Month
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(modifier = Modifier.padding(start = 8.dp),
            text = month.name,
            style = Typography.subtitle1,
            color =MaterialTheme.colors.onSurface)
    }
}




