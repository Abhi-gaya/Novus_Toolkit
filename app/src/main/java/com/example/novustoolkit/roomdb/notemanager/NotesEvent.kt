package com.example.novustoolkit.roomdb.notemanager



sealed interface NotesEvent{
    object SaveNote : NotesEvent
    data class SetTitle(val title: String): NotesEvent
    data class SetContent(val content: String): NotesEvent
    object ShowDialog : NotesEvent
    object HideDialog : NotesEvent
    data class DeleteNote(val note : Notes) : NotesEvent
    data class SortNotes(val sortType : SortTypeNotes) : NotesEvent
}