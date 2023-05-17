package com.example.practica3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.practica3.R
import com.example.practica3.constants.BROWSER_COMPANY
import com.example.practica3.constants.BROWSER_NAME
import com.example.practica3.constants.BROWSER_URL
import com.example.practica3.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {

    private var binding: FragmentWebViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbarIcon()
        setOptionsWebView()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setUpToolbarIcon() {
        binding?.webViewToolbar?.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding?.webViewToolbar?.setNavigationOnClickListener {

            findNavController().navigate(R.id.action_webViewFragment_to_browsersFragment)
        }

        val titleName = arguments?.getString(BROWSER_NAME)
        val titleCompany = arguments?.getString(BROWSER_COMPANY)
        val tittle = StringBuilder().append(titleCompany).append(" $titleName").toString()
        binding?.webViewToolbar?.title = tittle
    }

    private fun setOptionsWebView() {

        arguments?.getString(BROWSER_URL)?.let { binding?.webView?.loadUrl(it) }

        binding?.webView?.settings?.setSupportZoom(true)
        binding?.webView?.settings?.builtInZoomControls = true
        binding?.webView?.settings?.displayZoomControls = true
    }
}