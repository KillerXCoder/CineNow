package cz.utb.fai.cinenow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.utb.fai.cinenow.domain.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movie: Movie, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movie.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Späť")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original${movie.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Plagát filmu ${movie.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Hodnotenie: %.1f".format(movie.voteAverage))
            Text("Premiéra: %s".format(movie.releaseDate))
            if (movie.genres.isNotEmpty()) {
                Text("Žánre: %s".format(movie.genres.joinToString(", ")))
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (movie.overview.isNotEmpty()) {
                Text(
                    text = "Obsah:",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
