package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CheckAnswerParams
import javax.inject.Inject

class CheckAnswerUseCase @Inject constructor() {

    fun checkAnswers(checkAnswerParams: CheckAnswerParams): Boolean {
        return checkAnswerParams.answers[checkAnswerParams.numberButton] == checkAnswerParams.correctAnswer
    }
}