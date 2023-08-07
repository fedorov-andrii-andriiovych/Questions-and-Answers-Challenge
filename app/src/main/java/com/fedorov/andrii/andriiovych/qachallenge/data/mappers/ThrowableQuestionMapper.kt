package com.fedorov.andrii.andriiovych.qachallenge.data.mappers

import com.skydoves.sandwich.ApiResponse
import java.io.IOException
import javax.inject.Singleton

@Singleton
object ThrowableQuestionMapper {

    fun map(throwable: ApiResponse.Failure.Exception<*>): String {
        if (throwable.exception is IOException) return "You are not connected to internet, Please try again."
        return throwable.message ?: "Unknown exception. Try again later."
    }
}