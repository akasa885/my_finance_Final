package android.latihan.my_finance.controller

import android.content.Intent
import android.latihan.my_finance.R
import android.latihan.my_finance.databinding.BaseActivityBinding
import android.latihan.my_finance.model.loginWithGoogle
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class BaseActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var indentLogin : TextView
    private lateinit var viewingNav : NavigationView
    private lateinit var signOffBut : MenuItem
    private lateinit var header : ConstraintLayout

    companion object{
        const val EXTRA_EMAIL = "com.piusanggoro.logsapp.EXTRA_EEMAIL"
        const val SIGNOUT_REQUEST = 5107
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding untuk mendapat tampilan dari main activity
        val binding = DataBindingUtil.setContentView<BaseActivityBinding>(this,
            R.layout.base_activity
        )
        drawerLayout = binding.drawerLayout
        firebaseAuth = FirebaseAuth.getInstance()
        viewingNav = binding.navView
        var headView = viewingNav.getHeaderView(0)
        header = headView.findViewById(R.id.navHeader)
        indentLogin = header.findViewById<TextView>(R.id.viewAccount)
        Log.d(loginWithGoogle.TAG, "onCreate")
        //inisialisasi textHeader
        signOffBut = viewingNav.menu.findItem(R.id.buttonSignout)
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        //penangkapan ditaruh setelah setup controller apabila sebelumnya tidak tertangkap
        viewingNav.setNavigationItemSelectedListener(this)
    }

override fun onStart() {
    super.onStart()
    //mengecek penangkapan dari activity login adakah user atau tidak
    if(intent.hasExtra(EXTRA_EMAIL)){
        indentLogin.setText(intent.getStringExtra(EXTRA_EMAIL))
    }

    val currentUser = firebaseAuth.currentUser
    //mengecek kebaradaan user ada atau tidak
    existanceUser(currentUser)
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

override fun onSupportNavigateUp(): Boolean {
    val navController = this.findNavController(R.id.myNavHostFragment)
    return NavigationUI.navigateUp(navController, drawerLayout)
}

override fun onNavigationItemSelected(item: MenuItem): Boolean {
    //menu item selected navigation
    val navController = this.findNavController(R.id.myNavHostFragment)
    when (item.itemId){
        R.id.setelanFragment ->{
            navController.navigate(R.id.action_homeFragment_to_setting)
        }
        R.id.tentangFragment ->{
            navController.navigate(R.id.action_homeFragment_to_about)
        }
        R.id.buttonSignout ->{
            signOut(1)
        }
    }
    closeOurDrawer()
    return true
}

override fun onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START)
    }else{
//        super.onBackPressed()
    }
}


fun existanceUser(user: FirebaseUser?){
    if(user!= null){
        deactivateClick()
        indentLogin.setText(getString(R.string.google_email_user, user!!.email))
        signOut(0)
    }
}

fun deactivateClick(){
    header.setOnClickListener(null)
}

fun closeOurDrawer(){
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
}

fun openOurDrawer(){
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
}

fun signOut(letTurn : Int?)  {
    if(letTurn == 1){
        indentLogin.setText(getString(R.string.default_head_title))
        signOffBut.setVisible(false)
        //signOUt Request to Activity
        val setSignOut = Intent(this, loginWithGoogle::class.java)
        setSignOut.putExtra(loginWithGoogle.EXTRA_OUT,"MA1")
        startActivityForResult(setSignOut, SIGNOUT_REQUEST)
        //back to login page
        val confirmSignOut = Intent(this, MainActivity::class.java)
        confirmSignOut.putExtra(MainActivity.SIGNOUT_REQUEST, "0")
        startActivity(confirmSignOut)
    }else{
        signOffBut.setVisible(true)
    }
}
}