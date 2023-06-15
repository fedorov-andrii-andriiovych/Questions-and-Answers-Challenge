package com.fedorov.andrii.andriiovych.qachallenge.repositories

import com.fedorov.andrii.andriiovych.qachallenge.model.QuestionModel

interface NetworkRepository {

   suspend fun getNewQuestion(category: Int,difficulty: String ,type: String) : QuestionModel

}