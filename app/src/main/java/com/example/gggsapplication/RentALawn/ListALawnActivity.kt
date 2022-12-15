package com.example.gggsapplication.RentALawn

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.gggsapplication.FeaturedHouses.FeaturedHouse
import com.example.gggsapplication.Fragments.RentALawnFragment
import com.example.gggsapplication.R
import com.example.gggsapplication.databinding.ActivityListAlawnBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ListALawnActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListAlawnBinding
    private lateinit var database: DatabaseReference
    private val ImageBack = 1
    lateinit var storage : StorageReference
    lateinit var firstName : String
    lateinit var lastName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAlawnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // -------- Creating a variable to store users first and last name

        // making sure the uploading button is not visible until the user finished filling in his credentials
        binding.uploadingLawnImage.visibility = View.GONE
        // -------------------------- Managing the Submit button ---------------------------------
        binding.SubmitLawnInfoBtn.setOnClickListener {
            // --------------- Getting all of the values from the input fields ------------------
            firstName = binding.GettingFirstName.text.toString()
            lastName = binding.GettingLastName.text.toString()
            val email = binding.NewLawnEmail.text.toString()
            val phone = binding.NewLawnPhone.text.toString()
            val address = binding.NewLawnAddress.text.toString()
            val hours = binding.NewLawnHours.text.toString()
            val price = binding.NewLawnPrice.text.toString()

            // ------ Using an if loop to make sure that none of the input fields are empty -------------
            if (firstName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()
                && address.isNotEmpty() && hours.isNotEmpty() && price.isNotEmpty()) {
                database = FirebaseDatabase.getInstance().getReference("RentALawn")
                val listedLawn = Lawn(email, phone, address, hours, price)
                database.child("$firstName $lastName").setValue(listedLawn).addOnSuccessListener {

                }.addOnFailureListener {

                }
                binding.uploadingLawnImage.visibility = View.VISIBLE;
            }
            else {
                Toast.makeText(this, "You forgot to fill out one of the fields!", Toast.LENGTH_SHORT).show()
            }
        }

        // ------------- Creating a storage reference -----------------
        storage = FirebaseStorage.getInstance().reference.child("ImageFolder")

        // -------------------------- Managing the Image upload button ------------------------
        binding.uploadLawnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, ImageBack)
        }
    }

    // ---------------- Creating a function to upload the image to the database ------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageBack) {
            if(resultCode == RESULT_OK){
                val imageData = data!!.getData()
                val imageName: StorageReference = storage.child("image" + imageData!!.getLastPathSegment())
                imageName.putFile(imageData).addOnSuccessListener(OnSuccessListener {
                    imageName.getDownloadUrl().addOnSuccessListener ( OnSuccessListener<Uri> { uri ->
                        val dataBaseReference: DatabaseReference = FirebaseDatabase.getInstance()
                            .getReferenceFromUrl("https://gggsapplication-default-rtdb.firebaseio.com/").
                            child("RentALawn").child("$firstName $lastName").
                            child("Picture")
                        val hashMap:HashMap<String,String> = HashMap()
                        hashMap.put("imageUrl", uri.toString())
                        dataBaseReference.setValue(hashMap)
                        Toast.makeText(this, "Image Uploaded Successfully",Toast.LENGTH_SHORT).show()
                        this@ListALawnActivity.finish()
                    })
                })
            }
        }
    }
}