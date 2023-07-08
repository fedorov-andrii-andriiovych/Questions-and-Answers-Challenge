package com.fedorov.andrii.andriiovych.qachallenge.domain.repositories

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ResultOf

interface NetworkRepository {

    suspend fun getNewQuestion(questionParams: QuestionParams): ResultOf<QuestionModel>
    suspend fun getNewToken(): Boolean

}