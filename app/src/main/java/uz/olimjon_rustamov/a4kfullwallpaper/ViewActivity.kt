package uz.olimjon_rustamov.a4kfullwallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.ActivityViewBinding
import uz.olimjon_rustamov.a4kfullwallpaper.db.DBHelper
import uz.olimjon_rustamov.a4kfullwallpaper.db.HitDao
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Hit

class ViewActivity : AppCompatActivity() {
    private lateinit var vb : ActivityViewBinding
    private lateinit var db:HitDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityViewBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val hit = intent.extras?.getSerializable("hit") as Hit
        Picasso.get().load(hit.largeImageURL).into(vb.imageView)
        db = DBHelper.getInstance(vb.root.context).hitDao()
        vb.backBtn.setOnClickListener {
            finish()
        }
        vb.downloadBtn.setOnClickListener {
            Toast.makeText(vb.root.context, "Download", Toast.LENGTH_SHORT).show()
        }
        vb.shareBtn.setOnClickListener {
            Toast.makeText(vb.root.context, "Share", Toast.LENGTH_SHORT).show()
        }
        vb.favouriteBtn.setOnClickListener {
            if (db.getById(hit.id).size != 0) {
                db.deleteHit(hit)
                Toast.makeText(vb.root.context, "UnSaved!", Toast.LENGTH_SHORT).show()
            } else {
                db.insertHit(hit)
                Toast.makeText(vb.root.context, "Saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}