package com.example.gggsapplication.FeaturedHouses

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.gggsapplication.databinding.ActivityAddingFeaturedHouseBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class AddingFeaturedHouse : AppCompatActivity() {
    private lateinit var binding: ActivityAddingFeaturedHouseBinding
    private lateinit var database: DatabaseReference
    private var selectedImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingFeaturedHouseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getResult  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                selectedImage = it.data?.data
            }
        }

        binding.UploadHouseImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            "image/*".also { intent.type = it }
            getResult.launch(intent)
        }

        binding.SubmitHouseInfoBtn.setOnClickListener {
            // Getting all of the values
            val Name    = binding.OwnersName.text.toString()
            val Address = binding.NewHouseAddress.text.toString()
            val Description = binding.NewHouseDescription.text.toString()

            var FeaturedHousesDB = FirebaseDatabase.getInstance().getReference("FeaturedHouses")

            //val FeaturedHouse = FeaturedHouse(Address, Description)
            selectedImage?.let { it1 ->
                FirebaseStorage.getInstance().reference.child("ImageFolder/"+Name+".jpg").putFile(
                    it1
                ).addOnFailureListener {
                    // Handle unsuccessful uploads
                    Toast.makeText(this, "image upload failed", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(this, "image uploaded", Toast.LENGTH_SHORT).show()
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                }
            }



            //database.child(Address).setValue(FeaturedHouse).addOnSuccessListener {
            //    binding.NewHouseAddress.text?.clear()
            //    binding.NewHouseDescription.text?.clear()



                // Outputting a message which will tell the user that their house has been listed successfully
            //    Toast.makeText(this, "House Featured Successfully!!!", Toast.LENGTH_SHORT).show()
            //}.addOnFailureListener{
            //    Toast.makeText(this, "Failed To Add The House", Toast.LENGTH_SHORT).show()
            //}
        }
    }
}