package com.edufun.quizzify

// Imports for Jetpack Compose animation system.
import androidx.compose.animation.core.LinearEasing // A type of Easing for animations to proceed at a constant rate.
import androidx.compose.animation.core.animateFloat // Function to animate a Float value.
import androidx.compose.animation.core.infiniteRepeatable // Creates an animation that repeats indefinitely.
import androidx.compose.animation.core.rememberInfiniteTransition // Remembers an InfiniteTransition, used for continuous animations.
import androidx.compose.animation.core.tween // Specifies a duration-based animation with optional easing.
// Jetpack Compose UI imports for building the user interface.
import androidx.compose.foundation.Image // For displaying images.
import androidx.compose.foundation.background // Modifier for setting the background of a composable.
import androidx.compose.foundation.isSystemInDarkTheme // Checks if the system is currently in dark theme mode.
import androidx.compose.foundation.layout.Arrangement // For arranging children within a layout (e.g., Column, Row).
import androidx.compose.foundation.layout.Box // A layout composable that stacks its children on top of each other.
import androidx.compose.foundation.layout.Column // A layout composable that places its children in a vertical sequence.
import androidx.compose.foundation.layout.Spacer // An empty composable used to create space between other composables.
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the entire available space.
import androidx.compose.foundation.layout.height // Modifier to set a fixed height for a composable.
import androidx.compose.foundation.layout.size // Modifier to set a fixed size (width and height) for a composable.
import androidx.compose.foundation.layout.width // Modifier to set a fixed width for a composable.
// Jetpack Compose Material 3 components.
import androidx.compose.material3.CircularProgressIndicator // A circular indeterminate progress indicator. (Not used in the current version of the code)
import androidx.compose.material3.LinearProgressIndicator // A linear progress indicator.
// Jetpack Compose runtime imports.
import androidx.compose.runtime.Composable // Annotation indicating a function is a Jetpack Compose UI component.
import androidx.compose.runtime.getValue // Delegate for reading State.
// Alignment for positioning content within a layout.
import androidx.compose.ui.Alignment
// Modifier for styling and behavior of Composables.
import androidx.compose.ui.Modifier
// Graphics import, specifically for Color.
import androidx.compose.ui.graphics.Color
// For loading drawable resources (like images).
import androidx.compose.ui.res.painterResource
// Density-independent pixels, a unit for specifying UI element dimensions.
import androidx.compose.ui.unit.dp

// LoadingScreen Composable function
// This function displays a loading screen, typically shown during transitions or when data is being fetched.
// It features the application logo and a linear progress indicator that animates continuously.
@Composable
fun LoadingScreen() {
    // `isSystemInDarkTheme()` checks if the user's device is currently set to dark mode.
    // This allows the loading screen to adapt its colors for better visibility.
    val isDarkTheme = isSystemInDarkTheme()

    // `rememberInfiniteTransition()` creates and remembers a transition that runs indefinitely.
    // This transition is used to drive continuous animations, like the progress bar.
    val infiniteTransition = rememberInfiniteTransition(label = "loading_transition") // Added a label for better debugging/inspection.

    // `animateFloat` is used within the `infiniteTransition` to create a Float value that animates over time.
    // This `progress` value will animate from 0f to 1f and then repeat.
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f, // The starting value of the animation.
        targetValue = 1f, // The end value of the animation.
        // `animationSpec` defines how the animation progresses over time.
        animationSpec = infiniteRepeatable(
            // `tween` creates a duration-based animation.
            animation = tween(durationMillis = 1500, easing = LinearEasing) // The animation takes 1500ms (1.5 seconds)
            // and uses LinearEasing for a constant speed.
        ), label = "loading_progress" // Added a label for the animation.
    )

    // Box Composable is used as a container that allows stacking its children.
    // Here, it's used to center the content on the screen.
    Box(
        modifier = Modifier
            .fillMaxSize() // Makes the Box occupy the entire available screen space.
            // Sets the background color of the Box.
            // If dark theme is active, the background is Black; otherwise, it's White.
            .background(if (isDarkTheme) Color.Black else Color.White),
        contentAlignment = Alignment.Center // Centers the content (the Column) within the Box, both horizontally and vertically.
    ) {
        // Column Composable arranges its children (logo and progress bar) vertically.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Aligns the children (Image and LinearProgressIndicator) to the horizontal center of the Column.
            verticalArrangement = Arrangement.Center // Arranges the children vertically in the center of the Column.
        ) {
            // Image Composable to display the application logo.
            Image(
                painter = painterResource(id = R.drawable.logo), // Loads the logo image from the drawable resources.
                // Ensure 'logo.png' or 'logo.xml' exists in res/drawable.
                contentDescription = "Logo", // Accessibility description for the image.
                modifier = Modifier.size(120.dp) // Sets the size of the logo image to 120dp x 120dp.
            )
            // Spacer Composable adds a fixed amount of empty space vertically between the logo and the progress bar.
            Spacer(modifier = Modifier.height(16.dp)) // Creates a 16dp vertical space.

            // LinearProgressIndicator Composable displays a horizontal progress bar.
            LinearProgressIndicator(
                progress = { progress }, // The current progress of the indicator, bound to the animated `progress` state.
                // The lambda syntax `{ progress }` is used when the progress can change.
                // Sets the color of the progress bar itself.
                // White if in dark theme, Gray otherwise.
                color = if (isDarkTheme) Color.White else Color.Gray,
                // Sets the color of the track (the background) of the progress bar.
                // DarkGray if in dark theme, LightGray otherwise.
                trackColor = if (isDarkTheme) Color.DarkGray else Color.LightGray,
                modifier = Modifier
                    .width(160.dp) // Sets the width of the progress bar to 160dp.
                    .height(8.dp)  // Sets the height (thickness) of the progress bar to 8dp.
            )
        }
    }
}
