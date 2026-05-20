package id.nns.narutomovieapp_local.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.nns.narutomovieapp_local.data.NarutoMovieData
import id.nns.narutomovieapp_local.data.NarutoMovieRepository.getNarutoMovies
import id.nns.narutomovieapp_local.utils.CustomColorUtils.getLightMutedColorFromImage
import id.nns.narutomovieapp_local.R.drawable.placeholder
import id.nns.narutomovieapp_local.utils.toFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("about")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Naruto Movie App",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        MovieList(
            movies = getNarutoMovies(),
            modifier = Modifier.padding(innerPadding),
            onMovieClick = { movieId ->
                navController.navigate("detail/${movieId}")
            }
        )
    }
}

@Composable
private fun MovieList(movies: List<NarutoMovieData>, modifier: Modifier, onMovieClick: (Int) -> Unit) {
    val context = LocalContext.current
    val defaultColor = MaterialTheme.colorScheme.surfaceVariant
    val movieColors = remember {
        movies.associate { movie ->
            movie.id to getLightMutedColorFromImage(
                context,
                movie.img ?: placeholder,
                defaultColor
            )
        }
    }

    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            val cardColor = movieColors[movie.id] ?: defaultColor

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = cardColor,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                onClick = { onMovieClick(movie.id) }
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    Image(
                        painter = painterResource(movie.img ?: placeholder),
                        contentDescription = movie.title,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                    )
                    Spacer(
                        modifier = Modifier
                            .width(12.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                        Text(
                            text = movie.date.toFormattedDate(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "${movie.duration} • Score: ${movie.score}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}
