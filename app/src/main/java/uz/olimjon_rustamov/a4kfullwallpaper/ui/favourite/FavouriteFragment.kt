package uz.olimjon_rustamov.a4kfullwallpaper.ui.favourite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import uz.olimjon_rustamov.a4kfullwallpaper.MainActivity
import uz.olimjon_rustamov.a4kfullwallpaper.R
import uz.olimjon_rustamov.a4kfullwallpaper.TestAdapter
import uz.olimjon_rustamov.a4kfullwallpaper.ViewActivity
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.FragmentFavouriteBinding
import uz.olimjon_rustamov.a4kfullwallpaper.db.DBHelper
import uz.olimjon_rustamov.a4kfullwallpaper.db.HitDao
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Hit
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Photos

class FavouriteFragment : Fragment() {

    private lateinit var vb: FragmentFavouriteBinding
    private lateinit var listHit :ArrayList<Hit>
    private lateinit var adapter: TestAdapter
    private lateinit var dao :HitDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentFavouriteBinding.inflate(layoutInflater)

        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar_main).title = "Favourite"

        dao = DBHelper.getInstance(vb.root.context).hitDao()

        val hit = Hit()
        hit.id = 11927345
        hit.previewURL = "https://imgv3.fotor.com/images/homepage-feature-card/Fotor-AI-photo-enhancement-tool.jpg"
        dao.insertHit(hit)
        setAdapter()

        return vb.root
    }

    private fun setAdapter() {
        listHit = ArrayList()
        listHit = dao.getALlQuery() as ArrayList<Hit>
        adapter = TestAdapter()
        adapter.addPhoto(Photos(listHit, listHit.size, listHit.size))
        vb.favouriteRv.adapter = adapter
        adapter.itemClick = object:TestAdapter.OnItemClicked{
            override fun onClick(hit: Hit) {
                val intent = Intent(vb.root.context, ViewActivity::class.java)
                intent.putExtra("hit", hit)
                startActivity(intent)
            }
        }
    }
}