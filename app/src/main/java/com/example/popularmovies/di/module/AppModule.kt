package com.example.popularmovies.di.module

import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideAppExecutors(): AppExecutors {

        return AppExecutors()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideNetworkIo(appExecutors: AppExecutors): Executor {

        return appExecutors.networkIo
    }

}

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
class AppExecutors(

    val networkIo: Executor = Executors.newFixedThreadPool(3) as Executor

)