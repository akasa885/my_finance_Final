package android.latihan.my_finance.view





import android.latihan.my_finance.controller.MainActivity
import android.latihan.my_finance.R
import android.latihan.my_finance.controller.BaseActivity
import android.latihan.my_finance.databinding.FragmentHomeBinding
import android.latihan.my_finance.controller.pagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*


class homeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false)
        (activity as BaseActivity?)!!.openOurDrawer()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState == null){
            view_pager.adapter =
                pagerAdapter(
                    this,
                    childFragmentManager
                )
            tabs.setupWithViewPager(view_pager)
        }
    }
}