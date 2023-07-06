package com.fedorov.andrii.andriiovych.qachallenge.domain.repositories

import com.fedorov.andrii.andriiovych.qachallenge.domain.model.QuestionModel

interface NetworkRepository {

   suspend fun getNewQuestion(category: Int,difficulty: String ,type: String) : QuestionModel

}