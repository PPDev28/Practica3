package com.example.practica3.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.practica3.R


class LauncherFragment : Fragment(R.layout.fragment_launcher) {

    private lateinit var timer: CountDownTimer

    companion object {
        const val SPLASH_SCREEN_TIMEOUT = 1000L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        showSplashScreen()

//        val newFragment = BrowsersFragment()
//        val transaction = requireFragmentManager().beginTransaction()
//        transaction.replace(R.id.action_launcherFragment_to_browsersFragment2, newFragment)
//        transaction.addToBackStack(null) // agregar a la pila
//        transaction.commit()

    }

    private fun showSplashScreen() {
        timer = object : CountDownTimer(SPLASH_SCREEN_TIMEOUT, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                findNavController().navigate(R.id.action_launcherFragment_to_browsersFragment2)
            }
        }
        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }
}