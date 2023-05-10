package com.example.practica3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practica3.databinding.FragmentBrowserItemsBinding
import com.example.practica3.models.WebBrowserBo

class BrowserFragmentListAdapter :
    ListAdapter<WebBrowserBo, BrowserFragmentListAdapter.WebBrowserViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<WebBrowserBo>() {
        override fun areItemsTheSame(oldItem: WebBrowserBo, newItem: WebBrowserBo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WebBrowserBo, newItem: WebBrowserBo): Boolean {
            return oldItem.browserName == newItem.browserName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebBrowserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentBrowserItemsBinding.inflate(layoutInflater, parent, false)
        return WebBrowserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WebBrowserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WebBrowserViewHolder(private val binding: FragmentBrowserItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webBrowserBo: WebBrowserBo) {

            binding.browserFragmentLogoImage.setImageResource(webBrowserBo.browserImage)
            binding.browserFragmentTittle.text = webBrowserBo.browserName
            binding.browserFragmentYear.text = webBrowserBo.browserCreationDate.toString()
            binding.browserFragmentCompany.text = webBrowserBo.browserCompany
            binding.browserFragmentIconMobile.isInvisible = !webBrowserBo.browserMobile


        }
    }
}
