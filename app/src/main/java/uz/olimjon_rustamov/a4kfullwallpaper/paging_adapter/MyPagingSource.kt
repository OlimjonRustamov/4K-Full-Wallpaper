package uz.olimjon_rustamov.a4kfullwallpaper.paging_adapter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.ApiHelper
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Photos

//class MyPagingSource:PagingSource<Int, Photos>() {
//
//    override fun getRefreshKey(state: PagingState<Int, Photos>): Int? {
//        return state.anchorPosition?.let {anchorPosition->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
//        try {
//            val nextPageNumber = params.key ?: 1
//            val response = ApiHelper.getHomePhotos("")
//        } catch (e: Exception) {
//            Log.d("TTTT", "load: ${e.message}")
//        }
//        return LoadResult.Page(, null, null)
//    }
//}