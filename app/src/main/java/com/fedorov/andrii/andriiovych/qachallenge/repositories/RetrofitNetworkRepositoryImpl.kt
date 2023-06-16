package com.fedorov.andrii.andriiovych.qachallenge.repositories

import android.util.Log
import com.fedorov.andrii.andriiovych.qachallenge.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.network.RetrofitQuestionsClient

class RetrofitNetworkRepositoryImpl(private val questionServices: QuestionServices = RetrofitQuestionsClient.create()) :
    NetworkRepository {

    override suspend fun getNewQuestion(category: Int,difficulty: String ,type: String): QuestionModel {
        val questionResponse = questionServices.getNewQuestion(category = category, difficulty = difficulty, type = type).results[0]
        val answers = mutableListOf<String>()
        answers.addAll(questionResponse.incorrectAnswers)
        answers.add(questionResponse.correctAnswer)
        answers.shuffle()
        Log.d("TAGGG",questionResponse.toString())
        return QuestionModel(
            category = questionResponse.category,
            type = questionResponse.type,
            difficulty = questionResponse.difficulty,
            question = questionResponse.question,
            correct_answer = questionResponse.correctAnswer,
            incorrect_answers = questionResponse.incorrectAnswers,
            answers = answers
        )
    }
}