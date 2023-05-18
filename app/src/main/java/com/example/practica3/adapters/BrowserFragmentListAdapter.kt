package com.example.practica3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica3.databinding.FragmentBrowserItemsBinding
import com.example.practica3.models.WebBrowserBo

class BrowserFragmentListAdapter(private val clickListener: IOnItemClickListener) :
    ListAdapter<WebBrowserBo, BrowserFragmentListAdapter.WebBrowserViewHolder>(DiffCallback) {

    interface IOnItemClickListener {
        fun onIconWebClickItem(position: Int, webBrowserBo: WebBrowserBo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebBrowserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentBrowserItemsBinding.inflate(layoutInflater, parent, false)
        return WebBrowserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WebBrowserViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class WebBrowserViewHolder(private val binding: FragmentBrowserItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webBrowserBo: WebBrowserBo, clickListener: IOnItemClickListener) {
            with(binding) {
                Glide.with(itemView.context).load(webBrowserBo.browserImage)
                    .into(binding.browserFragmentLogoImage)
                browserFragmentLogoImage.setImageResource(webBrowserBo.browserImage)
                browserFragmentTittle.text = webBrowserBo.browserName
                browserFragmentYear.text = webBrowserBo.browserCreationDate.toString()
                browserFragmentCompany.text = webBrowserBo.browserCompany
                browserFragmentIconMobile.isInvisible = !webBrowserBo.browserMobile

                browserFragmentIconWeb.setOnClickListener {
                    clickListener.onIconWebClickItem(adapterPosition, webBrowserBo)
                }
            }
        }
    }

    class DiffCallback() : DiffUtil.ItemCallback<WebBrowserBo>() {
        override fun areItemsTheSame(oldItem: WebBrowserBo, newItem: WebBrowserBo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WebBrowserBo, newItem: WebBrowserBo): Boolean {
            return oldItem.browserName == newItem.browserName
        }

    }
}
