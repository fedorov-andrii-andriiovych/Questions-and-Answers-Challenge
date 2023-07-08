package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
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
class MultipleViewModel @Inject constructor(private val newQuestionUseCase: NewQuestionUseCase) :
    ViewModel() {
    private val _screenState = MutableStateFlow<ResultOf<QuestionModel>>(ResultOf.Loading)
    val screenState: StateFlow<ResultOf<QuestionModel>> = _screenState
    private val _button0ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button0ColorState: StateFlow<Color> = _button0ColorState
    private val _button1ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button1ColorState: StateFlow<Color> = _button1ColorState
    private val _button2ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button2ColorState: StateFlow<Color> = _button2ColorState
    private val _button3ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button3ColorState: StateFlow<Color> = _button3ColorState
    private val questionState = MutableStateFlow(QuestionModel())

    var categoryState = CategoryModel()
    var difficultyState = QuestionDifficulty.ANY

    fun getNewQuestion() = viewModelScope.launch(Dispatchers.IO) {
        _screenState.value = ResultOf.Loading
        val result =
            newQuestionUseCase.getNewQuestion(
                QuestionParams(
                    category = categoryState.id,
                    type = QuestionType.MULTIPLE.value,
                    difficulty = difficultyState.value
                )
            )
        _screenState.value = result
        if (result is ResultOf.Success<QuestionModel>) {
            questionState.value = result.value
        }
    }

    fun checkCorrectAnswer(numberButton: Int) {
        when (numberButton) {
            0 -> {
                if (questionState.value.answers[0] == questionState.value.correct_answer) {
                    _button0ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    _button0ColorState.value = ButtonBackgroundFalse
                }
            }
            1 -> {
                if (questionState.value.answers[1] == questionState.value.correct_answer) {
                    _button1ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    _button1ColorState.value = ButtonBackgroundFalse
                }
            }
            2 -> {
                if (questionState.value.answers[2] == questionState.value.correct_answer) {
                    _button2ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    _button2ColorState.value = ButtonBackgroundFalse
                }
            }
            3 -> {
                if (questionState.value.answers[3] == questionState.value.correct_answer) {
                    _button3ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    _button3ColorState.value = ButtonBackgroundFalse
                }
            }
        }
    }

    private fun updateQuestion() = viewModelScope.launch(Dispatchers.Default) {
        delay(500)
        resetButtonColor()
        getNewQuestion()
    }

    private fun resetButtonColor(){
        _button0ColorState.value = PrimaryBackgroundPink
        _button1ColorState.value = PrimaryBackgroundPink
        _button2ColorState.value = PrimaryBackgroundPink
        _button3ColorState.value = PrimaryBackgroundPink
    }
}