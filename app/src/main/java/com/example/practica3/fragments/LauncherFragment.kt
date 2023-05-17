package com.example.practica3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practica3.R
import com.example.practica3.databinding.FragmentLauncherBinding


class LauncherFragment : Fragment(R.layout.fragment_launcher){

    private var _binding: FragmentLauncherBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLauncherBinding.inflate(inflater, container, false)
        return _binding?.root
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}