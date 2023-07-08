package com.fedorov.andrii.andriiovych.qachallenge.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fedorov.andrii.andriiovych.qachallenge.R
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink


@Composable
fun FailureScreen(message: String, onClickRetry: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = message, textAlign = TextAlign.Center, color = Color.White,fontSize = 24.sp,)
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = { onClickRetry() },
            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryBackgroundPink),
            shape = RoundedCornerShape(25.dp),
            border = BorderStroke(
                1.dp,
                Color.Black
            )
        ) {
            Text(
                text = stringResource(R.string.retry),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}