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

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbarIcon()
        setOptionsWebView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpToolbarIcon() {
        binding?.webViewToolbar?.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding?.webViewToolbar?.setNavigationOnClickListener {

            findNavController().navigate(R.id.action_webViewFragment_to_browsersFragment)
        }

        val titleName = arguments?.getString("browserName")
        val titleCompany = arguments?.getString("browserCompany")
        val tittle = StringBuilder().append(titleCompany).append(" $titleName").toString()
        binding?.webViewToolbar?.title = tittle
    }

    private fun setOptionsWebView() {

        arguments?.getString("browserUrl")?.let { binding?.webView?.loadUrl(it) }

        binding?.webView?.settings?.setSupportZoom(true)
        binding?.webView?.settings?.builtInZoomControls = true
        binding?.webView?.settings?.displayZoomControls = true
    }
}