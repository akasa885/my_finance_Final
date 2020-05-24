package android.latihan.my_finance.view

import android.app.Activity
import android.content.Intent
import android.latihan.my_finance.R
import android.latihan.my_finance.controller.MainActivity
import android.latihan.my_finance.databinding.FragmentAccountSignBinding
import android.latihan.my_finance.model.loginWithGoogle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment


class loginFragment : Fragment(){
    var binding : FragmentAccountSignBinding? = null
    companion object{
        const val ADD_LOGIN_REQUEST = 1
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentAccountSignBinding>(inflater,
            R.layout.fragment_account_sign,container,false)
        (activity as MainActivity?)!!.closeOurDrawer()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var loginDong = view.findViewById<Button>(R.id.tombolLogin)
        loginDong.setOnClickListener{
            startActivityForResult(
                Intent(activity, loginWithGoogle::class.java),
                ADD_LOGIN_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_LOGIN_REQUEST && resultCode == Activity.RESULT_OK) {
            Toast.makeText(activity,"berhasil",Toast.LENGTH_SHORT).show()
        }
    }
}