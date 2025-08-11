package com.edufun.quizzify

// Jetpack Compose UI imports
import androidx.compose.foundation.background // For setting background colors
import androidx.compose.foundation.layout.* // For layout components like Column, Row, Spacer, size, padding, etc.
import androidx.compose.foundation.lazy.LazyColumn // For efficiently displaying scrollable lists
import androidx.compose.foundation.lazy.rememberLazyListState // To remember the state of a LazyColumn
import androidx.compose.material.icons.Icons // Default Material icons
import androidx.compose.material.icons.outlined.Logout // Outlined Logout icon
import androidx.compose.material.icons.outlined.Menu // Outlined Menu icon (hamburger icon)
import androidx.compose.material.icons.outlined.Person // Outlined Person icon (for profile)
import androidx.compose.material3.* // Material 3 components like ModalNavigationDrawer, IconButton, Text, etc.
import androidx.compose.runtime.* // Core Compose runtime functions like remember, mutableStateOf, collectAsState
import androidx.compose.ui.Alignment // For aligning children within layouts
import androidx.compose.ui.Modifier // For applying styling and behavior to Composables
import androidx.compose.ui.draw.drawWithContent // For custom drawing operations
import androidx.compose.ui.res.painterResource // For loading drawable resources (images)
import androidx.compose.ui.draw.clip // For clipping Composables to shapes
import androidx.compose.foundation.Image // For displaying images
import androidx.compose.foundation.shape.CircleShape // A circular shape for clipping
import androidx.compose.runtime.getValue // For reading State delegates
import androidx.compose.runtime.setValue // For writing to State delegates
import androidx.compose.ui.geometry.Offset // For specifying coordinates in custom drawing
import androidx.compose.ui.graphics.Color // For representing colors
import androidx.compose.ui.platform.LocalConfiguration // To get information about the current device configuration (like screen width)
import androidx.compose.ui.text.font.FontWeight // For specifying font weight (e.g., Bold)
import androidx.compose.ui.text.style.TextOverflow // For handling text that overflows its container
import androidx.compose.ui.unit.Dp // Density-independent pixels unit
import androidx.compose.ui.unit.dp // Extension for Dp values
import androidx.compose.ui.unit.sp // Scale-independent pixels unit (for text size)
import com.edufun.quizzify.ui.theme.* // Project-specific theme imports (colors, typography)
import kotlinx.coroutines.launch // For launching coroutines

// AppBar Composable function
// This function defines the top application bar, which includes a menu button to open the drawer
// and the application title.
//
// Parameters:
//   drawerState: The state of the ModalNavigationDrawer, used to control opening and closing it.
//   modifier: An optional Modifier for customizing the AppBar.
@Composable
fun AppBar(drawerState: DrawerState, modifier: Modifier = Modifier) {
    // rememberCoroutineScope provides a CoroutineScope tied to this Composable's lifecycle.
    // It's used here to launch a coroutine for opening the drawer, which is a suspend function.
    val scope = rememberCoroutineScope()
    // Column to arrange AppBar elements.
    Column {
        // Row arranges its children horizontally (menu icon and title).
        Row(
            modifier = modifier
                .fillMaxWidth() // Makes the Row take the full width of its parent.
                .background(Purple40), // Sets the background color of the AppBar.
            verticalAlignment = Alignment.CenterVertically // Vertically align items in the Row
        ) {
            // IconButton for the menu action (opening the drawer).
            IconButton(
                onClick = {
                    // Launches a coroutine in the remembered scope.
                    scope.launch {
                        drawerState.open() // Calls the suspend function to open the navigation drawer.
                    }
                }
            ) {
                // Icon Composable for the menu (hamburger) icon.
                Icon(
                    Icons.Outlined.Menu, // Uses the predefined outlined Menu icon.
                    tint = Color.White, // Sets the icon color to white.
                    contentDescription = "Open navigation drawer", // Accessibility description for the icon.
                    modifier = Modifier.size(25.dp) // Sets the size of the icon.
                )
            }
            // Text Composable for the application title.
            Text(
                text = "Quizzify",
                color = Color.White, // Ensure text color is visible on Purple40
                style = MaterialTheme.typography.headlineSmall, // Applies a predefined text style.
                modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 12.dp, end = 12.dp) // Adjusted padding
            )
        }
    }
}

// DrawerTab Composable function
// This function defines the main structure of the screen, incorporating a ModalNavigationDrawer.
// The drawer contains user profile information and navigation items (Profile, Logout).
// The main content area of the screen is defined by the `MenuScreen` composable.
//
// Parameters:
//   onQuizSelected: Lambda function passed to MenuScreen, called when a quiz is selected.
//   onLogout: Lambda function called when the user confirms logout from the drawer.
//             This lambda is expected to be handled by the AppNavigator to trigger the logout sequence.
//   onProfile: Lambda function called when the "Profile" item in the drawer is clicked.
@Composable
fun DrawerTab(onQuizSelected: (String) -> Unit, onLogout: () -> Unit, onProfile: () -> Unit) {
    // rememberDrawerState creates and remembers the state of the navigation drawer (open/closed).
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // LocalConfiguration.current provides access to device configuration, here used to get screen width.
    // Calculates the width of the drawer to be 75% of the screen width.
    val drawerWidth = (LocalConfiguration.current.screenWidthDp * 0.75).dp // Correctly use .dp
    // `showDialog` is a state variable to control the visibility of the logout confirmation AlertDialog.
    var showDialog by remember { mutableStateOf(false) }

    // ModalNavigationDrawer is a Material 3 component that provides a standard navigation drawer.
    ModalNavigationDrawer(
        drawerState = drawerState, // Pass the drawerState to control the drawer
        // `drawerContent` defines the content displayed inside the navigation drawer when it's open.
        drawerContent = {
            // ModalDrawerSheet is the container for the drawer's content.
            ModalDrawerSheet(
                drawerContainerColor = Color.Black, // Sets the background color of the drawer.
                drawerTonalElevation = Dp.Hairline, // Sets a very subtle elevation for the drawer.
                modifier = Modifier
                    .width(drawerWidth) // Sets the calculated width of the drawer.
                    // `drawWithContent` allows custom drawing operations on top of or beneath the content.
                    .drawWithContent {
                        drawContent() // Draws the actual content of the ModalDrawerSheet first.
                        // Draws a thin white vertical line on the right edge of the drawer for visual separation.
                        drawLine(
                            color = Color.White,
                            start = Offset(size.width, 0f), // Starts at the top-right edge.
                            end = Offset(size.width, size.height), // Ends at the bottom-right edge.
                            strokeWidth = 0.3.dp.toPx() // Sets the thickness of the line.
                        )
                    }
            ) {
                // Column for arranging user profile information at the top of the drawer.
                Column(modifier = Modifier.padding(20.dp)) { // Adds padding around the profile section.
                    // Image for the user's profile picture.
                    Image( // TODO: Profile Image - Placeholder for dynamic profile image.
                        painter = painterResource(R.drawable.logo), // Uses a placeholder logo image.
                        contentDescription = "User Profile Picture", // Accessibility description.
                        alignment = Alignment.TopStart,
                        modifier = Modifier
                            .size(60.dp) // Slightly larger profile image
                            .clip(shape = CircleShape) // Clips the image to a circular shape.
                    )
                    Spacer(modifier = Modifier.height(12.dp)) // Adds vertical spacing.

                    // Text for the user's name.
                    Text(
                        text = "John Doe", // TODO: Replace with actual user name.
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium, // Using MaterialTheme typography
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                    // Text for the user's handle or email.
                    Text(
                        text = "@JustJohn", // TODO: Replace with actual user handle/email.
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall, // Using MaterialTheme typography
                        maxLines = 1, // Restricts the text to a single line.
                        overflow = TextOverflow.Ellipsis, // Adds "..." if the text overflows.
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Column for displaying user stats (e.g., courses enrolled, quizzes done).
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        // Row for "Courses Enrolled" stat.
                        Row {
                            Text(
                                text = "3", // TODO: Replace with actual data.
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = " Courses Enrolled",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp)) // Spacing between stat items

                        // Row for "Quizzes done" stat.
                        Row {
                            Text(
                                text = "16", // TODO: Replace with actual data.
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = " Quizzes done",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }

                Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 16.dp)) // Visual separator

                // LazyColumn for displaying the list of navigation items (Profile, Logout).
                LazyColumn(
                    state = rememberLazyListState(), // Remembers the scroll state of the list.
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp) // Horizontal padding for items
                ) {
                    // `item` defines a single item in the LazyColumn.
                    item {
                        // TextButton for the "Profile" navigation action.
                        TextButton(
                            onClick = onProfile, // Calls the onProfile lambda when clicked.
                            modifier = Modifier.fillMaxWidth() // Make button take full width for better touch target
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) { // Arranges icon and text horizontally.
                                Icon(
                                    Icons.Outlined.Person, // Profile icon.
                                    tint = Color.White,
                                    contentDescription = "Profile",
                                    modifier = Modifier.size(24.dp), // Standard icon size
                                )
                                Spacer(Modifier.width(16.dp)) // Space between icon and text
                                Text(
                                    text = "Profile",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium, // Consistent typography
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    // `item` for the "Logout" navigation action.
                    item {
                        TextButton(
                            onClick = { showDialog = true }, // Sets showDialog to true to display the confirmation dialog.
                            modifier = Modifier.fillMaxWidth() // Make button take full width
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) { // Arranges icon and text horizontally.
                                Icon(
                                    Icons.Outlined.Logout, // Logout icon.
                                    tint = Color.White,
                                    contentDescription = "Logout",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(Modifier.width(16.dp)) // Space between icon and text
                                Text(
                                    text = "Logout",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        // Main content of the screen (outside the drawer)
        Column(modifier = Modifier.fillMaxSize()) {
            AppBar(drawerState = drawerState) // AppBar is part of the main content
            MenuScreen(
                onQuizSelected = onQuizSelected // Pass the quiz selection handler to MenuScreen
            )

            // AlertDialog for logout confirmation
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Confirm Logout") },
                    text = { Text(text = "Are you sure you want to log out?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false // Dismiss dialog
                                onLogout()      // This calls the lambda passed from AppNavigator
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Purple40) // Use theme color
                        ) {
                            Text("Logout", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false }
                        ) {
                            Text("Cancel", color = Orange) // Use theme or accent color
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceVariant, // Dialog background
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant, // Dialog title text color
                    textContentColor = MaterialTheme.colorScheme.onSurfaceVariant // Dialog body text color
                )
            }
        }
    }
}
