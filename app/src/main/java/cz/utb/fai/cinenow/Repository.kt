package cz.utb.fai.cinenow

import android.util.Log
import cz.utb.fai.cinenow.api.TMDBApiService
import cz.utb.fai.cinenow.database.MovieDao
import cz.utb.fai.cinenow.domain.NowPlayingDomain
import cz.utb.fai.cinenow.mappers.asDatabaseModel
import cz.utb.fai.cinenow.mappers.asDomainModel
import java.util.concurrent.TimeUnit

class Repository(
    private val apiService: TMDBApiService,
    private val movieDao: MovieDao
) {

    suspend fun getNowPlaying(): NowPlayingDomain {
        try {
            val cachedMovies = movieDao.getAllMovies()
            val firstMovie = cachedMovies.firstOrNull()

            val isDataFresh = firstMovie != null && (System.currentTimeMillis() - firstMovie.lastUpdated) < TimeUnit.HOURS.toMillis(1)

            if (cachedMovies.isNotEmpty() && isDataFresh) {
                val domainMovies = cachedMovies.map { it.asDomainModel() }
                return NowPlayingDomain(page = 1, totalPages = 1, results = domainMovies)
            } else {
                Log.d("Repository", "Fetching new data from API")
                refreshNowPlaying()
            }
        } catch (e: Exception) {
            Log.e("Repository", "Could not get data", e)
        }

        val finalMovies = movieDao.getAllMovies().map { it.asDomainModel() }
        return NowPlayingDomain(page = 1, totalPages = 1, results = finalMovies)
    }

    private suspend fun refreshNowPlaying() {
        try {
            val genreMap = getGenres()
            val networkModel = apiService.getNowPlaying()

            if (networkModel != null) {
                val domainModel = networkModel.asDomainModel()

                val movieEntities = domainModel.results.map { domainMovie ->
                    val genreNames = domainMovie.genreIds.map { genreId ->
                        genreMap[genreId] ?: "Neznámy žáner"
                    }
                    domainMovie.asDatabaseModel(genreNames)
                }

                movieDao.deleteAllMovies()
                movieDao.insertMovies(movieEntities)
            }
        } catch (e: Exception) {
            Log.e("Repository", "Failed to refresh data", e)
        }
    }

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
}
