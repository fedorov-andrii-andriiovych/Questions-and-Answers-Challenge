package com.fedorov.andrii.andriiovych.qachallenge.domain.repositories

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams

sealed class ResultOfResponse<out T> {
    data class Success<out R>(val value: R) : ResultOfResponse<R>()
    data class Failure(
        val message: String,
    ) : ResultOfResponse<Nothing>()
}

interface NetworkRepository {

    suspend fun getNewQuestion(questionParams: QuestionParams): ResultOfResponse<QuestionModel>
    suspend fun getNewToken(): Boolean

}