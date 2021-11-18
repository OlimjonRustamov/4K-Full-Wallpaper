package uz.olimjon_rustamov.a4kfullwallpaper.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Photos

interface ApiService {
    @GET("api")
    suspend fun getHome(@Query("key") key: String, @Query("q")tab:String): Photos
}