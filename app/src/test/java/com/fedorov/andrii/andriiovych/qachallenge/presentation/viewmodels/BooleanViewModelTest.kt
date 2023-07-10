package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.CheckAnswerUseCase
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class BooleanViewModelTest {

    private val newQuestionUseCase = mock<NewQuestionUseCase>()
    private val checkAnswerUseCase = mock<CheckAnswerUseCase>()
    private val dispatcher = Dispatchers.Unconfined
    private lateinit var booleanViewModel: BooleanViewModel

    @AfterEach
    fun afterEach() {
        Mockito.reset(newQuestionUseCase)
        Mockito.reset(checkAnswerUseCase)
    }

    @BeforeEach
    fun beforeEach() {
        booleanViewModel = BooleanViewModel(
            newQuestionUseCase = newQuestionUseCase,
            checkAnswerUseCase = checkAnswerUseCase,
            dispatcher = dispatcher
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should screen_state = success and question_state = value`() = runTest{
        val testData = ResultOf.Success(value = QuestionModel(question = "test"))
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testData)

        booleanViewModel.getNewQuestion()
        val actual = ResultOf.Success(value = QuestionModel(question = "test"))

        Assertions.assertEquals(booleanViewModel.screenState.value, actual)
        Assertions.assertEquals(booleanViewModel.questionState.value, actual.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should screen_state = failure and question_state = empty`() = runTest{
        val testData = ResultOf.Failure("test")
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testData)

        booleanViewModel.getNewQuestion()
        val actual = ResultOf.Failure("test")
        val questionState = QuestionModel()

        Assertions.assertEquals(booleanViewModel.screenState.value, actual)
        Assertions.assertEquals(booleanViewModel.questionState.value, questionState)
    }
}