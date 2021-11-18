package uz.olimjon_rustamov.a4kfullwallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var vb: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)

        setToolbar()
        setBtmNavigation()
    }

    private fun setBtmNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        vb.btmNavView.setupWithNavController(navController)
    }

    private fun setToolbar() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        vb.toolbarMain.setNavigationOnClickListener {
            vb.drawerLayout.open()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

}