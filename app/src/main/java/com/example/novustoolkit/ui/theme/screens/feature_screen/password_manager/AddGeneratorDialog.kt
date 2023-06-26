package com.example.novustoolkit.ui.theme.screens.feature_screen.password_manager

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.novustoolkit.R
import com.example.novustoolkit.roomdb.passwordmanager.PasswordEvent
import com.example.novustoolkit.roomdb.passwordmanager.PasswordState
import com.example.novustoolkit.ui.theme.LightRed
import com.example.novustoolkit.ui.theme.topAppBarBackgroundColor
import com.example.novustoolkit.ui.theme.topAppBarContentColor
import kotlin.random.Random

@Composable
fun AddGeneratorDialogBox(
    state : PasswordState,
    onEvent :(PasswordEvent) -> Unit
){
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    AlertDialog(onDismissRequest = {
        onEvent(PasswordEvent.HideGeneratorDialog)
    },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        title = {
            Text(text = "Generate Password",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.topAppBarContentColor,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        },
        text = {
            SelectionContainer() {
                OutlinedTextField(
                    value = state.generatedPass,
                    onValueChange = {},
                    readOnly = true,
                    keyboardActions = KeyboardActions.Default,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.topAppBarContentColor,
                            backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            clipboardManager.setText(AnnotatedString(state.generatedPass))
                            Toast.makeText(context,"Password Copied to Clipboard" ,Toast.LENGTH_SHORT).show()
                         }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id =R.drawable.copy),
                                contentDescription ="copy",
                            tint = MaterialTheme.colors.topAppBarContentColor)
                        }
                    }
                )
            }
        },
        buttons = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.topAppBarBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    onEvent(PasswordEvent.GeneratePassword(generatedPass = state.generatedPass))
                },
                colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightRed,
                        contentColor = MaterialTheme.colors.topAppBarContentColor
                )
                ) {
                    Text(
                        text = "Generate",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    )
}


fun generatePass():String{
    val sampleUpperAlphabets:Array<Char> = arrayOf('A','B','C','D','E','F','G','H','I','J',
        'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z')    // sample array of uppercase alphabets
    val sampleLowerAlphabets:Array<Char> = arrayOf('a','b','c','d','e','f','g','h','i','j','k',
        'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z')    // sample array of lowercase alphabets
    val sampleSymbols:Array<Char> = arrayOf('!','@','#','$','%','&','?')    // sample array of special characters
    val sampleNums:Array<Char> = arrayOf('1','2','3','4','5','6','7','8','9')      // sample array of numbers
    var passLength = 10     // the length desired length of the password
    var password = ""    // the password that will be created/generated
    var i=0
    var upperAlphabets:Array<Char> = sampleUpperAlphabets   // uppercase alphabets array to be used in random generator
    var lowerAlphabets:Array<Char> = sampleLowerAlphabets   // lowercase alphabets array to be used in random generator
    var symbols:Array<Char> = sampleSymbols     // symbol array to be used in random generator
    var nums:Array<Char> = sampleNums

    while(i < passLength) {
        when(Random.nextInt(1,5)) {   // pick random number from 1-4 to decide which array to pick an element out of
            1 -> {
                if(upperAlphabets.isEmpty()) continue
                var upperSelect = Random.nextInt(upperAlphabets.size)   // pick random index in 'upperAlphabets' array
                password += upperAlphabets[upperSelect]
            }
            2 -> {
                if(lowerAlphabets.isEmpty()) continue
                var lowerSelect = Random.nextInt(lowerAlphabets.size)   // pick random index in 'lowerAlphabets' array
                password += lowerAlphabets[lowerSelect]
            }
            3 -> {
                if(symbols.isEmpty()) continue
                var symbolSelect = Random.nextInt(symbols.size)   // pick random index in 'symbols' array
                password += symbols[symbolSelect]
            }
            4 -> {
                if(nums.isEmpty()) continue
                var numsSelect = Random.nextInt(nums.size)   // pick random index in 'nums' array
                password += nums[numsSelect]
            }
        }
        i++
    }
    return password
}