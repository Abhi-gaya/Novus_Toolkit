package com.example.novustoolkit.ui.theme.screens.feature_screen.notes_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.novustoolkit.roomdb.notemanager.NoteState
import com.example.novustoolkit.roomdb.notemanager.NotesEvent
import com.example.novustoolkit.ui.theme.LightYellow
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor2

@Composable
fun AddNoteDialogBox(
    state : NoteState,
    onEvent : (NotesEvent) -> Unit
){
    AlertDialog(onDismissRequest = {
        onEvent(NotesEvent.HideDialog)
    },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        title = {
            Text(text = "Add Note",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.topAppBarContentColor,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.verticalScroll(rememberScrollState()),
            ) {
                TextField(value = state.title,
                    onValueChange = {
                        onEvent(NotesEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.topAppBarContentColor,
                        cursorColor = MaterialTheme.colors.topAppBarContentColor
                    )
                )
                TextField(value = state.content,
                    onValueChange = {
                        onEvent(NotesEvent.SetContent(it))
                    },
                    placeholder = {
                        Text(text = "Content")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.topAppBarContentColor,
                        cursorColor = MaterialTheme.colors.topAppBarContentColor
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
                    onEvent(NotesEvent.SaveNote)
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightYellow
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