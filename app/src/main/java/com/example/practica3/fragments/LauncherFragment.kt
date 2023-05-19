package com.example.practica3.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.practica3.R
import com.example.practica3.databinding.FragmentLauncherBinding


class LauncherFragment : Fragment() {
    companion object {
        const val SPLASH_SCREEN_TIMEOUT = 1000L
    }

    private var binding: FragmentLauncherBinding? = null
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLauncherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showSplashScreen()
    }

    override fun onDestroyView() {
        binding = null
        timer.cancel()
        super.onDestroyView()
    }

    private fun showSplashScreen() {
        timer = object : CountDownTimer(SPLASH_SCREEN_TIMEOUT, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                /* no-op */
            }

            override fun onFinish() {
                val navigate = findNavController()
                navigate.navigate(R.id.action_launcher_fragment_to_browsers_fragment)
            }
        }
        timer.start()
    }
}