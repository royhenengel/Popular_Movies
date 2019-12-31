package com.example.popularmovies.remote.api.details.entity.movie

import com.google.gson.annotations.SerializedName

data class ResponseMovieDetails(

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("video")
    val video: Boolean? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("revenue")
    val revenue: Int? = null,

    @SerializedName("genres")
    val genres: List<ResponseGenresItem?>? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("production_countries")
    val responseProductionCountriesList: List<ResponseProductionCountriesItem?>? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @SerializedName("budget")
    val budget: Int? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("spoken_languages")
    val responseSpokenLanguagesList: List<ResponseSpokenLanguagesItem?>? = null,

    @SerializedName("production_companies")
    val responseProductionCompaniesList: List<ResponseProductionCompaniesItem?>? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("belongs_to_collection")
    val responseBelongsToCollection: ResponseBelongsToCollection? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("adult")
    val adult: Boolean? = null,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("status")
    val status: String? = null

)