package com.fedorov.andrii.andriiovych.qachallenge.domain.models

data class CheckAnswerParams(
    val answers: List<String>,
    val correctAnswer: String,
    val numberButton: Int
)
