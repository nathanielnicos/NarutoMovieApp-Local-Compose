package id.nns.narutomovieapp_local

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.nns.narutomovieapp_local.ui.about.AboutScreen
import id.nns.narutomovieapp_local.ui.detail.DetailScreen
import id.nns.narutomovieapp_local.ui.list.MovieListScreen
import id.nns.narutomovieapp_local.ui.theme.NarutoMovieAppLocalTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NarutoMovieAppLocalTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "list"
                ) {
                    composable(
                        route = "list"
                    ) {
                        MovieListScreen(
                            navController = navController,
                        )
                    }
                    composable(
                        route = "about"
                    ) {
                        AboutScreen()
                    }
                    composable(
                        route = "detail/{movieId}",
                        arguments = listOf(
                            navArgument("movieId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                        DetailScreen(
                            movieId = movieId,
                        )
                    }
                }
            }
        }
    }
}
