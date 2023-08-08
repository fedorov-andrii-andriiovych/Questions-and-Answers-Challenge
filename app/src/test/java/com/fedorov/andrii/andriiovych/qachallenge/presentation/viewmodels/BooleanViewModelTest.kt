package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CheckAnswerParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.ResultOfResponse
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.CheckAnswerUseCase
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundFalse
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundTrue
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink
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
    private lateinit var booleanViewModel: BooleanViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
        Mockito.reset(newQuestionUseCase)
        Mockito.reset(checkAnswerUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        booleanViewModel = BooleanViewModel(
            newQuestionUseCase = newQuestionUseCase,
            checkAnswerUseCase = checkAnswerUseCase
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should screen_state = success`() = runTest {
        val testData = ResultOfResponse.Success(value = QuestionModel(question = "test"))
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testData)

        booleanViewModel.getNewQuestion()
        val actual = ScreenState.Success(value = QuestionModel(question = "test"))
        val expected = booleanViewModel.screenState.value

        Assertions.assertEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should screen_state = failure`() = runTest {
        val testData = ResultOfResponse.Failure("test")
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testData)

        booleanViewModel.getNewQuestion()
        val actual = ScreenState.Error("test")
        val expected = booleanViewModel.screenState.value

        Assertions.assertEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should check the answer and return the true and changed color buttonTrue = colorTrue`()  = runTest{
        val testResult = ResultOfResponse.Success(value = QuestionModel(question = "test", correct_answer = "1", answers = listOf("1")))
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testResult)
        booleanViewModel.getNewQuestion()

        val testNumber = 0
        val testParams = CheckAnswerParams(
            numberButton = testNumber,
            correctAnswer = "1",
            answers = listOf("1")
        )
        val testData = true
        Mockito.`when`(checkAnswerUseCase.checkAnswers(testParams)).thenReturn(testData)
        booleanViewModel.checkCorrectAnswer(testNumber)

        Mockito.verify(checkAnswerUseCase, Mockito.times(1)).checkAnswers(testParams)
        Assertions.assertEquals(booleanViewModel.buttonTrueColorState.value, ButtonBackgroundTrue)
        Assertions.assertEquals(booleanViewModel.buttonFalseColorState.value, PrimaryBackgroundPink)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should check the answer and return the false and changed color buttonTrue = colorFalse`()  = runTest{
        val testResult = ResultOfResponse.Success(value = QuestionModel(question = "test", correct_answer = "1", answers = listOf("1")))
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testResult)
        booleanViewModel.getNewQuestion()

        val testNumber = 0
        val testParams = CheckAnswerParams(
            numberButton = testNumber,
            correctAnswer = "1",
            answers = listOf("1")
        )
        val testData = false
        Mockito.`when`(checkAnswerUseCase.checkAnswers(testParams)).thenReturn(testData)
        booleanViewModel.checkCorrectAnswer(testNumber)

        Mockito.verify(checkAnswerUseCase, Mockito.times(1)).checkAnswers(testParams)
        Assertions.assertEquals(booleanViewModel.buttonTrueColorState.value, ButtonBackgroundFalse)
        Assertions.assertEquals(booleanViewModel.buttonFalseColorState.value, PrimaryBackgroundPink)
    }
}