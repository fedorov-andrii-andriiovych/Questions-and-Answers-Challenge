package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ResultOfScreen
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class NewQuestionUseCaseTest {
    companion object{
        private const val ERROR = "Error"
        private const val HARD = "Hard"
        private const val BOOLEAN = "Boolean"
        private const val ONE = "One"

    }

    private val networkRepository = mock<NetworkRepository>()

    @AfterEach
    fun tearDown(){
        Mockito.reset(networkRepository)
    }

    @Test
     fun `should return the same data from repository`()  = runBlocking {

        val testData = ResultOfScreen.Success(
            value = QuestionModel(
                category = ONE,
                difficulty = HARD,
                type = BOOLEAN
            )
        )
        Mockito.`when`(networkRepository.getNewQuestion(QuestionParams())).thenReturn(testData)

        val useCase = NewQuestionUseCase(networkRepository = networkRepository)

        val result = useCase.getNewQuestion(QuestionParams()) as ResultOfScreen.Success
        val actual = result.value
        val expected = QuestionModel(category = ONE, difficulty = HARD, type = BOOLEAN)

        Assertions.assertEquals(actual, expected)
    }

    @Test
    fun `should return error from repository`()  = runBlocking {

        val testData = ResultOfScreen.Failure(message = ERROR)
        Mockito.`when`(networkRepository.getNewQuestion(QuestionParams())).thenReturn(testData)

        val useCase = NewQuestionUseCase(networkRepository = networkRepository)

        val result = useCase.getNewQuestion(QuestionParams()) as ResultOfScreen.Failure
        val actual = result.message
        val expected = ERROR

        Assertions.assertEquals(actual, expected)
    }
}
