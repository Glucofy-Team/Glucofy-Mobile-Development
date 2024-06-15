package com.dicoding2.glucofy.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.data.remote.retrofit.ApiService

class FoodPagingSource (
    private val apiService: ApiService,
    private val name: String?
) : PagingSource<Int, FoodListItem>() {
    override fun getRefreshKey(state: PagingState<Int, FoodListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FoodListItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getFood(name ?: "", position, params.loadSize)

            LoadResult.Page(
                data = responseData.foodListItem,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.foodListItem.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}