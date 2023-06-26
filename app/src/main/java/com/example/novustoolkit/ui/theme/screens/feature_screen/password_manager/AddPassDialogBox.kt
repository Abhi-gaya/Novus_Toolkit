package com.example.novustoolkit.ui.theme.screens.feature_screen.password_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.novustoolkit.roomdb.passwordmanager.PasswordEvent
import com.example.novustoolkit.roomdb.passwordmanager.PasswordState
import com.example.novustoolkit.ui.theme.LightRed
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor

@Composable
fun AddPassDialogBox(
    state : PasswordState,
    onEvent :(PasswordEvent) -> Unit
){
    AlertDialog(onDismissRequest = {
        onEvent(PasswordEvent.HideDialog)
    },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        title = {
            Text(text = "Add Password",
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
                TextField(value = state.platform_name,
                    onValueChange = {
                        onEvent(PasswordEvent.SetPlatform_Name(it))
                    },
                    placeholder = {
                        Text(text = "Platform Name")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.topAppBarContentColor,
                        cursorColor = MaterialTheme.colors.topAppBarBackgroundColor
                    )
                )
                TextField(value = state.username,
                    onValueChange = {
                        onEvent(PasswordEvent.SetUsername(it))
                    },
                    placeholder = {
                        Text(text = "Username")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.topAppBarContentColor,
                        cursorColor = MaterialTheme.colors.topAppBarBackgroundColor
                    )
                )
                TextField(value = state.password,
                    onValueChange = {
                        onEvent(PasswordEvent.SetPassword(it))
                    },
                    placeholder = {
                        Text(text = "Password")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.topAppBarContentColor,
                        cursorColor = MaterialTheme.colors.topAppBarBackgroundColor
                    )
                )
            }
        },
            buttons = {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.topAppBarBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        onEvent(PasswordEvent.SavePassword)
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightRed,
                        contentColor = Color.Black
                    )
                    ) {
                        Text(
                            text = "Add",
                            color = MaterialTheme.colors.topAppBarContentColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            )
}
