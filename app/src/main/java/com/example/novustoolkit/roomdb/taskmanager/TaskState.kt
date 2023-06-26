package com.example.novustoolkit.roomdb.taskmanager

data class TaskState(
    val Task:List<tasks> = emptyList(),
    val title : String ="",
    val desc : String = "",
    val priority: Priority = Priority.Low,
    val isAddingTask : Boolean = false,
    val sortType : Priority = Priority.High
)
