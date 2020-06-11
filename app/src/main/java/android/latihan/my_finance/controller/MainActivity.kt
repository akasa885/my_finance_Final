package android.latihan.my_finance.controller

import android.latihan.my_finance.R
import android.latihan.my_finance.model.loginWithGoogle
import android.latihan.my_finance.view.loginFragment
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity(), NavigationHost{
    companion object{
        const val SIGNOUT_REQUEST = "1"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(intent.hasExtra(SIGNOUT_REQUEST)){
            Toast.makeText(this, "Sign Out Successfull!", Toast.LENGTH_LONG).show()
        }
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, loginFragment())
                .commit()
        }
    }

    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onStart() {
        super.onStart()
        Log.d(loginWithGoogle.TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(loginWithGoogle.TAG,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(loginWithGoogle.TAG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(loginWithGoogle.TAG,"onstop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(loginWithGoogle.TAG,"onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(loginWithGoogle.TAG,"onRestart")
    }
}
