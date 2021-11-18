package uz.olimjon_rustamov.a4kfullwallpaper.ui.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import uz.olimjon_rustamov.a4kfullwallpaper.MainActivity
import uz.olimjon_rustamov.a4kfullwallpaper.R
import uz.olimjon_rustamov.a4kfullwallpaper.databinding.FragmentRandomBinding

class RandomFragment : Fragment() {

    private lateinit var vb: FragmentRandomBinding

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


        return vb.root
    }
}