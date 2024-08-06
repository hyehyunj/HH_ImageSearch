package com.android.hh_imagesearch.activity.presentation.my

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.activity.data.model.SearchModel
import com.android.hh_imagesearch.databinding.RecyclerviewMyHolderBinding
import com.bumptech.glide.Glide


class MyRecyclerViewAdapter(


    private val item: MutableList<SearchModel>,
    private val itemClickListener: (item: SearchModel) -> Unit
    ) : RecyclerView.Adapter<MyRecyclerViewAdapter.Holder>()
    {
        companion object {
            private const val TAG = "MyRecyclerViewAdapter"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding =
                RecyclerviewMyHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return Holder(binding, itemClickListener)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(item[position])
        }

        override fun getItemCount(): Int {
            return item.size
        }

        class Holder(private val binding: RecyclerviewMyHolderBinding, private val itemClickListener: (item: SearchModel) -> Unit) :
            RecyclerView.ViewHolder(binding.root) {



            fun bind(item: SearchModel) {
                binding.apply {

//                    myHolderTvTitle.text = item.sitename
//                    tvItemLocation.text = formatter.format(item.datetime)
                    myHolder.setOnClickListener {
                        Log.d(TAG, "어댑터 클릭감지")
                        itemClickListener(item)
                    }
                }
               Glide.with(itemView.context)
                   .load(item.thumbnail)
                   .into(binding.myHolderIvTitle)

            }
        }

        fun updateList(items: List<SearchModel>) {
            item.clear()
            item.addAll(items)
            notifyDataSetChanged()
        }


    }
