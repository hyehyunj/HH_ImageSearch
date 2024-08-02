package com.android.hh_imagesearch.activity.presentation.my

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.model.Documents
import com.android.hh_imagesearch.databinding.RecyclerviewMyHolderBinding
import com.bumptech.glide.Glide


class MyRecyclerViewAdapter(


    private val item: MutableList<Documents>,
    private val itemClickListener: (item: Documents, position: Int) -> Unit
    ) : RecyclerView.Adapter<MyRecyclerViewAdapter.Holder>()
    {
        companion object {
            private const val TAG = "MyRecyclerViewAdapter"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding =
                RecyclerviewMyHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return Holder(binding)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(item[position])
        }

        override fun getItemCount(): Int {
            return item.size
        }

        inner class Holder(private val binding: RecyclerviewMyHolderBinding) :
            RecyclerView.ViewHolder(binding.root) {



            fun bind(item: Documents) {
                binding.apply {

                    myHolderTvTitle.text = item.display_sitename
//                    tvItemLocation.text = formatter.format(item.datetime)
                    myHolder.setOnClickListener {
                        Log.d(TAG, "어댑터 클릭감지")
                        itemClickListener(item, adapterPosition)
                    }
                }
               Glide.with(itemView.context)
                   .load(item.thumbnail_url)
                   .into(binding.myHolderIvTitle)

            }
        }
    }
