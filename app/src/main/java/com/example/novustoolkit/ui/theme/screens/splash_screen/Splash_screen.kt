package com.example.novustoolkit.ui.theme.screens.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.novustoolkit.R
import com.example.novustoolkit.ui.theme.logoColor
import com.example.novustoolkit.ui.theme.screens.home_screen.novus
import kotlinx.coroutines.delay

@Composable
fun Splash_screen(
    navController: NavController,
    novus: novus,
    novus2: novus){
    LaunchedEffect(key1 = true){
        delay(2000L)
        navController.navigate(novus.name){
            popUpTo(novus2.name){
                inclusive = true
            }
        }
    }
    Column(Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.novusicon_blue),
            contentDescription ="",
            Modifier.width(70.dp)
                .height(70.dp)
                .padding(bottom = 20.dp)
        )
        Text(text = "Novus",
            style =MaterialTheme.typography.caption,
            color = MaterialTheme.colors.logoColor)
        Text(text = "Toolkit",
            style =MaterialTheme.typography.caption,
            color = MaterialTheme.colors.logoColor)
    }
}