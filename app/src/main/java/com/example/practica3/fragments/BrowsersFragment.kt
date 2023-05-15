package com.example.practica3.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica3.R
import com.example.practica3.adapters.BrowserFragmentListAdapter
import com.example.practica3.databinding.FragmentBrowserBinding
import com.example.practica3.enums.BrowserOSEnum
import com.example.practica3.models.WebBrowserBo
import com.example.practica3.models.WebBrowserDto
import com.example.practica3.models.WebBrowserDtoMapper
import com.example.practica3.retrofit.WebBrowserApiClient
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrowsersFragment : Fragment(R.layout.fragment_browser),
    BrowserFragmentListAdapter.IOnItemClickListener {

    private var _binding: FragmentBrowserBinding? = null
    private val binding get() = _binding
    private val browserAdapter by lazy { BrowserFragmentListAdapter(this) }
    private var savedState = mutableSetOf<BrowserOSEnum>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapterWithRetrofit()
        setupMenuFilters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMenuFilters() {

        binding?.broserFragmentToolbar?.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.browser_fragment_action_sorted -> showSortDialog()
                R.id.browser_fragment_action_filter -> showFilterDialog()
            }
            true
        }
    }

    private fun showFilterDialog() {
        val browsersSuitableOS = BrowserOSEnum.values().map { it.name }.toTypedArray()
        val checkedItems = BooleanArray(browsersSuitableOS.size) { false }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.menu_choose_options)
            .setMultiChoiceItems(browsersSuitableOS, checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }
            .setPositiveButton(R.string.menu_accept) { _, _ ->
                val selectedFilters = mutableSetOf<BrowserOSEnum>()

                for (i in browsersSuitableOS.indices) {
                    if (checkedItems[i]) {
                        selectedFilters.add(BrowserOSEnum.valueOf(browsersSuitableOS[i]))
                    }
                }

                savedState.addAll(selectedFilters)
                Log.e("Valores en savedState", savedState.toString())

                addNewChipToChipGroup(savedState,)
                applyChipFilters(savedState,)
            }
            .setNegativeButton(R.string.menu_decline, null)
            .show()
    }

    private fun addNewChipToChipGroup(filters: MutableSet<BrowserOSEnum>, ) {
        binding?.chipGroup?.removeAllViews()
        filters.forEach {
            val newChip = Chip(context)
            newChip.text = it.name
            newChip.isCloseIconVisible = true
            newChip.setChipBackgroundColorResource(R.color.project_blue_chips)
            newChip.tag = it.name
            binding?.chipGroup?.addView(newChip)
            newChip.setOnCloseIconClickListener {

                binding?.chipGroup?.removeView(newChip)
                val browserOS = BrowserOSEnum.valueOf(newChip.tag.toString())
                savedState.remove(browserOS)
                applyChipFilters(savedState,)

//                Log.e("CONTENIDO LISTA", webBrowsersBoList.toString())
//                Log.e("Valores en savedState click", savedState.toString())
            }
        }
    }

    private fun applyChipFilters(filters: MutableSet<BrowserOSEnum> ) {
        if (filters.isEmpty()) {
            browserAdapter.submitList(browserAdapter.currentList)
        } else {
            //TODO : no usar current list , sacar la lista de retrofit y pasarla utilizarla aqui
            browserAdapter.submitList(browserAdapter.currentList.filter { browser ->
                filters.all {
                    browser.compatibleOS.contains(it)
                }
            })
        }
        Log.e("Valores en Filtros", filters.toString())
    }

    private fun showSortDialog() {
        val radioButtonList = arrayOf("Nombre", "Compañía", "Creación")
        var selectedRadioButton = 0

        val builder = context?.let { MaterialAlertDialogBuilder(it) }
        builder?.setTitle(R.string.sort)
        builder?.setSingleChoiceItems(radioButtonList, selectedRadioButton) { _, which ->
            selectedRadioButton = which
        }
        builder?.setPositiveButton(R.string.menu_accept) { _, _ ->
            when (selectedRadioButton) {
                0 -> {
                    browserAdapter.submitList(browserAdapter.currentList.sortedBy { it.browserName })
                }
                1 -> {
                    browserAdapter.submitList(browserAdapter.currentList.sortedBy { it.browserCompany })
                }
                2 -> {
                    browserAdapter.submitList(browserAdapter.currentList.sortedBy { it.browserCreationDate })
                }
            }
        }
        builder?.setNegativeButton(R.string.menu_decline) { dialog, _ ->
            dialog.dismiss()
        }
        val sortDialog = builder?.create()
        sortDialog?.show()
    }

    private fun setUpAdapterWithRetrofit() {
        val call = WebBrowserApiClient.getWebBrowserService()?.getWebBrowsers()
        call?.enqueue(object : Callback<List<WebBrowserDto>> {
            override fun onResponse(
                call: Call<List<WebBrowserDto>>,
                response: Response<List<WebBrowserDto>>
            ) {
                val webBrowsersDtoList = response.body()
                val webBrowsersBoList =
                    (webBrowsersDtoList?.map { dto -> WebBrowserDtoMapper().map(dto) } ?: mutableListOf()) as MutableList<WebBrowserBo>
                binding?.browserFragmentRecyclerView?.adapter = browserAdapter
                binding?.browserFragmentRecyclerView?.layoutManager = LinearLayoutManager(context)
                browserAdapter.submitList(webBrowsersBoList)


                Log.e("Lista en funcion", webBrowsersBoList.toString())
            }

            override fun onFailure(call: Call<List<WebBrowserDto>>, t: Throwable) {
                Log.e(TAG, "Error con la lista de navegadores", t)

            }
        })
    }


    override fun onIconWebClickItem(position: Int, webBrowserBo: WebBrowserBo) {

        val bundleGetData = Bundle()
        bundleGetData.putString("browserName", webBrowserBo.browserName)
        bundleGetData.putString("browserCompany", webBrowserBo.browserCompany)
        bundleGetData.putString("browserUrl", webBrowserBo.browserWeb)

        findNavController().navigate(
            R.id.action_browsersFragment_to_webViewFragment,
            bundleGetData
        )
    }
}


