package com.fedorov.andrii.andriiovych.qachallenge.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.domain.model.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundFalse
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundTrue
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooleanViewModel @Inject constructor(private val newQuestionUseCase: NewQuestionUseCase) :
    ViewModel() {
    val screenState = MutableStateFlow<ResultOf<QuestionModel>>(ResultOf.Loading)
    val button0ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button1ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val difficultyState = MutableStateFlow(QuestionDifficulty.ANY)
    val questionState = MutableStateFlow(QuestionModel())
    val categoryState = MutableStateFlow(CategoryModel())
    fun getNewQuestion() = viewModelScope.launch(Dispatchers.IO) {
        screenState.value = ResultOf.Loading
        val result =
            newQuestionUseCase.getNewQuestion(
                category = categoryState.value.id,
                type = QuestionType.BOOLEAN.value,
                difficulty = difficultyState.value.value
            )
        screenState.value = result
        if (result is ResultOf.Success<QuestionModel>){
            button0ColorState.value = PrimaryBackgroundPink
            button1ColorState.value = PrimaryBackgroundPink
            questionState.value = result.value
        }
    }

    fun checkCorrectAnswer(numberButton: Int) {
        when (numberButton) {
            0 -> {
                if (TRUE == questionState.value.correct_answer) {
                    button0ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    button0ColorState.value = ButtonBackgroundFalse
                }
            }
            1 -> {
                if (FALSE == questionState.value.correct_answer) {
                    button1ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    button1ColorState.value = ButtonBackgroundFalse
                }
            }
        }
    }

    private fun updateQuestion() = viewModelScope.launch(Dispatchers.IO) {
        delay(500)
        getNewQuestion()
    }

    companion object {
        private const val FALSE = "False"
        private const val TRUE = "True"
    }
}