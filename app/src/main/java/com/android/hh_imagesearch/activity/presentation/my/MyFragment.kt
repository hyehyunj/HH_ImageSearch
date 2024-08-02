package com.android.hh_imagesearch.activity.presentation.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.android.hh_imagesearch.activity.presentation.home.HomeRecyclerViewAdapter
import com.android.hh_imagesearch.activity.data.model.Documents
import com.android.hh_imagesearch.activity.presentation.main.MainViewModel
import com.android.hh_imagesearch.databinding.FragmentMyStorageBinding

//private const val ARG_PARAM1 = "param1"

class MyFragment : Fragment() {

//    private var param1: Parcelable? = null
companion object {
    private const val TAG = "ImageSearchFragment"
}

    private var _binding : FragmentMyStorageBinding? = null
    private val binding get() = _binding as FragmentMyStorageBinding
    private val sharedViewModel : MainViewModel by activityViewModels()
    private lateinit var adapter : HomeRecyclerViewAdapter
    private var imageItem = mutableListOf<Documents>()


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
        _binding = FragmentMyStorageBinding.inflate(inflater, container, false)
        return binding.root
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