package com.fedorov.andrii.andriiovych.qachallenge

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.repositories.RetrofitNetworkRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val networkRepository: NetworkRepository = RetrofitNetworkRepositoryImpl()) :
    ViewModel() {
    init {
        getNewQuestionTrueFalse()
    }

    val questionState = mutableStateOf<QuestionModel?>(null)

    fun getNewQuestionTrueFalse(type: String = "boolean") = viewModelScope.launch(Dispatchers.IO) {
        val newQuestion = networkRepository.getNewQuestion()
        questionState.value = newQuestion
    }

    fun getNewQuestionMultipleChoice(type: String = "multiple") =
        viewModelScope.launch(Dispatchers.IO) {
            val newQuestion = networkRepository.getNewQuestion()
            questionState.value = newQuestion
        }

}