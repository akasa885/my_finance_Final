package android.latihan.my_finance.view

import android.latihan.my_finance.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class laporanFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var v : View = inflater.inflate(R.layout.fragment_laporan, container, false)
        return v
    }
    companion object {
        fun newInstance(): laporanFragment {
            return laporanFragment()
        }
    }
}