package com.android.hh_imagesearch.activity.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.Documents
import com.android.hh_imagesearch.activity.data.ImageModel
import com.android.hh_imagesearch.databinding.ItemRecyclerviewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import java.text.Format
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.contracts.contract


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
                    Log.d(TAG, "d ${item.thumbnail_url.toUri()}")
                    tvItemTitle.text = item.display_sitename
//                    tvItemLocation.text = formatter.format(item.datetime)
                }
               Glide.with(itemView.context)
                   .load(item.thumbnail_url)
                   .into(binding.ivItemTitle)

            }
        }
    }
