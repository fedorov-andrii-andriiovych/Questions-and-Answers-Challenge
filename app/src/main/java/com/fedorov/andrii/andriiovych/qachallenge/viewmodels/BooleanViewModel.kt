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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BooleanViewModel @Inject constructor( val networkRepository: NetworkRepository) :
    ViewModel() {
    private val type = "boolean"
    val button0ColorState = MutableStateFlow(PrimaryBackgroundBox)
    val button1ColorState = MutableStateFlow(PrimaryBackgroundBox)
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
            questionState.value = newQuestion
        }
    }

    fun checkCorrectAnswer(numberButton: Int) {
        when (numberButton) {
            0 -> {
                if ("True" == questionState.value.correct_answer) {
                    button0ColorState.value = ButtonBackgroundTrue
                    updateQuestion()
                } else {
                    button0ColorState.value = ButtonBackgroundFalse
                }
            }
            1 -> {
                if ("False" == questionState.value.correct_answer) {
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
}