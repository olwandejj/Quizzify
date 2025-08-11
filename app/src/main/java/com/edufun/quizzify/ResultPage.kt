package com.edufun.quizzify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.edufun.quizzify.ui.theme.*

// ResultScreen Composable function
// This function is responsible for displaying the user's quiz score and providing an option to restart the quiz.
//
// Parameters:
//   score: An Int representing the user's final score in the quiz.
//   onRestart: A lambda function that will be executed when the user clicks the "Return to Home" button.
//              This function typically handles navigation back to the quiz's starting screen.
@Composable
fun ResultScreen(score: Int, onRestart: () -> Unit) {
    // Column Composable is used to arrange its children vertically.
    Column(
        // Modifier is used to configure the Column's appearance and behavior.
        modifier = Modifier
            .fillMaxSize() // Makes the Column take up the entire available screen space.
            .padding(16.dp), // Adds 16 density-independent pixels of padding around the Column.
        // horizontalAlignment aligns the children of the Column horizontally to the center.
        horizontalAlignment = Alignment.CenterHorizontally,
        // verticalArrangement arranges the children of the Column vertically in the center.
        verticalArrangement = Arrangement.Center
    ) {
        // Text Composable to display the quiz completion message and the user's score.
        Text(
            text = "Quiz Finished! Your Score: $score", // The text to be displayed, incorporating the user's score.
            style = MaterialTheme.typography.headlineMedium, // Applies a predefined text style from the MaterialTheme.
            textAlign = TextAlign.Center, // Centers the text horizontally.
            modifier = Modifier.padding(bottom = 32.dp) // Adds 32 dp of padding to the bottom of the Text.
        )
        // Button Composable that allows the user to restart the quiz.
        Button(
            onClick = onRestart, // Specifies the lambda function to be executed when the button is clicked.
            colors = ButtonDefaults.buttonColors(containerColor = Orange) // Sets the button's background color using a custom color (Orange).
        ) {
            // Text Composable to display the label of the button.
            Text(text = "Return to Home")
        }
    }
}
