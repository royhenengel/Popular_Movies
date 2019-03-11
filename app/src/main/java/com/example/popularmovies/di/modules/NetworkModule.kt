package com.example.popularmovies.di.modules

import android.util.Log
import com.example.popularmovies.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val TAG_LOGGING_INTERCEPTOR = "OkHttp"

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    fun provideRetrofit(httpUrl: HttpUrl, gsonConverterFactory: GsonConverterFactory, client: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(httpUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @JvmStatic
    fun provideGson(): Gson {

        return GsonBuilder().create()
    }

    @JvmStatic
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {

        return GsonConverterFactory.create(gson)
    }

    @Provides
    @JvmStatic
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @JvmStatic
    fun provideInterceptor(): Interceptor {

        return HttpLoggingInterceptor { Log.d(TAG_LOGGING_INTERCEPTOR, it)}.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @JvmStatic
    fun provideHttpUrl(): HttpUrl? {

        return HttpUrl.parse(BuildConfig.MOVIES_BASE_URL)
    }



}