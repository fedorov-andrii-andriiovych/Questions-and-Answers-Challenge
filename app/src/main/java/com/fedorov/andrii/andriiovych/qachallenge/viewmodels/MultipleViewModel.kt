package com.fedorov.andrii.andriiovych.qachallenge.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.QuestionDifficulty
import com.fedorov.andrii.andriiovych.qachallenge.model.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.repositories.RetrofitNetworkRepositoryImpl
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundFalse
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundTrue
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MultipleViewModel(private val networkRepository: NetworkRepository = RetrofitNetworkRepositoryImpl()) :
    ViewModel() {
    val button0ColorState = MutableStateFlow(PrimaryBackgroundBox)
    val button1ColorState = MutableStateFlow(PrimaryBackgroundBox)
    val button2ColorState = MutableStateFlow(PrimaryBackgroundBox)
    val button3ColorState = MutableStateFlow(PrimaryBackgroundBox)
    private val type = "multiple"
    val difficultyState = MutableStateFlow(QuestionDifficulty.ANY)
    val questionState = MutableStateFlow(
        QuestionModel(
            "",
            "",
            "",
            "",
            "",
            listOf(),
            listOf("", "", "", "")
        )
    )
    val categoryState = MutableStateFlow(CategoryModel("Nothing", -1))
    fun getNewQuestion() = viewModelScope.launch(Dispatchers.IO) {
        val type: String = type
        val category: Int = categoryState.value.id
        val difficulty: String =
            if (difficultyState.value == QuestionDifficulty.ANY) "" else difficultyState.value.toString()
                .lowercase()
        val newQuestion =
            networkRepository.getNewQuestion(
                category = category,
                type = type,
                difficulty = difficulty
            )
        withContext(Dispatchers.Main) {
            button0ColorState.value = PrimaryBackgroundBox
            button1ColorState.value = PrimaryBackgroundBox
            button2ColorState.value = PrimaryBackgroundBox
            button3ColorState.value = PrimaryBackgroundBox
            questionState.value = newQuestion
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