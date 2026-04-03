package com.romahduda.movies30.util
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.romahduda.movies30.data.remote.dto.MovieDto
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException

class FakeMoviePagingSource(
    private val mockMovies: List<MovieDto> = emptyList(),
    private var shouldReturnError: Boolean = false
) : PagingSource<Int, MovieDto>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        return try {
            if (shouldReturnError) {
                throw HttpException(retrofit2.Response.error<MovieDto>(400, "".toResponseBody()))
            } else {
                val startIndex = params.key ?: 1
                val endIndex = (startIndex - 1) + params.loadSize
                val sublist = mockMovies.subList(
                    startIndex - 1,
                    minOf(endIndex, mockMovies.size)
                )

                LoadResult.Page(
                    data = sublist,
                    prevKey = if (startIndex > 1) startIndex - 1 else null,
                    nextKey = if (endIndex < mockMovies.size) endIndex + 1 else null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun setShouldReturnError(){
        shouldReturnError = true
    }
}

