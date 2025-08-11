package com.edufun.quizzify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edufun.quizzify.ui.theme.*

// LoginScreen Composable function
// This function defines the UI for the user login screen.
// It includes input fields for email and password, a login button,
// and options to navigate to the registration screen or trigger a password reset.
//
// Parameters:
//   onLogin: A lambda function to be executed when the "Login" button is clicked.
//            This function should handle the user authentication logic.
//   onGoToRegister: A lambda function to be executed when the "Don't have an account? Register" text is clicked.
//                   This function should navigate the user to the registration screen.
//   onForgotPassword: A lambda function (currently a placeholder) that would handle forgotten password requests.
@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onGoToRegister: () -> Unit,
    onForgotPassword: () -> Unit // Placeholder for forgot password functionality
) {
    // State variables for holding the input values of the text fields.
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Column Composable arranges its children vertically.
    Column(
        modifier = Modifier
            .fillMaxSize() // Makes the Column occupy the entire available screen space.
            .padding(16.dp) // Adds 16dp padding around the Column.
            .verticalScroll(rememberScrollState()), // Enables vertical scrolling if content exceeds screen height.
        verticalArrangement = Arrangement.Center, // Arranges children vertically in the center.
        horizontalAlignment = Alignment.CenterHorizontally // Aligns children horizontally in the center.
    ) {
        // Image Composable to display the application logo.
        Image(
            painter = painterResource(R.drawable.logo), // Loads the image from drawable resources.
            contentScale = ContentScale.Fit, // Scales the image to fit within its bounds, maintaining aspect ratio.
            contentDescription = "App Logo",
            modifier = Modifier
                .fillMaxWidth(0.6f) // Takes 60% of the available width.
                .aspectRatio(1f) // Maintains a square aspect ratio for the logo container.
                .clip(shape = RoundedCornerShape(16.dp)) // Clips the image to a rounded rectangle.
                .padding(bottom = 24.dp)
        )
        // Text Composable for the screen title "Login".
        Text(
            text = "Login",
            color = Color.Yellow, // Sets the text color.
            style = MaterialTheme.typography.headlineLarge, // Applies a predefined text style.
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // TextField Composable for email input.
        TextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            label = { Text("Email Address") },
            placeholder = { Text("example@domain.com") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface), // Use theme color
            colors = TextFieldDefaults.colors( // Use Material 3 TextFieldDefaults
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        )

        // TextField Composable for password input.
        TextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            label = { Text("Password") },
            placeholder = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        )
        // Button Composable for submitting the login form.
        Button(
            onClick = onLogin, // TODO: Implement actual login logic.
            colors = ButtonDefaults.buttonColors(containerColor = Purple40),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Login", color = Color.White)
        }

        // TextButton Composable for navigating to the registration screen.
        TextButton(onClick = onGoToRegister) {
            Text(text = "Don't have an account? Register", color = Orange)
        }

        // TextButton Composable for "Forgot Password" functionality (placeholder).
        TextButton(onClick = onForgotPassword) {
            Text(text = "Forgot Password?", color = MaterialTheme.colorScheme.primary)
        }
    }
}
