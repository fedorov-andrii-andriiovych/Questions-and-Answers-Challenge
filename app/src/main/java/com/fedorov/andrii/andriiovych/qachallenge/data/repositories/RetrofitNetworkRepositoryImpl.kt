package com.fedorov.andrii.andriiovych.qachallenge.data.repositories

import android.text.Html
import android.util.Log
import com.fedorov.andrii.andriiovych.qachallenge.data.network.NetworkException
import com.fedorov.andrii.andriiovych.qachallenge.data.network.QuestionResponse
import com.fedorov.andrii.andriiovych.qachallenge.data.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.domain.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.domain.viewmodels.ResultOf
import com.google.gson.Gson
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

private const val SOMETHING_WENT_WRONG = "Something went wrong, please reload the page"

class RetrofitNetworkRepositoryImpl @Inject constructor(private val questionServices: QuestionServices) :
    NetworkRepository {

    override suspend fun getNewQuestion(
        category: Int,
        difficulty: String,
        type: String
    ): ResultOf<QuestionModel> {
        Log.d("TAGGG",type)
        return try {
            val result = parseResponse(
                questionServices.getNewQuestion(
                    category = category,
                    difficulty = difficulty,
                    type = type
                )
            )
            Log.d("TAGGG",result.toString())
            ResultOf.Success(result)
        } catch (e: UnknownHostException) {
            ResultOf.Failure(e.message ?: SOMETHING_WENT_WRONG)
        } catch (e: ConnectException) {
            ResultOf.Failure(e.message ?: SOMETHING_WENT_WRONG)
        } catch (e: Exception) {
            ResultOf.Failure(e.message ?: SOMETHING_WENT_WRONG)
        }
    }

    private fun parseResponse(response: Response<QuestionResponse>): QuestionModel {
        val data: QuestionResponse?

        if (response.isSuccessful)
            data = response.body()
        else
            throw getNetworkException(response)

        if (data != null)
            return mapData(data)
        else
            throw NetworkException(message = SOMETHING_WENT_WRONG)
    }

    private fun mapData(response: QuestionResponse): QuestionModel {
        val questionResponse = response.results[0]
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

    private fun decodeHtmlString(htmlString: String): String {
        return Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    private fun getNetworkException(response: Response<QuestionResponse>): NetworkException {
        return Gson().fromJson(response.errorBody()!!.charStream(), NetworkException::class.java)
    }
}