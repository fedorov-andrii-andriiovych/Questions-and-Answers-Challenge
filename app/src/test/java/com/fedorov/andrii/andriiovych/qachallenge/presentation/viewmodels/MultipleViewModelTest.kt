package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CheckAnswerParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.ResultOfResponse
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.CheckAnswerUseCase
import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewQuestionUseCase
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundFalse
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.ButtonBackgroundTrue
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class MultipleViewModelTest {
    private val newQuestionUseCase = mock<NewQuestionUseCase>()
    private val checkAnswerUseCase = mock<CheckAnswerUseCase>()
    private lateinit var multipleViewModel: MultipleViewModel

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
        multipleViewModel = MultipleViewModel(
            newQuestionUseCase = newQuestionUseCase,
            checkAnswerUseCase = checkAnswerUseCase
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should screen_state = success`() = runTest {
        val testData = ResultOfResponse.Success(value = QuestionModel(question = "test"))
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testData)

        multipleViewModel.getNewQuestion()
        val actual = ScreenState.Success(value = QuestionModel(question = "test"))
        val expected = multipleViewModel.screenState.value

        Assertions.assertEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should screen_state = failure`() = runTest {
        val testData = ResultOfResponse.Failure("test")
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testData)

        multipleViewModel.getNewQuestion()
        val actual = ScreenState.Failure("test")
        val expected = multipleViewModel.screenState.value

        Assertions.assertEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should check the answer and return the true and changed color button2 = colorTrue`()  = runTest{
        val testResult = ResultOfResponse.Success(value = QuestionModel(question = "test", correct_answer = "1", answers = listOf("1")))
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testResult)
        multipleViewModel.getNewQuestion()

        val testNumber = 2
        val testParams = CheckAnswerParams(
            numberButton = testNumber,
            correctAnswer = "1",
            answers = listOf("1")
        )
        val testData = true
        Mockito.`when`(checkAnswerUseCase.checkAnswers(testParams)).thenReturn(testData)
        multipleViewModel.checkCorrectAnswer(testNumber)

        Mockito.verify(checkAnswerUseCase, Mockito.times(1)).checkAnswers(testParams)
        Assertions.assertEquals(multipleViewModel.button2ColorState.value, ButtonBackgroundTrue)
        Assertions.assertEquals(multipleViewModel.button3ColorState.value, PrimaryBackgroundPink)
        Assertions.assertEquals(multipleViewModel.button1ColorState.value, PrimaryBackgroundPink)
        Assertions.assertEquals(multipleViewModel.button0ColorState.value, PrimaryBackgroundPink)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should check the answer and return the false and changed color button2 = colorFalse`()  = runTest{
        val testResult = ResultOfResponse.Success(value = QuestionModel(question = "test", correct_answer = "1", answers = listOf("1")))
        Mockito.`when`(newQuestionUseCase.getNewQuestion(any())).thenReturn(testResult)
        multipleViewModel.getNewQuestion()

        val testNumber = 2
        val testParams = CheckAnswerParams(
            numberButton = testNumber,
            correctAnswer = "1",
            answers = listOf("1")
        )
        val testData = false
        Mockito.`when`(checkAnswerUseCase.checkAnswers(testParams)).thenReturn(testData)
        multipleViewModel.checkCorrectAnswer(testNumber)

        Mockito.verify(checkAnswerUseCase, Mockito.times(1)).checkAnswers(testParams)
        Assertions.assertEquals(multipleViewModel.button2ColorState.value, ButtonBackgroundFalse)
        Assertions.assertEquals(multipleViewModel.button3ColorState.value, PrimaryBackgroundPink)
        Assertions.assertEquals(multipleViewModel.button1ColorState.value, PrimaryBackgroundPink)
        Assertions.assertEquals(multipleViewModel.button0ColorState.value, PrimaryBackgroundPink)
    }
}