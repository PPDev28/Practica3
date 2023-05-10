package com.example.practica3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica3.R
import com.example.practica3.adapters.BrowserFragmentListAdapter
import com.example.practica3.databinding.FragmentBrowserBinding
import com.example.practica3.models.WebBrowserBo
import com.example.practica3.providers.MockProvider.Companion.browserLista
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BrowsersFragment : Fragment(R.layout.fragment_browser) {


    private val binding by lazy { FragmentBrowserBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setUpAdapter()
        setupMenu()

    }

    private fun setUpAdapter() {


        val adapter = BrowserFragmentListAdapter()
        adapter.submitList(mockBrowser(4))
        binding.browserFragmentRecyclerView.adapter = adapter
        binding.browserFragmentRecyclerView.layoutManager = LinearLayoutManager(context)

    }


    private fun setupMenu() {

        binding.broserFragmentToolbar.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.action_sorted-> showSortDialog()
                R.id.action_filter -> showFilterDialog()
            }
            true
        }
    }

    private fun showFilterDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Filtrar")
            .setMessage("Navegadores")
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }


    private fun showSortDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Ordenar")
            .setMessage("Navegadores")
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun mockBrowser(number: Int): List<WebBrowserBo> {

        return if (number > browserLista.size) {
            println("La lista solo tiene ${browserLista.size} personas.")
            emptyList()

        } else {
            browserLista.take(number)
        }

    }
}