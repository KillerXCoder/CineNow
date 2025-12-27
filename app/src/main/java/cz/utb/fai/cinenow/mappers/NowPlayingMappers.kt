package cz.utb.fai.cinenow.mappers

import cz.utb.fai.cinenow.api.NowPlayingNetwork
import cz.utb.fai.cinenow.domain.NowPlayingDomain
import cz.utb.fai.cinenow.api.Movie as ApiMovieResult
import cz.utb.fai.cinenow.domain.Movie as DomainMovieResult

fun NowPlayingNetwork.asDomainModel(): NowPlayingDomain {
    return NowPlayingDomain(
        page = this.page,
        totalPages = this.total_pages,
        results = this.results.map(ApiMovieResult::asDomainModel)
    )
}

fun ApiMovieResult.asDomainModel(): DomainMovieResult {
    return DomainMovieResult(
        id = this.id,
        overview = this.overview,
        posterPath = this.poster_path,
        releaseDate = this.release_date,
        title = this.title,
        voteAverage = this.vote_average,
        genreIds = this.genre_ids
    )
}
