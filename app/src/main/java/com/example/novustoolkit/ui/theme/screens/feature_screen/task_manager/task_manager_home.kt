package com.example.novustoolkit.ui.theme.screens.feature_screen.task_manager

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novustoolkit.roomdb.taskmanager.Priority
import com.example.novustoolkit.roomdb.taskmanager.TaskEvent
import com.example.novustoolkit.roomdb.taskmanager.TaskState
import com.example.novustoolkit.ui.theme.LightGreen
import com.example.novustoolkit.ui.theme.LightRed
import com.example.novustoolkit.ui.theme.screens.feature_screen.AddDialogBox
import com.example.novustoolkit.ui.theme.screens.feature_screen.PriorityIcon
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun Task_manager(
    state : TaskState,
    onHomeClicked :() -> Unit,
    onEvent :(TaskEvent) -> Unit,
){
    var expanded1 by remember{
        mutableStateOf(false)
    }
    val rotate :Float by animateFloatAsState(
        targetValue = if(expanded1) 180f else 0f
    )
    val context = LocalContext.current
    Scaffold(backgroundColor = LightGreen,
        content = {
            if (state.isAddingTask){
                AddDialogBox(
                    state = state,
                    onEvent = onEvent,
                    onPrioritySelected = {
                        onEvent(TaskEvent.SetPrority(it))
                }
                )
            }
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        LightGreen
                    )
                    .padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.weight(6f))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .wrapContentWidth()
                                .weight(4f)
                                .clickable { expanded1 = true }
                                .background(MaterialTheme.colors.topAppBarBackgroundColor)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colors.topAppBarContentColor
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(horizontal = 6.dp)
                                    .weight(1f),
                                onDraw = {
                                    drawCircle(color = state.priority.color)
                                })
                            Text(
                                text = state.priority.name,
                                color = MaterialTheme.colors.topAppBarContentColor,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    expanded1 = true
                                },
                                modifier = Modifier
                                    .alpha(ContentAlpha.high)
                                    .rotate(rotate)
                                    .weight(1f)
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
                                    onEvent(TaskEvent.SetPrority(Priority.Low))
                                    onEvent(TaskEvent.SortTaskPriority(sortType = Priority.Low))
                                }) {
                                    PriorityIcon(priority = Priority.Low)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded1 = false
                                    onEvent(TaskEvent.SetPrority(Priority.Medium))
                                    onEvent(TaskEvent.SortTaskPriority(sortType = Priority.Medium))
                                }) {
                                    PriorityIcon(priority = Priority.Medium)
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded1 = false
                                    onEvent(TaskEvent.SetPrority(Priority.High))
                                    onEvent(TaskEvent.SortTaskPriority(sortType = Priority.High))
                                }) {
                                    PriorityIcon(priority = Priority.High)
                                }
                            }
                        }
                    }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(state.Task) { tasks ->
                        var isTaskDone by remember {
                            mutableStateOf(false)
                        }
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.topAppBarBackgroundColor,
                            shape = RoundedCornerShape(10),
                            elevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(checked = isTaskDone,
                                    onCheckedChange = {
                                        isTaskDone = !isTaskDone
                                        if(isTaskDone){
                                        Toast.makeText(context,"Congratulations Task Completed",Toast.LENGTH_SHORT).show()
                                    }
                                    else{
                                            Toast.makeText(context,"Task Incomplete",Toast.LENGTH_SHORT).show()
                                    }}
                                , colors = CheckboxDefaults.colors(uncheckedColor = LightRed))
                                Column(
                                    modifier = Modifier.weight(8f)
                                ) {
                                    if (isTaskDone){
                                        Text(
                                            text = tasks.title,
                                            color = Color.Gray,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Left,
                                            maxLines = 1,
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                        Text(
                                            text = tasks.description,
                                            color = Color.Gray,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Left,
                                            maxLines = 10,
                                            overflow = TextOverflow.Ellipsis,
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    }
                                    else {
                                        Text(
                                            text = tasks.title,
                                            color = MaterialTheme.colors.topAppBarContentColor,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Left,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = tasks.description,
                                            color = MaterialTheme.colors.topAppBarContentColor,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Left,
                                            maxLines = 10,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                                Canvas(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .weight(1f)
                                ) {
                                    drawCircle(color = tasks.priority.color)
                                }
                                IconButton(
                                    onClick = {
                                        onEvent(
                                            TaskEvent.DeleteTask(task = tasks)
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Task",
                                        tint = MaterialTheme.colors.topAppBarContentColor
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        },
        topBar = {
            DefaultAppBar(onHomeClicked = onHomeClicked, heading = "Tasks")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(TaskEvent.ShowDialog)
            },
                backgroundColor = Color.Black) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add task",
                    modifier = Modifier.padding(4.dp),
                    tint =Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
@Preview
fun des(){
    Task_manager(state = TaskState(), onHomeClicked = { /*TODO*/ }, onEvent = {})
}
