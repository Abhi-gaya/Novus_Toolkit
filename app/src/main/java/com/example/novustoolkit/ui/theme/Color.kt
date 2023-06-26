package com.example.novustoolkit.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val DarkBlue = Color(0xFF252539)
val Green = Color(0xFF8ED385)
val Red = Color(0xFFFF8080)
val Blue = Color(0xFF4C95FF)
val Yellow = Color(0xFFFEDC00)
val LightGreen = Color(0xFFC2DFBD)
val LightRed = Color(0xFFFCCACA)
val LightBlue = Color(0xFFCCE1FF)
val LightYellow = Color(0xFFFBEF9D)
val HighpriorityColor  = Color(0xFFE9312D)
val MediumpriorityColor  = Color(0xFFFFCA28)
val LowpriorityColor  = Color(0xFF4FE92D)
val Lavender = Color(0xFFE6E6FD)
val Blue2 = Color(0xFFC9DBEC)
val blueScheme = Color(0xFFF4FAFF)
val brightBlue = Color(0xFF0098FA)
val foodColor = Color(0xFFE563F7)
val shoppingColor = Color(0xFFFF9812)
val transportColor = Color(0xFF4FE92D)
val entertainmentColor = Color(0xFFFF0269)
val othersColor = Color(0xFFFFD72D)
val balanceColor= Color(0xFF8b0a50)
val logocolor = Color(0xFF012867)

val Colors.topAppBarContentColor :Color
    @Composable
    get() = if (isLight) DarkBlue else Color.White

val Colors.topAppBarBackgroundColor :Color
    @Composable
    get() = if (isLight) Color.White else DarkBlue

val Colors.expenseListIconColor :Color
    @Composable
    get() = if (isLight) brightBlue else DarkBlue

val Colors.topAppBarContentColor2 :Color
    @Composable
    get() = if (isLight) Color.Black else Color.Black

val Colors.topAppBarContentColor3 :Color
    @Composable
    get() = if (isLight) Color.White else Color.Black

val Colors.ExpenseItemColor :Color
    @Composable
    get() = if (isLight) blueScheme else Lavender

val Colors.ExpensesListBackgroundColor :Color
    @Composable
    get() = if (isLight) Blue2/*blueScheme*/ else DarkBlue

val Colors.logoColor : Color
@Composable
get() = if(isLight) logocolor else Color.White