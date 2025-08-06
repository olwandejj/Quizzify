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
import com.edufun.quizzify.ui.theme.*


// Menu
@Composable
fun MenuScreen(onQuizSelected: (String) -> Unit) {

    val allList = QuizzifyViewModel().allList()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items (items = allList){
                    quizName ->
                Column(modifier = Modifier.padding(vertical = 10.dp)) {
                    Image(
                        painter = quizName.pic,
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        alignment = Alignment.TopStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(7.dp))
                    )
                    Button(
                        onClick = { onQuizSelected(quizName.text) },
                        colors = ButtonDefaults.buttonColors(containerColor = Orange),
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomEnd = 15.dp, bottomStart = 15.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = quizName.text)
                    }
                }
            }
        }
    }
}
