import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edufun.quizzify.ui.theme.*

// Data class to represent a single entry in the user's quiz history.
//
// Parameters:
//   subj: The subject or name of the quiz.
//   result: The user's score or result for that quiz (e.g., "80%").
data class quizHistoryDetail(
    val subj: String,
    val result: String
)

// Composable function to provide a sample list of quiz history details.
// This is currently using hardcoded data.
// TODO: Configure Firebase - This indicates that quiz history should eventually be fetched from Firebase.
@Composable
fun allQuizHistory(): List<quizHistoryDetail> {
    /*
   * TODO: Add allQuizHistory to QuizzifyViewModel.kt - Suggestion to move this data logic
   *       into the ViewModel for better separation of concerns and data management.
   * */
    return listOf(
        quizHistoryDetail(
            "General Knowledge",
            "80%"
        ),
        quizHistoryDetail(
            "Math Quiz",
            "78%"
        ),
        quizHistoryDetail(
            "Science Quiz",
            "72%"
        ),
    )
}

// ProfileScreen Composable function - This is the main screen for displaying user profile information.
// It shows the user's profile image, name, and their quiz history.
//
// Parameters:
//   name: The name of the user to be displayed.
//   profileImage: An integer resource ID for the user's profile image.
//   onMenu: A lambda function to be executed when the back/menu button is pressed,
//           typically for navigating back or opening a menu.
@Composable
fun ProfileScreen(name: String, profileImage: Int, onMenu: () -> Unit) {
    // Retrieves the list of quiz history details.
    val quizHistory = allQuizHistory()

    // Surface provides a background color and elevation for its content.
    Surface(
        modifier = Modifier
            .fillMaxSize() // Makes the Surface take up the entire available screen space.
            .background(MaterialTheme.colorScheme.background) // Sets the background from the current theme.
    ) {
        // Column arranges its children vertically.
        Column {
            // Displays the custom ProfileBackButton composable.
            ProfileBackButton(onMenu)

            // Column for the main content of the profile screen (image, name, history).
            Column(
                modifier = Modifier
                    .fillMaxSize() // Fills the remaining available space.
                    .padding(16.dp), // Adds padding around the content.
                horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally.
                verticalArrangement = Arrangement.Top // Aligns children to the top of the Column.
            ) {
                // Image Composable to display the user's profile picture.
                Image(
                    painter = painterResource(id = profileImage), // Loads the image using the provided resource ID. TODO: Potentially load dynamically.
                    contentDescription = "Profile Picture", // Accessibility description.
                    contentScale = ContentScale.Crop, // Scales the image to fill its bounds, cropping if necessary.
                    modifier = Modifier
                        .size(120.dp) // Sets a fixed size for the image.
                        .clip(CircleShape) // Clips the image to a circular shape.
                        .background(MaterialTheme.colorScheme.primary) // Sets a background color (visible if the image is transparent or smaller than the clip).
                )

                // Spacer adds empty space between composables for layout purposes.
                Spacer(modifier = Modifier.height(16.dp))

                // Text Composable to display the user's name.
                Text(
                    text = name, // TODO: Potentially load dynamically from user data.
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground // Text color from the theme.
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Text Composable for the "Quiz Results History" section title.
                Text(
                    text = "Quiz Results History",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start, // Aligns text to the start (left for LTR languages).
                        color = Orange // Uses a custom color.
                    ),
                    modifier = Modifier
                        .fillMaxWidth() // Makes the Text take the full available width.
                        .padding(start = 8.dp) // Adds padding to the start of the text.
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Column to display the list of quiz history items.
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)) // Clips the column to have rounded corners.
                        .background(color = Orange.copy(alpha = 0.6f)) // Sets a semi-transparent background color.
                        .padding(16.dp) // Adds padding inside the rounded background.
                ) {
                    // Iterates through each item in the `quizHistory` list.
                    quizHistory.forEach { quizHistoryDetail ->
                        // Column for each individual quiz history entry.
                        Column {
                            // Text Composable to display the subject of the quiz.
                            Text(
                                text = quizHistoryDetail.subj,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onSurface // Text color for content on a surface.
                                ),
                                modifier = Modifier.padding(vertical = 8.dp) // Adds vertical padding.
                            )
                            // Text Composable to display the result of the quiz.
                            Text(
                                text = quizHistoryDetail.result,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            // HorizontalDivider to visually separate quiz history entries.
                            HorizontalDivider(thickness = 2.dp, color = Orange.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}

// ProfileBackButton Composable function - Displays a top bar with a back arrow and "Profile Page" title.
//
// Parameters:
//   onMenu: A lambda function to be executed when the back button is clicked.
@Composable
fun ProfileBackButton(onMenu: () -> Unit) {
    // rememberCoroutineScope is available but not directly used in this snippet.
    // It's useful for launching coroutines from event handlers if needed.
    val scope = rememberCoroutineScope()

    // Row arranges its children horizontally, creating the top bar effect.
    Row(
        modifier = Modifier
            .fillMaxWidth() // Makes the Row take the full width.
            .background(Purple40), // Sets the background color of the bar.
        verticalAlignment = Alignment.CenterVertically // Aligns children vertically to the center.
    ) {
        // IconButton for the back navigation action.
        IconButton(
            onClick = onMenu, // Executes the onMenu lambda when clicked.
            modifier = Modifier.align(Alignment.CenterVertically), // Aligns this button within the Row.
        ) {
            // Icon Composable to display the back arrow.
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, // Uses a standard back arrow icon with auto-mirroring.
                contentDescription = "Localized description" // Accessibility description.
            )
        }
        // Text Composable to display the title "Profile Page".
        Text(
            text = "Profile Page",
            style = MaterialTheme.typography.headlineSmall, // Applies a predefined text style.
            modifier = Modifier.padding(12.dp) // Adds padding around the text.
        )
    }
}
