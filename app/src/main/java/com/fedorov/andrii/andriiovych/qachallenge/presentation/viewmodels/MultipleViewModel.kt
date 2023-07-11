package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CheckAnswerParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.ResultOfResponse
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.CheckAnswerUseCase
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundFalse
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundTrue
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultipleViewModel @Inject constructor(
    private val newQuestionUseCase: NewQuestionUseCase,
    private val checkAnswerUseCase: CheckAnswerUseCase,
) :
    ViewModel() {
    private val _screenState =
        MutableStateFlow<ScreenState<QuestionModel>>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState<QuestionModel>> = _screenState
    private val _button0ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button0ColorState: StateFlow<Color> = _button0ColorState
    private val _button1ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button1ColorState: StateFlow<Color> = _button1ColorState
    private val _button2ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button2ColorState: StateFlow<Color> = _button2ColorState
    private val _button3ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button3ColorState: StateFlow<Color> = _button3ColorState
    private var difficultyState = QuestionDifficulty.ANY
    var categoryState = CategoryModel()


    fun getNewQuestion() = viewModelScope.launch {
        _screenState.value = ScreenState.Loading
        val result =
            newQuestionUseCase.getNewQuestion(
                QuestionParams(
                    category = categoryState.id,
                    type = QuestionType.MULTIPLE.value,
                    difficulty = difficultyState.value
                )
            )
        when (result) {
            is ResultOfResponse.Success<QuestionModel> -> _screenState.value =
                ScreenState.Success(value = result.value)
            is ResultOfResponse.Failure -> _screenState.value =
                ScreenState.Failure(message = result.message)
        }
    }

    fun checkCorrectAnswer(numberButton: Int) {
        val questionModel = (screenState.value as ScreenState.Success).value
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
            0 -> _button0ColorState
            1 -> _button1ColorState
            2 -> _button2ColorState
            3 -> _button3ColorState
            else -> _button0ColorState
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
        _button0ColorState.value = PrimaryBackgroundPink
        _button1ColorState.value = PrimaryBackgroundPink
        _button2ColorState.value = PrimaryBackgroundPink
        _button3ColorState.value = PrimaryBackgroundPink
    }

    companion object {
        const val BUTTON_0 = 0
        const val BUTTON_1 = 1
        const val BUTTON_2 = 2
        const val BUTTON_3 = 3
    }
}