package com.fedorov.andrii.andriiovych.qachallenge.data.mappers.exceptions

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class BaseException()

data class NetworkException(@StringRes val value: Int) : BaseException()
data class UnknownException(@StringRes val value: Int) : BaseException()
data class RequestException(val value: String) : BaseException()
data class ErrorEnvelope(val code: Int, val message: String) : BaseException()

@Composable
fun BaseException.message() = when (this) {
    is NetworkException -> stringResource(id = this.value)
    is UnknownException -> stringResource(id = this.value)
    is RequestException -> this.value
    is ErrorEnvelope -> "${this.code} ${this.message}"
}


