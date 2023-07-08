package com.fedorov.andrii.andriiovych.qachallenge.presentation.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.uicomponents.TopText
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.MultipleViewModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ResultOf

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
                onButton_0_Clicked = { multipleViewModel.checkCorrectAnswer(MultipleViewModel.BUTTON_0) },
                onButton_1_Clicked = { multipleViewModel.checkCorrectAnswer(MultipleViewModel.BUTTON_1) },
                onButton_2_Clicked = { multipleViewModel.checkCorrectAnswer(MultipleViewModel.BUTTON_2) },
                onButton_3_Clicked = { multipleViewModel.checkCorrectAnswer(MultipleViewModel.BUTTON_3) },
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
    TopText(text = questionModel.category)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), verticalArrangement = Arrangement.SpaceEvenly
    ) {
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
        ButtonWithTextMultiple(
            buttonText = questionModel.answers[0],
            buttonColorState = button_0_ColorState,
            onButtonClicked = onButton_0_Clicked
        )

        ButtonWithTextMultiple(
            buttonText = questionModel.answers[1],
            buttonColorState = button_1_ColorState,
            onButtonClicked = onButton_1_Clicked
        )

        ButtonWithTextMultiple(
            buttonText = questionModel.answers[2],
            buttonColorState = button_2_ColorState,
            onButtonClicked = onButton_2_Clicked
        )

        ButtonWithTextMultiple(
            buttonText = questionModel.answers[3],
            buttonColorState = button_3_ColorState,
            onButtonClicked = onButton_3_Clicked
        )
    }
}
@Composable
fun ButtonWithTextMultiple(
    buttonText: String,
    buttonColorState: Color,
    onButtonClicked: () -> Unit
) {
    Button(
        onClick = onButtonClicked,
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColorState),
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}