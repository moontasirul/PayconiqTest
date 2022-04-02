package com.payconiq.testApplication.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.payconiq.testApplication.data.remote.ApiEndPoint.Companion.BASE_URL
import com.payconiq.testApplication.data.remote.apiService.IGitHubUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()



    @Singleton
    @Provides
    fun provideGitHubUserService(retrofit: Retrofit): IGitHubUserService =
        retrofit.create(IGitHubUserService::class.java)


    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().addNetworkInterceptor(HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.HEADERS
                    })
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder().addHeader(
                            "Accept",
                            "application/vnd.github.v3+json"
                        ).build()
                        chain.proceed(request)
                    }.build()
            )
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}