package com.fedorov.andrii.andriiovych.qachallenge.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fedorov.andrii.andriiovych.qachallenge.MainViewModel
import com.fedorov.andrii.andriiovych.qachallenge.QuestionType
import com.fedorov.andrii.andriiovych.qachallenge.R
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundBox


@Composable
fun HomeScreen(
    modifier: Modifier,
    mainViewModel: MainViewModel,
    onClickType: (QuestionType) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(25.dp))
                .background(color = PrimaryBackgroundBox)
                .fillMaxWidth()
                .height(100.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(25.dp))

        ) {
            Text(
                text = "Q&A Challenge",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f), contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = R.drawable.qa ), contentDescription = "qa")
        }

        Button(
            onClick = { onClickType(QuestionType.ANY) },
            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryBackgroundBox),
            shape = RoundedCornerShape(25.dp),
            border = BorderStroke(
                1.dp,
                Color.Black
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            Text(
                text = "Multiple Choice",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Button(
            onClick = { onClickType(QuestionType.ANY) },
            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryBackgroundBox),
            shape = RoundedCornerShape(25.dp),
            border = BorderStroke(
                1.dp,
                Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "True / False",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}