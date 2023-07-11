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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fedorov.andrii.andriiovych.qachallenge.R
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.uicomponents.TopText
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.BooleanViewModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ScreenState

@Composable
fun BooleanQuizScreen(booleanViewModel: BooleanViewModel, modifier: Modifier) {
    val screenState by booleanViewModel.screenState.collectAsState()
    val buttonTrueColorState by booleanViewModel.buttonTrueColorState.collectAsState()
    val buttonFalseColorState by booleanViewModel.buttonFalseColorState.collectAsState()
    when (screenState) {
        is ScreenState.Success<QuestionModel> -> {
            val questionModel = (screenState as ScreenState.Success<QuestionModel>).value
            BooleanSuccessScreen(
                questionModel = questionModel,
                onTrueClicked = { booleanViewModel.checkCorrectAnswer(BooleanViewModel.TRUE) },
                onFalseClicked = { booleanViewModel.checkCorrectAnswer(BooleanViewModel.FALSE) },
                buttonTrueColorState,
                buttonFalseColorState
            )
        }
        is ScreenState.Failure -> {
            FailureScreen(
                message = (screenState as ScreenState.Failure).message,
                onClickRetry = { booleanViewModel.getNewQuestion() })
        }
        is ScreenState.Loading -> {
            LoadingScreen()
        }
    }
}

@Composable
fun BooleanSuccessScreen(
    questionModel: QuestionModel,
    onTrueClicked: () -> Unit,
    onFalseClicked: () -> Unit,
    buttonTrueColorState: Color,
    buttonFalseColorState: Color,
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
        ButtonWithTextBoolean(
            buttonText = stringResource(R.string.tru),
            buttonColorState = buttonTrueColorState,
            onButtonClicked = onTrueClicked
        )

        ButtonWithTextBoolean(
            buttonText = stringResource(R.string.fals),
            buttonColorState = buttonFalseColorState,
            onButtonClicked = onFalseClicked
        )
    }
}

@Composable
fun ButtonWithTextBoolean(
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
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}