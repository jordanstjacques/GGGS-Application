package com.example.gggsapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Creating the top options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_top,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // ------------------------------------------ Functions For Changing The Screens Through The Buttom Menu -----------------------------------------
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