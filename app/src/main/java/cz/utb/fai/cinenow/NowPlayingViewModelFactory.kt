package cz.utb.fai.cinenow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NowPlayingViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NowPlayingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NowPlayingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}