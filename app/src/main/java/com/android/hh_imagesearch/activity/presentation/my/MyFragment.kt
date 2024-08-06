package com.android.hh_imagesearch.activity.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.hh_imagesearch.R
import com.android.hh_imagesearch.activity.data.model.SearchModel
import com.android.hh_imagesearch.activity.presentation.main.MainViewModel
import com.android.hh_imagesearch.databinding.FragmentMyBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MyFragment : Fragment() {

    companion object {
        private const val TAG = "MyFragment"
    }

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding as FragmentMyBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var myRecyclerViewAdapter: MyRecyclerViewAdapter
    private var imageItem = mutableListOf<SearchModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.imagesLiveData.observe(viewLifecycleOwner) {
            myRecyclerViewAdapter.updateList(it)
        }
        //어댑터 초기화
        initAdapter()
//        adapter.updateList(sharedViewModel.selectedImageList)


    }

    //어댑터 초기화하는 함수
    private fun initAdapter() {
        myRecyclerViewAdapter = MyRecyclerViewAdapter(imageItem, itemClickListener = { item, position ->
            removeImage(item)
        })
        binding.myRecyclerView.adapter = myRecyclerViewAdapter
        binding.myRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
    }


    //이미지 삭제해주는 함수
    fun removeImage(item: SearchModel) {
        MaterialAlertDialogBuilder(
            requireContext(), R.style.my_remove_dialog
        )
            .setCancelable(false)
            .setTitle("삭제하시겠습니까?")
            .setNegativeButton("삭제") { dialog, which ->
                sharedViewModel.removeImage(item)
            }
            .setPositiveButton("취소") { dialog, which ->
            }
            .show()
    }





    override fun onResume() {
        super.onResume()


    }
}