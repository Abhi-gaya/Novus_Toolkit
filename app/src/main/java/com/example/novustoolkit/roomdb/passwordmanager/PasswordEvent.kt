package com.example.novustoolkit.roomdb.passwordmanager

import com.example.novustoolkit.roomdb.notemanager.NotesEvent
import com.example.novustoolkit.roomdb.notemanager.SortTypeNotes

sealed interface PasswordEvent{
    object SavePassword : PasswordEvent
    data class SetPlatform_Name(val plaform_name: String): PasswordEvent
    data class SetUsername(val username: String): PasswordEvent
    data class SetPassword(val password: String): PasswordEvent
    object ShowDialog : PasswordEvent
    object HideDialog : PasswordEvent
    object ShowGeneratorDialog : PasswordEvent
    object HideGeneratorDialog : PasswordEvent
    data class DeletePassword(val password : Passwords) : PasswordEvent
    data class GeneratePassword(val generatedPass: String ): PasswordEvent
    data class SortPassword(val sortType : SortTypePasswords) : PasswordEvent
}