package android.latihan.my_finance.view

import android.content.Intent
import android.latihan.my_finance.R
import android.latihan.my_finance.model.commuViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton

class transaksiFragment :  Fragment(){
    private var communicationViewModel: commuViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicationViewModel = ViewModelProviders.of(requireActivity()).get(commuViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var v : View = inflater.inflate(R.layout.fragment_transaksi, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addTransac = view.findViewById<FloatingActionButton>(R.id.buttonAddTransac)
        addTransac.setOnClickListener {
            startActivityForResult(
                Intent(activity, addEditTransaksi::class.java),
                ADD_TRANS_REQUEST
            )
        }
    }

    companion object {
        const val ADD_TRANS_REQUEST = 1
        fun newInstance(): transaksiFragment {
            return transaksiFragment()
        }
    }
}