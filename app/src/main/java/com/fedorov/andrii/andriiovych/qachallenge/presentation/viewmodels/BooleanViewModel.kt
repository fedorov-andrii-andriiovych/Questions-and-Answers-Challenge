package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundFalse
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundTrue
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooleanViewModel @Inject constructor(private val newQuestionUseCase: NewQuestionUseCase) :
    ViewModel() {
    private val _screenState = MutableStateFlow<ResultOf<QuestionModel>>(ResultOf.Loading)
    val screenState:StateFlow<ResultOf<QuestionModel>> = _screenState
    private val _button0ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button0ColorState:StateFlow<Color> = _button0ColorState
    private val _button1ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button1ColorState:StateFlow<Color> = _button1ColorState
    private val difficultyState = MutableStateFlow(QuestionDifficulty.ANY)
    val questionState = MutableStateFlow(QuestionModel())
    val categoryState = MutableStateFlow(CategoryModel())
    fun getNewQuestion() = viewModelScope.launch(Dispatchers.IO) {
        _screenState.value = ResultOf.Loading
        val result =
            newQuestionUseCase.getNewQuestion(
                category = categoryState.value.id,
                type = QuestionType.BOOLEAN.value,
                difficulty = difficultyState.value.value
            )
        _screenState.value = result
        if (result is ResultOf.Success<QuestionModel>){
            _button0ColorState.value = PrimaryBackgroundPink
            _button1ColorState.value = PrimaryBackgroundPink
            questionState.value = result.value
        }
    }

    fun checkCorrectAnswer(numberButton: Int) {
        when (numberButton) {
            0 -> {
                if (TRUE == questionState.value.correct_answer) {
                    _button0ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    _button0ColorState.value = ButtonBackgroundFalse
                }
            }
            1 -> {
                if (FALSE == questionState.value.correct_answer) {
                    _button1ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                   _button1ColorState.value = ButtonBackgroundFalse
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