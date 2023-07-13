package com.fedorov.andrii.andriiovych.qachallenge.data.repositories

import android.text.Html
import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.QuestionResponseMapper
import com.fedorov.andrii.andriiovych.qachallenge.data.network.NetworkException
import com.fedorov.andrii.andriiovych.qachallenge.data.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.data.network.UserToken
import com.fedorov.andrii.andriiovych.qachallenge.data.network.models.QuestionResponse
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.ResultOfResponse
import com.fedorov.andrii.andriiovych.qachallenge.presentation.di.IoDispatcher
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
    private val questionResponseMapper: QuestionResponseMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    NetworkRepository {

    override suspend fun getNewToken(): Boolean = withContext(dispatcher){
        return@withContext try {
            userToken.token = questionServices.getNewToken().token
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getNewQuestion(
        questionParams: QuestionParams
    ): ResultOfResponse<QuestionModel> = withContext(dispatcher) {
        return@withContext try {
            val result = parseResponse(
                questionServices.getNewQuestion(
                    category = questionParams.category,
                    difficulty = questionParams.difficulty,
                    type = questionParams.type,
                    token = userToken.token
                )
            )
            ResultOfResponse.Success(result)
        } catch (e: UnknownHostException) {
            ResultOfResponse.Failure(e.message ?: SOMETHING_WENT_WRONG)
        } catch (e: ConnectException) {
            ResultOfResponse.Failure(e.message ?: SOMETHING_WENT_WRONG)
        } catch (e: Exception) {
            ResultOfResponse.Failure(e.message ?: SOMETHING_WENT_WRONG)
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
        return questionResponseMapper.mapData(response = response)
    }

    private fun getNetworkException(response: Response<QuestionResponse>): NetworkException {
        return Gson().fromJson(response.errorBody()!!.charStream(), NetworkException::class.java)
    }
}