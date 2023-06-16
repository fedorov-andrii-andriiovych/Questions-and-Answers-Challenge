package com.fedorov.andrii.andriiovych.qachallenge.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fedorov.andrii.andriiovych.qachallenge.MainViewModel
import com.fedorov.andrii.andriiovych.qachallenge.QuestionType
import com.fedorov.andrii.andriiovych.qachallenge.viewmodels.BooleanViewModel
import com.fedorov.andrii.andriiovych.qachallenge.viewmodels.MultipleViewModel


@Composable
fun QuizScreen(mainViewModel: MainViewModel,modifier: Modifier) {
    val multipleViewModel:MultipleViewModel = viewModel()
    val booleanViewModel:BooleanViewModel = viewModel()
    when (mainViewModel.typeState.value) {
        QuestionType.BOOLEAN -> {
            booleanViewModel.categoryState.value = mainViewModel.categoryState.value
            booleanViewModel.getNewQuestion()
            BooleanQuizScreen(booleanViewModel,Modifier)
        }
        QuestionType.MULTIPLE -> {
            multipleViewModel.categoryState.value = mainViewModel.categoryState.value
            multipleViewModel.getNewQuestion()
            MultipleQuizScreen(multipleViewModel,Modifier)
        }
        else -> throw IllegalAccessException()
    }
}