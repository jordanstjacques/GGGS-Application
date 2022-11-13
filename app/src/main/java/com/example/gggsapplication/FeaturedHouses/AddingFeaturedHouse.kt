package com.example.gggsapplication.FeaturedHouses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gggsapplication.databinding.ActivityAddingFeaturedHouseBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddingFeaturedHouse : AppCompatActivity() {
    private lateinit var binding: ActivityAddingFeaturedHouseBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingFeaturedHouseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SubmitHouseInfoBtn.setOnClickListener {
            // Getting all of the values
            val Address = binding.NewHouseAddress.text.toString()
            val Description = binding.NewHouseDescription.text.toString()

            database = FirebaseDatabase.getInstance().getReference("FeaturedHouses")
            val FeaturedHouse = FeaturedHouse(Address, Description)
            database.child(Address).setValue(FeaturedHouse).addOnSuccessListener {
                binding.NewHouseAddress.text?.clear()
                binding.NewHouseDescription.text?.clear()

                // Outputting a message which will tell the user that their house has been listed successfully
                Toast.makeText(this, "House Featured Successfully!!!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed To Add The House", Toast.LENGTH_SHORT).show()
            }
        }
    }
}