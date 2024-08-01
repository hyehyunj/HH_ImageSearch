package com.android.hh_imagesearch.activity.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.Documents
import com.android.hh_imagesearch.activity.data.ImageModel
import com.android.hh_imagesearch.databinding.ItemRecyclerviewBinding


class RecyclerViewAdapter(


    private val item: MutableList<Documents>
    ) : RecyclerView.Adapter<RecyclerViewAdapter.Holder>()
    {
        companion object {
            private const val TAG = "RecyclerViewAdapter"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.Holder {
            val binding =
                ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            Log.d(TAG, "d 리사이클러어댑터")
            return Holder(binding)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(item[position])
        }

        override fun getItemCount(): Int {
            return item.size
        }

        inner class Holder(private val binding: ItemRecyclerviewBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Documents) {
                binding.apply {
                    ivItemTitle.setImageURI(item.image_url.toUri())
                    tvItemTitle.text = item.display_sitename
                    tvItemLocation.text = item.datetime

                    Log.d(TAG, "d 0번 인덱스 ${ivItemTitle.setImageURI(item.image_url.toUri())}")
                }
            }
        }
    }
