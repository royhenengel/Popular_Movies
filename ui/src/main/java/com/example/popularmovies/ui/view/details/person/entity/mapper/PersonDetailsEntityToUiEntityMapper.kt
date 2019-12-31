package com.example.popularmovies.ui.view.details.person.entity.mapper

import androidx.arch.core.util.Function
import com.example.popularmovies.data.entity.cast.PersonDetailsEntity
import com.example.popularmovies.ui.util.dateAsString
import com.example.popularmovies.ui.view.details.person.entity.PersonDetailsUiEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonDetailsEntityToUiEntityMapper @Inject constructor() : Function<PersonDetailsEntity, PersonDetailsUiEntity> {

    override fun apply(personDetailsEntity: PersonDetailsEntity): PersonDetailsUiEntity {

        val birthday = dateAsString(personDetailsEntity.birthday,
            PATTERN_DATE
        ) ?: NULLABLE_STRING_DEFAULT
        val deathDay = dateAsString(personDetailsEntity.deathDay,
            PATTERN_DATE
        ) ?: NULLABLE_STRING_DEFAULT
        val lifeExpectancy = getPersonLifeExpectancy(birthday, deathDay)

        return PersonDetailsUiEntity(
            name = personDetailsEntity.name,
            lifeExpectancy = lifeExpectancy,
            biography = personDetailsEntity.biography,
            imageUrl = "${MOVIES_IMAGE_BASE_URL}${personDetailsEntity.imagePath}"
        )
    }

    private fun getPersonLifeExpectancy(birthday: String, deathDay: String): String {

        return if (deathDay.isBlank()){
            birthday
        }
        else{
            String.format(PATTERN_BIRTH_TO_DEATH, birthday, deathDay)
        }
    }

    companion object{
        private const val MOVIES_IMAGE_BASE_URL = "https://api.themoviedb.org/"

        private const val PATTERN_DATE = "dd/MM/yyyy"

        private const val PATTERN_BIRTH_TO_DEATH = "%s - %s"

        private const val NULLABLE_STRING_DEFAULT = ""
    }

}

