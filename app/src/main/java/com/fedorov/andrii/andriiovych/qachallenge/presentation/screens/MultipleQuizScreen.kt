package com.fedorov.andrii.andriiovych.qachallenge.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.MultipleViewModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ResultOf
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink

@Composable
fun MultipleQuizScreen(multipleViewModel: MultipleViewModel, modifier: Modifier) {
    val screenState by multipleViewModel.screenState.collectAsState()
    val button0ColorState by multipleViewModel.button0ColorState.collectAsState()
    val button1ColorState by multipleViewModel.button1ColorState.collectAsState()
    val button2ColorState by multipleViewModel.button2ColorState.collectAsState()
    val button3ColorState by multipleViewModel.button3ColorState.collectAsState()
    val questionState by multipleViewModel.questionState.collectAsState()
    val categoryState by multipleViewModel.categoryState.collectAsState()

    when (screenState) {
        is ResultOf.Success, ResultOf.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp), verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(color = PrimaryBackgroundPink)
                        .fillMaxWidth()
                        .height(60.dp)
                        .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(25.dp))

                ) {
                    Text(
                        text = categoryState.name,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.Center
                ) {
                    if (screenState is ResultOf.Loading) {
                        CircularProgressIndicator(color = PrimaryBackgroundPink)
                    } else {
                        Text(
                            text = questionState.question,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Button(
                    onClick = { multipleViewModel.checkCorrectAnswer(0) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = button0ColorState),
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        1.dp,
                        Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = questionState.answers[0],
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Button(
                    onClick = { multipleViewModel.checkCorrectAnswer(1) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = button1ColorState),
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        1.dp,
                        Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = questionState.answers[1],
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Button(
                    onClick = { multipleViewModel.checkCorrectAnswer(2) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = button2ColorState),
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        1.dp,
                        Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = questionState.answers[2],
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Button(
                    onClick = { multipleViewModel.checkCorrectAnswer(3) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = button3ColorState),
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        1.dp,
                        Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = questionState.answers[3],
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

            }
        }
        is ResultOf.Failure -> {
            FailureScreen(
                message = (screenState as ResultOf.Failure).message,
                onClickRetry = { multipleViewModel.getNewQuestion() })
        }
    }

}