package com.example.gggsapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

class BlogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
    }

    // Creating a menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Handling what happens if the menu items are clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            // If the menu icon was pressed
            R.id.app_menu -> {
                true
            }

            // ------------ If any of the menu options are clicked a new activity opens up ---------
            // Opening the home page activity
            R.id.home_page -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }

            // Opening the about page activity
            R.id.about_page -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                true
            }

            // Opening the featured houses page activity
            R.id.featured_houses_page -> {
                val intent = Intent(this, FeaturedHousesActivity::class.java)
                startActivity(intent)
                true
            }

            // Opening the bank st merchants page activity
            R.id.bank_st_merchants_page -> {
                val intent = Intent(this, BankStMerchantsActivity::class.java)
                startActivity(intent)
                true
            }

            // Opening the bank st merchants page activity
            R.id.bank_st_merchants_page -> {
                val intent = Intent(this, BankStMerchantsActivity::class.java)
                startActivity(intent)
                true
            }

            // Opening the rent a lawn page activity
            R.id.rent_a_lawn_page -> {
                val intent = Intent(this, RentALawnActivity::class.java)
                startActivity(intent)
                true
            }

            // Opening the shop page activity
            R.id.shop_page -> {
                val intent = Intent(this, ShopActivity::class.java)
                startActivity(intent)
                true
            }

            // Opening the blog page activity
            R.id.blog_page -> {
                val intent = Intent(this, BlogActivity::class.java)
                startActivity(intent)
                true
            }

            // Opening the contact page activity
            R.id.contact_page -> {
                val intent = Intent(this, ContactActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}