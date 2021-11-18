package uz.olimjon_rustamov.a4kfullwallpaper.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.olimjon_rustamov.a4kfullwallpaper.TestAdapter
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.FragmentItemPagerBinding
import uz.olimjon_rustamov.a4kfullwallpaper.utils.Status
import uz.olimjon_rustamov.a4kfullwallpaper.viewmodel.MyViewModel

class ItemPagerFragment : Fragment() {

    private var tab: String? = null
    private lateinit var vb :FragmentItemPagerBinding
    private lateinit var adapter :TestAdapter
    private var page=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tab = it.getString("tab")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentItemPagerBinding.inflate(layoutInflater)

        loadAdapter()

        return vb.root
    }

    private fun loadAdapter() {
        val vm = ViewModelProvider(this).get(MyViewModel::class.java)
        if (tab == null) {
            tab = ""
        }
        vm.getPhotos(tab!!).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    vb.vpRv.visibility = View.VISIBLE
                    vb.homeProgress.visibility = View.GONE
                    if (it.data != null) {
                        adapter = TestAdapter()
                        adapter.addPhoto(it.data)
                        vb.vpRv.layoutManager = GridLayoutManager(vb.root.context, 3)
                        vb.vpRv.adapter = adapter
                    }
                    scrollChanged()
                }
                Status.ERROR -> {
                    vb.homeProgress.visibility = View.GONE
                    Toast.makeText(vb.root.context, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    vb.homeProgress.visibility = View.VISIBLE
                    vb.vpRv.visibility = View.GONE
                }
            }
        })
    }
    private fun scrollChanged() {
        vb.vpRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!vb.vpRv.canScrollVertically(1)&&dy>0) {
                    val vm = ViewModelProvider(this@ItemPagerFragment).get(MyViewModel::class.java)
                    vm.getScroll(tab!!, page).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                page++
                                vb.scrollVpProgress.visibility = View.GONE
                                if (it.data != null) {
                                    adapter.addPhoto(it.data)
                                }
                            }
                            Status.ERROR -> {
                                vb.scrollVpProgress.visibility = View.GONE
                            }
                            Status.LOADING -> {
                                vb.scrollVpProgress.visibility = View.VISIBLE
                                vb.vpRv.scrollToPosition(adapter.getSize()-1)
                            }
                        }
                    })
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(tab: String) =
            ItemPagerFragment().apply {
                arguments = Bundle().apply {
                    putString("tab", tab)
                }
            }
    }
}