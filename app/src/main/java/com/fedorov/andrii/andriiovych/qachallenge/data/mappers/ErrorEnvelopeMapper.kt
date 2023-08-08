package com.fedorov.andrii.andriiovych.qachallenge.data.mappers

import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.exceptions.ErrorEnvelope
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import javax.inject.Singleton

@Singleton
object ErrorEnvelopeMapper : ApiErrorModelMapper<ErrorEnvelope> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorEnvelope {
        return ErrorEnvelope(apiErrorResponse.statusCode.code, apiErrorResponse.message())
    }
}