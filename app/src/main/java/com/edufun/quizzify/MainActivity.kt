package com.edufun.quizzify

// Import for ProfileScreen - assuming this is a composable function defined elsewhere.
import ProfileScreen
// Android OS and Activity specific imports.
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler // Import for handling back button presses in Compose.
import androidx.activity.compose.setContent // Import for setting the Compose content of an Activity.
// Jetpack Compose layout imports.
import androidx.compose.foundation.layout.fillMaxSize
// Jetpack Compose Material 3 UI components.
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
// Jetpack Compose runtime imports for state management and composable functions.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue // Delegate for reading State.
import androidx.compose.runtime.mutableStateOf // Creates a mutable State object.
import androidx.compose.runtime.remember // Remembers a value across recompositions.
import androidx.compose.runtime.setValue // Delegate for writing to State.
// Modifier for styling and behavior of Composables.
import androidx.compose.ui.Modifier
// Graphics import, specifically for Color.
import androidx.compose.ui.graphics.Color
// ViewModel integration with Compose.
import androidx.lifecycle.viewmodel.compose.viewModel
// Project-specific theme import.
import com.edufun.quizzify.ui.theme.QuizzifyTheme
// Jetpack Compose animation imports.
import androidx.compose.animation.*
import androidx.compose.animation.core.tween // Animation timing function.
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
// Kotlin Coroutines for asynchronous operations.
import kotlinx.coroutines.delay // Suspends a coroutine for a specified time.


// MainActivity is the main entry point of the Android application.
// It inherits from ComponentActivity, which is the base class for activities using Jetpack Compose.
class MainActivity : ComponentActivity() {
    // onCreate is a lifecycle method called when the activity is first created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Calls the superclass's onCreate method.
        // setContent is used to define the layout of the activity using Composable functions.
        setContent {
            // QuizzifyTheme applies the custom theme defined for the application.
            // This includes colors, typography, and shapes.
            QuizzifyTheme {
                // Surface is a container that provides a background color and elevation.
                // It's often used as the root Composable for a screen.
                Surface(
                    modifier = Modifier.fillMaxSize(), // Makes the Surface occupy the entire screen.
                    color = Color.Black // Sets the background color of the Surface to Black.
                ) {
                    // AppNavigator is a Composable function responsible for handling
                    // the navigation between different screens of the application.
                    AppNavigator()
                }
            }
        }
    }
}

// Sealed class AppScreen defines the different screens or destinations in the application.
// Using a sealed class ensures that all possible screen types are known at compile time.
sealed class AppScreen {
    object Login : AppScreen()     // Represents the login screen.
    object Register : AppScreen()  // Represents the registration screen.
    object Menu : AppScreen()      // Represents the main menu screen (home screen).
    object Quiz : AppScreen()      // Represents the quiz taking screen.
    object Profile : AppScreen()   // Represents the user profile screen.
    object Loading : AppScreen()   // Represents a loading/transition screen.
}

// AppNavigator Composable function manages the navigation flow and transitions between different AppScreen destinations.
// It uses AnimatedContent to provide animated transitions between screens.
//
// Parameters:
//   viewModel: An instance of QuizzifyViewModel, defaulted using `viewModel()`.
//              This ViewModel likely holds data or state relevant to the navigation or screens.
@OptIn(ExperimentalAnimationApi::class) // Opt-in for experimental animation APIs.
@Composable
fun AppNavigator(viewModel: QuizzifyViewModel = viewModel()) {
    // `currentScreen` is a mutable state variable that holds the current screen being displayed.
    // It now starts with the Login screen.
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Login) }
    // `isLoggingOut` is a state variable to manage the visual state during logout.
    var isLoggingOut by remember { mutableStateOf(false) }
    // `isLoading` can be a general loading state, e.g., after login before showing menu.
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // BackHandler is a Composable that allows intercepting the system back button press.
    BackHandler(enabled = currentScreen != AppScreen.Login && currentScreen != AppScreen.Menu) {
        // Defines custom back navigation logic based on the current screen.
        when (currentScreen) {
            is AppScreen.Quiz -> currentScreen = AppScreen.Menu      // From Quiz, go back to Menu.
            is AppScreen.Profile -> currentScreen = AppScreen.Menu   // From Profile, go back to Menu.
            is AppScreen.Register -> currentScreen = AppScreen.Login // From Register, go back to Login.
            else -> { /* No custom action for other screens by default, app will exit if on Menu or Login */ }
        }
    }
    // Specific BackHandler for Login screen to exit the app
    BackHandler(enabled = currentScreen == AppScreen.Login) {
        (context as? Activity)?.finish()
    }


    // Conditional rendering based on the `isLoggingOut` or `isLoading` state.
    if (isLoggingOut || isLoading) {
        LoadingScreen() // Display the LoadingScreen Composable.
        LaunchedEffect(Unit) {
            delay(1500) // Suspend for 1.5 seconds.
            if (isLoggingOut) {
                isLoggingOut = false
                currentScreen = AppScreen.Login // Navigate to Login after logout.
            }
            if (isLoading) {
                isLoading = false
                currentScreen = AppScreen.Menu // Navigate to Menu after initial loading (e.g., after login).
            }
        }
    } else {
        // AnimatedContent provides animations when its `targetState` (currentScreen) changes.
        AnimatedContent(
            targetState = currentScreen,
            // `transitionSpec` defines how the content enters and exits.
            transitionSpec = {
                // Custom transitions based on the target and initial states.
                when {
                    // Login/Register to Menu
                    (initialState is AppScreen.Login || initialState is AppScreen.Register) && targetState is AppScreen.Menu ->
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)) with
                                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
                    // Menu to Login (Logout)
                    initialState is AppScreen.Menu && targetState is AppScreen.Login ->
                        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500)) with
                                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
                    // To Profile
                    targetState is AppScreen.Profile && initialState !is AppScreen.Profile ->
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)) with
                                slideOutHorizontally(targetOffsetX = { -it / 2 }, animationSpec = tween(500))
                    // From Profile
                    initialState is AppScreen.Profile && targetState !is AppScreen.Profile ->
                        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500)) with
                                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
                    // To Quiz
                    targetState is AppScreen.Quiz && initialState !is AppScreen.Quiz ->
                        slideInVertically(initialOffsetY = { it }, animationSpec = tween(500)) with
                                slideOutVertically(targetOffsetY = { -it / 2 }, animationSpec = tween(500)) // Corrected exit
                    // From Quiz
                    initialState is AppScreen.Quiz && targetState !is AppScreen.Quiz ->
                        slideInVertically(initialOffsetY = { -it }, animationSpec = tween(500)) with // Corrected entry
                                slideOutVertically(targetOffsetY = { it }, animationSpec = tween(500))

                    // Transitions for Loading screen (e.g., during login or logout)
                    targetState is AppScreen.Loading || initialState is AppScreen.Loading ->
                        fadeIn(animationSpec = tween(500)) with fadeOut(animationSpec = tween(500))
                    // Default fade transition for other screen changes.
                    else ->
                        fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
                }
            },
            label = "AppScreenAnimation" // Added label for AnimatedContent
        ) { targetScreen -> // `targetScreen` is the screen being transitioned to.
            // Surface container for the content of the target screen.
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                // `when` expression to determine which Composable function to display based on the `targetScreen`.
                when (targetScreen) {
                    is AppScreen.Login -> LoginScreen(
                        onLogin = {
                            // TODO: Implement actual login logic here
                            // For now, simulate login and go to loading then menu
                            isLoading = true // Show loading screen
                        },
                        onGoToRegister = { currentScreen = AppScreen.Register },
                        onForgotPassword = { /* TODO: Handle forgot password */ }
                    )
                    is AppScreen.Register -> RegisterScreen(
                        onRegister = {
                            // TODO: Implement actual registration logic here
                            // For now, simulate registration and go to loading then menu
                            isLoading = true // Show loading screen
                        },
                        onBackToLogin = { currentScreen = AppScreen.Login }
                    )
                    is AppScreen.Menu -> DrawerTab(
                        onQuizSelected = { quizName ->
                            viewModel.loadQuiz(quizName) // Loads the selected quiz data in the ViewModel.
                            currentScreen = AppScreen.Quiz // Navigates to the Quiz screen.
                        },
                        onLogout = { isLoggingOut = true }, // Sets logging out state to true.
                        onProfile = { currentScreen = AppScreen.Profile } // Navigates to Profile screen.
                    )
                    is AppScreen.Quiz -> QuizApp(
                        viewModel = viewModel, // Passes the ViewModel to the QuizApp.
                        onQuitQuiz = { currentScreen = AppScreen.Menu } // Navigates back to Menu screen when quiz is quit.
                    )
                    is AppScreen.Profile -> ProfileScreen(
                        name = "John Doe", // TODO: Replace hardcoded profile data.
                        profileImage = R.drawable.image, // Ensure this drawable resource exists.
                        onMenu = { currentScreen = AppScreen.Menu } // Navigates back to Menu screen.
                    )
                    is AppScreen.Loading -> LoadingScreen() // Displays the loading screen.
                }
            }
        }
    }
}
