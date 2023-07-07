package com.fedorov.andrii.andriiovych.qachallenge.data.network.models

import com.google.gson.annotations.SerializedName

data class TokenResponse(

	@field:SerializedName("response_code")
	val responseCode: Int,

	@field:SerializedName("response_message")
	val responseMessage: String,

	@field:SerializedName("token")
	val token: String
)
