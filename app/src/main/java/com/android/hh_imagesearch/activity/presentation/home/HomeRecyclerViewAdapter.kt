package com.android.hh_imagesearch.activity.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.model.Documents
import com.android.hh_imagesearch.databinding.RecyclerviewHomeHolderBinding
import com.bumptech.glide.Glide


class HomeRecyclerViewAdapter(


    private val item: MutableList<Documents>,
    private val itemClickListener: (item: Documents, position: Int) -> Unit
    ) : RecyclerView.Adapter<HomeRecyclerViewAdapter.Holder>()
    {
        companion object {
            private const val TAG = "RecyclerViewAdapter"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding =
                RecyclerviewHomeHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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



            fun bind(item: Documents) {
                binding.apply {

                    homeHolderTvTitle.text = item.display_sitename
//                    tvItemLocation.text = formatter.format(item.datetime)
                    homeHolder.setOnClickListener {
                        Log.d(TAG, "어댑터 클릭감지")
                        itemClickListener(item, adapterPosition)
                    }
                }
               Glide.with(itemView.context)
                   .load(item.thumbnail_url)
                   .into(binding.homeHolderIvTitle)

            }
        }
    }
