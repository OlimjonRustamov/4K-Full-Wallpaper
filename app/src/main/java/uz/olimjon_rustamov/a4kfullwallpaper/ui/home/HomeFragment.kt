package uz.olimjon_rustamov.a4kfullwallpaper.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.olimjon_rustamov.a4kfullwallpaper.MainActivity
import uz.olimjon_rustamov.a4kfullwallpaper.R
import uz.olimjon_rustamov.a4kfullwallpaper.TestAdapter
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.FragmentHomeBinding
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.LoadingSrollBinding
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Photos
import uz.olimjon_rustamov.a4kfullwallpaper.ui.home.adapters.HomePagerAdapter
import uz.olimjon_rustamov.a4kfullwallpaper.utils.Status
import uz.olimjon_rustamov.a4kfullwallpaper.viewmodel.MyViewModel

class HomeFragment : Fragment() {
    private lateinit var vb: FragmentHomeBinding
    private lateinit var pagerAdapter: HomePagerAdapter
    private var page=1
    private var searchWord=""
    private lateinit var adapter:TestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentHomeBinding.inflate(layoutInflater)

        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar_main).title = "Home"
        setViewPager()
        adapter = TestAdapter()

        return vb.root
    }

    private fun setViewPager() {
        pagerAdapter = HomePagerAdapter(childFragmentManager)
        vb.homeViewPager.adapter = pagerAdapter


        vb.tabLayout.setupWithViewPager(vb.homeViewPager)
        vb.tabLayout.getTabAt(0)!!.text = "All"
        vb.tabLayout.getTabAt(1)!!.text = "New"
        vb.tabLayout.getTabAt(2)!!.text = "Animals"
        vb.tabLayout.getTabAt(3)!!.text = "Technology"
        vb.tabLayout.getTabAt(4)!!.text = "Nature"
    }

    private fun rvScrolled() {
        vb.searchRv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!vb.searchRv.canScrollVertically(1)&&dy>0 && searchWord!="") {
                    val vm = ViewModelProvider(this@HomeFragment).get(MyViewModel::class.java)
                    vm.getScroll(searchWord, page).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                page++
                                vb.scrollProgress.visibility = View.GONE
                                if (it.data != null) {
                                    adapter.addPhoto(it.data)
                                }
                            }
                            Status.ERROR -> {
                                vb.scrollProgress.visibility = View.GONE
                            }
                            Status.LOADING -> {
                                vb.scrollProgress.visibility = View.VISIBLE
                                vb.searchRv.scrollToPosition(adapter.getSize()-1)
                            }
                        }
                    })
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val myActionMenuItem = menu.findItem(R.id.search_menu)
        val searchView = myActionMenuItem.actionView as SearchView
        val txtSearch =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = "Search photos..."
        txtSearch.setTextColor(Color.WHITE)
        txtSearch.setHintTextColor(Color.LTGRAY)
        txtSearch.setBackgroundResource(android.R.color.transparent)
        txtSearch.setTextColor(Color.BLACK)

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                handler.removeCallbacks(this)
                if (txtSearch.text != null && txtSearch.text.isNotEmpty()) {
                    textchanged(txtSearch.text.toString())
                    vb.homeViewPager.visibility = View.GONE
                    vb.tabLayout.visibility = View.GONE
                } else {
                    vb.tabLayout.visibility = View.VISIBLE
                    vb.homeViewPager.visibility = View.VISIBLE
                    vb.searchRv.visibility = View.GONE
                }
            }
        }

        txtSearch.addTextChangedListener {
            Log.d("TTTT", "onCreateOptionsMenu: textchanged")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 1000)
        }
    }

    private fun textchanged(word: String) {
        page=1
        searchWord = word
        adapter.clear()
        val vm = ViewModelProvider(this).get(MyViewModel::class.java)
        vm.getPhotos(word).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    page++
                    if (it.data != null) {
                        vb.searchProgress.visibility = View.GONE
                        vb.searchRv.visibility = View.VISIBLE
                        adapter.addPhoto(it.data)
                        vb.searchRv.adapter = adapter
                        vb.searchRv.layoutManager = GridLayoutManager(vb.root.context, 3)
                    }
                    rvScrolled()
                }
                Status.ERROR -> {
                    Toast.makeText(vb.root.context, it.message, Toast.LENGTH_SHORT).show()
                    vb.searchRv.visibility = View.VISIBLE
                    vb.searchProgress.visibility = View.GONE
                }
                Status.LOADING -> {
                    vb.searchProgress.visibility = View.VISIBLE
                    vb.searchRv.visibility = View.GONE
                }
            }
        })
    }

}