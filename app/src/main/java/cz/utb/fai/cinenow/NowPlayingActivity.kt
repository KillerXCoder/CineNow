package cz.utb.fai.cinenow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class NowPlayingActivity : ComponentActivity() {

    private val repository by lazy { (application as CineNowApplication).repository }
    private val viewModelFactory by lazy { NowPlayingViewModelFactory(repository) }
    private val viewModel: NowPlayingViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                val selectedMovie by viewModel.selectedMovie.collectAsState()

                if (selectedMovie == null) {
                    NowPlayingScreen(viewModel = viewModel)
                } else {
                    MovieDetailScreen(
                        movie = selectedMovie!!,
                        onBack = { viewModel.clearSelectedMovie() }
                    )
                }
            }
        }
    }
}
