package com.fedorov.andrii.andriiovych.qachallenge.presentation.di

import com.fedorov.andrii.andriiovych.qachallenge.data.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.data.network.UserToken
import com.fedorov.andrii.andriiovych.qachallenge.data.repositories.RetrofitNetworkRepositoryImpl
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindNetworkRepository(retrofitNetworkRepositoryImpl: RetrofitNetworkRepositoryImpl): NetworkRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideQuestionServices(): QuestionServices {
        val baseUrl = "https://opentdb.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
        return retrofit.create(QuestionServices::class.java)
    }

    @Singleton
    @Provides
    fun provideUserToken(): UserToken {
        return UserToken
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher
