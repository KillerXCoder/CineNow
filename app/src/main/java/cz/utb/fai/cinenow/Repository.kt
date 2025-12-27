package cz.utb.fai.cinenow

import android.util.Log
import cz.utb.fai.cinenow.api.TMDBApiService
import cz.utb.fai.cinenow.domain.NowPlayingDomain
import cz.utb.fai.cinenow.mappers.asDomainModel
class Repository(
    private val apiService: TMDBApiService
) {

    private suspend fun getGenres(): Map<Int, String> {
        val genreMap = mutableMapOf<Int, String>()
        try {
            val genreResponse = apiService.getGenres()
            genreResponse?.genres?.forEach { genre ->
                genreMap[genre.id] = genre.name
            }
        } catch (e: Exception) {
            Log.e("Repository", "Failed to fetch genres", e)
        }
        return genreMap
    }

    suspend fun getNowPlaying(): NowPlayingDomain? {
        try {
            val genreMap = getGenres()
            val networkModel = apiService.getNowPlaying()

            if (networkModel != null) {
                val domainModel = networkModel.asDomainModel()

                val enrichedResults = domainModel.results.map { domainMovie ->
                    val genreNames = domainMovie.genreIds.map { genreId ->
                        genreMap[genreId] ?: "Neznámy žáner"
                    }
                    domainMovie.copy(genres = genreNames)
                }

                return domainModel.copy(results = enrichedResults)
            }
        } catch (e: Exception) {
            Log.e("Repository", "API call failed", e)
        }

        return null
    }
}