package com.android.hh_imagesearch.activity.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.model.ContentModel
import com.android.hh_imagesearch.activity.presentation.util.Util
import com.android.hh_imagesearch.databinding.RecyclerviewHomeHolderBinding
import com.bumptech.glide.Glide


class HomeRecyclerViewAdapter(
   private val itemClickListener: (item: ContentModel) -> Unit
) : ListAdapter<ContentModel, HomeRecyclerViewAdapter.Holder>(diffUtil) {

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

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RecyclerviewHomeHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return Holder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        private val binding: RecyclerviewHomeHolderBinding,
        private val itemClickListener: (ContentModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {


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



//    fun updateMyContent(item: ContentModel) {
//        if(item.selectedContent) binding.homeHolderIvSelected.isVisible = true
//        else binding.homeHolderIvSelected.isVisible = false
//    }


    fun updateList(item: MutableList<ContentModel>) {
        item.clear()
        item.addAll(item)
        submitList(item)
    }


}
