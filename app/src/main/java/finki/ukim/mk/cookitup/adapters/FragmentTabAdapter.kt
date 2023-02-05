package finki.ukim.mk.cookitup.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import finki.ukim.mk.cookitup.ui.home.ShowApiFragment
import finki.ukim.mk.cookitup.ui.home.ShowCameraFragment
import finki.ukim.mk.cookitup.ui.home.ShowWrittenFragment

class FragmentTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ShowApiFragment()
            1 -> ShowWrittenFragment()
            else -> ShowCameraFragment()
        }
    }
}