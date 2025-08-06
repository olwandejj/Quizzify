package com.edufun.quizzify


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

// List of Questions
data class quizHistoryDetail(
    val subj: String,
    val result: String
)

@Composable
fun allQuizHistory():List<quizHistoryDetail>{ // TODO: Configure Firebase
   /*
   * Add allQuizHistory to QuizzifyViewModel.kt
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

// Profile Page Composable
@Composable
fun ProfileScreen(name: String, profileImage: Int, onMenu: () -> Unit) {
    val quizHistory = allQuizHistory()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            ProfileBackButton(onMenu)

            // Profile Image
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = profileImage), // TODO
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Name
                Text(
                    text = name, // TODO
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Quiz History Section
                Text(
                    text = "Quiz Results History",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = Orange
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quiz History List
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Orange.copy(alpha = 0.6f))
                        .padding(16.dp)
                ) {
                    quizHistory.forEach { quizHistoryDetail ->
                        Column {
                            Text(
                                text = quizHistoryDetail.subj,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
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
                            HorizontalDivider(thickness = 2.dp, color = Orange.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileBackButton(onMenu: () -> Unit) {
    val scope = rememberCoroutineScope()
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Purple40),

        ) {
        IconButton(
            onClick = onMenu,
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Localized description"
            )
        }
        Text(
            text = "Profile Page",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(12.dp)
        )
    }
}