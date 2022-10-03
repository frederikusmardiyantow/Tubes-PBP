package com.example.ugd3_d_0659

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.entity.MataPelajaran
import kotlinx.android.synthetic.main.rv_item_mata_pelajaran.*

class FragmentMataPelajaran : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mata_pelajaran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter: RVMataPelajaranAdapter = RVMataPelajaranAdapter(MataPelajaran.listOfMataPelajaran)

        //menghubungkan rvMataPelajaran dengan recycler view yang ada pada layout
        val rvMataPelajaran: RecyclerView = view.findViewById(R.id.rv_mata_pelajaran)

        //set layout manager dari recycler view
        rvMataPelajaran.layoutManager = layoutManager

        //tidak mengubah size recycler view jika terdapat item ditambahkan atau dikurangkan
        rvMataPelajaran.setHasFixedSize(true)

        //set adapter dari recycler view
        rvMataPelajaran.adapter = adapter

        adapter.onItemClick = {
            var mFragmentKelas = FragmentKelasIndonesia()

            (activity as HomeActivity).changeFragment(mFragmentKelas)

        }
    }
}

