package uz.olimjon_rustamov.a4kfullwallpaper.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import uz.olimjon_rustamov.a4kfullwallpaper.ui.home.ItemPagerFragment

class HomePagerAdapter(frm: FragmentManager) :
    FragmentPagerAdapter(frm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 5

    override fun getItem(position: Int): Fragment {
        when(position){
            0->{ return ItemPagerFragment.newInstance("") }
            1->{ return ItemPagerFragment.newInstance("New") }
            2->{ return ItemPagerFragment.newInstance("Animals") }
            3->{ return ItemPagerFragment.newInstance("Technology") }
            4->{ return ItemPagerFragment.newInstance("Nature") }
        }
        return ItemPagerFragment.newInstance("")
    }
}