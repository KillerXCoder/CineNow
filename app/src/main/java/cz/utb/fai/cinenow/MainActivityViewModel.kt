package cz.utb.fai.cinenow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MainViewModel : ViewModel() {

    var isLoading = true

    init {
        viewModelScope.launch {
            delay(2000)
            isLoading = false
        }
    }
}