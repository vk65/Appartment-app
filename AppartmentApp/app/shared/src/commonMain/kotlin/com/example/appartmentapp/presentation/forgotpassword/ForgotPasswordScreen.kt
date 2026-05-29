package com.example.appartmentapp.presentation.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val NoBrokerRed = Color(0xFFE53935)

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
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is ForgotPasswordUiState.Idle, is ForgotPasswordUiState.Error, is ForgotPasswordUiState.Loading -> {
                if (uiState is ForgotPasswordUiState.Idle || uiState is ForgotPasswordUiState.Error || (uiState is ForgotPasswordUiState.Loading && email.isNotEmpty() && otp.isEmpty())) {
                    Text(
                        text = "Forgot Password",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Enter your registered email to receive an OTP",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(40.dp))
                    
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NoBrokerRed,
                            focusedLabelColor = NoBrokerRed
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    if (uiState is ForgotPasswordUiState.Loading) {
                        CircularProgressIndicator(color = NoBrokerRed)
                    } else {
                        Button(
                            onClick = { viewModel.sendOtp(email) },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NoBrokerRed)
                        ) {
                            Text("Send OTP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            ForgotPasswordUiState.OtpSent -> {
                Text(
                    text = "Verify OTP",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "OTP sent to $email",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("OTP") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NoBrokerRed,
                        focusedLabelColor = NoBrokerRed
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = { viewModel.verifyOtp(otp) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NoBrokerRed)
                ) {
                    Text("Verify OTP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            ForgotPasswordUiState.OtpVerified -> {
                Text(
                    text = "Reset Password",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
                
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NoBrokerRed,
                        focusedLabelColor = NoBrokerRed
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = { viewModel.resetPassword(newPassword) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NoBrokerRed)
                ) {
                    Text("Reset Password", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            ForgotPasswordUiState.Success -> {
                Text(
                    text = "Success!",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
                Text(
                    text = "Your password has been reset successfully.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onSuccess,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NoBrokerRed)
                ) {
                    Text("Back to Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (uiState is ForgotPasswordUiState.Error) {
            Text(
                text = (uiState as ForgotPasswordUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        if (uiState !is ForgotPasswordUiState.Success) {
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onNavigateBack) {
                Text("Back to Login", color = NoBrokerRed)
            }
        }
    }
}
