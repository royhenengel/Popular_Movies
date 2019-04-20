package com.example.popularmovies

import android.app.Activity
import android.app.Application
import com.example.popularmovies.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class MoviesApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        initDi()
        initTimber()
    }

    override fun activityInjector(): AndroidInjector<Activity> {

        return dispatchingAndroidInjector
    }

    private fun initDi() {

         AppInjector.init(this)
    }

    private fun initTimber() {

        if (BuildConfig.DEBUG) run { Timber.plant(Timber.DebugTree()) }
    }

}