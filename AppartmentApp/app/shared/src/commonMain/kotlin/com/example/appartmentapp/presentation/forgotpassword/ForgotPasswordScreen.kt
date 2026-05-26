package com.example.appartmentapp.presentation.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
    onNavigateBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is ForgotPasswordUiState.Idle, is ForgotPasswordUiState.Error, is ForgotPasswordUiState.Loading -> {
                if (uiState is ForgotPasswordUiState.Idle || uiState is ForgotPasswordUiState.Error || (uiState is ForgotPasswordUiState.Loading && email.isNotEmpty() && otp.isEmpty())) {
                    Text("Forgot Password", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (uiState is ForgotPasswordUiState.Loading) {
                        CircularProgressIndicator()
                    } else {
                        Button(
                            onClick = { viewModel.sendOtp(email) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Send OTP")
                        }
                    }
                }
            }
            ForgotPasswordUiState.OtpSent -> {
                Text("Verify OTP", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("OTP sent to $email", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("OTP") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.verifyOtp(otp) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verify")
                }
            }
            ForgotPasswordUiState.OtpVerified -> {
                Text("Reset Password", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.resetPassword(newPassword) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reset Password")
                }
            }
            ForgotPasswordUiState.Success -> {
                Text("Password Reset Successful", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onSuccess, modifier = Modifier.fillMaxWidth()) {
                    Text("Back to Login")
                }
            }
        }

        if (uiState is ForgotPasswordUiState.Error) {
            Text(
                text = (uiState as ForgotPasswordUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (uiState !is ForgotPasswordUiState.Success) {
            TextButton(onClick = onNavigateBack) {
                Text("Back")
            }
        }
    }
}
