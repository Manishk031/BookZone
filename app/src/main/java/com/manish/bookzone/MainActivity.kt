package com.manish.bookzone

import android.bluetooth.BluetoothCsipSetCoordinator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem:MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.Toolbar)
        frameLayout = findViewById(R.id.fragment)
        navigationView = findViewById(R.id.navigationView)
        setUPToolbar()
        openDashboard()


        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }
            it.isCheckable =true
            it.isChecked = true
            previousMenuItem =it

            when(it.itemId){
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                   supportFragmentManager.beginTransaction()
                       .replace(R.id.fragment,ProfileFragement())
                       .commit()
                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                }
               R.id.fav -> {
                   supportFragmentManager.beginTransaction()
                       .replace(R.id.fragment,FavouriteFragment())
                       .commit()
                   supportActionBar?.title="Favourite"
                   drawerLayout.closeDrawers()
               }
                R.id.about ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment,AboutFragment())
                        .commit()
                    supportActionBar?.title="About"
                    drawerLayout.closeDrawers()
                }

            }
            return@setNavigationItemSelectedListener true
        }

    }
      fun setUPToolbar()
      {
          setSupportActionBar(toolbar)
          supportActionBar?.title = "TOOLBAR"
          supportActionBar?.setHomeButtonEnabled(true)
          supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id =item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }


    fun openDashboard(){
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment,fragment)
        transaction.commit()
        supportActionBar?.title="Dashboard"
        navigationView.setCheckedItem(R.id.dashboard) // selected menu item throw open a app

    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.fragment)
        when(frag){
            !is DashboardFragment  -> openDashboard()
             else -> {
                 super.onBackPressed()
             }
        }
    }

}