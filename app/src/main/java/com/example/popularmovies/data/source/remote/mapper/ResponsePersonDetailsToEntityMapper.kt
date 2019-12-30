package com.example.popularmovies.data.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.cast.ResponsePersonDetails
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.ui.util.dateFromString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponsePersonDetailsToEntityMapper @Inject constructor() : Function<ResponsePersonDetails, PersonDetailsEntity> {

    override fun apply(responsePersonDetails: ResponsePersonDetails): PersonDetailsEntity {

        return PersonDetailsEntity(
            id = responsePersonDetails.id ?: NULLABLE_INT_DEFAULT,
            name = responsePersonDetails.name ?: NULLABLE_STRING_DEFAULT,
            birthday = com.example.popularmovies.ui.util.dateFromString(
                responsePersonDetails.birthday
            ),
            deathDay = com.example.popularmovies.ui.util.dateFromString(
                responsePersonDetails.deathday
            ),
            biography = responsePersonDetails.biography ?: NULLABLE_STRING_DEFAULT,
            imagePath = responsePersonDetails.profilePath ?: NULLABLE_STRING_DEFAULT,
            imdbId = responsePersonDetails.imdbId ?: NULLABLE_STRING_DEFAULT
        )
    }

    companion object{

        private const val NULLABLE_INT_DEFAULT = -1
        private const val NULLABLE_STRING_DEFAULT = ""
    }
}