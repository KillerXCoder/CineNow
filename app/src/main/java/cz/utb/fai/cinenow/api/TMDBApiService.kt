package cz.utb.fai.cinenow.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("region") region: String = "SK",
        @Query("language") language: String = "sk-SK",
    ): NowPlayingNetwork?

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "sk-SK"
    ): GenreNetwork?
}