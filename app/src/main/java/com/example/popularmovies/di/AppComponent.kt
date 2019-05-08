package com.example.popularmovies.di

import android.app.Application
import com.example.popularmovies.MoviesApp
import com.example.popularmovies.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ApiModule::class,
        DataModule::class,
        ViewModelModule::class,
        ActivityBuildersModule::class,
        DataBuildersModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MoviesApp)

}
