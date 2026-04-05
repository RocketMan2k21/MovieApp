import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asPagingSourceFactory
import com.romahduda.movies30.data.local.entity.LikedMovieEntity
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
            poster_path = "/path$it",
            isFavorite = false,
            remoteIndex = 1
        )
    }

    private val pagingSourceFactory = items.asPagingSourceFactory()
    override suspend fun getMovieById(movieId: Int): Flow<MovieDetails> {
        TODO("Not yet implemented")
    }


    override fun getPagingMovieFlow(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSourceFactory() }
        ).flow
    }

    override fun getMovieList(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun makeMovieFavorite(movieId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun makeMovieUnfavorite(movieId: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getLikedMovieEntries(): Flow<List<LikedMovieEntity>> {
        TODO("Not yet implemented")
    }
}
