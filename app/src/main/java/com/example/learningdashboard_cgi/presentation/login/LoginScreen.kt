package com.example.learningdashboard_cgi.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit
) {
    LaunchedEffect(viewModel.uiState) {
        if (viewModel.uiState is LoginUiState.Success) {
            onLoginSuccess()
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val passwordFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text("Login")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            isError = emailError.isNotEmpty(),

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    passwordFocusRequester.requestFocus()
                }
            )
        )

        if (emailError.isNotEmpty()) {
            Text(emailError)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError.isNotEmpty(),

            modifier = Modifier.focusRequester(passwordFocusRequester),

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        if (passwordError.isNotEmpty()) {
            Text(passwordError)
        }

        if (viewModel.uiState is LoginUiState.Error) {
            Text(
                text = (viewModel.uiState as LoginUiState.Error).message
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            emailError = ""
            passwordError = ""

            if(email.isBlank())
            {
                emailError = "Email is required"
            }
            else if(!email.contains("@"))
            {
                emailError = "Enter a valid email"
            }
            if(password.isBlank())
            {
                passwordError = "Password is required"
            }
            if (
                email.isNotBlank() &&
                email.contains("@") &&
                password.isNotBlank()
            ) {
                viewModel.login(email, password)
            }
        },
            enabled = viewModel.uiState !is LoginUiState.Loading
        ) {
            if(viewModel.uiState is LoginUiState.Loading)
            {
                CircularProgressIndicator()
            }
            else {
                Text("Login")
            }
        }
    }
}