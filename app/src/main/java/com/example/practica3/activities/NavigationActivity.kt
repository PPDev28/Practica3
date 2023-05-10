package com.example.practica3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.practica3.R
import com.example.practica3.fragments.BrowsersFragment

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_main)

        supportFragmentManager.commit {
            replace(R.id.nav_host_fragment, BrowsersFragment())
        }

    }
}