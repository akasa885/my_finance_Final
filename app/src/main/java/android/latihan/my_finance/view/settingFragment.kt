package android.latihan.my_finance.view

import android.content.Intent
import android.latihan.my_finance.R
import android.latihan.my_finance.controller.BaseActivity
import android.latihan.my_finance.databinding.FragmentSettingBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class settingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSettingBinding>(inflater,
            R.layout.fragment_setting,container,false)
        (activity as BaseActivity?)!!.closeOurDrawer()
        binding.cardEditCat.setOnClickListener {
            startActivityForResult(
                Intent(activity, settingCategoryView::class.java),
                SET_CAT
            )
        }
        binding.cardEditCur.setOnClickListener {
            startActivityForResult(
                Intent(activity, settingCurrencyView::class.java),
                SET_CUR
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    companion object{
        const val SET_CAT = 1
        const val SET_CUR = 2
    }
}