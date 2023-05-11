package com.example.practica3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practica3.R
import com.example.practica3.databinding.FragmentLauncherBinding


class LauncherFragment : Fragment(R.layout.fragment_launcher){

    private val binding by lazy { FragmentLauncherBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}