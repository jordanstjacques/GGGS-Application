package com.example.gggsapplication.FeaturedHouses

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.gggsapplication.R
import com.example.gggsapplication.databinding.ActivityAddingFeaturedHouseBinding
import com.google.android.material.color.MaterialColors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AddingFeaturedHouse : AppCompatActivity() {
    private lateinit var binding: ActivityAddingFeaturedHouseBinding
    private lateinit var database: DatabaseReference
    private var selectedImage: Uri? = null
    private val tagList = mutableSetOf<String>()

    @SuppressLint("ResourceAsColor")
    fun tagButtonClicked(view: View) {
        val buttonText = ((view as Button).text) as String
        println(tagList)
        if (tagList.contains(buttonText)) {
            tagList.remove(buttonText)
            view.setBackgroundColor(MaterialColors.getColor(view, com.google.android.material.R.attr.colorPrimary))
        } else {
            view.setBackgroundColor(R.color.peach)
            view.setBackgroundColor(MaterialColors.getColor(view, com.google.android.material.R.attr.colorPrimaryVariant))
            tagList.add(buttonText)
        }
        Toast.makeText(this, buttonText, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingFeaturedHouseBinding.inflate(layoutInflater)
        //binding.fragment = this;
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
            val user = FirebaseAuth.getInstance().currentUser
            var uid = ""
            if (user != null) {
                // User is signed in
                user?.let {
                    uid = user.uid
                }
            } else {
                Toast.makeText(this, "Please sign in and try again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val name = binding.OwnersName.text.toString()
            val address = binding.NewHouseAddress.text.toString()
            val description = binding.NewHouseDescription.text.toString()

            selectedImage?.let { it1 ->
                FirebaseStorage.getInstance().reference.child("ImageFolder/"+uid+".jpg").putFile(
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

            val featuredHouse = FeaturedHouse(name, address, tagList.toList(), description, "ImageFolder/"+uid+".jpg")

            database = FirebaseDatabase.getInstance().getReference("FeaturedHouses")
            database.child(uid).setValue(featuredHouse).addOnSuccessListener {
                Toast.makeText(this, "Featured house uploaded to firebase",
                    Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Featured house failed to upload to firebase",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}