package com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels


import com.fedorov.andrii.andriiovych.qachallenge.domain.usecases.NewTokenUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*

class MainViewModelTest {
    private val newTokenUseCase = mock<NewTokenUseCase>()
    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
        Mockito.reset(newTokenUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        mainViewModel = MainViewModel(newTokenUseCase = newTokenUseCase)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return true `() = runTest {
        val testData = true
        Mockito.`when`(newTokenUseCase.getNewToken()).thenReturn(testData)

        mainViewModel.getNewToken()

        Mockito.verify(newTokenUseCase,Mockito.times(2)).getNewToken()
    }
}