package android.latihan.my_finance

import android.latihan.my_finance.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding untuk mendapat tampilan dari main activity
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        var viewingNav : NavigationView = binding.navView
        var headView = viewingNav.getHeaderView(0)
        var header = headView.findViewById<ConstraintLayout>(R.id.navHeader)
        //menangkap control input item dari navBar
        //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
        header.setOnClickListener {
            Toast.makeText(this, "Header Login Clicked", Toast.LENGTH_SHORT).show()
            loginCallend()
        }
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        //penangkapan ditaruh setelah setup controller apabila sebelumnya tidak tertangkap
        viewingNav.setNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        when (item.itemId){
            R.id.setelanFragment ->{
                navController.navigate(R.id.action_homeFragment_to_setting)
//                Toast.makeText(this, "Settingclicked", Toast.LENGTH_SHORT).show()
            }
            R.id.tentangFragment ->{
                navController.navigate(R.id.action_homeFragment_to_about)
//                Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show()
            }
        }
        closeOurDrawer()
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    fun loginCallend(){
        val navController = this.findNavController(R.id.myNavHostFragment)
        navController.navigate(R.id.action_homeFragment_to_Login)
        closeOurDrawer()
    }

    fun closeOurDrawer(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
    fun openOurDrawer(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
}
