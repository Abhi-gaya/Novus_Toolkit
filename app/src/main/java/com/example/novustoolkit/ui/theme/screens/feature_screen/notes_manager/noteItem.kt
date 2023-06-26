package com.example.novustoolkit.ui.theme.screens.feature_screen.notes_manager

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.novustoolkit.R
import com.example.novustoolkit.roomdb.notemanager.NoteState
import com.example.novustoolkit.roomdb.notemanager.Notes
import com.example.novustoolkit.roomdb.notemanager.NotesEvent
import com.example.novustoolkit.ui.theme.DarkBlue
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor2
import java.util.*

@Composable
fun noteItem(
    note : Notes,
    modifier :Modifier = Modifier,
    onEvent : (NotesEvent) -> Unit,
    cutCornerSize :Dp = 30.dp,
    cornerRadius :Dp = 10.dp
){
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color.White,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(0xFFFFFF, 0x000000, 0.5f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp),
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = note.title.toUpperCase(Locale.getDefault()),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.topAppBarContentColor2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
        IconButton(onClick = {
            clipboardManager.setText(AnnotatedString(note.content))
            Toast.makeText(context,"Note Content Copied to Clipboard" , Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.align(Alignment.TopEnd)) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.copy),
                contentDescription ="copy",
                tint = MaterialTheme.colors.topAppBarContentColor2)
        }
            IconButton(
                onClick = {
                    onEvent(
                        NotesEvent.DeleteNote(note = note)
                    )
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task",
                    tint = Color.Black
                )
            }
    }
}