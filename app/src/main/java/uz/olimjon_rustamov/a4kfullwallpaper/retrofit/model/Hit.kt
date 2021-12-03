package uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Hit :Serializable{
    @PrimaryKey
    var id: Int = 0

    var collections: Int? = null
    var comments: Int? = null
    var downloads: Int? = null
    var imageHeight: Int? = null
    var imageSize: Int? = null
    var imageWidth: Int? = null
    var largeImageURL: String? = null
    var likes: Int? = null
    var pageURL: String? = null
    var previewHeight: Int? = null
    var previewURL: String? = null
    var previewWidth: Int? = null
    var tags: String? = null
    var type: String? = null
    var user: String? = null
    var userImageURL: String? = null
    var user_id: Int? = null
    var views: Int? = null
    var webformatHeight: Int? = null
    var webformatURL: String? = null
    var webformatWidth: Int? = null
}