package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.*

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DailyFragment()
            1 -> CalendarFragment()
            2 -> BillsFragment()
            3 -> SummaryFragment()
            4 -> GoalsFragment()
            else -> DailyFragment()
        }


    }


}