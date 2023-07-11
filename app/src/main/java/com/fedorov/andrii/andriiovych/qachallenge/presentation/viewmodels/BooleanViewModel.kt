package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CheckAnswerParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.CheckAnswerUseCase
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import com.fedorov.andrii.andriiovych.qachallenge.presentation.di.IoDispatcher
import com.fedorov.andrii.andriiovych.qachallenge.presentation.di.NetworkModule
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundFalse
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundTrue
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BooleanViewModel @Inject constructor(
    private val newQuestionUseCase: NewQuestionUseCase,
    private val checkAnswerUseCase: CheckAnswerUseCase,
) :
    ViewModel() {
    private val _screenState = MutableStateFlow<ResultOf<QuestionModel>>(ResultOf.Loading)
    val screenState: StateFlow<ResultOf<QuestionModel>> = _screenState
    private val _buttonTrueColorState = MutableStateFlow(PrimaryBackgroundPink)
    val buttonTrueColorState: StateFlow<Color> = _buttonTrueColorState
    private val _buttonFalseColorState = MutableStateFlow(PrimaryBackgroundPink)
    val buttonFalseColorState: StateFlow<Color> = _buttonFalseColorState
    private var difficultyState = QuestionDifficulty.ANY

    var categoryState = CategoryModel()
    fun getNewQuestion() = viewModelScope.launch {
        _screenState.value = ResultOf.Loading
        _screenState.value =
            newQuestionUseCase.getNewQuestion(
                QuestionParams(
                    category = categoryState.id,
                    type = QuestionType.BOOLEAN.value,
                    difficulty = difficultyState.value
                )
            )
    }

    fun checkCorrectAnswer(numberButton: Int) {
        val questionModel = (screenState.value as ResultOf.Success).value
        val result = checkAnswerUseCase.checkAnswers(
            CheckAnswerParams(
                answers = questionModel.answers,
                correctAnswer = questionModel.correct_answer,
                numberButton = numberButton
            )
        )
        val colorState = getColorStateForButton(numberButton)
        if (result) {
            correctAnswer(colorState)
        } else {
            wrongAnswer(colorState)
        }
    }

    private fun getColorStateForButton(numberButton: Int): MutableStateFlow<Color> {
        return when (numberButton) {
            0 -> _buttonTrueColorState
            1 -> _buttonFalseColorState
            else -> _buttonTrueColorState
        }
    }

    private fun correctAnswer(colorState: MutableStateFlow<Color>) {
        colorState.value = ButtonBackgroundTrue
        updateQuestion()
    }

    private fun wrongAnswer(colorState: MutableStateFlow<Color>) {
        colorState.value = ButtonBackgroundFalse
    }

    private fun updateQuestion() = viewModelScope.launch {
        delay(500)
        resetButtonColor()
        getNewQuestion()
    }

    private fun resetButtonColor() {
        _buttonTrueColorState.value = PrimaryBackgroundPink
        _buttonFalseColorState.value = PrimaryBackgroundPink
    }

    companion object {
        const val FALSE = 1
        const val TRUE = 0
    }
}