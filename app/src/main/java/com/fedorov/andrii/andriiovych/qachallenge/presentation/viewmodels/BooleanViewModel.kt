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
class BooleanViewModel @Inject constructor(private val newQuestionUseCase: NewQuestionUseCase) :
    ViewModel() {
    private val _screenState = MutableStateFlow<ResultOf<QuestionModel>>(ResultOf.Loading)
    val screenState: StateFlow<ResultOf<QuestionModel>> = _screenState
    private val _buttonTrueColorState = MutableStateFlow(PrimaryBackgroundPink)
    val buttonTrueColorState: StateFlow<Color> = _buttonTrueColorState
    private val _buttonFalseColorState = MutableStateFlow(PrimaryBackgroundPink)
    val buttonFalseColorState: StateFlow<Color> = _buttonFalseColorState
    private val questionState = MutableStateFlow(QuestionModel())

    var difficultyState = QuestionDifficulty.ANY
    var categoryState = CategoryModel()
    fun getNewQuestion() = viewModelScope.launch(Dispatchers.IO) {
        _screenState.value = ResultOf.Loading
        val result =
            newQuestionUseCase.getNewQuestion(
                QuestionParams(
                    category = categoryState.id,
                    type = QuestionType.BOOLEAN.value,
                    difficulty = difficultyState.value
                )
            )
        _screenState.value = result
        if (result is ResultOf.Success<QuestionModel>) {
            _buttonTrueColorState.value = PrimaryBackgroundPink
            _buttonFalseColorState.value = PrimaryBackgroundPink
            questionState.value = result.value
        }
    }

    fun checkCorrectAnswer(clickedButton: Boolean) {
        when (clickedButton) {
            true -> {
                if (TRUE == questionState.value.correct_answer) {
                    _buttonTrueColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    _buttonTrueColorState.value = ButtonBackgroundFalse
                }
            }
            false -> {
                if (FALSE == questionState.value.correct_answer) {
                    _buttonFalseColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    _buttonFalseColorState.value = ButtonBackgroundFalse
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