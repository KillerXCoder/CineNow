package cz.utb.fai.cinenow.mappers

import cz.utb.fai.cinenow.api.NowPlayingNetwork
import cz.utb.fai.cinenow.database.MovieEntity
import cz.utb.fai.cinenow.domain.Movie
import cz.utb.fai.cinenow.domain.NowPlayingDomain
import cz.utb.fai.cinenow.api.Movie as ApiMovie


fun NowPlayingNetwork.asDomainModel(): NowPlayingDomain {
    return NowPlayingDomain(
        page = this.page,
        totalPages = this.total_pages,
        results = this.results.map(ApiMovie::asDomainModel)
    )
}

fun ApiMovie.asDomainModel(): Movie {
    return Movie(
        id = this.id,
        overview = this.overview,
        posterPath = this.poster_path ?: "",
        releaseDate = this.release_date,
        title = this.title,
        voteAverage = this.vote_average,
        genreIds = this.genre_ids
    )
}


fun MovieEntity.asDomainModel(): Movie {
    return Movie(
        id = this.id,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        voteAverage = this.voteAverage,
        genres = this.genres,
        genreIds = emptyList()
    )
}

fun Movie.asDatabaseModel(genres: List<String>): MovieEntity {
    return MovieEntity(
        id = this.id,
        overview = this.overview,
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate,
        title = this.title,
        voteAverage = this.voteAverage,
        genres = genres // Use the passed genre names
    )
}
