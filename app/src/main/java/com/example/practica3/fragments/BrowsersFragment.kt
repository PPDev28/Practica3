package com.example.practica3.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica3.R
import com.example.practica3.adapters.BrowserFragmentListAdapter
import com.example.practica3.databinding.FragmentBrowserBinding
import com.example.practica3.enums.BrowserOSEnum
import com.example.practica3.models.WebBrowserBo

class BrowsersFragment : Fragment(R.layout.fragment_browser) {


    private lateinit var binding: FragmentBrowserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
//        val adapter = BrowserFragmentListAdapter()
//        binding.browserFragmentRecyclerView.adapter = adapter
//        binding.browserFragmentRecyclerView.layoutManager = LinearLayoutManager(this@BrowsersFragment)
//        adapter.submitList(mockBrowser(1))

        val lista = listOf(
            WebBrowserBo(
                "Chrome",
                "Google",
                2008,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.UNKNOWN
            ),
            WebBrowserBo(
                "Firefox",
                "Google",
                2008,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.UNKNOWN
            )

        )

        val browsers = mockBrowser(2)
        Log.e("browsers list ", browsers.toString())
        val adapter = BrowserFragmentListAdapter(lista)
        adapter.submitList(browsers)
        binding.browserFragmentRecyclerView.adapter = adapter
        binding.browserFragmentRecyclerView.layoutManager = LinearLayoutManager(context)

    }

    private fun mockBrowser(number: Int): List<WebBrowserBo> {
            val lista = listOf(
                WebBrowserBo(
                    "Chrome",
                    "Google",
                    2008,
                    R.drawable.logo_alten,
                    "www.alten.es",
                    true,
                    BrowserOSEnum.UNKNOWN
                )
            )
        return lista
    }
}