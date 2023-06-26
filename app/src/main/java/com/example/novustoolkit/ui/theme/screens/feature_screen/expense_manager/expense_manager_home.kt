package com.example.novustoolkit.ui.theme.screens.feature_screen.expense_manager

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.novustoolkit.roomdb.expensemanager.ExpenseEvent
import com.example.novustoolkit.roomdb.expensemanager.ExpenseState
import com.example.novustoolkit.ui.theme.*
import com.example.novustoolkit.ui.theme.screens.feature_screen.expense_manager.PieChart.PieChart
import com.example.novustoolkit.ui.theme.screens.feature_screen.task_manager.DefaultAppBar
import com.example.novustoolkit.viewModel.SharedViewModel

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Expense_manager(
    state:ExpenseState,
    onEvent :(ExpenseEvent) -> Unit,
    onHomeClicked :() -> Unit,
    onFoodClicked :() -> Unit,
    viewModel: SharedViewModel
){
    val isAddingexpense = remember{
        mutableStateOf(false)
    }
    val totalExpense by viewModel.totalExpense.collectAsState(0)
    val foodExpense by viewModel.totalfoodcategoryExpense.collectAsState(null)
    val shopExpense by viewModel.totalShopcategoryExpense.collectAsState(null)
    val transportExpense by viewModel.totalTransportcategoryExpense.collectAsState(null)
    val entertainmentExpense by viewModel.totalEntertainmentcategoryExpense.collectAsState(null)
    val OtherExpense by viewModel.totalOthercategoryExpense.collectAsState(null)
    val uiTotal = if (totalExpense!=null) totalExpense else 0
    val uifoodtotal =if (foodExpense!=null) foodExpense else 0
    val uishoptotal =if (shopExpense!=null) shopExpense else 0
    val uitranstotla = if(transportExpense!=null) transportExpense else 0
    val uientertotal = if (entertainmentExpense!=null) entertainmentExpense else 0
    val uiothertotal = if (OtherExpense!=null) OtherExpense else 0
    val remainingBalace =(state.Budget - uiTotal!!)
    Scaffold(backgroundColor = LightBlue,
        content = {
            if (isAddingexpense.value){
                AddFoodExpenseDialogBox(
                    state = state,
                    onEvent = onEvent,
                    onCategorySelected = {
                        onEvent(ExpenseEvent.SetCategory(it))
                    },
                    onMonthSelected = {
                        onEvent(ExpenseEvent.SetMonth(it))
                    },
                    isAdd = isAddingexpense
                )
            }
            if(state.isAddingBudget){
                AddBudgetDialog(state = state, onEvent = onEvent,viewmodel =viewModel)
            }
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(LightBlue)) {
                        Box(modifier = Modifier.weight(5f).fillMaxWidth()){
                            PieChart(modifier = Modifier.fillMaxSize().padding(start = 20.dp),
                                progress = listOf(
                                    uifoodtotal!!.toFloat(),
                                    uishoptotal!!.toFloat(),
                                    uitranstotla!!.toFloat(),
                                    uientertotal!!.toFloat(),
                                    uiothertotal!!.toFloat(),
                                    remainingBalace!!.toFloat()) ,
                                colors = listOf(
                                    foodColor,
                                    shoppingColor,
                                    transportColor,
                                    entertainmentColor,
                                    othersColor,
                                    balanceColor
                                ),
                            isDonut = true)
                        }//yaha graph aayega
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(6f)) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                            shape = RoundedCornerShape(18),
                            border = BorderStroke(width = 2.dp, color = Color.Black),
                            color = brightBlue,
                            elevation = 50.dp
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)){
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(Modifier.weight(5f),
                                    verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Total Budget",
                                        color = Color.White,
                                        style = MaterialTheme.typography.subtitle2
                                    )
                                    Text(
                                        text = "₹ "+state.Budget.toString(),
                                        color = DarkBlue,
                                        style = MaterialTheme.typography.h6
                                    )
                                }
                                Column(Modifier.weight(5f),
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Remaining Capital",
                                        color = Color.White,
                                        style = MaterialTheme.typography.subtitle2
                                    )
                                    Text(text = "₹ "+(remainingBalace).toString(),
                                        color = DarkBlue,
                                        style = MaterialTheme.typography.h6)
                                }
                            }
                                Surface(
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .align(Alignment.Center),
                                    color = Color.White,
                                    onClick = {
                                        onEvent(ExpenseEvent.ShowBudgetDialog)
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Budget",
                                        modifier = Modifier.padding(4.dp),
                                        tint = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(250.dp))
                        }
                        Surface(modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .align(Alignment.BottomCenter)) {
                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = MaterialTheme.colors.topAppBarBackgroundColor))
                            }
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(fraction = 0.85f)
                                .align(
                                    alignment = BiasAlignment(
                                        verticalBias = 1.5f,
                                        horizontalBias = 0f
                                    )
                                ),
                            shape = RoundedCornerShape(10),
                            border = BorderStroke(width = 0.6.dp, color = Color.Black),
                            color = MaterialTheme.colors.ExpensesListBackgroundColor,
                            elevation = 50.dp
                        ) {
                            Column() {
                                Spacer(modifier = Modifier.height(30.dp))
                                Surface(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp),
                                    color = MaterialTheme.colors.ExpenseItemColor,
                                    shape = RoundedCornerShape(30),
                                    elevation = 8.dp,
                                    onClick = {
                                        onFoodClicked()//onOthersClicked()
                                    }) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Surface(modifier = Modifier
                                            .size(38.dp)
                                            .weight(0.7f),
                                            shape = CircleShape,
                                            color = MaterialTheme.colors.topAppBarContentColor
                                        ) {
                                            Icon(imageVector = Icons.Outlined.List,
                                                contentDescription = "List icon",
                                                tint = MaterialTheme.colors.expenseListIconColor)
                                        }
                                        Text(
                                            text = "Expense List",
                                            modifier = Modifier
                                                .padding(vertical = 10.dp)
                                                .weight(3.5f),
                                            color = MaterialTheme.colors.topAppBarContentColor2,
                                            style = MaterialTheme.typography.h6,
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Center
                                        )
                                        Column(
                                            Modifier.weight(0.9f),
                                            verticalArrangement = Arrangement.SpaceAround,
                                            horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "Total Expense",
                                                color = MaterialTheme.colors.topAppBarContentColor2,
                                                style = MaterialTheme.typography.overline,
                                                fontWeight = FontWeight.Normal,
                                                textAlign = TextAlign.Center
                                            )
                                            Text(
                                                text = "₹ " + totalExpense.toString(),
                                                color = MaterialTheme.colors.topAppBarContentColor2,
                                                style = MaterialTheme.typography.h6,
                                                fontWeight = FontWeight.Medium,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                        Icon(
                                            modifier = Modifier.weight(1f),
                                            imageVector = Icons.Filled.ArrowForward,
                                            contentDescription = "",
                                            tint = MaterialTheme.colors.topAppBarContentColor2
                                        )
                                    }
                                }
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "   Category ",
                                            modifier = Modifier.weight(2f),
                                            color = MaterialTheme.colors.topAppBarContentColor,
                                            style = MaterialTheme.typography.h5,
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = "Total Amount",
                                            modifier = Modifier
                                                .weight(2f),
                                            color = MaterialTheme.colors.topAppBarContentColor,
                                            style = MaterialTheme.typography.h5,
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    LazyColumn(modifier = Modifier.padding(vertical = 10.dp),
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        contentPadding = PaddingValues(12.dp)) {
                                        item {
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "1. Food ",
                                                    modifier = Modifier.weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.Normal,
                                                    textAlign = TextAlign.Center
                                                )
                                                Text(
                                                    text = "₹ "+foodExpense.toString(),
                                                    modifier = Modifier
                                                        .weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.SemiBold,
                                                    textAlign = TextAlign.Center
                                                )
                                                Canvas(modifier = Modifier
                                                    .size(18.dp).weight(0.5f)){
                                                    drawCircle(color = foodColor)
                                                }
                                            }
                                        }
                                        item {
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "2. Shopping ",
                                                    modifier = Modifier.weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.Normal,
                                                    textAlign = TextAlign.Center
                                                )
                                                Text(
                                                    text = "₹ "+shopExpense.toString(),
                                                    modifier = Modifier
                                                        .weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.SemiBold,
                                                    textAlign = TextAlign.Center
                                                )
                                                Canvas(modifier = Modifier
                                                    .size(18.dp).weight(0.5f)){
                                                    drawCircle(color = shoppingColor)
                                                }
                                            }
                                        }
                                        item {
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "3. Transport ",
                                                    modifier = Modifier.weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.Normal,
                                                    textAlign = TextAlign.Center
                                                )
                                                Text(
                                                    text = "₹ "+transportExpense.toString(),
                                                    modifier = Modifier
                                                        .weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.SemiBold,
                                                    textAlign = TextAlign.Center
                                                )
                                                Canvas(modifier = Modifier
                                                    .size(18.dp).weight(0.5f)){
                                                    drawCircle(color = transportColor)
                                                }
                                            }
                                        }
                                        item {
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "4. Entertainment",
                                                    modifier = Modifier.weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.Normal,
                                                    textAlign = TextAlign.Center
                                                )
                                                Text(
                                                    text = "₹ "+entertainmentExpense.toString(),
                                                    modifier = Modifier
                                                        .weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.SemiBold,
                                                    textAlign = TextAlign.Center
                                                )
                                                Canvas(modifier = Modifier
                                                    .size(18.dp).weight(0.5f)){
                                                    drawCircle(color = entertainmentColor)
                                                }
                                            }
                                        }
                                        item {
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "5. Others",
                                                    modifier = Modifier.weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.Normal,
                                                    textAlign = TextAlign.Center
                                                )
                                                Text(
                                                    text = "₹ "+OtherExpense.toString(),
                                                    modifier = Modifier
                                                        .weight(2f),
                                                    color = MaterialTheme.colors.topAppBarContentColor,
                                                    style = MaterialTheme.typography.h6,
                                                    fontWeight = FontWeight.SemiBold,
                                                    textAlign = TextAlign.Center
                                                )
                                                Canvas(modifier = Modifier
                                                    .size(18.dp).weight(0.5f)){
                                                    drawCircle(color = othersColor)
                                                }
                                            }
                                        }
                                        item{ Spacer(modifier = Modifier.height(40.dp))}
                                    }
                                }
                            }
                        }
                    }
                    }

        },
        topBar = {
            DefaultAppBar(
                onHomeClicked = onHomeClicked,
                heading = "Expenses"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          isAddingexpense.value=true
                },
                backgroundColor = MaterialTheme.colors.topAppBarContentColor) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Password",
                    modifier = Modifier.padding(4.dp),
                    tint = MaterialTheme.colors.topAppBarBackgroundColor
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}