package com.example.novustoolkit.roomdb.taskmanager

import com.example.novustoolkit.ui.theme.HighpriorityColor
import com.example.novustoolkit.ui.theme.LowpriorityColor
import com.example.novustoolkit.ui.theme.MediumpriorityColor

enum class Priority(val color: androidx.compose.ui.graphics.Color) {
    High(HighpriorityColor),
    Medium(MediumpriorityColor),
    Low(LowpriorityColor)
}