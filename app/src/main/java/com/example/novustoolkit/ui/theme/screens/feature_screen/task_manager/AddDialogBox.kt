package com.example.novustoolkit.ui.theme.screens.feature_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.novustoolkit.roomdb.taskmanager.Priority
import com.example.novustoolkit.roomdb.taskmanager.TaskEvent
import com.example.novustoolkit.roomdb.taskmanager.TaskState
import com.example.novustoolkit.ui.theme.*

@Composable
fun AddDialogBox(
    state : TaskState,
    onEvent : (TaskEvent) -> Unit,
    onPrioritySelected : (Priority) -> Unit
){
    var expanded by remember{
        mutableStateOf(false)
    }
    val rotate :Float by animateFloatAsState(
        targetValue = if(expanded) 180f else 0f
    )
    AlertDialog(onDismissRequest = {
                  onEvent(TaskEvent.HideDialog)
    },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        title = {
            Text(text = "Add Task",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .padding(bottom = 20.dp),
        color = MaterialTheme.colors.topAppBarContentColor
        )},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier =Modifier.verticalScroll(rememberScrollState()),
            ) {
                TextField(value = state.title,
                    onValueChange ={
                        onEvent(TaskEvent.SetTitle(it))
                    },
                placeholder = {
                    Text(text = "To-do task")
                },
                colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.topAppBarContentColor,
                        cursorColor = MaterialTheme.colors.topAppBarBackgroundColor
                    )
                )
                TextField(value = state.desc,
                    onValueChange ={
                        onEvent(TaskEvent.SetDesc(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.topAppBarContentColor,
                        cursorColor = MaterialTheme.colors.topAppBarBackgroundColor
                    )
                )
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
                    Canvas(
                        modifier = Modifier
                            .size(15.dp)
                            .weight(1f),
                        onDraw ={
                            drawCircle(color = state.priority.color)
                        } )
                    Text(text = state.priority.name,
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
                            .weight(1.5f)){
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
                        { expanded = false
                            onPrioritySelected(Priority.Low)}) {
                            PriorityIcon(priority = Priority.Low)
                        }
                        DropdownMenuItem(onClick =
                        { expanded = false
                            onPrioritySelected(Priority.Medium)}) {
                            PriorityIcon(priority = Priority.Medium)
                        }
                        DropdownMenuItem(onClick =
                        { expanded = false
                            onPrioritySelected(Priority.High)}) {
                            PriorityIcon(priority = Priority.High)
                        }
                    }
                }
            }
        },
        buttons = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        onEvent(TaskEvent.SaveContent)
                    },colors = ButtonDefaults.buttonColors(
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
fun PriorityIcon(
    priority: Priority
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier
            .size(18.dp)){
            drawCircle(color = priority.color)
        }
        Text(modifier = Modifier.padding(start = 8.dp),
            text = priority.name,
            style = Typography.subtitle1,
            color =MaterialTheme.colors.onSurface)
    }
}
