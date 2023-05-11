package com.example.practica3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.practica3.R
import com.example.practica3.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val binding by lazy { FragmentWebViewBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.loadUrl("https://www.alten.es/")

        setUpToolbarIcon()
    }

    private fun setUpToolbarIcon(){
        binding.webViewToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.webViewToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_webViewFragment_to_browsersFragment)
        }

        binding.webViewToolbar.title = arguments?.getString("browserName")


    }

}