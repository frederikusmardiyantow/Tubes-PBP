package com.example.ugd3_d_0659

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.PagerIndicatorForRecyclerViews.OnPagerNumberChangeListener
import com.example.ugd3_d_0659.PagerIndicatorForRecyclerViews.RVMPFavoritAdapter
import com.example.ugd3_d_0659.databinding.FragmentHomeBinding
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator

class FragmentHome : Fragment(), OnPagerNumberChangeListener {
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var binding:FragmentHomeBinding? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var pagerIndicator: IndefinitePagerIndicator
    private lateinit var pagerIndicatorHorizontal: IndefinitePagerIndicator
    private var isVerticalEnabled = false
    private var recyclerViewAdapter: RVMPFavoritAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding!!.root

        bindViews(view)
        setupViews()

        return view
        //inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun bindViews(view: View) {
        with(view) {
            recyclerView = findViewById(R.id.recyclerview)
            pagerIndicator = findViewById(R.id.recyclerview_pager_indicator)
            pagerIndicatorHorizontal = findViewById(R.id.recyclerview_pager_indicator_horizontal)
        }
    }

    private fun setupViews() {
        recyclerViewAdapter = RVMPFavoritAdapter(requireContext())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        if (isVerticalEnabled) {
            pagerIndicator.attachToRecyclerView(recyclerView)
            pagerIndicator.visibility = View.VISIBLE
        } else {
            pagerIndicatorHorizontal.attachToRecyclerView(recyclerView)
            pagerIndicatorHorizontal.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val namaUserLogin = sharedPreferences!!.getString("nama","")

        binding?.nama?.setText(namaUserLogin)

    }

    override fun onPagerNumberChanged() {
        recyclerViewAdapter?.notifyDataSetChanged()
    }
}