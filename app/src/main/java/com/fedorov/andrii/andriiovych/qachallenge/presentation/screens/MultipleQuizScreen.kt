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
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
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

    when (screenState) {
        is ResultOf.Success -> {
            val questionModel = (screenState as ResultOf.Success<QuestionModel>).value
            MultipleSuccessScreen(
                questionModel = questionModel,
                onButton_0_Clicked = {multipleViewModel.checkCorrectAnswer(0)},
                onButton_1_Clicked = {multipleViewModel.checkCorrectAnswer(1)},
                onButton_2_Clicked = {multipleViewModel.checkCorrectAnswer(2)},
                onButton_3_Clicked = {multipleViewModel.checkCorrectAnswer(3)},
                button_0_ColorState = button0ColorState,
                button_1_ColorState = button1ColorState,
                button_2_ColorState = button2ColorState,
                button_3_ColorState = button3ColorState
            )
        }
        is ResultOf.Failure -> {
            FailureScreen(
                message = (screenState as ResultOf.Failure).message,
                onClickRetry = { multipleViewModel.getNewQuestion() })
        }
        is ResultOf.Loading -> {
            LoadingScreen()
        }
    }

}

@Composable
fun MultipleSuccessScreen(
    questionModel: QuestionModel,
    onButton_0_Clicked: () -> Unit,
    onButton_1_Clicked: () -> Unit,
    onButton_2_Clicked: () -> Unit,
    onButton_3_Clicked: () -> Unit,
    button_0_ColorState: Color,
    button_1_ColorState: Color,
    button_2_ColorState: Color,
    button_3_ColorState: Color
) {
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
                text = questionModel.category,
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
                text = questionModel.question,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { onButton_0_Clicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = button_0_ColorState),
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
                text = questionModel.answers[0],
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Button(
            onClick = { onButton_1_Clicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = button_1_ColorState),
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
                text = questionModel.answers[1],
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Button(
            onClick = { onButton_2_Clicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = button_2_ColorState),
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
                text = questionModel.answers[2],
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Button(
            onClick = { onButton_3_Clicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = button_3_ColorState),
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
                text = questionModel.answers[3],
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}