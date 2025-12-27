package cz.utb.fai.cinenow.api

data class GenreNetwork(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)
