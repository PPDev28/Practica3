package com.example.practica3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica3.R
import com.example.practica3.adapters.BrowserFragmentListAdapter
import com.example.practica3.databinding.FragmentBrowserBinding
import com.example.practica3.enums.BrowserOSEnum
import com.example.practica3.models.WebBrowserBo
import com.example.practica3.providers.MockProvider.Companion.browserList
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BrowsersFragment : Fragment(R.layout.fragment_browser),
    BrowserFragmentListAdapter.IOnItemClickListener {

    private var _binding: FragmentBrowserBinding? = null
    private val binding get() = _binding
    private val browserAdapter by lazy { BrowserFragmentListAdapter(this) }
    private val browserListWithMockFun = mockBrowser(6)
    private var savedState = mutableSetOf<BrowserOSEnum>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        setupMenuFilters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAdapter() {

        browserAdapter.submitList(browserListWithMockFun)
        binding?.browserFragmentRecyclerView?.adapter = browserAdapter
        binding?.browserFragmentRecyclerView?.layoutManager = LinearLayoutManager(context)
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

                addNewChipToChipGroup(savedState)
                applyChipFilters(savedState)
            }
            .setNegativeButton(R.string.menu_decline, null)
            .show()
    }

    private fun addNewChipToChipGroup(filters: MutableSet<BrowserOSEnum>) {
        binding?.chipGroup?.removeAllViews()
        filters.forEach { it ->
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
                applyChipFilters(savedState)
            }
        }
    }

    private fun applyChipFilters(filters: Set<BrowserOSEnum>) {
        browserAdapter.submitList(browserListWithMockFun.filter { browser ->
            filters.all { browser.compatibleOS.contains(it) }
        })
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
                    browserAdapter.submitList(browserListWithMockFun.sortedBy { it.browserName })
                }
                1 -> {
                    browserAdapter.submitList(browserListWithMockFun.sortedBy { it.browserCompany })
                }
                2 -> {
                    browserAdapter.submitList(browserListWithMockFun.sortedBy { it.browserCreationDate })
                }
            }
        }
        builder?.setNegativeButton(R.string.menu_decline) { dialog, _ ->
            dialog.dismiss()
        }
        val sortDialog = builder?.create()
        sortDialog?.show()
    }

    private fun mockBrowser(number: Int): List<WebBrowserBo> {

        if (number > browserList.size) {

            Toast.makeText(
                context,
                "No hay tantos navegadores para mostrar",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        return browserList.take(number).sortedBy { it.browserName }
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


