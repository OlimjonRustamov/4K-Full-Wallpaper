package uz.olimjon_rustamov.a4kfullwallpaper.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.ApiClient
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.ApiHelper
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.ApiService
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Photos
import uz.olimjon_rustamov.a4kfullwallpaper.utils.Resource

class MyViewModel:ViewModel() {

    private var photos = MutableLiveData<Resource<Photos>>()
    private var scrollphotos = MutableLiveData<Resource<Photos>>()


    fun getPhotos(tab:String): LiveData<Resource<Photos>> {
        viewModelScope.launch {
            photos.postValue(Resource.loading(null))
            try {
                val response = ApiHelper.getHomePhotos(tab)
                photos.postValue(Resource.success(response))
            } catch (e: Exception) {
                photos.postValue(Resource.error(e.toString(), null))
            }
        }
        return photos
    }

    fun getScroll(tab:String, page:Int): LiveData<Resource<Photos>> {
        viewModelScope.launch {
            scrollphotos.postValue(Resource.loading(null))
            try {
                val response = ApiHelper.getScrollPhotos(tab, page)
                scrollphotos.postValue(Resource.success(response))
            } catch (e: Exception) {
                scrollphotos.postValue(Resource.error(e.toString(), null))
            }
        }
        return scrollphotos
    }
}