package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.EpisodesDetailsDataClass
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
