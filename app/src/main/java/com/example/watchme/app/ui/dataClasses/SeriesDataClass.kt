package com.example.watchme.app.ui.dataClasses

import com.example.watchme.app.data.network.responses.CrewCredit

data class SeriesDataClass(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val overview: String,
    val posterPath: String,
    val firstAirDate: String,
    val name: String,
    val voteAverage: Float,
    val voteCount: Int,
)

data class SeriesDetailsDataClass(
    val adult: Boolean,
    val backdropPath: String,
    val createdBy: List<SeriesCreatedByDataClass>,
    val episodeRunTime: List<Int>,
    val firstAirDate: String,
    val genres: List<GenreDataClass>,
    val homepage: String,
    val id: Int,
    val lastEpisodeToAir: EpisodesDataClass,
    val name: String,
    val nextEpisodeToAir: EpisodesDataClass,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val overview: String,
    val posterPath: String,
    val productionCompanies: List<ProductionCompaniesDataClass>,
    val seasons: List<SeasonDataClass>,
    val type: String,
    val voteAverage: Float,
)

data class SeriesCreatedByDataClass(
    val id: Int,
    val name: String,
    val gender: Int,
    val profilePath: String,
)

data class GenreDataClass(
    val id: Int,
    val name: String,
)

data class EpisodeDetailsDataClass(
    val episodeNumber: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val runtime: Int,
    val seasonNumber: Int,
    val imagePath: String?,
    val crew: List<CrewCredit>,
)

data class SeasonDataClass(
    val airDate: String,
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val seasonNumber: Int,
    val voteAverage: Float
)

data class EpisodesDataClass(
    val id: Int,
    val name: String,
    val overview: String,
    val voteAverage: Float,
    val airDate: String,
    val episodeNumber: Int,
    val runtime: Int,
    val seasonNumber: Int,
    val showId: Int,
)

//data class SeriesRecommendation(
//
//)


