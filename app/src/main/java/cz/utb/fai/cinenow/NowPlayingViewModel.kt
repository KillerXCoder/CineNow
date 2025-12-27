package cz.utb.fai.cinenow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.cinenow.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NowPlayingViewModel(private val repository: Repository) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    init {
        viewModelScope.launch {
            try {
                val nowPlaying = repository.getNowPlaying()
                _movies.value = nowPlaying?.results ?: emptyList()
            } catch (_: Exception) {
                _movies.value = emptyList()
            }
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun clearSelectedMovie() {
        _selectedMovie.value = null
    }
}
