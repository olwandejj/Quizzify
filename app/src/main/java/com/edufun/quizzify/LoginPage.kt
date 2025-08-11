package com.edufun.quizzify

// Jetpack Compose UI imports for building the user interface.
import androidx.compose.foundation.Image // For displaying images.
import androidx.compose.foundation.layout.Arrangement // For arranging children within a layout.
import androidx.compose.foundation.layout.Column // A layout composable that places its children in a vertical sequence.
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the entire available space.
import androidx.compose.foundation.layout.fillMaxWidth // Modifier to make a composable fill the maximum width of its parent.
import androidx.compose.foundation.layout.padding // Modifier to add padding around a composable.
import androidx.compose.foundation.layout.size // Modifier to set a fixed size for a composable.
import androidx.compose.foundation.rememberScrollState // Remembers the scroll state for scrollable containers.
import androidx.compose.foundation.shape.RoundedCornerShape // Defines a shape with rounded corners.
import androidx.compose.foundation.verticalScroll // Modifier to make a Column scrollable vertically.
import androidx.compose.material3.* // Material 3 components like TextField, Button, Text.
import androidx.compose.runtime.Composable // Annotation indicating a function is a Jetpack Compose UI component.
import androidx.compose.runtime.getValue // Delegate for reading State.
import androidx.compose.runtime.mutableStateOf // Creates a mutable State object that Compose can observe.
import androidx.compose.runtime.remember // Remembers a value produced by calculation across recompositions.
import androidx.compose.runtime.setValue // Delegate for writing to State.
import androidx.compose.ui.Alignment // For aligning children within a layout.
import androidx.compose.ui.Modifier // An ordered, immutable collection of elements that decorate or add behavior to Compose UI elements.
import androidx.compose.ui.draw.clip // Modifier to clip the content of a composable to a specific shape.
import androidx.compose.ui.graphics.Color // Represents a color.
import androidx.compose.ui.layout.ContentScale // Defines how an image should be scaled within its bounds.
import androidx.compose.ui.res.painterResource // For loading drawable resources (like images).
import androidx.compose.ui.text.TextStyle // Defines the styling for text (font size, color, etc.).
import androidx.compose.ui.text.input.PasswordVisualTransformation // Transforms the visual representation of text for password fields.
import androidx.compose.ui.unit.dp // Density-independent pixels, a unit for specifying UI element dimensions.
import androidx.compose.ui.unit.sp // Scale-independent pixels, a unit for specifying font sizes.
// Project-specific theme imports.
import com.edufun.quizzify.ui.theme.* // Imports custom theme colors (Purple40, Orange), typography, etc.

// LoginScreen Composable function
// This function defines the UI for the login screen of the application.
// It includes input fields for email and password, a login button,
// and a text button to navigate to the registration screen.
//
// Parameters:
//   onLogin: A lambda function that is executed when the "Login" button is clicked.
//            This function should handle the login logic (e.g., authenticating the user).
//   onRegisterNavigate: A lambda function that is executed when the "Don't have an account? Register" text is clicked.
//                       This function should handle navigation to the registration screen.
@Composable
fun LoginScreen(onLogin: () -> Unit, onRegisterNavigate: () -> Unit) {
    // State variables for holding the input values of the email and password text fields.
    // `remember` is used to store the state across recompositions.
    // `mutableStateOf` creates an observable state holder; when its value changes, Compose recomposes relevant parts of the UI.
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Column Composable arranges its children vertically.
    Column(
        // Modifier is used to configure the Column's appearance and behavior.
        modifier = Modifier
            .fillMaxSize() // Makes the Column occupy the entire available screen space.
            .padding(16.dp) // Adds 16 density-independent pixels of padding around the Column's content.
            .verticalScroll(rememberScrollState()), // Enables vertical scrolling if the content exceeds the screen height.
        // `rememberScrollState()` creates and remembers the scroll state.
        verticalArrangement = Arrangement.Center, // Arranges children vertically, centering them within the Column.
        horizontalAlignment = Alignment.CenterHorizontally // Aligns children horizontally, centering them within the Column.
    ) {
        // Image Composable to display the application logo.
        Image(
            painter = painterResource(R.drawable.logo), // Loads the image from the drawable resources (res/drawable/logo).
            // Ensure 'logo.png' or 'logo.xml' exists in your drawables.
            contentScale = ContentScale.FillWidth, // Scales the image to fill the width of its container, potentially cropping or adjusting height.
            contentDescription = null, // Accessibility description for the image. Should be descriptive (e.g., "Quizzify App Logo").
            alignment = Alignment.TopStart, // Aligns the image to the top start of its bounds within its allocated space.
            modifier = Modifier
                .fillMaxWidth() // Makes the Image take the full width available within its parent Column.
                .clip(shape = RoundedCornerShape(7.dp)) // Clips the image to a shape with rounded corners of 7dp radius.
        )
        // Text Composable for the screen title "Login".
        Text(
            text = "Login",
            color = Color.Yellow, // Sets the text color directly to yellow. Consider using theme colors for better consistency.
            style = MaterialTheme.typography.headlineLarge, // Applies a predefined large headline text style from the MaterialTheme.
            modifier = Modifier.padding(bottom = 24.dp) // Adds 24dp padding to the bottom of the text, creating space below it.
        )
        // TextField Composable for email input.
        TextField(
            value = email, // The current value of the email input field, bound to the `email` state variable.
            onValueChange = { email = it }, // Lambda function called when the user types in the field. Updates the `email` state.
            singleLine = true, // Restricts the input to a single line.
            label = { Text("Email Address") }, // Label text displayed above or inside the text field as a hint.
            placeholder = { Text("example@domain.com") }, // Placeholder text displayed when the field is empty.
            modifier = Modifier
                .fillMaxWidth() // Makes the TextField take the full width available.
                .padding(vertical = 8.dp) // Adds 8dp padding to the top and bottom of the TextField.
                .size(60.dp), // Sets a fixed size for the TextField (likely interpreted as height here).
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White,), // Custom text style for the input text (18sp font size, white color).
        )
        // TextField Composable for password input.
        TextField(
            value = password, // The current value of the password input field, bound to the `password` state variable.
            onValueChange = { password = it }, // Lambda function called when the user types. Updates the `password` state.
            singleLine = true, // Restricts the input to a single line.
            label = { Text("Password") }, // Label for the password field.
            placeholder = { Text("Password") }, // Placeholder text for the password field.
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(60.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
            visualTransformation = PasswordVisualTransformation(), // Hides the entered characters (e.g., shows dots '••••') for password security.
        )
        // Button Composable for submitting the login form.
        Button(
            onClick = onLogin, // Calls the `onLogin` lambda function when the button is clicked.
            // TODO: Add login configuration - Reminder to implement the actual login logic.
            colors = ButtonDefaults.buttonColors(containerColor = Purple40), // Sets the button's background color using a custom theme color.
            modifier = Modifier
                .fillMaxWidth() // Makes the Button take the full width available.
                .padding(vertical = 8.dp) // Adds 8dp padding to the top and bottom of the Button.
        ) {
            // Text Composable displayed on the button.
            Text(text = "Login", color = Color.White) // Sets button text to "Login" with white color.
        }
        // TextButton Composable for navigating to the registration screen.
        // TextButton is a button styled as plain text, often used for secondary actions.
        TextButton(onClick = onRegisterNavigate) { // Calls the `onRegisterNavigate` lambda when clicked.
            // Text displayed on the TextButton.
            Text(text = "Don't have an account? Register", color = Orange) // Sets text with a custom orange color.
        }
    }
}
