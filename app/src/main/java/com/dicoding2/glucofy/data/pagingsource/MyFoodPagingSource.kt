package com.dicoding2.glucofy.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding2.glucofy.data.remote.response.MyFoodListItem
import com.dicoding2.glucofy.data.remote.retrofit.ApiService

class MyFoodPagingSource (
    private val apiService: ApiService,
) : PagingSource<Int, MyFoodListItem>() {
    override fun getRefreshKey(state: PagingState<Int, MyFoodListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyFoodListItem> {
        return try {
            val position = params.key ?: FoodPagingSource.INITIAL_PAGE_INDEX
            val responseData = apiService.getMyFood(position, params.loadSize)

            val nextPage = if (responseData.myFoodLisItem.isEmpty()) null else position + 1


            LoadResult.Page(
                data = responseData.myFoodLisItem,
                prevKey = if (position == FoodPagingSource.INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = nextPage
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}