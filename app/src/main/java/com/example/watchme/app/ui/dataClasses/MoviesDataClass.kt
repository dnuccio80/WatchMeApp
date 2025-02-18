package com.example.watchme.app.ui.dataClasses

import com.example.watchme.app.data.network.responses.CastCredit
import com.example.watchme.app.data.network.responses.Collection
import com.example.watchme.app.data.network.responses.CrewCredit
import com.example.watchme.app.data.network.responses.Genres
import com.example.watchme.app.data.network.responses.Movie
import com.example.watchme.app.data.network.responses.ProductionCompanies
import com.google.gson.annotations.SerializedName

data class MovieDataClass(
    val id: Int,
    val genreIds: List<Int>,
    val releaseDate: String,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val voteAverage: Float,
    val voteCount: Int
)

data class DetailsMovieDataClass(
    val adult: Boolean,
    val backdropImage: String?,
    val collection: CollectionDataClass?,
    val budget: Long,
    val genres: List<GenresDataClass>,
    val homepage: String,
    val id: Int,
    val overview: String,
    val productionCompanies: List<ProductionCompaniesDataClass>,
    val releaseDate: String,
    val revenue: Long,
    val title: String,
    val voteAverage: Float,
    val runtime: Int,
)

data class MovieCreditsDataClass(
    val cast: List<CastCreditDataClass>,
    val crew: List<CrewCreditDataClass>
)

data class CastCreditDataClass(
    val id: Int,
    val name: String,
    val gender: Int,
    val profilePath: String?,
    val character: String,
)

data class CrewCreditDataClass(
    val id: Int,
    val department: String,
    val name: String,
    val gender: Int,
    val profilePath: String?,
)

data class GenresDataClass(
    val idGenre: Int,
    val nameGenre: String,
)

data class ProductionCompaniesDataClass(
    val idProductionCompany: Int,
    val logoProductionCompany: String?,
    val nameProductionCompany: String,
    val originCountryProductionCompany: String,
)

data class CollectionDataClass(
    val idCollection: String,
    val nameCollection: String,
    val backdropCollection: String?
)

data class BackdropImageDataClass(
    val filePath: String,
)

data class ReviewDataClass(
    val author: String,
    val authorDetails: AuthorDetailsDataClass,
    val content: String,
    val createdAt: String,
)

data class AuthorDetailsDataClass(
    val name: String,
    val username: String,
    val avatarPath: String?,
    val rating: Float,
)

data class VideoDataClass(
    val name:String,
    val key:String,
    val size:Int,
)