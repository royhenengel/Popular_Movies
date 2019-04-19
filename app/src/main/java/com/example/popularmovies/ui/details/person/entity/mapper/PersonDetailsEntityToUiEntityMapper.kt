package com.example.popularmovies.ui.details.person.entity.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.data.details.entity.cast.PersonDetailsEntity
import com.example.popularmovies.data.details.entity.movie.MovieActorInEntity
import com.example.popularmovies.ui.common.scrollingthumbnail.entity.ThumbnailUiEntity
import com.example.popularmovies.ui.details.person.entity.PersonDetailsUiEntity
import com.example.popularmovies.util.dateAsString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonDetailsEntityToUiEntityMapper @Inject constructor() : Function<PersonDetailsEntity, PersonDetailsUiEntity> {

    override fun apply(personDetailsEntity: PersonDetailsEntity): PersonDetailsUiEntity {

        return PersonDetailsUiEntity(
            name = personDetailsEntity.name,
                birthday = dateAsString(personDetailsEntity.birthday, PATTERN_DATE) ?: NULLABLE_STRING_DEFAULT,
                deathDay = dateAsString(personDetailsEntity.deathDay, PATTERN_DATE) ?: NULLABLE_STRING_DEFAULT,
                biography = personDetailsEntity.biography,
                imagePath = personDetailsEntity.imagePath
        )
    }

    companion object{

        private const val PATTERN_DATE = "dd/MM/yyyy"
        private const val NULLABLE_STRING_DEFAULT = ""
    }

}

