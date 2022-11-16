package com.example.gggsapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gggsapplication.Fragments.ContactFragment
import com.example.gggsapplication.Fragments.FeaturedHousesFragment
import com.example.gggsapplication.Fragments.HomeFragment
import com.example.gggsapplication.Fragments.RentALawnFragment
import com.example.gggsapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Creating lateinit variables
    private lateinit var serviceSlidesRecyclerView : RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing arrays with the info that will be displayed on slides
        replaceFragment(HomeFragment())

        binding.bottomNavMenu.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home_page -> replaceFragment(HomeFragment())
                R.id.rent_a_lawn_page -> replaceFragment(RentALawnFragment())
                R.id.featured_houses_page -> replaceFragment(FeaturedHousesFragment())
                R.id.contact_page -> replaceFragment(ContactFragment())
            }
            true
        }
    }
    // ---------------- Creating a function for switching fragments -----------------
    private fun replaceFragment (fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    // Creating the top options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_top,menu)
        return super.onCreateOptionsMenu(menu)
    }
    // ------------------------------------------ Functions For Changing The Screens Through The Top Menu -----------------------------------------
    // What to do when the sign in page is clicked
    fun onClickingSignIn(item: MenuItem) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}