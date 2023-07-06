package com.fedorov.andrii.andriiovych.qachallenge.domain.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.domain.model.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R) : ResultOf<R>()
    data class Failure(
        val message: String,
    ) : ResultOf<Nothing>()

    object Loading : ResultOf<Nothing>()
}

enum class QuestionType(val value: String) {
    MULTIPLE("multiple"),
    BOOLEAN("boolean"),
    ANY("")
}

enum class QuestionDifficulty(val value: String) {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard"),
    ANY("")
}

@HiltViewModel
class MainViewModel @Inject constructor(private val newTokenUseCase: NewTokenUseCase) :
    ViewModel() {
    init {
        getNewToken()
    }

    val categories = listOf(
        CategoryModel("General Knowledge", 9),
        CategoryModel("Books", 10),
        CategoryModel("Film", 11),
        CategoryModel("Music", 12),
        CategoryModel("Musicals & Theatres", 13),
        CategoryModel("Television", 14),
        CategoryModel("Video Games", 15),
        CategoryModel("Board Games", 16),
        CategoryModel("Science & Nature", 17),
        CategoryModel("Computers", 18),
        CategoryModel("Mathematics", 19),
        CategoryModel("Mythology", 20),
        CategoryModel("Sports", 21),
        CategoryModel("Geography", 22),
        CategoryModel("History", 23),
        CategoryModel("Politics", 24),
        CategoryModel("Art", 25),
        CategoryModel("Celebrities", 26),
        CategoryModel("Animals", 27),
        CategoryModel("Vehicles", 28),
        CategoryModel("Comics", 29),
        CategoryModel("Gadgets", 30),
        CategoryModel("Japanese Anime & Manga", 31),
        CategoryModel("Cartoon & Animations", 32),
    )
    val typeState = mutableStateOf(QuestionType.ANY)

    private fun getNewToken() = viewModelScope.launch (Dispatchers.IO) {
        newTokenUseCase.getNewToken()
    }

}