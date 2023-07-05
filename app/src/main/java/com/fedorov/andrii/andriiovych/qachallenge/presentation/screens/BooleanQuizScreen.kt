package com.fedorov.andrii.andriiovych.qachallenge.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fedorov.andrii.andriiovych.qachallenge.R
import com.fedorov.andrii.andriiovych.qachallenge.ResultOf
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundBox
import com.fedorov.andrii.andriiovych.qachallenge.domain.viewmodels.BooleanViewModel

@Composable
fun BooleanQuizScreen(booleanViewModel: BooleanViewModel, modifier: Modifier) {
    val screenState by booleanViewModel.screenState.collectAsState()
    val button0ColorState by booleanViewModel.button0ColorState.collectAsState()
    val button1ColorState by booleanViewModel.button1ColorState.collectAsState()
    val questionState by booleanViewModel.questionState.collectAsState()
    val categoryState by booleanViewModel.categoryState.collectAsState()
    when {
        screenState is ResultOf.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp), verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(color = PrimaryBackgroundBox)
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
                    Text(
                        text = questionState.question,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = { booleanViewModel.checkCorrectAnswer(0) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = button0ColorState),
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        1.dp,
                        Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.tru),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Button(
                    onClick = { booleanViewModel.checkCorrectAnswer(1) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = button1ColorState),
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        1.dp,
                        Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.fals),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

            }
        }
        screenState is ResultOf.Failure -> {
            FailureScreen(
                message = (screenState as ResultOf.Failure).message,
                onClickRetry = { booleanViewModel.getNewQuestion() })
        }
        screenState is ResultOf.Loading -> {
            LoadingScreen()
        }
    }
}