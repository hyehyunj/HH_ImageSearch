package com.android.hh_imagesearch.activity.presentation.my

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.hh_imagesearch.activity.data.model.Documents
import com.android.hh_imagesearch.activity.presentation.main.MainViewModel
import com.android.hh_imagesearch.databinding.FragmentMyBinding

//private const val ARG_PARAM1 = "param1"

class MyFragment : Fragment() {

    //    private var param1: Parcelable? = null
    companion object {
        private const val TAG = "MyFragment"
    }

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding as FragmentMyBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: MyRecyclerViewAdapter
    private var imageItem = mutableListOf<Documents>()


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getParcelable(ARG_PARAM1)
//        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initAdapter()
        updateImage()


    }
//어댑터 초기화하는 함수
    private fun initAdapter() {
        adapter = MyRecyclerViewAdapter(imageItem, itemClickListener = { item, position ->
        })
        binding.myRecyclerView.adapter = adapter
        binding.myRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
    }
//이미지 추가해주는 함수
    private fun updateImage() {
        sharedViewModel.imagesLiveData.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }
    //이미지 삭제해주는 함수


    override fun onResume() {
        super.onResume()


    }


//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: Parcelable) =
//            MyStorageFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(ARG_PARAM1, param1)
//                }
//            }
//    }
}