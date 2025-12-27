package cz.utb.fai.cinenow.domain

data class NowPlayingDomain(
    val page: Int,
    val totalPages: Int,
    val results: List<Movie>
)
data class Movie(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val genreIds: List<Int>,
    val genres: List<String> = emptyList()
)
