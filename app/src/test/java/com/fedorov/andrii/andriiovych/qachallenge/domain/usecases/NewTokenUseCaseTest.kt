package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class NewTokenUseCaseTest {

    companion object{
        private const val TRUE = true
        private const val FALSE = false

    }

    private val networkRepository = mock<NetworkRepository>()

    @AfterEach
    fun tearDown(){
        Mockito.reset(networkRepository)
    }

    @Test
    fun `should return true from repository`()  = runBlocking {

        val testData = TRUE
        Mockito.`when`(networkRepository.getNewToken()).thenReturn(testData)

        val useCase = NewTokenUseCase(networkRepository = networkRepository)
        val actual = useCase.getNewToken()
        val expected = TRUE

        Assertions.assertEquals(actual, expected)
    }

    @Test
    fun `should return false from repository`()  = runBlocking {

        val testData = FALSE
        Mockito.`when`(networkRepository.getNewToken()).thenReturn(testData)

        val useCase = NewTokenUseCase(networkRepository = networkRepository)
        val actual = useCase.getNewToken()
        val expected = FALSE

        Assertions.assertEquals(actual, expected)
    }
}