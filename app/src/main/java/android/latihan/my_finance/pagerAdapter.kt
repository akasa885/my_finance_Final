package android.latihan.my_finance

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class pagerAdapter(private val mContext: homeFragment, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var ft = fm.beginTransaction()
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            transaksiFragment.newInstance()
        } else {
            laporanFragment.newInstance()
        }

    }
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int {
        return 2 }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }
}