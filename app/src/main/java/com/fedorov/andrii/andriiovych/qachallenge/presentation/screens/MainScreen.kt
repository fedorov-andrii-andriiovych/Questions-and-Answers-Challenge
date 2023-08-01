package com.fedorov.andrii.andriiovych.qachallenge.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fedorov.andrii.andriiovych.qachallenge.R
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.uicomponents.TopText
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.MainViewModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.QuestionType
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink


@Composable
fun MainScreen(
    modifier: Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    onClickType: (QuestionType) -> Unit
) {
    TopText(text = stringResource(R.string.qa_chalenge))
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
            Image(
                painter = painterResource(id = R.drawable.qa), contentDescription = stringResource(
                    R.string.qa
                )
            )
        }
        ButtonWithTextHome(
            buttonText = stringResource(R.string.multiple_choice),
            buttonColorState = PrimaryBackgroundPink,
            onButtonClicked = { onClickType(QuestionType.MULTIPLE) }
        )

        ButtonWithTextHome(
            buttonText = stringResource(R.string.tru_fals),
            buttonColorState = PrimaryBackgroundPink,
            onButtonClicked = { onClickType(QuestionType.BOOLEAN) }
        )
    }
}

@Composable
fun ButtonWithTextHome(
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
            .padding(bottom = dimensionResource(id = R.dimen.spaceXXMedium))
    ) {
        Text(
            text = buttonText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}