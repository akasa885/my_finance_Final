package android.latihan.my_finance.view

import android.app.Activity
import android.content.Intent
import android.latihan.my_finance.R
import android.latihan.my_finance.controller.BaseActivity
import android.latihan.my_finance.controller.MainActivity
import android.latihan.my_finance.controller.NavigationHost
import android.latihan.my_finance.databinding.FragmentAccountSignBinding
import android.latihan.my_finance.model.loginWithGoogle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class loginFragment : Fragment(){
    lateinit var binding : FragmentAccountSignBinding
    private lateinit var loginDong : Button
    private lateinit var firebaseAuth: FirebaseAuth

    companion object{
        const val ADD_LOGIN_REQUEST = 1
        private var identity : TextView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account_sign, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginDong = view.findViewById(R.id.tombolLogin)
        val currentUser = firebaseAuth.currentUser
        checkUser(currentUser)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_LOGIN_REQUEST && resultCode == Activity.RESULT_OK) {
            val restartForMain = Intent(activity,BaseActivity::class.java)
            restartForMain.putExtra(BaseActivity.EXTRA_EMAIL,data!!.getStringExtra("datauser"))
            startActivity(restartForMain)
        }else{
            Toast.makeText(activity, "No Account Login", Toast.LENGTH_LONG).show()
        }
    }
    fun setClicktoLogin(){
        loginDong.setOnClickListener{
            startActivityForResult(
                Intent(activity, loginWithGoogle::class.java),
                ADD_LOGIN_REQUEST)
        }
    }

    fun checkUser(user:FirebaseUser?){
        if(user != null){
//            startActivityForResult(Intent(activity, loginWithGoogle::class.java), ADD_LOGIN_REQUEST)
            Toast.makeText(activity, "Account Login ${user.email}", Toast.LENGTH_LONG).show()
            setClicktoLogin()
        } else{
            setClicktoLogin()
        }
    }
}