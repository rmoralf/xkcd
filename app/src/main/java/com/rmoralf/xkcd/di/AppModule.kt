package com.rmoralf.xkcd.di

import android.content.Context
import com.rmoralf.xkcd.core.utils.Constants.API_ENDPOINT
import com.rmoralf.xkcd.core.utils.Constants.CACHE_SIZE
import com.rmoralf.xkcd.data.network.remote.ComicsService
import com.rmoralf.xkcd.data.repository.ComicsRepositoryImpl
import com.rmoralf.xkcd.domain.repository.ComicsRepository
import com.rmoralf.xkcd.domain.usecases.GetComic
import com.rmoralf.xkcd.domain.usecases.GetLatestComic
import com.rmoralf.xkcd.domain.usecases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideComicRepository(
        comicsService: ComicsService
    ): ComicsRepository = ComicsRepositoryImpl(comicsService)

    @Provides
    fun provideComicsService(restClient: Retrofit): ComicsService =
        restClient.create()

    @Provides
    fun providesUseCases(
        comicsRepository: ComicsRepository
    ) = UseCases(
        getLatestComic = GetLatestComic(comicsRepository),
        getComic = GetComic(comicsRepository)
    )

    //Retrofit2
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .cache(Cache(context.cacheDir, CACHE_SIZE))
        .build()

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
}