package com.example.ugd3_d_0659.view_binding_pelajaran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ugd3_d_0659.databinding.FragmentPelajaranIndonesiaBinding

class PelajaranIndonesia7 : Fragment() {

    var _binding: FragmentPelajaranIndonesiaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPelajaranIndonesiaBinding.inflate(inflater, container, false)
        val adapter = PelajaranIndonesiaAdapter(MateriIndonesia7List.materiIndonesiaList)
        _binding?.materiRv?.adapter = adapter

        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.tvIsiMataPelajaranDiAyoBelajar?.text = "Bahasa Indonesia"
        _binding?.tvIsiKelasDiAyoBelajar?.text = "VII"
        _binding?.tvIsiPengajarDiAyoBelajar?.text = "Tania Syari"
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}