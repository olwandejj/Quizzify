package com.edufun.quizzify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edufun.quizzify.ui.theme.*

// RegisterScreen Composable function
// This function defines the UI for the user registration screen.
// It includes input fields for email, password, and password confirmation,
// a registration button, and a link to navigate back to the login screen.
//
// Parameters:
//   onRegister: A lambda function to be executed when the "Register" button is clicked.
//               This function should handle the user registration logic.
//   onBackToLogin: A lambda function to be executed when the "Already have an account? Login" text is clicked.
//                  This function should navigate the user back to the login screen.
@Composable
fun RegisterScreen(onRegister: () -> Unit, onBackToLogin: () -> Unit) {
    // State variables for holding the input values of the text fields.
    // `remember` is used to keep the state across recompositions.
    // `mutableStateOf` creates an observable state holder.
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    // Column Composable arranges its children vertically.
    Column(
        modifier = Modifier
            .fillMaxSize() // Makes the Column occupy the entire available screen space.
            .padding(16.dp) // Adds 16dp padding around the Column.
            .verticalScroll(rememberScrollState()), // Enables vertical scrolling if the content exceeds the screen height.
        verticalArrangement = Arrangement.Center, // Arranges children vertically in the center of the Column.
        horizontalAlignment = Alignment.CenterHorizontally // Aligns children horizontally in the center of the Column.
    ) {
        // Image Composable to display the application logo.
        Image(
            painter = painterResource(R.drawable.logo), // Loads the image from drawable resources.
            contentScale = ContentScale.FillWidth, // Scales the image to fill the width of its container.
            contentDescription = null, // Content description for accessibility (can be improved).
            alignment = Alignment.TopStart, // Aligns the image to the top start of its bounds.
            modifier = Modifier
                .fillMaxWidth() // Makes the Image take the full width available.
                .clip(shape = RoundedCornerShape(7.dp)) // Clips the image to a rounded rectangle shape.
        )
        // Text Composable for the screen title "Register".
        Text(
            text = "Register",
            color = Color.Yellow, // Sets the text color to yellow.
            style = MaterialTheme.typography.headlineLarge, // Applies a predefined large headline text style.
            modifier = Modifier.padding(bottom = 24.dp) // Adds padding to the bottom of the text.
        )
        // TextField Composable for email input.
        TextField(
            value = email, // The current value of the email input field.
            onValueChange = { email = it }, // Lambda function called when the input value changes. Updates the `email` state.
            singleLine = true, // Restricts the input to a single line.
            label = { Text("Email Address") }, // Label text displayed above or inside the text field.
            placeholder = { Text("example@domain.com") }, // Placeholder text displayed when the field is empty.
            modifier = Modifier
                .fillMaxWidth() // Makes the TextField take the full width available.
                .padding(vertical = 8.dp) // Adds vertical padding.
                .size(60.dp), // Sets a fixed size for the TextField (height).
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White,), // Custom text style for the input.
        )

        // TextField Composable for password input.
        TextField(
            value = password, // The current value of the password input field.
            onValueChange = { password = it }, // Lambda function called when the input value changes. Updates the `password` state.
            singleLine = true, // Restricts the input to a single line.
            label = { Text("Password") }, // Label text.
            placeholder = { Text("Password") }, // Placeholder text.
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
            visualTransformation = PasswordVisualTransformation(), // Hides the entered characters for password security.
        )

        // TextField Composable for confirming the password.
        TextField(
            value = confirmPassword, // The current value of the confirm password input field.
            onValueChange = { confirmPassword = it }, // Lambda function called when the input value changes. Updates the `confirmPassword` state.
            singleLine = true,
            label = { Text("Confirm Password") }, // Changed label for clarity
            placeholder = { Text("Confirm Password") }, // Changed placeholder for clarity
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
            visualTransformation = PasswordVisualTransformation(), // Hides the entered characters.
        )
        // Button Composable for submitting the registration form.
        Button(
            onClick = onRegister, // Calls the `onRegister` lambda when clicked. // TODO: Add register config - Reminder to implement registration logic.
            colors = ButtonDefaults.buttonColors(containerColor = Purple40), // Sets the button's background color.
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Register", color = Color.White) // Text displayed on the button.
        }

        // TextButton Composable to navigate back to the login screen.
        TextButton(onClick = onBackToLogin) { // Calls the `onBackToLogin` lambda when clicked.
            Text(text = "Already have an account? Login", color = Orange) // Text for the button, styled with a custom color.
        }
    }
}
