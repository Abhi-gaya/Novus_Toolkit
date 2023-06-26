package com.example.novustoolkit.ui.theme.screens.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.novustoolkit.R
import com.example.novustoolkit.R.drawable.novusicon_blue
import com.example.novustoolkit.ui.theme.*

enum class novus(){
    splash_screen,
    home,
    task_home,
    pass_home,
    notes_home,
    expense_home,
    expense_list
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")//to supress error of the content padding in the scaffold
@Composable
fun HomeScreen(
    onTaskButtonClicked: () -> Unit = {},
    onPassButtonClicked: () -> Unit = {},
    onExpenseButtonClicked: () -> Unit = {},
    onNotesButtonClicked: () -> Unit = {}
){
    Scaffold (content = {
        HomeScreen_content(
            onTaskButtonClicked,
            onPassButtonClicked,
            onExpenseButtonClicked,
            onNotesButtonClicked
        )
    },
        topBar = {
            HomeTopBar()
        }
            )
}
@Composable
fun HomeTopBar() {
    TopAppBar(backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
       elevation = 5.dp,
       contentColor = MaterialTheme.colors.topAppBarContentColor,
        modifier = Modifier.padding(4.dp)
    ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "NOVUS",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h4,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.weight(9f)
                )
                Icon(
                    painter = painterResource(id = novusicon_blue),
                    contentDescription = "app icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
//}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen_content(
    onTaskButtonClicked: () -> Unit = {},
    onPassButtonClicked: () -> Unit = {},
    onExpenseButtonClicked: () -> Unit = {},
    onNotesButtonClicked: () -> Unit = {}
){
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.topAppBarBackgroundColor) {
        Box(modifier = Modifier.fillMaxSize()
            .padding(10.dp),
            contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                   Surface(shape = RoundedCornerShape(10.dp),
                       color = Green,
                       elevation = 10.dp,
                       modifier = Modifier.width(125.dp)
                           .height(125.dp),
                   onClick = {
                       onTaskButtonClicked()
                   }
                   ) {
                       Column(verticalArrangement = Arrangement.SpaceBetween,
                           horizontalAlignment = Alignment.CenterHorizontally,
                           modifier = Modifier.padding(14.dp)) {
                           Icon(painter = painterResource(id = R.drawable.todo_task_icon),
                               contentDescription ="task manager",
                               tint = Color.White,
                               modifier = Modifier.size(60.dp))
                           Text(
                               text = "Tasks",
                               color = Color.White,
                               style = MaterialTheme.typography.h6,
                               fontWeight = FontWeight.SemiBold
                           )
                       }
                   }
                    Surface(shape = RoundedCornerShape(10.dp),
                        color = Red,
                        elevation = 10.dp,
                        modifier = Modifier.width(125.dp)
                            .height(125.dp),
                        onClick = {
                            onPassButtonClicked()
                        }
                    ) {
                        Column(verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(14.dp)) {
                            Icon(painter = painterResource(id = R.drawable.password_icon),
                                contentDescription ="password manager",
                                tint = Color.White,
                                modifier = Modifier.size(60.dp))
                            Text(
                                text = "Passwords",
                                color = Color.White,
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    Surface(shape = RoundedCornerShape(10.dp),
                        color = Blue,
                        elevation = 10.dp,
                        modifier = Modifier.width(125.dp)
                            .height(125.dp),
                        onClick = {
                            onExpenseButtonClicked()
                        }
                    ) {
                        Column(verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(14.dp)) {
                            Icon(painter = painterResource(id = R.drawable.expense_icon),
                                contentDescription ="expense manager",
                                tint = Color.White,
                                modifier = Modifier.size(60.dp))
                            Text(
                                text = "Expenses",
                                color = Color.White,
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Surface(shape = RoundedCornerShape(10.dp),
                        color = Yellow,
                        elevation = 10.dp,
                        modifier = Modifier.width(125.dp)
                            .height(125.dp),
                        onClick = {
                            onNotesButtonClicked()
                        }
                    ) {
                        Column(verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(14.dp)) {
                            Icon(painter = painterResource(id = R.drawable.notes_icon),
                                contentDescription ="notes manager",
                                tint = Color.White,
                                modifier = Modifier.size(60.dp))
                            Text(
                                text = "Notes",
                                color = Color.White,
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
@Preview
fun dispplay(){
    HomeScreen()
}