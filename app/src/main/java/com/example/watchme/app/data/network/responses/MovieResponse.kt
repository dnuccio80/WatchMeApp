package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.CastCreditDataClass
import com.example.watchme.app.ui.dataClasses.CollectionDataClass
import com.example.watchme.app.ui.dataClasses.CrewCreditDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.GenresDataClass
import com.example.watchme.app.ui.dataClasses.MovieCreditsDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.ProductionCompaniesDataClass
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

fun MovieResponse.toMovieDataClass(): List<MovieDataClass> {
    return result.map {
        MovieDataClass(
            id = it.id,
            genreIds = it.genreIds,
            releaseDate = it.releaseDate,
            title = it.title,
            overview = it.overview,
            poster = it.poster,
            backdrop = it.backdrop,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}


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

fun DetailsMovieResponse.toDetailsMovieDataClass(): DetailsMovieDataClass {
    return DetailsMovieDataClass(
        adult = adult,
        backdropImage = backdropImage,
        collection = collection?.toCollectionDataClass(),
        budget = budget,
        genres = genres.map {
            it.toGenresDataClass()
        },
        homepage = homepage,
        id = id,
        overview = overview,
        productionCompanies = productionCompanies.map { it.toProductionCompaniesDataClass() },
        releaseDate = releaseDate,
        revenue = revenue,
        title = title,
        voteAverage = voteAverage,
        runtime = runtime
    )
}

data class Collection(
    @SerializedName("id") val idCollection: String,
    @SerializedName("name") val nameCollection: String,
    @SerializedName("poster_path") val posterCollection: String,
    @SerializedName("backdrop_path") val backdropCollection: String,
)

fun Collection.toCollectionDataClass(): CollectionDataClass? {
    if(nameCollection.isEmpty()) return null

    return CollectionDataClass(
        idCollection = idCollection,
        nameCollection = nameCollection,
        backdropCollection = backdropCollection
    )
}

data class Genres(
    @SerializedName("id") val idGenre: Int,
    @SerializedName("name") val nameGenre: String,
)

fun Genres.toGenresDataClass(): GenresDataClass {
    return GenresDataClass(
        idGenre = idGenre,
        nameGenre = nameGenre
    )
}

data class ProductionCompanies(
    @SerializedName("id") val idProductionCompany: Int,
    @SerializedName("logo_path") val logoProductionCompany: String,
    @SerializedName("name") val nameProductionCompany: String,
    @SerializedName("origin_country") val originCountryProductionCompany: String,
)

fun ProductionCompanies.toProductionCompaniesDataClass() : ProductionCompaniesDataClass {
    return ProductionCompaniesDataClass(
        idProductionCompany = idProductionCompany,
        logoProductionCompany = logoProductionCompany,
        nameProductionCompany = nameProductionCompany,
        originCountryProductionCompany = originCountryProductionCompany
    )
}

data class MovieCreditsResponse(
    @SerializedName("cast") val cast: List<CastCredit>,
    @SerializedName("crew") val crew: List<CrewCredit>
)

fun MovieCreditsResponse.toMovieCreditsDataClass(): MovieCreditsDataClass {
    return MovieCreditsDataClass(
        cast = cast.map { it.toCastCreditDataClass() },
        crew = crew.map { it.toCrewCreditDataClass() }
    )
}

data class CastCredit(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("character") val character: String,
)

fun CastCredit.toCastCreditDataClass(): CastCreditDataClass {
    return CastCreditDataClass(
        id = id,
        name = name,
        gender = gender,
        profilePath = profilePath,
        character = character
    )
}

data class CrewCredit(
    @SerializedName("id") val id: Int,
    @SerializedName("department") val department: String,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("job") val job: String,
)

fun CrewCredit.toCrewCreditDataClass(): CrewCreditDataClass {
    return CrewCreditDataClass(
        id = id,
        department = department,
        name = name,
        gender = gender,
        profilePath = profilePath
    )
}

data class ImageBackdrop(
    @SerializedName("backdrops") val backdrops: List<Backdrop>
)

fun ImageBackdrop.toBackdropImageDataClass() : List<BackdropImageDataClass> {
    return backdrops.map {
        BackdropImageDataClass(
            filePath = it.filePath
        )
    }
}

data class Backdrop(
    @SerializedName("file_path") val filePath: String,
)

