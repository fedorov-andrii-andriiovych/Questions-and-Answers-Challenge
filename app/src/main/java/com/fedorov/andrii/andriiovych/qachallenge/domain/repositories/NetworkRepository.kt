package com.fedorov.andrii.andriiovych.qachallenge.domain.repositories

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ResultOf

interface NetworkRepository {

   suspend fun getNewQuestion(category: Int,difficulty: String ,type: String) : ResultOf<QuestionModel>
   suspend fun getNewToken():Boolean

}