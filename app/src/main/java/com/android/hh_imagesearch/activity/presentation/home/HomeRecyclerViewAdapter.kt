package com.android.hh_imagesearch.activity.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.hh_imagesearch.activity.data.model.ContentModel
import com.android.hh_imagesearch.activity.presentation.util.Util
import com.android.hh_imagesearch.databinding.RecyclerviewHomeHolderBinding
import com.android.hh_imagesearch.databinding.RecyclerviewHomeHolderLoadingBinding
import com.bumptech.glide.Glide

const val LOADING = 0
const val ITEM = 1

class HomeRecyclerViewAdapter(
   private val itemClickListener: (item: ContentModel) -> Unit
) : ListAdapter<ContentModel, ViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ContentModel>() {
            override fun areItemsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
                return oldItem.uId == newItem.uId
            }
            override fun areContentsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
        is ContentModel -> ITEM
            else -> LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(viewType == ITEM) {
            val binding =
                RecyclerviewHomeHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                ItemHolder(binding, itemClickListener)
        }
        else {val binding =
                RecyclerviewHomeHolderLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            LoadingHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is ItemHolder)
            holder.bind(getItem(position))
    }

    class ItemHolder(
        private val binding: RecyclerviewHomeHolderBinding,
        private val itemClickListener: (ContentModel) -> Unit
    ) :
        ViewHolder(binding.root) {


        fun bind(item: ContentModel) {
            binding.apply {
                homeHolderTvTitle.text = item.title
                homeHolderTvDateTime.text = Util.makeDateTimeFormat(item.dateTime)
                binding.homeHolderIvSelected.isVisible = item.selectedContent
                binding.homeHolderIvVideo.isVisible = item.type != "image"
                homeHolder.setOnClickListener {
                    itemClickListener(item)
                }
            }
            Glide.with(itemView.context)
                .load(item.thumbnail)
                .into(binding.homeHolderIvTitle)
        }
    }
}

class LoadingHolder(
    private val binding: RecyclerviewHomeHolderLoadingBinding
) :
    ViewHolder(binding.root) {
    fun bind(item: ContentModel) {
        binding.apply {
        }
    }
}
