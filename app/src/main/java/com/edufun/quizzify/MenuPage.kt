package com.edufun.quizzify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.edufun.quizzify.ui.theme.* // Imports custom theme colors, typography, etc.

// MenuScreen Composable function
// This function defines the UI for the main menu screen of the Quizzify app.
// It displays a list of available quizzes, allowing the user to select one.
//
// Parameters:
//   onQuizSelected: A lambda function that is executed when a user selects a quiz.
//                   It passes the name (String) of the selected quiz.
@Composable
fun MenuScreen(onQuizSelected: (String) -> Unit) {

    // Instantiates QuizzifyViewModel to access the list of quizzes.
    // Note: It's generally recommended to provide ViewModels via Hilt or a ViewModelProvider
    // rather than instantiating them directly within a Composable for better lifecycle management and testability.
    val allList = QuizzifyViewModel().allList()

    // Column Composable arranges its children vertically.
    // It's set to fill the entire screen size.
    Column(
        modifier = Modifier
            .fillMaxSize(), // Makes the Column occupy the entire available screen space.
        horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally within the Column.
        verticalArrangement = Arrangement.Center // Centers children vertically within the Column.
    ) {
        // LazyColumn is used to display a scrollable list of items efficiently.
        // It only composes and lays out items that are currently visible on screen.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // Makes the LazyColumn take up the entire available space within its parent Column.
                .padding(16.dp), // Adds 16dp padding around the LazyColumn.
            horizontalAlignment = Alignment.CenterHorizontally, // Aligns items horizontally to the center within the LazyColumn.
            verticalArrangement = Arrangement.Center // Arranges items vertically to the center. If there are many items, this will center the currently visible block.
        ) {
            // `items` is a builder function for LazyColumn that takes a list of data
            // and a lambda to define how each item in the list should be displayed.
            items (items = allList){ quizName -> // For each 'quizName' (which is a ListDetail object) in 'allList'
                // Column to group the image and button for each quiz item.
                Column(modifier = Modifier.padding(vertical = 10.dp)) { // Adds vertical padding between quiz items.
                    // Image Composable to display the picture associated with the quiz.
                    Image(
                        painter = quizName.pic, // The Painter object for the quiz image.
                        contentScale = ContentScale.FillWidth, // Scales the image to fill the width of its container, potentially cropping height.
                        contentDescription = null, // Accessibility description (should ideally be quizName.text or similar).
                        alignment = Alignment.TopStart, // Aligns the image to the top start of its bounds.
                        modifier = Modifier
                            .fillMaxWidth() // Makes the Image take the full width available.
                            .clip(shape = RoundedCornerShape(7.dp)) // Clips the image with rounded corners.
                    )
                    // Button Composable for selecting the quiz.
                    Button(
                        onClick = { onQuizSelected(quizName.text) }, // When clicked, calls the onQuizSelected lambda with the quiz's text/name.
                        colors = ButtonDefaults.buttonColors(containerColor = Orange), // Sets the button's background color using a custom color.
                        // Defines custom rounded corners for the button (top corners are flat, bottom corners are rounded).
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomEnd = 15.dp, bottomStart = 15.dp),
                        modifier = Modifier
                            .fillMaxWidth() // Makes the Button take the full width available.
                    ) {
                        // Text Composable to display the name of the quiz on the button.
                        Text(text = quizName.text)
                    }
                }
            }
        }
    }
}
