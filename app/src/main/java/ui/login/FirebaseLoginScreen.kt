package com.divyansh.wanderpilot.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.divyansh.wanderpilot.auth.AuthRepository

@Composable
fun FirebaseLoginScreen(
    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val authRepository = AuthRepository()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                authRepository.login(email, password) { success, result ->

                    message = result

                    if (success) {
                        onLoginSuccess()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = onSignupClick
        ) {
            Text("Don't have an account? Sign Up")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = message)
    }
}