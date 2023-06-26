package com.example.novustoolkit.roomdb.notemanager


data class NoteState(
    val notes: List<Notes> = emptyList(),
    val title:String = "",
    val content : String = "",
    val isAddingNote : Boolean = false,
    val sortType : SortTypeNotes = SortTypeNotes.A_to_Z
)
