package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    @SerializedName("results") val results: List<Series>
)

data class Series(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("name") val name: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
)

fun SeriesResponse.toSeriesDataClass(): List<SeriesDataClass> {
    return results.map {
        SeriesDataClass(
            adult = it.adult,
            backdropPath = it.backdropPath,
            genreIds = it.genreIds,
            id = it.id,
            overview = it.overview,
            posterPath = it.posterPath,
            firstAirDate = it.firstAirDate,
            name = it.name,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

data class SeriesDetailsResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("created_by") val createdBy: List<SeriesCreatedBy>,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: Episode,
    @SerializedName("name") val name: String,
    @SerializedName("next_episode_to_air") val nextEpisodeToAir: Episode,
    @SerializedName("networks") val networks: List<Episode>,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanies>,
    @SerializedName("seasons") val seasons: List<Season>,
    @SerializedName("type") val type: String,
    @SerializedName("vote_average") val voteAverage: Float,
)

data class Season(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode_count") val episodeCount: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("vote_average") val voteAverage: Float,

)

data class SeasonDetails(
    @SerializedName("episodes") val episodes: List<Episodes>,
)

data class Episodes(
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("still_path") val imagePath: String,
    @SerializedName("crew") val crew: List<CrewCredit>,
)

data class SeriesCreatedBy(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profilePath: String,
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)

data class Episode(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("show_id") val showId: Int,
)

data class Network(
    @SerializedName("id") val id: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String,
)

