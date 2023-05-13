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


class LauncherFragment : Fragment(R.layout.fragment_launcher) {

    private val binding by lazy { FragmentLauncherBinding.inflate(layoutInflater) }
    private lateinit var timer: CountDownTimer

    companion object {
        const val SPLASH_SCREEN_TIMEOUT = 1000L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showSplashScreen()
    }

    private fun showSplashScreen() {
        timer = object : CountDownTimer(SPLASH_SCREEN_TIMEOUT, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                val navigate = findNavController()
                navigate.navigate(R.id.action_launcherFragment_to_browsersFragment)
            }
        }
        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }
}