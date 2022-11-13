package com.example.gggsapplication.FeaturedHouses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.gggsapplication.ContactActivity
import com.example.gggsapplication.MainActivity
import com.example.gggsapplication.RentALawn.RentALawnActivity
import com.example.gggsapplication.databinding.ActivityFeaturedHousesBinding

class FeaturedHousesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeaturedHousesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeaturedHousesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ---- Mangaing the button for adding your own house to the listing -------
        binding.btnAddHouse.setOnClickListener {
            val intent = Intent(this, AddingFeaturedHouse::class.java)
            startActivity(intent)
        }

    }


    // ------------------------------------------ Functions For Changing The Screens Through The Bottom Menu -----------------------------------------
    // What to do when the Home option is clicked
    fun onClickingHomePage(item: MenuItem) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    // What to do when the Rent A Lawn option is clicked
    fun onClickingRentALawnPage (item: MenuItem) {
        val intent = Intent(this, RentALawnActivity::class.java)
        startActivity(intent)
    }

    // What to do when the Featured Houses option is clicked
    fun onClickingFeaturedHousesPage(item: MenuItem) {
        val intent = Intent(this, FeaturedHousesActivity::class.java)
        startActivity(intent)
    }

    // What to do when the Contact option is clicked
    fun onClickedContactPage(item: MenuItem) {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }
}