package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesDataClass
import com.example.watchme.app.ui.dataClasses.GenreDataClass
import com.example.watchme.app.ui.dataClasses.SeasonDataClass
import com.example.watchme.app.ui.dataClasses.SeriesCreatedByDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDetailsDataClass
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
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanies>,
    @SerializedName("seasons") val seasons: List<Season>,
    @SerializedName("type") val type: String,
    @SerializedName("vote_average") val voteAverage: Float,
)

fun SeriesDetailsResponse.toSeriesDetailsDataClass(): SeriesDetailsDataClass {
    return SeriesDetailsDataClass(
        adult = adult,
        backdropPath = backdropPath,
        createdBy = createdBy.map { it.toSeriesCreatedByDataClass() },
        episodeRunTime = episodeRunTime,
        firstAirDate = firstAirDate,
        genres = genres.map { it.toGenresDataClass() },
        homepage = homepage,
        id = id,
        lastEpisodeToAir = lastEpisodeToAir.toEpisodesDataClass(),
        name = name,
        nextEpisodeToAir = nextEpisodeToAir.toEpisodesDataClass(),
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        overview = overview,
        posterPath = posterPath,
        productionCompanies = productionCompanies.map { it.toProductionCompaniesDataClass() },
        seasons = seasons.map { it.toSeasonDataClass() },
        type = type,
        voteAverage = voteAverage,
    )
}

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

fun Season.toSeasonDataClass(): SeasonDataClass {
    return SeasonDataClass(
        airDate = airDate,
        episodeCount = episodeCount,
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        seasonNumber = seasonNumber,
        voteAverage = voteAverage,
    )
}

data class SeasonDetails(
    @SerializedName("episodes") val episodes: List<EpisodeDetails>,
)

fun SeasonDetails.toSeasonDetailsDataClass(): List<EpisodeDetailsDataClass> {
    return episodes.map {
        EpisodeDetailsDataClass(
            episodeNumber = it.episodeNumber,
            id = it.id,
            name = it.name,
            overview = it.overview,
            runtime = it.runtime,
            seasonNumber = it.seasonNumber,
            imagePath = it.imagePath,
            crew = it.crew
        )
    }
}


data class EpisodeDetails(
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("still_path") val imagePath: String,
    @SerializedName("crew") val crew: List<CrewCredit>,
)

fun EpisodeDetails.toEpisodeDataClass(): EpisodeDetailsDataClass {
    return EpisodeDetailsDataClass(
        episodeNumber = episodeNumber,
        id = id,
        name = name,
        overview = overview,
        runtime = runtime,
        seasonNumber = seasonNumber,
        imagePath = imagePath,
        crew = crew
    )
}

data class SeriesCreatedBy(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profilePath: String,
)

fun SeriesCreatedBy.toSeriesCreatedByDataClass(): SeriesCreatedByDataClass {
    return SeriesCreatedByDataClass(
        id = id,
        name = name,
        gender = gender,
        profilePath = profilePath,
    )
}

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)

fun Genre.toGenresDataClass(): GenreDataClass {
    return GenreDataClass(
        id = id,
        name = name,
    )
}

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

fun Episode.toEpisodesDataClass(): EpisodesDataClass {
    return EpisodesDataClass(
        id = id,
        name = name,
        overview = overview,
        voteAverage = voteAverage,
        airDate = airDate,
        episodeNumber = episodeNumber,
        runtime = runtime,
        seasonNumber = seasonNumber,
        showId = showId,
    )
}


data class Network(
    @SerializedName("id") val id: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String,
)

