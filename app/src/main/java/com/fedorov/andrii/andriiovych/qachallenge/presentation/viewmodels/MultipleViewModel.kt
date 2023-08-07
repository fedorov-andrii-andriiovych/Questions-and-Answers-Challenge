package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels

import androidx.compose.ui.graphics.Color
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.CheckAnswerUseCase
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MultipleViewModel @Inject constructor(
    private val newQuestionUseCase: NewQuestionUseCase,
    private val checkAnswerUseCase: CheckAnswerUseCase,
) : BaseQuizViewModel(newQuestionUseCase, checkAnswerUseCase) {

    private val _button0ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button0ColorState: StateFlow<Color> = _button0ColorState
    private val _button1ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button1ColorState: StateFlow<Color> = _button1ColorState
    private val _button2ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button2ColorState: StateFlow<Color> = _button2ColorState
    private val _button3ColorState = MutableStateFlow(PrimaryBackgroundPink)
    val button3ColorState: StateFlow<Color> = _button3ColorState

    override fun getColorStateForButton(numberButton: Int): MutableStateFlow<Color> {
        return when (numberButton) {
            0 -> _button0ColorState
            1 -> _button1ColorState
            2 -> _button2ColorState
            3 -> _button3ColorState
            else -> _button0ColorState
        }
    }

    override fun resetButtonColor() {
        _button0ColorState.value = PrimaryBackgroundPink
        _button1ColorState.value = PrimaryBackgroundPink
        _button2ColorState.value = PrimaryBackgroundPink
        _button3ColorState.value = PrimaryBackgroundPink
    }
}
