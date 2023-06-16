package com.fedorov.andrii.andriiovych.qachallenge.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fedorov.andrii.andriiovych.qachallenge.MainViewModel
import com.fedorov.andrii.andriiovych.qachallenge.QuestionType


@Composable
fun QuizScreen(mainViewModel: MainViewModel,modifier: Modifier) {
    when (mainViewModel.typeState.value) {
        QuestionType.BOOLEAN -> BooleanQuizScreen(mainViewModel,Modifier)
        QuestionType.MULTIPLE -> MultipleQuizScreen(mainViewModel,Modifier)
        else -> throw IllegalAccessException()
    }
}