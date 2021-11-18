package uz.olimjon_rustamov.a4kfullwallpaper.retrofit

import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Photos

object ApiHelper {
    val key = "24266546-40eab929d3cfcc2428c099c34"

    suspend fun getHomePhotos(tab:String):Photos {
        return ApiClient.apiService.getHome(key, tab)
    }
    suspend fun getScrollPhotos(tab:String, page:Int):Photos {
        return ApiClient.apiService.getScroll(key, tab, page)
    }
}