package com.phono.allinonecalculator.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.phono.allinonecalculator.fragments.CurrencyConverter
import com.phono.allinonecalculator.fragments.EMICalculatorFragment
import com.phono.allinonecalculator.fragments.ScientificCalculatorFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ScientificCalculatorFragment()
            1 -> EMICalculatorFragment()
            else -> CurrencyConverter()
        }
    }
}