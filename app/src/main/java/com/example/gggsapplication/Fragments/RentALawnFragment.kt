package com.example.gggsapplication.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.gggsapplication.MainActivity
import com.example.gggsapplication.R
import com.example.gggsapplication.RentALawn.LawnAdapter
import com.example.gggsapplication.RentALawn.LawnListing
import com.example.gggsapplication.RentALawn.ListALawnActivity
import com.example.gggsapplication.RentALawn.ListingImage
import com.example.gggsapplication.databinding.FragmentRentALawnBinding
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// Creating lateinit variables for the database reference, and Array Lists of lawns and lawn images
private lateinit var databaseReference : DatabaseReference
private lateinit var lawnArrayList : ArrayList<LawnListing>
private lateinit var lawnImageList : ArrayList<ListingImage>
private lateinit var lawnOwnerList : ArrayList<String>

/**
 * A simple [Fragment] subclass.
 * Use the [RentALawnFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentALawnFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRentALawnBinding

    // ------------------- Creating multiple late init variables for Firebase utensils ----------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRentALawnBinding.inflate(layoutInflater)
        binding.btnAddLawn.setOnClickListener {
            val intent = Intent(this@RentALawnFragment.requireContext(), ListALawnActivity::class.java)
            startActivity(intent)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lawnArrayList = arrayListOf<LawnListing>()
        lawnImageList = arrayListOf<ListingImage>()
        lawnOwnerList = arrayListOf<String>()
        binding.ListALawnRecyclerView.apply {
            adapter = LawnAdapter(lawnArrayList, lawnImageList, lawnOwnerList, context)
        }

        binding.ListALawnRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.ListALawnRecyclerView.setHasFixedSize(true)
        getLawnData()
    }

    private fun getLawnData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("RentALawn")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                lawnArrayList.clear()
                lawnImageList.clear()
                if (snapshot.exists()) {
                    for (lawnSnapshot in snapshot.children) {
                        val ownerName = lawnSnapshot.key.toString()
                        lawnOwnerList.add(ownerName)
                        val lawn = lawnSnapshot.getValue(LawnListing::class.java)
                        lawnArrayList.add(lawn!!)
                        val imageUrl = lawnSnapshot.child("Picture").getValue(ListingImage::class.java)
                        try {
                            lawnImageList.add(imageUrl!!)
                        } catch (e: java.lang.NullPointerException) {
                            lawnImageList.add(ListingImage(("https://firebasestorage.googleapis.com/v0/b/gggsapplication.appspot.com/o/ImageFolder%2Fimageimage%3A61?alt=media&token=09f0edfd-e15d-407e-bd05-4825651fdc7b")))
                        }
                        binding.ListALawnRecyclerView.adapter = LawnAdapter(lawnArrayList, lawnImageList, lawnOwnerList, requireContext())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RentALawnFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RentALawnFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}