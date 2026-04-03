import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asPagingSourceFactory
import com.romahduda.movies30.data.remote.repository.MovieRepo
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMyRepository : MovieRepo {

    private val items = (0..100).map {
        Movie(
            id = it,
            title = "title $it",
            vote_average = 5.0,
            release_date = "2022-01-01",
            poster_path = "/path$it"
        )
    }

    private val pagingSourceFactory = items.asPagingSourceFactory()

    override fun getMovieById(movieId: Int): Flow<MovieDetails> {
        val movieDetails = MovieDetails(
            id = movieId,
            title = "Movie $movieId",
            voteAverage = 5.0,
            releaseDate = "2022-01-01",
            overview = "Overview $movieId",
            budget = 1000,
            runtime = 120,
            revenue = 5000,
            tagline = "Tagline $movieId",
            posterPath = "/path$movieId"
        )
        return flowOf(movieDetails)
    }

    override fun getPagingMovieFlow(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSourceFactory() }
        ).flow
    }
}
