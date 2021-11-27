package uz.olimjon_rustamov.a4kfullwallpaper.ui.random

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
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.FragmentRandomBinding
import uz.olimjon_rustamov.a4kfullwallpaper.utils.Status
import uz.olimjon_rustamov.a4kfullwallpaper.viewmodel.MyViewModel

class RandomFragment : Fragment() {

    private lateinit var vb: FragmentRandomBinding
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
        vb = FragmentRandomBinding.inflate(layoutInflater)

        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar_main).title = "Random"

        setAdapter()

        return vb.root
    }

    private fun setAdapter() {
        adapter = TestAdapter()
        val vm = ViewModelProvider(this).get(MyViewModel::class.java)
        vm.getPhotos("random").observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    page++
                    if (it.data != null) {
                        page++
                        vb.randomProgress.visibility = View.GONE
                        vb.randomRv.visibility = View.VISIBLE
                        adapter.addPhoto(it.data)
                        vb.randomRv.adapter = adapter
                        vb.randomRv.layoutManager = GridLayoutManager(vb.root.context, 3)
                    }
                    rvScrolled()
                }
                Status.ERROR -> {
                    Toast.makeText(vb.root.context, it.message, Toast.LENGTH_SHORT).show()
                    vb.randomRv.visibility = View.VISIBLE
                    vb.randomProgress.visibility = View.GONE
                }
                Status.LOADING -> {
                    vb.randomProgress.visibility = View.VISIBLE
                    vb.randomRv.visibility = View.GONE
                }
            }
        })
    }

    private fun rvScrolled() {
        vb.randomRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!vb.randomRv.canScrollVertically(1)&&dy>0) {
                    val vm = ViewModelProvider(this@RandomFragment).get(MyViewModel::class.java)
                    vm.getScroll("random",page).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                page++
                                vb.loadingRandomProgress.visibility = View.GONE
                                if (it.data != null) {
                                    adapter.addPhoto(it.data)
                                }
                            }
                            Status.ERROR -> {
                                vb.loadingRandomProgress.visibility = View.GONE
                            }
                            Status.LOADING -> {
                                vb.loadingRandomProgress.visibility = View.VISIBLE
                                vb.randomRv.scrollToPosition(adapter.getSize()-1)
                            }
                        }
                    })
                }
            }
        })
    }
}