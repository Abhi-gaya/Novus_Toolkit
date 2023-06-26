package com.example.novustoolkit.ui.theme.screens.feature_screen.task_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.novustoolkit.R
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor

@Composable
fun DefaultAppBar(
    onHomeClicked :() -> Unit,
    heading :String
){
    Surface(color = MaterialTheme.colors.topAppBarBackgroundColor,
        shape = RoundedCornerShape(25),
        contentColor = MaterialTheme.colors.topAppBarContentColor,
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        content = {
                Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(MaterialTheme.colors.topAppBarBackgroundColor)
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    IconButton(onClick = {
                        onHomeClicked()
                    }) {
                        Icon(imageVector = Icons.Outlined.Home,
                            modifier = Modifier
                                .size(38.dp)
                                .weight(2f),
                            contentDescription = "",
                            tint = MaterialTheme.colors.topAppBarContentColor
                            )
                    }
                    Text(text = heading,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(6f),
                        textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5)
                    Icon(painter = painterResource(id = R.drawable.novusicon_blue),
                        contentDescription = "",
                        modifier = Modifier
                            .weight(1f),
                        tint = Color.Unspecified
                        )
                }
    }
    )
}
//@Composable
//@Preview
//fun a(
//
//){
//    //DefaultAppBar()
//}

//title = {
//    Text(
//        text = "Tasks",
//        color = MaterialTheme.colors.topAppBarContentColor,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.padding(start = 50.dp),
//        style = MaterialTheme.typography.h4)
//},
//navigationIcon = {
//    IconButton(onClick = {}, modifier = Modifier.padding(start = 10.dp)) {
//        Icon(imageVector = Icons.Outlined.Home,
//            modifier = Modifier.size(40.dp),
//            contentDescription ="" ,
//            tint = MaterialTheme.colors.topAppBarContentColor)
//    }
//}, elevation = 10.dp
//,
//backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
//actions = {}
