package com.fedorov.andrii.andriiovych.qachallenge.data.repositories

import android.text.Html
import com.fedorov.andrii.andriiovych.qachallenge.data.network.NetworkException
import com.fedorov.andrii.andriiovych.qachallenge.data.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.data.network.UserToken
import com.fedorov.andrii.andriiovych.qachallenge.data.network.models.QuestionResponse
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.presentation.di.IoDispatcher
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ResultOf
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

private const val SOMETHING_WENT_WRONG = "Something went wrong, please reload the page"

class RetrofitNetworkRepositoryImpl @Inject constructor(
    private val questionServices: QuestionServices,
    private val userToken: UserToken,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    NetworkRepository {

    override suspend fun getNewToken(): Boolean {
        return try {
            userToken.token = questionServices.getNewToken().token
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getNewQuestion(
        questionParams: QuestionParams
    ): ResultOf<QuestionModel> = withContext(dispatcher) {
        return@withContext try {
            val result = parseResponse(
                questionServices.getNewQuestion(
                    category = questionParams.category,
                    difficulty = questionParams.difficulty,
                    type = questionParams.type,
                    token = userToken.token
                )
            )
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

    private fun getNetworkException(response: Response<QuestionResponse>): NetworkException {
        return Gson().fromJson(response.errorBody()!!.charStream(), NetworkException::class.java)
    }
}