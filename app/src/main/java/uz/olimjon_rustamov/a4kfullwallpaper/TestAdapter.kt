package uz.olimjon_rustamov.a4kfullwallpaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.ItemPhotoBinding
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Photos

class TestAdapter(var photos: Photos) : RecyclerView.Adapter<TestAdapter.Vh>() {
    inner class Vh(var itemBinding: ItemPhotoBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(url: String) {
            Picasso.get().load(url).into(itemBinding.itemPhotoIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(photos.hits[position].previewURL)
    }

    override fun getItemCount(): Int = photos.hits.size
}