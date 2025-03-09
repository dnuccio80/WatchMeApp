package com.example.watchme.app.ui.dataClasses

import com.example.watchme.app.data.network.responses.CastCredit
import com.example.watchme.app.data.network.responses.CrewCredit

data class EpisodesDetailsDataClass (
    val airDate:String?,
    val crew:List<CrewCreditDataClass>,
    val episodeNumber:Int,
    val guestStars:List<CastCreditDataClass>,
    val name:String,
    val overview:String?,
    val id:Int,
    val runtime:Int,
    val seasonNumber:Int,
    val stillPath:String?,
    val voteAverage:Float?,
    val voteCount:Int?,
)

data class EpisodesRatedDataClass(
    val id: Int,
    val showId: Int,
    val name: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val stillPath: String?,
)