package com.example.ugd3_d_0659

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.entity.MataPelajaran
import com.example.ugd3_d_0659.view_binding_pelajaran.PelajaranIndonesia7
import kotlinx.android.synthetic.main.fragment_kelas.*
import kotlinx.android.synthetic.main.fragment_profil.*

class FragmentKelasIndonesia : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_kelas_vii.setOnClickListener(View.OnClickListener{
            var mFragmentPelajaranIndonesia = PelajaranIndonesia7()

            (activity as HomeActivity).changeFragment(mFragmentPelajaranIndonesia)
        })
    }

}