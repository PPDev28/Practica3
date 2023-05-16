package com.example.practica3.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.databinding.NavigationMainBinding

class NavigationActivity : AppCompatActivity() {

    private var _binding: NavigationMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = NavigationMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}