package com.fedorov.andrii.andriiovych.qachallenge.repositories

import android.text.Html
import android.util.Log
import com.fedorov.andrii.andriiovych.qachallenge.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.network.RetrofitQuestionsClient
import javax.inject.Inject

class RetrofitNetworkRepositoryImpl @Inject constructor(private val questionServices: QuestionServices) :
    NetworkRepository {

    override suspend fun getNewQuestion(category: Int,difficulty: String ,type: String): QuestionModel {
        val questionResponse = questionServices.getNewQuestion(category = category, difficulty = difficulty, type = type).results[0]
        val incorrectAnswers = questionResponse.incorrectAnswers.map { decodeHtmlString(it) }
        val correctAnswer = decodeHtmlString(questionResponse.correctAnswer)
        val answers = mutableListOf<String>()
        answers.addAll(incorrectAnswers)
        answers.add(correctAnswer)
        answers.shuffle()
        return QuestionModel(
            category = questionResponse.category,
            type = questionResponse.type,
            difficulty = questionResponse.difficulty,
            question = decodeHtmlString(questionResponse.question),
            correct_answer = correctAnswer,
            incorrect_answers = incorrectAnswers,
            answers = answers
        )
    }
    fun decodeHtmlString(htmlString: String): String {
        return Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString()
    }
}