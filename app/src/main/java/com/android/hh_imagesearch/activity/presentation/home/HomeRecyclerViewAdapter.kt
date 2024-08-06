package com.android.hh_imagesearch.activity.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.model.SearchModel
import com.android.hh_imagesearch.databinding.RecyclerviewHomeHolderBinding
import com.bumptech.glide.Glide


class HomeRecyclerViewAdapter(


    private val item: MutableList<SearchModel>,
    private val itemClickListener: (item: SearchModel) -> Unit
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.Holder>() {
    companion object {
        private const val TAG = "HomeRecyclerViewAdapter"
    }

    private val differCallback = object : DiffUtil.ItemCallback<SearchModel>() {
        override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
           return oldItem.uId == newItem.uId
        }

        override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem == newItem
        }

    }

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
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    class Holder(private val binding: RecyclerviewHomeHolderBinding, private val itemClickListener: (SearchModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchModel) {
            binding.apply {
//                homeHolderTvTitle.text = item.siteName
//                    tvItemLocation.text = formatter.format(item.datetime)
                homeHolder.setOnClickListener {
                    itemClickListener(item)
                    binding.homeHolderIvSelected.isVisible = true
                }
            }
            Glide.with(itemView.context)
                .load(item.thumbnail)
                .into(binding.homeHolderIvTitle)
        }

//        fun remove(items: List<Documents>) {
//            if(item.thu)binding.homeHolderIvSelected.isVisible = false
//        }
    }

//    fun updateList(items: List<Documents>) {
//        item.clear()
//        item.addAll(items)
//        notifyDataSetChanged()
//    }

    fun updateList(items: MutableList<SearchModel>) {
        item.clear()
        item.addAll(items)
        notifyDataSetChanged()
    }


}
