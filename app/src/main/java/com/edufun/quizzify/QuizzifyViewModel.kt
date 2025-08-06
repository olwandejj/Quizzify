package com.edufun.quizzify

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class QuizzifyViewModel : ViewModel() {
    // Menu View Model
    data class ListDetail(
        val pic: Painter,
        val text: String
    )
    @Composable
    fun allList():List<ListDetail>{ // TODO: Add Configure firebase
        return listOf(
            ListDetail(
                painterResource(R.drawable.g),
                "General Knowledge"
            ),
            ListDetail(
                painterResource(R.drawable.m),
                "Math Quiz",

                ),
            ListDetail(
                painterResource(R.drawable.s),
                "Science Quiz",
            ),
        )
    }

    // Question View Model
    private val quizzes: Map<String, List<Question>> = mapOf( // TODO: Add Configure Firestore
        "General Knowledge" to listOf(
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
    private var currentQuiz: List<Question> = emptyList()
    private var currentIndex = 0

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    fun loadQuiz(quizName: String) {
        currentQuiz = quizzes[quizName] ?: emptyList()
        currentIndex = 0
        _score.value = 0
        _currentQuestion.value = currentQuiz.getOrNull(currentIndex)
    }

    fun submitAnswer(selectedIndex: Int) {
        if (_currentQuestion.value?.correctAnswerIndex == selectedIndex) {
            _score.value += 1
        }
        currentIndex += 1
        _currentQuestion.value = currentQuiz.getOrNull(currentIndex)
    }
}
