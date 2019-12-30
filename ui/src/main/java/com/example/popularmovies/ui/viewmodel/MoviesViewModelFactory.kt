package com.example.popularmovies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class MoviesViewModelFactory @Inject constructor(

    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val provider = creators[modelClass]
            ?: creators.asIterable()
                .firstOrNull { modelClass.isAssignableFrom(it.key) }
                ?.value
            ?: throw IllegalArgumentException("Unknown model class: $modelClass")

        try {
            @Suppress("UNCHECKED_CAST") val model = provider.get() as T

            Timber.d("factory: ${this}, key: $modelClass, provider: $provider, model: $model")

            return model
        }
        catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}

