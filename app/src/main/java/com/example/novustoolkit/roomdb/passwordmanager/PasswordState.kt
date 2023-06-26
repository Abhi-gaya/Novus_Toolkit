package com.example.novustoolkit.roomdb.passwordmanager

data class PasswordState(
    val passwords : List<Passwords> = emptyList(),
    val platform_name :  String = "",
    val username : String ="",
    val password : String ="",
    val generatedPass : String = "",
    val isAddingPassword : Boolean = false,
    val isGeneratingPass : Boolean =false,
    val sortType : SortTypePasswords = SortTypePasswords.A_to_Z
)
