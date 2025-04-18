package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.EpisodesDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesRatedDataClass
import com.example.watchme.app.ui.dataClasses.TotalEpisodesRatedDataClass
import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("crew") val crew: List<CrewCredit>,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("guest_stars") val guestStars: List<CastCredit>,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("id") val id: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("still_path") val stillPath: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
)

fun EpisodeResponse.toEpisodesDetailsDataClass(): EpisodesDetailsDataClass {
    return EpisodesDetailsDataClass(
        airDate = airDate,
        crew = crew.map { it.toCrewCreditDataClass() },
        episodeNumber = episodeNumber,
        guestStars = guestStars.map { it.toCastCreditDataClass() },
        name = name,
        overview = overview,
        id = id,
        runtime = runtime,
        seasonNumber = seasonNumber,
        stillPath = stillPath,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

data class EpisodesRatedResponse(
    @SerializedName("results") val results: List<EpisodesRated>,
    @SerializedName("total_results") val totalResults: Int
)

fun EpisodesRatedResponse.toEpisodesRatedDataClass(): List<EpisodesRatedDataClass> {
    return results.map {
        EpisodesRatedDataClass(
            id = it.id,
            showId = it.showId,
            seasonNumber = it.seasonNumber,
            name =  it.name,
            episodeNumber = it.episodeNumber,
            stillPath = it.stillPath,
            rating = it.rating
        )
    }
}

fun EpisodesRatedResponse.toTotalEpisodesRatedDataClass(): TotalEpisodesRatedDataClass {
    return TotalEpisodesRatedDataClass(
        totalResults = totalResults
    )
}

data class EpisodesRated(
    @SerializedName("id") val id: Int,
    @SerializedName("show_id") val showId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("still_path") val stillPath: String,
    @SerializedName("rating") val rating: Float,

)
