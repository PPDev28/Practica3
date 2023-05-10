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
import com.example.practica3.enums.BrowserOSEnum
import com.example.practica3.models.WebBrowserBo
import com.example.practica3.providers.MockProvider.Companion.browserList
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BrowsersFragment : Fragment(R.layout.fragment_browser) {


    private val binding by lazy { FragmentBrowserBinding.inflate(layoutInflater) }
    private val browserAdapter by lazy { BrowserFragmentListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setUpAdapter()
        setupMenu()

    }

    private fun setUpAdapter() {

        browserAdapter.submitList(mockBrowser(5))
        binding.browserFragmentRecyclerView.adapter = browserAdapter
        binding.browserFragmentRecyclerView.layoutManager = LinearLayoutManager(context)

    }


    private fun setupMenu() {

        binding.broserFragmentToolbar.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.action_sorted -> showSortDialog()
                R.id.action_filter -> showFilterDialog()
            }
            true
        }
    }

    private fun showFilterDialog() {

        val opciones = BrowserOSEnum.values().map { it.name }.toTypedArray()
        val checkedItems = BooleanArray(opciones.size) { false }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Selecciona tus opciones")
            .setMultiChoiceItems(opciones, checkedItems) { dialog, which, isChecked ->
                // Aquí puedes manejar la lógica de lo que sucede cuando se selecciona o deselecciona una opción
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                // Aquí puedes manejar la lógica de lo que sucede cuando se pulsa el botón Aceptar
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun showSortDialog() {
        val radioButtonList = arrayOf("Nombre", "Compañía", "Creación")
        var selectedRadioButton = 0
        val builder = context?.let { MaterialAlertDialogBuilder(it) }
        builder?.setTitle("Ordenar")
        builder?.setSingleChoiceItems(radioButtonList, selectedRadioButton) { _, which ->
            selectedRadioButton = which
        }
        builder?.setPositiveButton("Aceptar") { _, _ ->
            when (selectedRadioButton) {
                0 -> {
                    browserAdapter.submitList(mockBrowser(5).sortedBy { it.browserName })
                }
                1 -> {
                    browserAdapter.submitList(mockBrowser(5).sortedBy { it.browserCompany })
                }
                2 -> {
                    browserAdapter.submitList(mockBrowser(5).sortedBy { it.browserCreationDate })
                }
            }
        }
        builder?.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder?.create()
        dialog?.show()
    }

    private fun mockBrowser(number: Int): List<WebBrowserBo> {

        return browserList.take(number).sortedBy { it.browserName }

//        val rootView: View = requireActivity().findViewById(android.R.id.content)
//            println("La lista solo tiene ${browserList.size} navegadores.")
//            Snackbar.make(rootView, "Mensaje de ejemplo", Snackbar.LENGTH_SHORT).show()

//        return if (number > browserList.size) {
//            println("La lista solo tiene ${browserList.size} navegadores.")
//            Snackbar.make(rootView, "Mensaje de ejemplo", Snackbar.LENGTH_SHORT).show()
//
//        } else {
//            browserList.take(number).sortedBy { it.browserName }
//        }

    }
}