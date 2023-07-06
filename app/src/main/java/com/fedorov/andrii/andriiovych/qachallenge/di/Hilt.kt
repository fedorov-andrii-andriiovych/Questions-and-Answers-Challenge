package com.fedorov.andrii.andriiovych.qachallenge.di

import com.fedorov.andrii.andriiovych.qachallenge.data.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.data.repositories.RetrofitNetworkRepositoryImpl
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            .build()
        return retrofit.create(QuestionServices::class.java)
    }
}