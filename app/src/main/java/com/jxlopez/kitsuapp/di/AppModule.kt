package com.jxlopez.kitsuapp.di

import com.jxlopez.kitsuapp.data.api.KitsuApi
import com.jxlopez.kitsuapp.data.datasource.remote.AnimeRemoteDataSource
import com.jxlopez.kitsuapp.data.datasource.remote.AnimeRemoteDataSourceImpl
import com.jxlopez.kitsuapp.data.datasource.repository.AnimeRepository
import com.jxlopez.kitsuapp.data.datasource.repository.AnimeRepositoryImpl
import com.jxlopez.kitsuapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    @Named("logging")
    internal fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(@Named("logging") httpLoggingInterceptor: Interceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideAnimeRemoteDataSource(animeRemoteDataSourceImpl: AnimeRemoteDataSourceImpl): AnimeRemoteDataSource = animeRemoteDataSourceImpl

    @Provides
    @Singleton
    fun provideAnimeRepository(animeRepositoryImpl: AnimeRepositoryImpl): AnimeRepository = animeRepositoryImpl

    @Singleton
    @Provides
    fun provideKitsuApi(retrofit: Retrofit): KitsuApi =
        retrofit.create(KitsuApi::class.java)
}