package com.edufun.quizzify

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// QuizzifyViewModel is a class that extends ViewModel.
// It is responsible for preparing and managing the data for the UI related to quizzes.
// This includes loading quiz categories, questions, tracking the current question, and calculating the score.
class QuizzifyViewModel : ViewModel() {

    // Section for UI elements related to the main menu or list of quizzes.
    // data class ListDetail represents the information needed to display a quiz category in a list.
    //
    // Parameters:
    //   pic: A Painter object used to display an image or icon for the quiz category.
    //   text: A String representing the name or title of the quiz category.
    data class ListDetail(
        val pic: Painter,
        val text: String
    )

    // Composable function to get the list of available quiz categories.
    // This function is marked @Composable because it uses painterResource, which is a composable function.
    // TODO: Consider moving this data to a non-composable function or initializing it in `init`
    //       if it doesn't strictly need to be @Composable for its core logic,
    //       or if it's fetched from a data source like Firebase.
    @Composable
    fun allList(): List<ListDetail> { // TODO: Add Configure firebase - Indication that this data might come from Firebase in the future.
        return listOf(
            ListDetail(
                painterResource(R.drawable.g), // Loads drawable resource for "General Knowledge".
                "General Knowledge"
            ),
            ListDetail(
                painterResource(R.drawable.m), // Loads drawable resource for "Math Quiz".
                "Math Quiz"
            ),
            ListDetail(
                painterResource(R.drawable.s), // Loads drawable resource for "Science Quiz".
                "Science Quiz"
            )
        )
    }

    // Section for managing quiz questions and state.
    // quizzes is a private map holding all the questions categorized by quiz name.
    // The key is the quiz name (String), and the value is a list of Question objects.
    // TODO: Add Configure Firestore - Indication that this data should ideally be fetched from Firestore.
    private val quizzes: Map<String, List<Question>> = mapOf(
        "General Knowledge" to listOf(
            // Each Question object contains the question text, a list of answer options, and the index of the correct answer.
            Question("What is the capital of France?", listOf("Paris", "Berlin", "Madrid", "Rome"), 0),
            Question("Which year did WW2 end?", listOf("1940", "1945", "1950", "1939"), 1),
            Question("Who wrote 'Romeo and Juliet'?", listOf("Charles Dickens", "William Shakespeare", "Jane Austen", "Mark Twain"), 1),
            Question("What is the largest ocean on Earth?", listOf("Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"), 3),
            Question("In which country is the Great Pyramid of Giza located?", listOf("Mexico", "Peru", "Egypt", "Sudan"), 2),
            Question("What is the currency of Japan?", listOf("Won", "Yuan", "Yen", "Rupee"), 2),
            Question("Which planet is known as the Morning Star or Evening Star?", listOf("Mars", "Venus", "Jupiter", "Mercury"), 1),
            Question("Who painted the Mona Lisa?", listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"), 2),
            Question("What is the tallest mountain in the world?", listOf("K2", "Kangchenjunga", "Mount Everest", "Lhotse"), 2),
            Question("Which two countries share the longest international border?", listOf("USA and Canada", "Russia and China", "Argentina and Chile", "Kazakhstan and Russia"), 0)
        ),
        "Math Quiz" to listOf(
            Question("What is 2 + 2?", listOf("3", "4", "5", "6"), 1),
            Question("Solve: 10 * 2", listOf("10", "20", "30", "40"), 1),
            Question("What is 15 / 3?", listOf("3", "5", "10", "15"), 1),
            Question("What is the square root of 81?", listOf("7", "8", "9", "10"), 2),
            Question("If a triangle has angles 90°, 30°, what is the third angle?", listOf("45°", "60°", "75°", "90°"), 1),
            Question("What is 7 multiplied by 8?", listOf("48", "54", "56", "64"), 2),
            Question("What is 100 - 43?", listOf("57", "67", "53", "63"), 0),
            Question("How many sides does a hexagon have?", listOf("5", "6", "7", "8"), 1),
            Question("What is 25% of 200?", listOf("25", "50", "75", "100"), 1),
            Question("What is the next prime number after 7?", listOf("8", "9", "10", "11"), 3)
        ),
        "Science Quiz" to listOf(
            Question("Which planet is known as the Red Planet?", listOf("Earth", "Mars", "Jupiter", "Venus"), 1),
            Question("What is H2O?", listOf("Oxygen", "Water", "Hydrogen", "Salt"), 1),
            Question("What force pulls objects towards the center of the Earth?", listOf("Magnetism", "Friction", "Gravity", "Tension"), 2),
            Question("What is the chemical symbol for Gold?", listOf("Ag", "Au", "Gd", "Go"), 1),
            Question("How many bones are in the adult human body?", listOf("206", "212", "198", "220"), 0),
            Question("What gas do plants absorb from the atmosphere?", listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"), 2),
            Question("What is the hardest natural substance on Earth?", listOf("Gold", "Iron", "Diamond", "Quartz"), 2),
            Question("Which part of the plant conducts photosynthesis?", listOf("Root", "Stem", "Flower", "Leaf"), 3),
            Question("What is the boiling point of water in Celsius?", listOf("90°C", "100°C", "110°C", "120°C"), 1),
            Question("What instrument is used to measure atmospheric pressure?", listOf("Thermometer", "Barometer", "Hygrometer", "Anemometer"), 1)
        )
    )

    // currentQuiz holds the list of questions for the currently selected quiz.
    private var currentQuiz: List<Question> = emptyList()
    // currentIndex tracks the index of the current question being displayed from the `currentQuiz` list.
    private var currentIndex = 0

    // _currentQuestion is a MutableStateFlow that holds the current Question object to be displayed.
    // It's private to control its modification within the ViewModel.
    // It's nullable to represent the state where no question is loaded (e.g., end of quiz).
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    // currentQuestion is a public StateFlow exposed to the UI, providing read-only access to the current question.
    // Observers (like Composables) can collect this flow to react to changes in the current question.
    val currentQuestion: StateFlow<Question?> = _currentQuestion.asStateFlow()

    // _score is a MutableStateFlow that holds the user's current score for the quiz.
    // It's private to control its modification within the ViewModel.
    private val _score = MutableStateFlow(0)
    // score is a public StateFlow exposed to the UI, providing read-only access to the current score.
    // Observers can collect this flow to display score updates.
    val score: StateFlow<Int> = _score.asStateFlow()

    // loadQuiz function is called to start a new quiz.
    //
    // Parameters:
    //   quizName: The name of the quiz to load (e.g., "General Knowledge").
    fun loadQuiz(quizName: String) {
        // Retrieves the list of questions for the given quizName from the `quizzes` map.
        // If the quizName is not found, it defaults to an empty list.
        currentQuiz = quizzes[quizName] ?: emptyList()
        // Resets the question index to the beginning of the quiz.
        currentIndex = 0
        // Resets the score to 0.
        _score.value = 0
        // Sets the first question of the loaded quiz as the current question.
        // Uses `getOrNull` to safely access the element and avoid IndexOutOfBoundsException if the list is empty.
        _currentQuestion.value = currentQuiz.getOrNull(currentIndex)
    }

    // submitAnswer function is called when the user selects an answer for the current question.
    //
    // Parameters:
    //   selectedIndex: The index of the answer option selected by the user.
    fun submitAnswer(selectedIndex: Int) {
        // Checks if the selected answer is correct by comparing it with the `correctAnswerIndex` of the current question.
        // The `?.` safe call operator ensures that `correctAnswerIndex` is accessed only if `_currentQuestion.value` is not null.
        if (_currentQuestion.value?.correctAnswerIndex == selectedIndex) {
            // If the answer is correct, increments the score.
            _score.value += 1
        }
        // Moves to the next question by incrementing the current index.
        currentIndex += 1
        // Updates the `_currentQuestion` StateFlow with the next question from the `currentQuiz` list.
        // `getOrNull` is used to handle the case where `currentIndex` might be out of bounds (end of the quiz),
        // in which case `_currentQuestion.value` will be set to null.
        _currentQuestion.value = currentQuiz.getOrNull(currentIndex)
    }
}
