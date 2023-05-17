package com.example.practica3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practica3.databinding.NavigationMainBinding

class NavigationActivity : AppCompatActivity() {

    private val binding by lazy { NavigationMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}