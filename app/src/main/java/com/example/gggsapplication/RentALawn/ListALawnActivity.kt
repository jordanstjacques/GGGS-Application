package com.example.gggsapplication.RentALawn

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.gggsapplication.FeaturedHouses.FeaturedHouse
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
    val ImageBack = 1
    lateinit var storage : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAlawnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance().reference.child("ImageFolder")

        // -------------------------- Managing the Image upload button ------------------------
        binding.uploadLawnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, ImageBack)
        }

        // -------------------------- Managing the Submit button ---------------------------------
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageBack) {
            if(resultCode == RESULT_OK){
                val imageData = data!!.getData()
                val imageName: StorageReference = storage.child("image" + imageData!!.getLastPathSegment())

                imageName.putFile(imageData).addOnSuccessListener(OnSuccessListener {
                    imageName.getDownloadUrl().addOnSuccessListener ( OnSuccessListener<Uri> { uri ->
                        val dataBaseReference: DatabaseReference = FirebaseDatabase.getInstance()
                            .getReferenceFromUrl("https://gggsapplication-default-rtdb.firebaseio.com/").child("Image")
                        val hashMap:HashMap<String,String> = HashMap()
                        hashMap.put("imageUrl", uri.toString())
                        dataBaseReference.setValue(hashMap)
                        Toast.makeText(this, "Data was incerted",Toast.LENGTH_SHORT).show()
                    })
                })
            }
        }
    }
}