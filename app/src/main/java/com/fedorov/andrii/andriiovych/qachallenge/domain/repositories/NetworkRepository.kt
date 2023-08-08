package com.fedorov.andrii.andriiovych.qachallenge.domain.repositories

import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.exceptions.BaseException
import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.exceptions.ErrorEnvelope
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import kotlinx.coroutines.flow.Flow

sealed class Resource<T> {
    data class Success<T>(val value: T) : Resource<T>()
    data class Error<T>(val error: ErrorEnvelope) : Resource<T>()
    data class Exception<T>(val exception: BaseException) : Resource<T>()
}


interface NetworkRepository {

    suspend fun getNewQuestion(questionParams: QuestionParams): Flow<Resource<QuestionModel>>
    suspend fun getNewToken(): Boolean

}