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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BooleanViewModel @Inject constructor(
    private val newQuestionUseCase: NewQuestionUseCase,
    private val checkAnswerUseCase: CheckAnswerUseCase,
) : BaseQuizViewModel(newQuestionUseCase, checkAnswerUseCase){

    private val _buttonTrueColorState = MutableStateFlow(PrimaryBackgroundPink)
    val buttonTrueColorState: StateFlow<Color> = _buttonTrueColorState
    private val _buttonFalseColorState = MutableStateFlow(PrimaryBackgroundPink)
    val buttonFalseColorState: StateFlow<Color> = _buttonFalseColorState

    override fun getColorStateForButton(numberButton: Int): MutableStateFlow<Color> {
        return when (numberButton) {
            0 -> _buttonTrueColorState
            1 -> _buttonFalseColorState
            else -> _buttonTrueColorState
        }
    }

    override fun resetButtonColor() {
        _buttonTrueColorState.value = PrimaryBackgroundPink
        _buttonFalseColorState.value = PrimaryBackgroundPink
    }

    companion object {
        const val FALSE = 1
        const val TRUE = 0
    }
}