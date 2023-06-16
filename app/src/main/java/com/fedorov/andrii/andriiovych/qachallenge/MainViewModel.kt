package com.fedorov.andrii.andriiovych.qachallenge

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.qachallenge.model.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.repositories.RetrofitNetworkRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class QuestionType() {
    MULTIPLE,
    BOOLEAN,
    ANY
}

enum class QuestionDifficulty() {
    EASY,
    MEDIUM,
    HARD,
    ANY
}

class MainViewModel(private val networkRepository: NetworkRepository = RetrofitNetworkRepositoryImpl()) :
    ViewModel() {

    val categories = listOf<CategoryModel>(
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
    val difficultyState = mutableStateOf(QuestionDifficulty.ANY)
    val questionState = mutableStateOf<QuestionModel>(QuestionModel("","","","","", listOf(),))
    val categoryState = mutableStateOf<CategoryModel?>(categories[2])


    fun getNewQuestion() = viewModelScope.launch(Dispatchers.IO) {
        val type: String =
            if (typeState.value == QuestionType.ANY) "" else typeState.value.toString().lowercase()
        val category: Int = categoryState.value!!.id
        val difficulty: String =
            if (difficultyState.value == QuestionDifficulty.ANY) "" else difficultyState.value.toString().lowercase()
        val newQuestion =
            networkRepository.getNewQuestion(
                category = category,
                type = type,
                difficulty = difficulty
            )
        questionState.value = newQuestion
    }

}