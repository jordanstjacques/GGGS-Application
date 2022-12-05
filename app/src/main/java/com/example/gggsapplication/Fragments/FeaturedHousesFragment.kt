package com.example.gggsapplication.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gggsapplication.FeaturedHouses.AddingFeaturedHouse
import com.example.gggsapplication.FeaturedHouses.FeaturedHomeAdapter
import com.example.gggsapplication.FeaturedHouses.FeaturedHouse
import com.example.gggsapplication.R
import com.example.gggsapplication.RentALawn.ListALawnActivity
import com.example.gggsapplication.databinding.FragmentFeaturedHousesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FeaturedHousesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeaturedHousesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var featuredHouses = mutableListOf<FeaturedHouse>()
    private lateinit var binding: FragmentFeaturedHousesBinding
    private lateinit var featuredHomeAdapter: FeaturedHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var databaseReference = FirebaseDatabase.getInstance().getReference("FeaturedHouses")
        //val featuredHouses = databaseReference.orderByChild("uid")
        //println(featuredHouses)
        databaseReference.addValueEventListener(object : ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()) {
                   featuredHouses.clear()
                   println("on data change called nithin")
                   for (featuredHouseSnapshot in snapshot.children) {
                       featuredHouseSnapshot.getValue<FeaturedHouse>()
                           ?.let { featuredHouses.add(it) }
                   }
               }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }

        })
        featuredHomeAdapter = FeaturedHomeAdapter(featuredHouses)
        binding.FeaturedHousesRecyclerView.setHasFixedSize(true)
        binding.FeaturedHousesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.FeaturedHousesRecyclerView.adapter = featuredHomeAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeaturedHousesBinding.inflate(layoutInflater)
        val user = FirebaseAuth.getInstance().currentUser
        binding.btnAddHouse.setOnClickListener{
            if (user != null) {
                val intent = Intent(
                    this@FeaturedHousesFragment.requireContext(),
                    AddingFeaturedHouse::class.java
                )
                startActivity(intent)
            } else {
                Toast.makeText(this@FeaturedHousesFragment.requireContext(),
                    "Please sign-in or sign-up before adding a featured house",
                    Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FeaturedHousesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeaturedHousesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}