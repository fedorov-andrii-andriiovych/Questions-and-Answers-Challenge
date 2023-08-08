package com.fedorov.andrii.andriiovych.qachallenge.data.mappers

import com.fedorov.andrii.andriiovych.qachallenge.R
import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.exceptions.BaseException
import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.exceptions.NetworkException
import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.exceptions.RequestException
import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.exceptions.UnknownException
import com.skydoves.sandwich.ApiResponse
import java.io.IOException
import javax.inject.Singleton

@Singleton
object ThrowableMapper {

    fun map(throwable: ApiResponse.Failure.Exception<*>): BaseException {
        if (throwable.exception is IOException)
            return NetworkException(value = R.string.network_exception)
        val message = throwable.exception.message
            ?: return UnknownException(value = R.string.unknown_exception)
        return RequestException(value = message)
    }
}