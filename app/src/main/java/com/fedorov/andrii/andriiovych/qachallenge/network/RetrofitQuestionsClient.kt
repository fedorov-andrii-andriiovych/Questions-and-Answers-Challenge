package com.fedorov.andrii.andriiovych.qachallenge.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitQuestionsClient {

    companion object {
        private const val baseUrl = "https://opentdb.com/"

        fun create(): QuestionServices {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(QuestionServices::class.java)
        }

    }
}