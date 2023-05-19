package com.example.practica3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practica3.databinding.NavigationMainBinding

class NavigationActivity : AppCompatActivity() {

    private var binding: NavigationMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavigationMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}