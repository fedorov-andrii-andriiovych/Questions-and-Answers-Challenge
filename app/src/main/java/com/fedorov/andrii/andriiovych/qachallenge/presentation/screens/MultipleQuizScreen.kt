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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.fedorov.andrii.andriiovych.qachallenge.R
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.uicomponents.TopText
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.Buttons
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.MultipleViewModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ScreenState

@Composable
fun MultipleQuizScreen(multipleViewModel: MultipleViewModel, modifier: Modifier) {
    val screenState by multipleViewModel.screenState.collectAsState()
    val button0ColorState by multipleViewModel.button0ColorState.collectAsState()
    val button1ColorState by multipleViewModel.button1ColorState.collectAsState()
    val button2ColorState by multipleViewModel.button2ColorState.collectAsState()
    val button3ColorState by multipleViewModel.button3ColorState.collectAsState()

    when (screenState) {
        is ScreenState.Success -> {
            val questionModel = (screenState as ScreenState.Success<QuestionModel>).value
            MultipleSuccessScreen(
                questionModel = questionModel,
                onButton_0_Clicked = { multipleViewModel.checkCorrectAnswer(Buttons.BUTTON_0.numberButton) },
                onButton_1_Clicked = { multipleViewModel.checkCorrectAnswer(Buttons.BUTTON_1.numberButton) },
                onButton_2_Clicked = { multipleViewModel.checkCorrectAnswer(Buttons.BUTTON_2.numberButton) },
                onButton_3_Clicked = { multipleViewModel.checkCorrectAnswer(Buttons.BUTTON_3.numberButton) },
                button_0_ColorState = button0ColorState,
                button_1_ColorState = button1ColorState,
                button_2_ColorState = button2ColorState,
                button_3_ColorState = button3ColorState
            )
        }
        is ScreenState.Failure -> {
            FailureScreen(
                message = (screenState as ScreenState.Failure).message,
                onClickRetry = { multipleViewModel.getNewQuestion() })
        }
        is ScreenState.Loading -> {
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
            .padding(dimensionResource(id = R.dimen.spaceXXMedium)),
        verticalArrangement = Arrangement.SpaceEvenly
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
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shapeMedium)),
        border = BorderStroke(dimensionResource(id = R.dimen.spaceXXXSmall), Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.spaceMedium))
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}