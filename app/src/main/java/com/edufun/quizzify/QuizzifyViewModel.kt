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
            Question("Which year did WW2 end?", listOf("1940", "1945", "1950", "1939"), 1)
        ),
        "Math Quiz" to listOf(
            Question("What is 2 + 2?", listOf("3", "4", "5", "6"), 1),
            Question("Solve: 10 * 2", listOf("10", "20", "30", "40"), 1)
        ),
        "Science Quiz" to listOf(
            Question("Which planet is known as the Red Planet?", listOf("Earth", "Mars", "Jupiter", "Venus"), 1),
            Question("What is H2O?", listOf("Oxygen", "Water", "Hydrogen", "Salt"), 1)
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

