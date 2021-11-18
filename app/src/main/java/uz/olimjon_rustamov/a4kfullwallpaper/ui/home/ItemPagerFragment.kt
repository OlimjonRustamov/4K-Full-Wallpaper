package uz.olimjon_rustamov.a4kfullwallpaper.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import uz.olimjon_rustamov.a4kfullwallpaper.R
import uz.olimjon_rustamov.a4kfullwallpaper.TestAdapter
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.FragmentItemPagerBinding
import uz.olimjon_rustamov.a4kfullwallpaper.utils.Status
import uz.olimjon_rustamov.a4kfullwallpaper.viewmodel.MyViewModel

class ItemPagerFragment : Fragment() {

    private var tab: String? = null
    private lateinit var vb :FragmentItemPagerBinding
    private lateinit var adapter :TestAdapter

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
                        adapter = TestAdapter(it.data)
                        vb.vpRv.layoutManager = GridLayoutManager(vb.root.context, 3)
                        vb.vpRv.adapter = adapter
                    }
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