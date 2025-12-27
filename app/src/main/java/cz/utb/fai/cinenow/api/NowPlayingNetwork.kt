package cz.utb.fai.cinenow.api

data class NowPlayingNetwork(
    val page: Int,
    val total_pages: Int,
    val results: List<Movie>
)
data class Movie(
    val id: Int,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val genre_ids: List<Int>
)
