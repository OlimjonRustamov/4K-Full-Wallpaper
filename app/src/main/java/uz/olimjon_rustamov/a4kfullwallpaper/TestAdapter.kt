package uz.olimjon_rustamov.a4kfullwallpaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.ItemPhotoBinding
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Hit
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Photos

class TestAdapter() : RecyclerView.Adapter<TestAdapter.Vh>() {
    var photos = Photos(ArrayList<Hit>(), 0, 0)
    var itemClick: OnItemClicked?=null


    fun addPhoto(new: Photos) {
        if (photos.hits.size != 0 && new.hits.size!=0) {
            if (new.hits[new.hits.size - 1].previewURL != photos.hits[photos.hits.size - 1].previewURL) {
                new.hits.forEach {
                    photos.hits.add(it)
                }
            }
        } else {
            new.hits.forEach {
                photos.hits.add(it)
            }
        }
        notifyDataSetChanged()
    }

    fun getSize() = photos.hits.size

    fun clear() {
        photos = Photos(ArrayList(), 0 , 0)
    }

    inner class Vh(var itemBinding: ItemPhotoBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(url: String, hit:Hit) {
            Picasso.get().load(url).into(itemBinding.itemPhotoIv)
            itemView.setOnClickListener {
                if (itemClick != null) {
                    itemClick!!.onClick(hit)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(photos.hits[position].previewURL!!, photos.hits[position])
    }

    override fun getItemCount(): Int = photos.hits.size

    interface OnItemClicked{
        fun onClick(hit:Hit)
    }
}