package com.example.balanceme.ui

import BalanceMeScaffold
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.balanceme.Routes
import com.example.balanceme.ui.components.CustomTopAppBar
import com.example.balanceme.ui.theme.RichBlack_Dark
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUp(navController: NavHostController, auth: FirebaseAuth) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBar(navController, auth)
    }
}

@Composable
fun ScaffoldWithTopBar(navController: NavHostController, auth: FirebaseAuth) {
    BalanceMeScaffold(
        topBar = {
            CustomTopAppBar(navController, "Signup", true)
        },
        content = {
            val email = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }
            val confirmPassword = remember { mutableStateOf(TextFieldValue()) }
            val emailErrorState = remember { mutableStateOf(false) }
            val passwordErrorState = remember { mutableStateOf(false) }
            val confirmPasswordErrorState = remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Sign Up")
                Spacer(Modifier.size(16.dp))

                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        if (emailErrorState.value) {
                            emailErrorState.value = false
                        }
                        email.value = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = emailErrorState.value,
                    label = {
                        Text(text = "Email*")
                    },
                )
                if (emailErrorState.value) {
                    Text(text = "Required", color = RichBlack_Dark)
                }
                Spacer(Modifier.size(16.dp))
                val passwordVisibility = remember { mutableStateOf(true) }
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {
                        if (passwordErrorState.value) {
                            passwordErrorState.value = false
                        }
                        password.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Password*")
                    },
                    singleLine = true,
                    isError = passwordErrorState.value,
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
                            Icon(
                                imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "visibility",
                                tint = RichBlack_Dark
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
                )
                if (passwordErrorState.value) {
                    Text(text = "Required", color = Color.Red)
                }

                Spacer(Modifier.size(16.dp))
                val cPasswordVisibility = remember { mutableStateOf(true) }
                OutlinedTextField(
                    value = confirmPassword.value,
                    onValueChange = {
                        if (confirmPasswordErrorState.value) {
                            confirmPasswordErrorState.value = false
                        }
                        confirmPassword.value = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = confirmPasswordErrorState.value,
                    label = {
                        Text(text = "Confirm Password*")
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            cPasswordVisibility.value = !cPasswordVisibility.value
                        }) {
                            Icon(
                                imageVector = if (cPasswordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "visibility",
                                tint = RichBlack_Dark
                            )
                        }
                    },
                    visualTransformation = if (cPasswordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
                )
                if (confirmPasswordErrorState.value) {
                    val msg = if (confirmPassword.value.text.isEmpty()) {
                        "Required"
                    } else if (confirmPassword.value.text != password.value.text) {
                        "Password not matching"
                    } else {
                        ""
                    }
                    Text(text = msg, color = RichBlack_Dark)
                }
                Spacer(Modifier.size(16.dp))
                Button(
                    onClick = {
                        when {
                            email.value.text.isEmpty() -> {
                                emailErrorState.value = true
                            }
                            password.value.text.isEmpty() -> {
                                passwordErrorState.value = true
                            }
                            confirmPassword.value.text.isEmpty() -> {
                                confirmPasswordErrorState.value = true
                            }
                            confirmPassword.value.text != password.value.text -> {
                                confirmPasswordErrorState.value = true
                            }
                            else -> {
                                auth.createUserWithEmailAndPassword(email.value.text.trim(),
                                    password.value.text.trim()).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            Log.d(TAG, "Registered successfully")
                                            Toast.makeText(
                                                navController.context,
                                                "Registered successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navController.navigate(Routes.Login.route) {
                                                launchSingleTop = true
                                            }
                                        } else {
                                            Log.d(TAG, "Registered Failed")
                                        }
                                }
                            }
                        }
                    },
                    content = {
                        Text(text = "Register", color = Color.White)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = RichBlack_Dark)
                )
                Spacer(Modifier.size(16.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    TextButton(onClick = {
                        navController.navigate("Login")
                    }) {
                        Text(text = "Login", color = RichBlack_Dark)
                    }
                }
            }
        })
}