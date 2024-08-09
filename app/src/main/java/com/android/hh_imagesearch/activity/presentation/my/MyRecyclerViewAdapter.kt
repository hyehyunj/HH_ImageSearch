package com.android.hh_imagesearch.activity.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.model.ContentModel
import com.android.hh_imagesearch.activity.presentation.home.LOADING
import com.android.hh_imagesearch.activity.presentation.util.Util
import com.android.hh_imagesearch.databinding.RecyclerviewMyHolderBinding
import com.bumptech.glide.Glide


class MyRecyclerViewAdapter(
    private val itemClickListener: (item: ContentModel) -> Unit,
    private val itemLongClickListener: (item: ContentModel) -> Unit
) : ListAdapter<ContentModel, MyRecyclerViewAdapter.Holder>(diffUtil) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RecyclerviewMyHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, itemClickListener, itemLongClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        private val binding: RecyclerviewMyHolderBinding,
        private val itemClickListener: (item: ContentModel) -> Unit,
        private val itemLongClickListener: (item: ContentModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ContentModel) {
            binding.apply {

                myHolderTvTitle.text = item.title
                myHolderTvDateTime.text = Util.makeDateTimeFormat(item.dateTime)
                myHolder.setOnClickListener {
                    itemClickListener(item)
                }
                myHolder.setOnLongClickListener {
                    itemLongClickListener(item)
                    true
                }
            }
            Glide.with(itemView.context)
                .load(item.thumbnail)
                .into(binding.myHolderIvTitle)

        }
    }
}
