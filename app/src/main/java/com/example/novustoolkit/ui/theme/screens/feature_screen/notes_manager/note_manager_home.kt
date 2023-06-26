package com.example.novustoolkit.ui.theme.screens.feature_screen.notes_manager

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novustoolkit.roomdb.notemanager.NoteState
import com.example.novustoolkit.roomdb.notemanager.NotesEvent
import com.example.novustoolkit.roomdb.notemanager.SortTypeNotes
import com.example.novustoolkit.roomdb.passwordmanager.PasswordEvent
import com.example.novustoolkit.roomdb.passwordmanager.SortTypePasswords
import com.example.novustoolkit.ui.theme.LightYellow
import com.example.novustoolkit.ui.theme.screens.feature_screen.task_manager.DefaultAppBar
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Notes_manager(
    state :  NoteState,
    onHomeClicked :() -> Unit,
    onEvent : (NotesEvent) -> Unit
){
    var expanded1 by remember{
        mutableStateOf(false)
    }
    val rotate :Float by animateFloatAsState(
        targetValue = if(expanded1) 180f else 0f)
    Scaffold(backgroundColor = LightYellow,
        content = {
            if (state.isAddingNote){
                AddNoteDialogBox(state = state,
                    onEvent = onEvent)
            }
            Column() {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.weight(6f))
                    Surface(modifier = Modifier
                        .wrapContentWidth()
                        .background(MaterialTheme.colors.topAppBarBackgroundColor)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colors.topAppBarContentColor
                        )
                        .weight(4f),
                        shape = RoundedCornerShape(25)) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .clickable { expanded1 = true }
                                .background(MaterialTheme.colors.topAppBarBackgroundColor),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.sortType.name,
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
                                    onEvent(NotesEvent.SortNotes(sortType = SortTypeNotes.A_to_Z))
                                }) {
                                    Text(text = "A to Z")
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded1 = false
                                    onEvent(NotesEvent.SortNotes(sortType = SortTypeNotes.Z_to_A))
                                }) {
                                    Text(text = "Z to A")
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.2f))
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(state.notes) { notes ->
                        noteItem(
                            note = notes,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            onEvent = onEvent
                        )
                    }
                    item { Spacer(modifier = Modifier.height(50.dp)) }
                }
            }
        },
        topBar = {
            DefaultAppBar(onHomeClicked = onHomeClicked, heading = "Notes")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(NotesEvent.ShowDialog)
            },
            backgroundColor = Color.Black
            ){
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                    modifier = Modifier.padding(4.dp),
                    tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
}