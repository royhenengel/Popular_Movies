package com.example.popularmovies.di.module

import android.util.Log
import com.example.popularmovies.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG_LOGGING_INTERCEPTOR = "OkHttp"

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    fun provideRetrofit(
        httpUrl: HttpUrl,
        gsonConverterFactory: GsonConverterFactory,
        rxJavaCallAdapterFactory: RxJava2CallAdapterFactory,
        coroutinesAdapter: CoroutineCallAdapterFactory,
        client: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(httpUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .addCallAdapterFactory(coroutinesAdapter)
            .build()
    }

    @Provides
    @JvmStatic
    fun provideGson(): Gson {

        return GsonBuilder().create()
    }

    @Provides
    @JvmStatic
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {

        return GsonConverterFactory.create(gson)
    }

    @Provides
    @JvmStatic
    fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {

        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @JvmStatic
    fun provideCoroutinesAdapterFactory(): CoroutineCallAdapterFactory {

        return CoroutineCallAdapterFactory()
    }

    @Provides
    @JvmStatic
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @JvmStatic
    fun provideInterceptor(): HttpLoggingInterceptor {

        return HttpLoggingInterceptor { Log.d(TAG_LOGGING_INTERCEPTOR, it) }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @JvmStatic
    fun provideHttpUrl(): HttpUrl {

        return HttpUrl.parse(BuildConfig.MOVIES_BASE_URL)!!
    }

}