package com.example.gggsapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gggsapplication.FeaturedHouses.FeaturedHousesActivity
import com.example.gggsapplication.RentALawn.RentALawnActivity

class MainActivity : AppCompatActivity() {
    // Creating lateinit variables
    private lateinit var serviceSlidesRecyclerView : RecyclerView
    private lateinit var slideArrayList : ArrayList<ServiceSlide>
    lateinit var servicesNames : Array <String>
    lateinit var servicesDescription : Array <String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing arrays with the info that will be displayed on slides

        servicesNames = arrayOf (
            "Featured Houses",
            "Bank St Merchants",
            "Branded Sponsorship"
        )

        servicesDescription = arrayOf (
            "blahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablah" +
                    "blahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablah",
            "blahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablah" +
                    "blahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablah",
            "blahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablah" +
                    "blahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablahblahblahblahblbhablah",
        )

        serviceSlidesRecyclerView = findViewById(R.id.ServicesSlidesRecView)
        serviceSlidesRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        serviceSlidesRecyclerView.setHasFixedSize(true)

        slideArrayList = arrayListOf<ServiceSlide>()
        getUserData()
    }

    private fun getUserData () {
        for(i in servicesNames.indices) {
            val slide  = ServiceSlide(servicesNames[i], servicesDescription[i])
            slideArrayList.add(slide)
        }

        serviceSlidesRecyclerView.adapter = ServicesSlidesAdapter(slideArrayList)
    }

    // ------------------------------------------- Creating the RecyclerView with more info on Major pages ---------------------------------------------


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