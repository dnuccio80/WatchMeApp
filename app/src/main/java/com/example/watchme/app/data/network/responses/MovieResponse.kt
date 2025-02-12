package com.example.watchme.app.data.network.responses

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val result: List<Movie>
)

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("backdrop_path") val backdrop: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int
)

data class DetailsMovieResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropImage: String,
    @SerializedName("belongs_to_collection") val collection: Collection,
    @SerializedName("budget") val budget: Long,
    @SerializedName("genres") val genres: List<Genres>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanies>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("revenue") val revenue: Long,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("runtime") val runtime: Int,
)

data class Collection(
    @SerializedName("id") val idCollection: String,
    @SerializedName("name") val nameCollection: String,
    @SerializedName("poster_path") val posterCollection: String,
    @SerializedName("backdrop_path") val backdropCollection: String,
)

data class Genres(
    @SerializedName("id") val idGenre: Int,
    @SerializedName("name") val nameGenre: String,
)

data class ProductionCompanies(
    @SerializedName("id") val idProductionCompany: Int,
    @SerializedName("logo_path") val logoProductionCompany: String,
    @SerializedName("name") val nameProductionCompany: String,
    @SerializedName("origin_country") val originCountryProductionCompany: String,
)

data class MovieCreditsResponse(
    @SerializedName("cast") val cast: List<CastCredit>,
    @SerializedName("crew") val crew: List<CrewCredit>
)

data class CastCredit(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("character") val character: String,
)

data class CrewCredit(
    @SerializedName("id") val id: Int,
    @SerializedName("department") val department:String,
    @SerializedName("name") val name:String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profilePath:String,
    @SerializedName("job") val job:String,
)

data class ImageBackdrop(
    @SerializedName("backdrops") val backdrops: List<Backdrop>
)

data class Backdrop(
    @SerializedName("file_path") val filePath: String,
)

