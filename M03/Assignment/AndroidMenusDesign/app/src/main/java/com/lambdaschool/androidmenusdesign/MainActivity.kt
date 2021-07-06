package com.lambdaschool.androidmenusdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Navigation Drawer
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.closer_drawer)

        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.nav_home -> { Toast.makeText(this, "Goes to Homepage", Toast.LENGTH_LONG).show() }
                R.id.nav_profile -> { Toast.makeText(this, "Goes to Profile Page", Toast.LENGTH_LONG).show() }
                R.id.nav_notifications -> { Toast.makeText(this, "Goes to Notifications Page", Toast.LENGTH_LONG).show() }
            }

            drawerLayout?.closeDrawers()

            true
        }
    }

    // Options Drawer
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_latest -> { Toast.makeText(this, "Shows the latest on top", Toast.LENGTH_LONG).show() }
            R.id.menu_preferences -> { Toast.makeText(this, "View preferences", Toast.LENGTH_LONG).show() }
        }
        return super.onOptionsItemSelected(item)
    }
}
