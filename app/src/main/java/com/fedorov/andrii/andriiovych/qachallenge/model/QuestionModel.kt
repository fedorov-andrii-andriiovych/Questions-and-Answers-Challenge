package com.fedorov.andrii.andriiovych.qachallenge.model

data class QuestionModel(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>,
    val answers: List<String>
)

