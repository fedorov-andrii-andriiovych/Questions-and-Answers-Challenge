package com.fedorov.andrii.andriiovych.qachallenge.data.repositories

import com.fedorov.andrii.andriiovych.qachallenge.data.model.QuestionModel

interface NetworkRepository {

   suspend fun getNewQuestion(category: Int,difficulty: String ,type: String) : QuestionModel

}