package com.example.popularmovies.data.source.remote.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.api.details.entity.cast.ResponseCastDetails
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.util.dateFromString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseCastDetailsToEntityMapper @Inject constructor() : Function<ResponseCastDetails, PersonDetailsEntity> {

    override fun apply(responseCastDetails: ResponseCastDetails): PersonDetailsEntity {

        return PersonDetailsEntity(
            id = responseCastDetails.id ?: NULLABLE_INT_DEFAULT,
            name = responseCastDetails.name ?: NULLABLE_STRING_DEFAULT,
            birthday = dateFromString(responseCastDetails.birthday),
            deathDay = dateFromString(responseCastDetails.deathday),
            biography = responseCastDetails.biography ?: NULLABLE_STRING_DEFAULT,
            imagePath = responseCastDetails.profilePath ?: NULLABLE_STRING_DEFAULT
        )
    }

    companion object{

        private const val NULLABLE_INT_DEFAULT = -1
        private const val NULLABLE_STRING_DEFAULT = ""
    }
}