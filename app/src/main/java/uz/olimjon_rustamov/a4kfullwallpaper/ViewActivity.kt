package uz.olimjon_rustamov.a4kfullwallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Hit

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val hit = intent.extras?.getSerializable("hit") as Hit
        Toast.makeText(this, "${hit.previewURL}", Toast.LENGTH_SHORT).show()
    }
}