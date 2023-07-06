package com.fedorov.andrii.andriiovych.qachallenge.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface QuestionServices {

    @GET("api.php")
    suspend fun getNewQuestion(
        @Query("amount") amount: Int = 1,
        @Query("category") category: Int ,
        @Query("difficulty") difficulty: String = "",
        @Query("type") type: String = ""
    ): Response<QuestionResponse>
}