package com.android.hh_imagesearch.activity.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.model.SearchModel
import com.android.hh_imagesearch.databinding.RecyclerviewHomeHolderBinding
import com.bumptech.glide.Glide


class HomeRecyclerViewAdapter(


    private val item: MutableList<SearchModel>,
    private val itemClickListener: (item: SearchModel, position: Int) -> Unit
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.Holder>() {
    companion object {
        private const val TAG = "HomeRecyclerViewAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RecyclerviewHomeHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    inner class Holder(private val binding: RecyclerviewHomeHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchModel) {
            binding.apply {
                homeHolderTvTitle.text = item.siteName
//                    tvItemLocation.text = formatter.format(item.datetime)
                homeHolder.setOnClickListener {
                    itemClickListener(item, adapterPosition)
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
