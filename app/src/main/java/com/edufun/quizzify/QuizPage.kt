package com.edufun.quizzify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.edufun.quizzify.ui.theme.Orange
import com.edufun.quizzify.ui.theme.Purple40


// Data class representing a single question in the quiz.
//
// Parameters:
//   questionText: The text of the question.
//   options: A list of strings representing the answer choices.
//   correctAnswerIndex: The index of the correct answer in the `options` list.
data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

// QuizApp Composable function - This is the main screen for taking a quiz.
// It displays the current question, answer options, score, and a back button.
//
// Parameters:
//   viewModel: An instance of QuizzifyViewModel, which holds the quiz logic and data.
//   onQuitQuiz: A lambda function to be executed when the user decides to quit the quiz (e.g., by pressing the back button and confirming).
@Composable
fun QuizApp(viewModel: QuizzifyViewModel, onQuitQuiz: () -> Unit) {
    // Collects the current question from the viewModel as a State object.
    // The UI will recompose whenever the currentQuestion in the viewModel changes.
    val question by viewModel.currentQuestion.collectAsState()
    // Collects the current score from the viewModel as a State object.
    // The UI will recompose whenever the score in the viewModel changes.
    val score by viewModel.score.collectAsState()
    // Declares a lambda function that will be assigned to handle answer selection.
    val onAnswerSelected: (Int) -> Unit

    // Checks if there is a current question to display.
    if (question != null) {
        // Assigns the lambda for handling answer selection. When an option is selected, it calls viewModel.submitAnswer().
        onAnswerSelected = { selectedIndex -> viewModel.submitAnswer(selectedIndex) }

        // Surface is a Material Design container that provides a background color and elevation.
        Surface(
            modifier = Modifier
                .fillMaxSize() // Makes the Surface take up the entire available screen space.
                .background(MaterialTheme.colorScheme.background) // Sets the background color from the current theme.
        ) {
            // Column arranges its children vertically.
            Column {
                // Displays the custom BackButton composable, passing the onQuitQuiz lambda.
                BackButton(onQuitQuiz)
                // Another Column for the main content of the quiz screen.
                Column(
                    modifier = Modifier
                        .fillMaxSize() // Fills the remaining available space.
                        .padding(16.dp), // Adds padding around the content.
                    horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally.
                    verticalArrangement = Arrangement.SpaceBetween // Distributes space evenly between children vertically.
                ) {
                    // Text Composable to display the current question text.
                    Text(
                        // The `!!` operator is used here assuming `question` will not be null at this point due to the `if (question != null)` check.
                        text = question!!.questionText,
                        style = MaterialTheme.typography.headlineMedium, // Applies a headline text style.
                        textAlign = TextAlign.Center, // Centers the text.
                        modifier = Modifier.padding(bottom = 32.dp, top = 80.dp) // Adds padding.
                    )
                    // Column to display the answer options as buttons.
                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        // Iterates through the options of the current question with their indices.
                        question!!.options.forEachIndexed { index, option ->
                            // Button Composable for each answer option.
                            Button(
                                onClick = { onAnswerSelected(index) }, // Calls onAnswerSelected with the index of the clicked option.
                                colors = ButtonDefaults.buttonColors(containerColor = Orange), // Sets the button's background color.
                                modifier = Modifier
                                    .fillMaxWidth() // Makes the button take the full width.
                                    .padding(vertical = 8.dp) // Adds vertical padding around the button.
                            ) {
                                Text(text = option) // Displays the text of the answer option.
                            }
                        }
                    }

                    // Text Composable to display the current score.
                    Text(
                        text = "Score: $score",
                        style = MaterialTheme.typography.bodyMedium, // Applies a body text style.
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 32.dp) // Adds top padding.
                    )
                    // Commented out "Quit Quiz" button, functionality is now likely handled by the BackButton and its dialog.
//                    Button(
//                        onClick = onQuitQuiz,
//                        colors = ButtonDefaults.buttonColors(containerColor = Orange),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 16.dp)
//                    ) {
//                        Text(text = "Quit Quiz")
//                    }
                }
            }
        }
    } else {
        // If there is no current question (e.g., quiz is finished), display the ResultScreen.
        // Passes the final score and the onQuitQuiz lambda (which might be used as a "restart" or "go home" action).
        ResultScreen(score = score, onRestart = { onQuitQuiz() })
    }
}


// BackButton Composable function - Displays a top app bar like structure with a back arrow and title.
// It includes a confirmation dialog when the back button is pressed.
//
// Parameters:
//   onQuitQuiz: A lambda function to be executed when the user confirms they want to quit the quiz.
@Composable
fun BackButton(onQuitQuiz: () -> Unit) {
    // rememberCoroutineScope is used to get a coroutine scope that is tied to this composable's lifecycle.
    // Not directly used in the provided snippet but often useful for launching coroutines in response to UI events.
    val scope = rememberCoroutineScope()
    // State variable to control the visibility of the AlertDialog.
    // `remember` ensures the state is preserved across recompositions.
    var showDialog by remember { mutableStateOf(false) }

    // Row arranges its children horizontally. Used here to create a top bar effect.
    Row(
        modifier = Modifier
            .fillMaxWidth() // Makes the Row take the full width.
            .background(Purple40), // Sets the background color of the bar.
        verticalAlignment = Alignment.CenterVertically // Aligns children vertically to the center of the Row.
    ) {
        // IconButton for the back navigation action.
        IconButton(
            onClick = { showDialog = true }, // When clicked, sets showDialog to true to display the AlertDialog.
            modifier = Modifier.align(Alignment.CenterVertically), // Aligns this icon button within the Row.
        ) {
            // Icon Composable to display the back arrow.
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, // Uses a pre-defined back arrow icon that supports auto-mirroring for RTL languages.
                contentDescription = "Localized description" // Accessibility description for the icon.
            )
        }
        // Text Composable to display the title "Quiz" in the bar.
        Text(
            text = "Quiz",
            style = MaterialTheme.typography.headlineSmall, // Applies a small headline text style.
            modifier = Modifier.padding(12.dp) // Adds padding around the text.
        )
    }

    // Conditional rendering of the AlertDialog based on the `showDialog` state.
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Called when the user clicks outside the dialog or presses the back button. Hides the dialog.
            title = {
                Text(text = "Confirm Action") // Title of the dialog.
            },
            text = {
                Text(text = "Are you sure you want to quit?") // Message/body of the dialog.
            },
            confirmButton = {
                // Button for the "confirm" action (quitting the quiz).
                Button(
                    onClick = {
                        onQuitQuiz() // Executes the provided lambda to handle quitting.
                        showDialog = false // Hides the dialog.
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Purple40), // Sets button color.
                ) {
                    Text("Quit?", color = Color.White) // Text on the confirm button.
                }
            },
            dismissButton = {
                // TextButton for the "dismiss" action (canceling the quit action).
                TextButton(
                    onClick = {
                        showDialog = false // Hides the dialog.
                    }
                ) {
                    Text("Dismiss", color = Orange) // Text on the dismiss button.
                }
            }
        )
    }
}
