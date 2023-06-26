package com.example.novustoolkit.roomdb.taskmanager

sealed interface TaskEvent{
    object SaveContent : TaskEvent
    data class SetTitle(val title: String): TaskEvent
    data class SetDesc(val description: String): TaskEvent
    data class SetPrority(val priority: Priority): TaskEvent
    object ShowDialog : TaskEvent
    object HideDialog : TaskEvent
    data class SortTaskPriority(val sortType: Priority): TaskEvent
    data class DeleteTask(val task : tasks) : TaskEvent
}