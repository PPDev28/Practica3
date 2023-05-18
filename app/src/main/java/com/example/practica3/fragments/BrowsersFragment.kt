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
import com.example.practica3.constants.*
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

class BrowsersFragment : Fragment(),
    BrowserFragmentListAdapter.IOnItemClickListener {

    private var binding: FragmentBrowserBinding? = null
    private val browserAdapter by lazy { BrowserFragmentListAdapter(this) }
    private var savedStateCheckBoxes = mutableSetOf<BrowserOSEnum>()
    private var webBrowserList: MutableList<WebBrowserBo> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpAdapterWithRetrofit()
        setupMenuFilters()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
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

                dialogCheckedItemsValue(browsersSuitableOS, checkedItems)
                addNewChipToChipGroup(savedStateCheckBoxes)
                applyChipFilters(savedStateCheckBoxes)
            }
            .setNegativeButton(R.string.menu_decline, null)
            .show()
    }

    private fun dialogCheckedItemsValue(
        browsersSuitableOS: Array<String>,
        checkedItems: BooleanArray
    ) {
        val selectedFilters = mutableSetOf<BrowserOSEnum>()

        for (i in browsersSuitableOS.indices) {
            if (checkedItems[i]) {
                selectedFilters.add(BrowserOSEnum.valueOf(browsersSuitableOS[i]))
            }
        }

        savedStateCheckBoxes.addAll(selectedFilters)
    }

    private fun addNewChipToChipGroup(savedStateCheckBoxes: MutableSet<BrowserOSEnum>) {
        binding?.chipGroup?.removeAllViews()

        savedStateCheckBoxes.forEach {
            val newChip = Chip(context)
            newChip.text = it.name
            newChip.isCloseIconVisible = true
            newChip.setChipBackgroundColorResource(R.color.project_blue_chips)
            newChip.tag = it.name
            binding?.chipGroup?.addView(newChip)
            newChip.setOnCloseIconClickListener {

                binding?.chipGroup?.removeView(newChip)
                val browserOS = BrowserOSEnum.valueOf(newChip.tag.toString())

                savedStateCheckBoxes.remove(browserOS)
                applyChipFilters(savedStateCheckBoxes)
            }
        }
    }

    private fun applyChipFilters(savedStateCheckBoxes: MutableSet<BrowserOSEnum>) {

        if (savedStateCheckBoxes.isNotEmpty()) {

            val filteredList = webBrowserList.filter { browserOS ->
                savedStateCheckBoxes.all {
                    browserOS.compatibleOS.contains(it)
                }
            }
            browserAdapter.submitList(filteredList)

        } else {
            browserAdapter.submitList(webBrowserList)
        }

    }

    private fun showSortDialog() {
        val radioButtonList = arrayOf("Nombre", "Compañía", "Creación")
        var selectedRadioButton = 0

        val dialogBuilder = context?.let { MaterialAlertDialogBuilder(it) }
        dialogBuilder?.setTitle(R.string.sort)
        dialogBuilder?.setSingleChoiceItems(radioButtonList, selectedRadioButton) { _, which ->
            selectedRadioButton = which
        }
        dialogBuilder?.setPositiveButton(R.string.menu_accept) { _, _ ->
            when (selectedRadioButton) {
                SORT_BY_NAME -> {
                    browserAdapter.submitList(browserAdapter.currentList.sortedBy { it.browserName })
                }
                SORT_BY_COMPANY -> {
                    browserAdapter.submitList(browserAdapter.currentList.sortedBy { it.browserCompany })
                }
                SORT_BY_CREATION_DATE -> {
                    browserAdapter.submitList(browserAdapter.currentList.sortedBy { it.browserCreationDate })
                }
            }
        }
        dialogBuilder?.setNegativeButton(R.string.menu_decline) { dialog, _ ->
            dialog.dismiss()
        }
        val sortDialog = dialogBuilder?.create()
        sortDialog?.show()
    }


    private fun setUpAdapterWithRetrofit() {

        binding?.browserFragmentRecyclerView?.adapter = browserAdapter
        binding?.browserFragmentRecyclerView?.layoutManager = LinearLayoutManager(context)

        val call = WebBrowserApiClient.getWebBrowserService()?.getWebBrowsers()
        call?.enqueue(object : Callback<List<WebBrowserDto>> {
            override fun onResponse(
                call: Call<List<WebBrowserDto>>,
                response: Response<List<WebBrowserDto>>
            ) {
                val webBrowsersDtoList = response.body()
                val webBrowserBoList2 =
                    (webBrowsersDtoList?.map { dto -> WebBrowserDtoMapper().map(dto) }
                        ?: mutableListOf()) as MutableList<WebBrowserBo>

                this@BrowsersFragment.webBrowserList.clear()
                this@BrowsersFragment.webBrowserList.addAll(webBrowserBoList2)
                browserAdapter.submitList(webBrowserBoList2)
            }

            override fun onFailure(call: Call<List<WebBrowserDto>>, error: Throwable) {
                Log.e(TAG, "Error with the web browser list", error)
            }
        })
    }

    override fun onIconWebClickItem(position: Int, webBrowserBo: WebBrowserBo) {

        val bundleGetData = Bundle()
        bundleGetData.putString(BROWSER_NAME, webBrowserBo.browserName)
        bundleGetData.putString(BROWSER_COMPANY, webBrowserBo.browserCompany)
        bundleGetData.putString(BROWSER_URL, webBrowserBo.browserWeb)

        findNavController().navigate(
            R.id.action_browsers_fragment_to_web_view_fragment,
            bundleGetData
        )
    }
}

