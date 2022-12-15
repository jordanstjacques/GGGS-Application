package com.example.gggsapplication.Fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gggsapplication.NewsAdapter
import com.example.gggsapplication.NewsImage
import com.example.gggsapplication.NewsSlide
import com.example.gggsapplication.R
import com.example.gggsapplication.RentALawn.LawnAdapter
import com.example.gggsapplication.RentALawn.LawnListing
import com.example.gggsapplication.RentALawn.ListingImage
import com.example.gggsapplication.databinding.FragmentHomeBinding
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// Creating lateinit variables for the database reference, and Array Lists of lawns and lawn images hh
private lateinit var databaseReference : DatabaseReference
private lateinit var newsArrayList : ArrayList<NewsSlide>
private lateinit var newsImageList : ArrayList<NewsImage>

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding

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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsArrayList = arrayListOf<NewsSlide>()
        newsImageList = arrayListOf<NewsImage>()
        binding.NewsSlidesRecView.apply {
            adapter = NewsAdapter(newsArrayList, newsImageList, context)
        }

        binding.NewsSlidesRecView.layoutManager = LinearLayoutManager(context)
        binding.NewsSlidesRecView.setHasFixedSize(true)
        getNewsData();
    }

    private fun getNewsData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("News")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                newsArrayList.clear()
                newsImageList.clear()
                if (snapshot.exists()) {
                    for (lawnSnapshot in snapshot.children) {
                        val news = lawnSnapshot.getValue(NewsSlide::class.java)
                        newsArrayList.add(news!!)
                        val imageUrl = lawnSnapshot.getValue(NewsImage::class.java)
                        try {
                            newsImageList.add(imageUrl!!)
                        } catch (e: java.lang.NullPointerException) {
                            newsImageList.add(NewsImage(("https://firebasestorage.googleapis.com/v0/b/gggsapplication.appspot.com/o/ImageFolder%2Fimageimage%3A61?alt=media&token=09f0edfd-e15d-407e-bd05-4825651fdc7b")))
                        }

                        var adapter = NewsAdapter(newsArrayList, newsImageList, requireContext())
                        binding.NewsSlidesRecView.adapter = adapter

                        adapter.setOnButtonClickListener(
                            object : NewsAdapter.onButtonClickListener{
                                override fun onButtonClicked(websiteUrl : String) {
                                    try{
                                        val uri = Uri.parse(websiteUrl)
                                        val intent = Intent(Intent.ACTION_VIEW, uri)
                                        startActivity(intent)
                                    }
                                    catch(e : ActivityNotFoundException){
                                    }
                                }
                            })
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}