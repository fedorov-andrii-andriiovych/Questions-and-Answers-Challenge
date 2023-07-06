package com.fedorov.andrii.andriiovych.qachallenge.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.domain.model.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundFalse
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundTrue
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundBox
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MultipleViewModel @Inject constructor(private val newQuestionUseCase: NewQuestionUseCase) :
    ViewModel() {
    val screenState = MutableStateFlow<ResultOf>(ResultOf.Loading)
    val button0ColorState = MutableStateFlow(PrimaryBackgroundBox)
    val button1ColorState = MutableStateFlow(PrimaryBackgroundBox)
    val button2ColorState = MutableStateFlow(PrimaryBackgroundBox)
    val button3ColorState = MutableStateFlow(PrimaryBackgroundBox)
    val difficultyState = MutableStateFlow(QuestionDifficulty.ANY)
    val questionState = MutableStateFlow(QuestionModel())
    val categoryState = MutableStateFlow(CategoryModel())
    fun getNewQuestion() = viewModelScope.launch(Dispatchers.IO) {
        screenState.value = ResultOf.Loading
        try {
            val newQuestion =
                newQuestionUseCase.getNewQuestion(
                    category = categoryState.value.id,
                    type = QuestionType.MULTIPLE.value,
                    difficulty = difficultyState.value.value
                )

            withContext(Dispatchers.Main) {
                button0ColorState.value = PrimaryBackgroundBox
                button1ColorState.value = PrimaryBackgroundBox
                button2ColorState.value = PrimaryBackgroundBox
                button3ColorState.value = PrimaryBackgroundBox
                questionState.value = newQuestion
            }
            screenState.value = ResultOf.Success
        } catch (e: Exception) {
            screenState.value = ResultOf.Failure(message = MainViewModel.SOMETHING_WENT_WRONG)
        }
    }

    fun checkCorrectAnswer(numberButton: Int) {
        when (numberButton) {
            0 -> {
                if (questionState.value.answers[0] == questionState.value.correct_answer) {
                    button0ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    button0ColorState.value = ButtonBackgroundFalse
                }
            }
            1 -> {
                if (questionState.value.answers[1] == questionState.value.correct_answer) {
                    button1ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    button1ColorState.value = ButtonBackgroundFalse
                }
            }
            2 -> {
                if (questionState.value.answers[2] == questionState.value.correct_answer) {
                    button2ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    button2ColorState.value = ButtonBackgroundFalse
                }
            }
            3 -> {
                if (questionState.value.answers[3] == questionState.value.correct_answer) {
                    button3ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    button3ColorState.value = ButtonBackgroundFalse
                }
            }
        }
    }

    private fun updateQuestion() = viewModelScope.launch(Dispatchers.IO) {
        delay(500)
        getNewQuestion()
    }
}