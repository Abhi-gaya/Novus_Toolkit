package com.example.novustoolkit.ui.theme.screens.feature_screen.password_manager

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.novustoolkit.roomdb.passwordmanager.PasswordEvent
import com.example.novustoolkit.roomdb.passwordmanager.PasswordState
import com.example.novustoolkit.roomdb.passwordmanager.SortTypePasswords
import com.example.novustoolkit.ui.theme.*
import com.example.novustoolkit.ui.theme.screens.feature_screen.password_manager.Biometric.Biometric
import com.example.novustoolkit.ui.theme.screens.feature_screen.task_manager.DefaultAppBar
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun Password_manager(
    state :PasswordState,
    onHomeClicked :() -> Unit,
    onEvent :(PasswordEvent) -> Unit
){
    var expanded1 by remember{
        mutableStateOf(false)
    }
    var auth by remember {
        mutableStateOf(true)
    }
    val rotate :Float by animateFloatAsState(
        targetValue = if(expanded1) 180f else 0f)
    val activity = LocalContext.current as FragmentActivity
    val context = LocalContext.current
    Scaffold(backgroundColor = LightRed,
        content = {
            if (state.isAddingPassword){
                AddPassDialogBox(
                    state = state,
                    onEvent = onEvent
                )
            }
            if (state.isGeneratingPass){
                AddGeneratorDialogBox(state = state,
                    onEvent =onEvent )
            }
            Column {
                Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically) {
                    Chip(onClick = {
                    onEvent(
                        PasswordEvent.ShowGeneratorDialog
                    )
                     },
                        modifier = Modifier
                            .weight(5f)
                            .height(50.dp)
                            .padding(start = 8.dp),
                    shape = RoundedCornerShape(25),
                        border = BorderStroke(2.dp,color = MaterialTheme.colors.topAppBarContentColor),
                        colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
                        contentColor = MaterialTheme.colors.topAppBarContentColor)
                    ) {
                        Spacer(modifier = Modifier.weight(2f))
                        Text(text = "Generate Password",modifier = Modifier.weight(10f))
                    }
                    Spacer(modifier = Modifier.weight(2f))
                    Surface(modifier = Modifier
                        .weight(3f)
                        .background(MaterialTheme.colors.topAppBarBackgroundColor)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colors.topAppBarContentColor
                        ),
                        shape = RoundedCornerShape(25)) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .clickable { expanded1 = true }
                                .background(MaterialTheme.colors.topAppBarBackgroundColor),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.sortType.name,
                                color = MaterialTheme.colors.topAppBarContentColor,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp)
                            )
                            IconButton(
                                onClick = {
                                    expanded1 = true
                                },
                                modifier = Modifier
                                    .alpha(ContentAlpha.high)
                                    .rotate(rotate)
                                    .weight(0.5f)
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
                                    onEvent(PasswordEvent.SortPassword(sortType = SortTypePasswords.A_to_Z))
                                }) {
                                    Text(text = "A to Z")
                                }
                                DropdownMenuItem(onClick =
                                {
                                    expanded1 = false
                                    onEvent(PasswordEvent.SortPassword(sortType = SortTypePasswords.Z_to_A))
                                }) {
                                    Text(text = "Z to A")
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(state.passwords) { password ->
                        var hidePasss by remember {
                            mutableStateOf(value = true)
                        }
                        var showUsername by remember {
                            mutableStateOf(value = false)
                        }
                        val rotate2 :Float by animateFloatAsState(
                            targetValue = if(showUsername) 180f else 0f
                        )
                        Column(modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = password.platform_name.toUpperCase(Locale.getDefault()),
                                color = MaterialTheme.colors.topAppBarContentColor2,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.topAppBarBackgroundColor,
                            shape = RoundedCornerShape(10),
                            elevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                            Column(modifier = Modifier.weight(9f),//
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                        TextField(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            value = password.password,
                                            onValueChange = {},
                                            textStyle = TextStyle.Default,
                                            readOnly = true,
                                            colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
                                                textColor = MaterialTheme.colors.topAppBarContentColor,
                                                trailingIconColor = MaterialTheme.colors.topAppBarContentColor
                                            ),
                                            shape = RoundedCornerShape(10),
                                            trailingIcon = {
                                                if (!hidePasss) {
                                                    IconButton(onClick = { hidePasss = true }) {
                                                        Icon(
                                                            imageVector = ImageVector.vectorResource(
                                                                id = com.example.novustoolkit.R.drawable.lock_pass
                                                            ),
                                                            contentDescription = "See Password"
                                                        )
                                                    }
                                                } else {
                                                    IconButton(onClick = { hidePasss = false }) {
                                                        Icon(
                                                            imageVector = ImageVector.vectorResource(
                                                                id = com.example.novustoolkit.R.drawable.see_password
                                                            ),
                                                            contentDescription = "Lock Password"
                                                        )
                                                    }
                                                }
                                            },
                                            visualTransformation = if (hidePasss) {
                                                PasswordVisualTransformation('*')
                                            } else {
                                                VisualTransformation.None
                                            }
                                        )
                                Row(modifier = Modifier.height(35.dp)){
                                   Spacer(modifier = Modifier.weight(8f))
                                   IconButton(onClick = {
                                       showUsername = !showUsername
                                   },
                                       modifier = Modifier
                                           .alpha(ContentAlpha.medium)
                                           .rotate(rotate2)
                                   ) {
                                       Icon(
                                           imageVector = Icons.Filled.ArrowDropDown,
                                           contentDescription = "Arrow Down",
                                           tint = MaterialTheme.colors.topAppBarContentColor
                                       )
                                   }
                                }
                                if (showUsername){
                                    Text(
                                        text = "Username: " +password.username,
                                        color = MaterialTheme.colors.topAppBarContentColor,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Left,
                                        maxLines = 1,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                    )
                                }
                                }
                                IconButton(
                                    onClick = {
                                        onEvent(
                                            PasswordEvent.DeletePassword(password = password)
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
                    }
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
                if (auth) {
                    Biometric.authenticate(
                        activity = activity,
                        title = "Biometric Authentication",
                        subtitle = "Authenticate to proceed",
                        description = "Authentication is must",
                        negativeText = "Cancel",
                        onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Authenticated successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            auth = false
                        },
                        onError = { errorCode, errorString ->
                                Toast.makeText(
                                    context,
                                    "Authentication error: $errorCode, $errorString",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            auth = false
                            onHomeClicked()
                        }
                    ) {
                            Toast.makeText(
                                context,
                                "Authentication failed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                    }
                }
            }
        },
        topBar = {
            DefaultAppBar(onHomeClicked = onHomeClicked, heading = "Passwords")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(PasswordEvent.ShowDialog)
            },
                backgroundColor = Color.Black) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add Password",
                    modifier = Modifier.padding(4.dp),
                    tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End

    )
}

//@Composable
//@Preview
//fun dis(){
//    Password_manager(state = PasswordState(
//        passwords = listOf( Passwords(
//            username = "Abhigya",
//            password = "abhi"
//        )
//        )
//    ), onHomeClicked = { /*TODO*/ }, onEvent ={} )
//}
//
