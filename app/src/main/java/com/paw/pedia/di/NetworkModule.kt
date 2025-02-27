package com.paw.pedia.di

import com.paw.pedia.data.local.LocalDataSource
import com.paw.pedia.data.local.LocalSource
import com.paw.pedia.data.local.persistence.DogBreedsDao
import com.paw.pedia.data.remote.RemoteDataSource
import com.paw.pedia.data.remote.RemoteSource
import com.paw.pedia.data.remote.service.DogBreedsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideDogBreedsService(retrofit: Retrofit): DogBreedsApi {
        return retrofit.create(DogBreedsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(api: DogBreedsApi): RemoteSource {
        return RemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(dao: DogBreedsDao): LocalSource {
        return LocalDataSource(dao)
    }
}
