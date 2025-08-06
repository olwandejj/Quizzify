package com.edufun.quizzify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edufun.quizzify.ui.theme.QuizzifyTheme
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizzifyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    AppNavigator()
                }
            }
        }
    }
}

// Navigation
sealed class AppScreen {
    object Menu : AppScreen()
    object Quiz : AppScreen()
    object Login : AppScreen()
    object Register : AppScreen()
    object Profile : AppScreen()
    object Loading : AppScreen()
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigator(viewModel: QuizzifyViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Login) }
    var isLoggingOut by remember { mutableStateOf(false) }
    var isLoggingIn by remember { mutableStateOf(false) }

    // Handle back button behavior
    BackHandler(enabled = currentScreen != AppScreen.Login) {
        when (currentScreen) {
            is AppScreen.Menu -> currentScreen = AppScreen.Login  // From Menu to Login
            is AppScreen.Quiz -> currentScreen = AppScreen.Menu    // From Quiz to Menu
            is AppScreen.Profile -> currentScreen = AppScreen.Menu // From Profile to Menu
            is AppScreen.Register -> currentScreen = AppScreen.Login // From Register to Login
            else -> {} // No action on Login, app will exit normally
        }
    }

    // If logging out, show the LoadingScreen and delay for the transition
    if (isLoggingOut) {
        LoadingScreen() // Show the loading screen
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500) // Simulate logout delay
            isLoggingOut = false // Set isLoggingOut to false after delay
            currentScreen = AppScreen.Login // Navigate back to Login screen
        }

    } else if (isLoggingIn) {
        LoadingScreen() // Show the loading screen
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500) // Simulate logout delay
            isLoggingIn = false // Set isLoggingOut to false after delay
            currentScreen = AppScreen.Menu // Navigate back to Menu screen
        }
    } else {

        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                if (targetState is AppScreen.Profile && initialState !is AppScreen.Profile) {
                    // Slide in from the right when navigating to Profile
                    slideInHorizontally(
                        initialOffsetX = { it }, // Start from full width
                        animationSpec = tween(500) // Animation duration 500ms
                    ) with slideOutHorizontally(
                        targetOffsetX = { -it / 2 }, // Slide out halfway to the left
                        animationSpec = tween(500)
                    )
                } else if (initialState is AppScreen.Profile && targetState != AppScreen.Profile) {
                    // Reverse animation when navigating away from Profile
                    slideInHorizontally(
                        initialOffsetX = { -it }, // Start from the left
                        animationSpec = tween(500)
                    ) with slideOutHorizontally(
                        targetOffsetX = { it }, // Slide out to the right
                        animationSpec = tween(500)
                    )
                } else if (targetState is AppScreen.Quiz && initialState !is AppScreen.Quiz) {
                    slideInVertically(initialOffsetY = { it }, animationSpec = tween(500)) with
                            slideOutVertically(targetOffsetY = { it }, animationSpec = tween(500))

                } else if (initialState is AppScreen.Loading && targetState is AppScreen.Login) {
                    fadeIn(animationSpec = tween(500)) with fadeOut(animationSpec = tween(500))
                } else if (initialState is AppScreen.Menu && targetState is AppScreen.Loading) {
                    // Animation for Menu to Loading transition
                    fadeIn(animationSpec = tween(500)) with fadeOut(animationSpec = tween(500))
                } else if (initialState is AppScreen.Loading && targetState != AppScreen.Loading) {
                    // Fade out Loading screen and fade into the next
                    fadeIn(animationSpec = tween(500)) with fadeOut(animationSpec = tween(500))

                } else if (initialState is AppScreen.Login && targetState is AppScreen.Loading) {
                    slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)) with slideOutHorizontally(targetOffsetX = { -it / 2 }, animationSpec = tween(500))
                } else if (initialState is AppScreen.Loading && targetState is AppScreen.Menu) {
                    slideInVertically(initialOffsetY = { it }, animationSpec = tween(500)) with
                            fadeOut(animationSpec = tween(500))

                } else {
                    fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
                }
            }
        ) { targetScreen ->
            // Use `targetScreen` here to display the appropriate screen
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background

            ) {
                when (targetScreen) {
                    is AppScreen.Login -> LoginScreen(
                        onLogin = { isLoggingIn = true },
                        onRegisterNavigate = { currentScreen = AppScreen.Register }
                    )

                    is AppScreen.Register -> RegisterScreen(
                        onRegister = { currentScreen = AppScreen.Menu },
                        onBackToLogin = { currentScreen = AppScreen.Login }
                    )

                    is AppScreen.Menu -> DrawerTab(
                        onQuizSelected = { quizName ->
                            viewModel.loadQuiz(quizName)
                            currentScreen = AppScreen.Quiz
                        },
                        onLogout = { isLoggingOut = true },
                        onProfile = { currentScreen = AppScreen.Profile }
                    )

                    is AppScreen.Quiz -> QuizApp(
                        viewModel = viewModel,
                        onQuitQuiz = { currentScreen = AppScreen.Menu }
                    )

                    is AppScreen.Profile -> ProfileScreen(
                        name = "John Doe",
                        profileImage = R.drawable.image,
                        onMenu = { currentScreen = AppScreen.Menu }
                    )

                    is AppScreen.Loading -> LoadingScreen()
                }
            }
        }
    }
}