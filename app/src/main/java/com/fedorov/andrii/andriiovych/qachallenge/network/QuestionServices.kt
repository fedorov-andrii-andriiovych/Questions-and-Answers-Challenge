package com.fedorov.andrii.andriiovych.qachallenge.network

import retrofit2.http.GET
import retrofit2.http.Query


interface QuestionServices {

    @GET("api.php")
    suspend fun getNewQuestion(
        @Query("amount") amount: Int = 1,
        @Query("category") category: Int = 27,
        @Query("difficulty") difficulty: String = "easy",
        @Query("type") type: String = "boolean"
    ): QuestionResponse
}