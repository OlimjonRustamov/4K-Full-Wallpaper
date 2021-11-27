package uz.olimjon_rustamov.a4kfullwallpaper.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.olimjon_rustamov.a4kfullwallpaper.MainActivity
import uz.olimjon_rustamov.a4kfullwallpaper.R
import uz.olimjon_rustamov.a4kfullwallpaper.TestAdapter
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.FragmentPopularBinding
import uz.olimjon_rustamov.a4kfullwallpaper.utils.Status
import uz.olimjon_rustamov.a4kfullwallpaper.viewmodel.MyViewModel

class PopularFragment : Fragment() {

    private lateinit var vb: FragmentPopularBinding
    private var page=1
    private lateinit var adapter: TestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentPopularBinding.inflate(layoutInflater)

        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar_main).title = "Popular"

        setAdapter()

        return vb.root
    }

    private fun setAdapter() {
        adapter = TestAdapter()
        val vm = ViewModelProvider(this).get(MyViewModel::class.java)
        vm.getPhotos("popular").observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    page++
                    if (it.data != null) {
                        page++
                        vb.popularProgress.visibility = View.GONE
                        vb.popularRv.visibility = View.VISIBLE
                        adapter.addPhoto(it.data)
                        vb.popularRv.adapter = adapter
                        vb.popularRv.layoutManager = GridLayoutManager(vb.root.context, 3)
                    }
                    rvScrolled()
                }
                Status.ERROR -> {
                    Toast.makeText(vb.root.context, it.message, Toast.LENGTH_SHORT).show()
                    vb.popularRv.visibility = View.VISIBLE
                    vb.popularProgress.visibility = View.GONE
                }
                Status.LOADING -> {
                    vb.popularProgress.visibility = View.VISIBLE
                    vb.popularRv.visibility = View.GONE
                }
            }
        })
    }

    private fun rvScrolled() {
        vb.popularRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!vb.popularRv.canScrollVertically(1)&&dy>0) {
                    val vm = ViewModelProvider(this@PopularFragment).get(MyViewModel::class.java)
                    vm.getScroll("popular",page).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                page++
                                vb.loadingPopularProgress.visibility = View.GONE
                                if (it.data != null) {
                                    adapter.addPhoto(it.data)
                                }
                            }
                            Status.ERROR -> {
                                vb.loadingPopularProgress.visibility = View.GONE
                            }
                            Status.LOADING -> {
                                vb.loadingPopularProgress.visibility = View.VISIBLE
                                vb.popularRv.scrollToPosition(adapter.getSize()-1)
                            }
                        }
                    })
                }
            }
        })
    }
}