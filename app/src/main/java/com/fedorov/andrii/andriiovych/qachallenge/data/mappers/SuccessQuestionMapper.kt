package com.fedorov.andrii.andriiovych.qachallenge.data.mappers

import android.text.Html
import com.fedorov.andrii.andriiovych.qachallenge.data.network.models.QuestionResponse
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ApiSuccessModelMapper
import javax.inject.Singleton

@Singleton
object SuccessQuestionMapper : ApiSuccessModelMapper<QuestionResponse, QuestionModel> {

    override fun map(apiSuccessResponse: ApiResponse.Success<QuestionResponse>): QuestionModel {
        val questionResponse = apiSuccessResponse.data.results[0]
        val incorrectAnswers = questionResponse.incorrectAnswers.map { decodeHtmlString(it) }
        val correctAnswer = decodeHtmlString(questionResponse.correctAnswer)
        val answers = mutableListOf<String>().apply {
            addAll(incorrectAnswers)
            add(correctAnswer)
            shuffle()
        }
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
    private fun decodeHtmlString(htmlString: String): String {
        return Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString()
    }
}