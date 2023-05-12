package com.example.practica3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
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

    //    private val binding by lazy { FragmentBrowserbinding?.inflate(layoutInflater) }
    private val browserAdapter by lazy { BrowserFragmentListAdapter(this) }
    private val browserListWithMockFun = mockBrowser(6)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        binding?.chipGroup.setOnCheckedChangeListener { group, checkedId ->
//            val position = binding?.chipGroup.indexOfChild(group.findViewById((checkedId)))
//            binding?.chipGroup.removeViewAt(position)
//        }
//
        setUpAdapter()
        setupMenu()

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


    private fun setupMenu() {

        binding?.broserFragmentToolbar?.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.action_sorted -> showSortDialog()
                R.id.action_filter -> showFilterDialog()
            }
            true
        }
    }

    private fun showFilterDialog() {

        val browsersOS = BrowserOSEnum.values().map { it.name }.toTypedArray()
        val checkedItems = BooleanArray(browsersOS.size) { false }

        MaterialAlertDialogBuilder(requireContext()).setTitle("Selecciona tus opciones")
            .setMultiChoiceItems(browsersOS, checkedItems) { dialog, which, isChecked ->
                checkedItems[which] = isChecked

            }.setPositiveButton("Aceptar") { dialog, which ->
                val saveState = mutableSetOf<BrowserOSEnum>()

                browsersOS.indices.forEach {
                    if (checkedItems[it]) {
                        saveState.add(BrowserOSEnum.valueOf(browsersOS[it]))
                        addFiltertoChip(saveState)
                    }
                }

            }.setNegativeButton("Cancelar", null).show()
    }

    private fun addFiltertoChip(saveState: Set<BrowserOSEnum>) {

        saveState.forEach { it ->

            when (it.name) {
                BrowserOSEnum.Windows.name -> newChipSettings(it.name, saveState)
                BrowserOSEnum.MacOs.name -> newChipSettings(it.name, saveState)
                BrowserOSEnum.Linux.name -> newChipSettings(it.name, saveState)
                BrowserOSEnum.Android.name -> newChipSettings(it.name, saveState)
                BrowserOSEnum.IOS.name -> newChipSettings(it.name, saveState)
                BrowserOSEnum.ChromeOS.name -> newChipSettings(it.name, saveState)
            }
        }

    }

    private fun newChipSettings(itName: String, saveState: Set<BrowserOSEnum>) {

        val compatibleOS = saveState.joinToString(", ") { it.name }
        val existingChip = binding?.chipGroup?.findViewWithTag<Chip>(saveState.first())

        if (existingChip != null) {
            existingChip.text = compatibleOS
        } else {
            val newChip = Chip(context)
            browserAdapter.submitList(browserListWithMockFun.filter {
                it.compatibleOS.containsAll(
                    saveState
                )
            })
            newChip.text = compatibleOS
            newChip.isCloseIconVisible = true
            binding?.chipGroup?.addView(newChip)
            newChip.tag = saveState.first()


            newChip.setOnCloseIconClickListener {
                binding?.chipGroup?.removeView(newChip)
                browserAdapter.submitList(browserListWithMockFun.filter {
                    it.compatibleOS.containsAll(
                        binding?.chipGroup?.children?.map { chip -> BrowserOSEnum.valueOf(chip.tag.toString()) }
                            ?.toList() ?: emptyList()
                    )
                })
            }
        }
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
        builder?.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder?.create()
        dialog?.show()
    }

    private fun mockBrowser(number: Int): List<WebBrowserBo> {

        if (number > browserList.size) {

            Toast.makeText(context, "No hay tantos navegadores para mostrar", Toast.LENGTH_SHORT)
                .show()

        }

        return browserList.take(number).sortedBy { it.browserName }
    }

    override fun onIconWebClickItem(position: Int, webBrowserBo: WebBrowserBo) {

        val bundleGetData = Bundle()
        bundleGetData.putString("name", webBrowserBo.browserName)
        bundleGetData.putString("url", webBrowserBo.browserWeb)

        findNavController().navigate(R.id.action_browsersFragment_to_webViewFragment, bundleGetData)


    }
}

