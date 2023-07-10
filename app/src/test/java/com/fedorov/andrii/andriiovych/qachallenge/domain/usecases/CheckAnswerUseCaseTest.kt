package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CheckAnswerParams
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CheckAnswerUseCaseTest {

    @Test
    fun `should return true`() {
        val testParams = CheckAnswerParams(
            answers = listOf("1", "2", "3", "4"),
            correctAnswer = "2",
            numberButton = 1
        )
        val checkAnswerUseCase = CheckAnswerUseCase()
        val actual = checkAnswerUseCase.checkAnswers(checkAnswerParams = testParams)
        val expected = true

        Assertions.assertEquals(actual,expected)
    }

    @Test
    fun `should return false`() {
        val testParams = CheckAnswerParams(
            answers = listOf("1", "2", "3", "4"),
            correctAnswer = "2",
            numberButton = 2
        )
        val checkAnswerUseCase = CheckAnswerUseCase()
        val actual = checkAnswerUseCase.checkAnswers(checkAnswerParams = testParams)
        val expected = false

        Assertions.assertEquals(actual,expected)
    }
}