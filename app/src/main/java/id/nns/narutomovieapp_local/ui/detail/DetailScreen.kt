package id.nns.narutomovieapp_local.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.nns.narutomovieapp_local.data.NarutoMovieData
import id.nns.narutomovieapp_local.utils.CustomColorUtils
import id.nns.narutomovieapp_local.R.drawable.placeholder
import id.nns.narutomovieapp_local.data.NarutoMovieRepository.getMovieById
import id.nns.narutomovieapp_local.utils.toFormattedDate

@Composable
fun DetailScreen(movieId: Int) {
    val movie = getMovieById(movieId)

    if (movie != null) {
        MovieDetail(movie)
    } else {
        Text(
            text = "Movie not found",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun MovieDetail(movie: NarutoMovieData) {
    val context = LocalContext.current
    val defaultColor = MaterialTheme.colorScheme.surfaceVariant
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                CustomColorUtils.getLightMutedColorFromImage(
                    context = context,
                    img = movie.img ?: placeholder,
                    defaultColor = defaultColor
                )
            )
    ) {
        Box {
            Image(
                painter = painterResource(movie.img ?: placeholder),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreCircle(movie.score)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Release: ${movie.date.toFormattedDate()}\nDuration: ${movie.duration}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black.copy(alpha = 0.8f)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ScoreCircle(score: Int) {
    val progress = score / 100f

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(60.dp)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = Color.Black,
            strokeWidth = 8.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
        )
        Text(
            text = "$score",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
        )
    }
}
