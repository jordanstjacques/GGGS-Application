package com.example.gggsapplication.RentALawn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gggsapplication.FeaturedHouses.FeaturedHouse
import com.example.gggsapplication.R
import com.example.gggsapplication.databinding.ActivityListAlawnBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ListALawnActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListAlawnBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAlawnBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.SubmitLawnInfoBtn.setOnClickListener {
            // Getting all of the values
            val Address = binding.NewLawnAddress.text.toString()
            val Description = binding.NewLawnDescription.text.toString()

            database = FirebaseDatabase.getInstance().getReference("RentALawn")
            val FeaturedHouse = Lawn(Address, Description)
            database.child(Address).setValue(FeaturedHouse).addOnSuccessListener {
                binding.NewLawnAddress.text?.clear()
                binding.NewLawnDescription.text?.clear()

                // Outputting a message which will tell the user that their house has been listed successfully
                Toast.makeText(this, "Lawn Listed Successfully!!!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed To List The Lawn", Toast.LENGTH_SHORT).show()
            }
        }
    }
}