package com.fedorov.andrii.andriiovych.qachallenge.data.repositories

import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.ErrorEnvelopeMapper
import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.SuccessQuestionMapper
import com.fedorov.andrii.andriiovych.qachallenge.data.mappers.ThrowableMapper
import com.fedorov.andrii.andriiovych.qachallenge.data.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.data.network.UserToken
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.Resource
import com.fedorov.andrii.andriiovych.qachallenge.presentation.di.IoDispatcher
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitNetworkRepositoryImpl @Inject constructor(
    private val questionServices: QuestionServices,
    private val userToken: UserToken,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    NetworkRepository {

    override suspend fun getNewToken(): Boolean = withContext(dispatcher) {
        return@withContext try {
            userToken.token = questionServices.getNewToken().token
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getNewQuestion(
        questionParams: QuestionParams
    ): Flow<Resource<QuestionModel>> {
        return flow {
            val response = questionServices.getNewQuestion(
                category = questionParams.category,
                difficulty = questionParams.difficulty,
                type = questionParams.type,
                token = userToken.token
            )
            response.suspendOnSuccess(SuccessQuestionMapper) {
                emit(Resource.Success(value = this))
            }.suspendOnError(ErrorEnvelopeMapper) {
                emit(Resource.Error<QuestionModel>(error = this))
            }.suspendOnException {
                emit(Resource.Exception<QuestionModel>(exception = ThrowableMapper.map(this)))
            }
        }.flowOn(dispatcher)

    }
}