package com.android.hh_imagesearch.activity.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.hh_imagesearch.R
import com.android.hh_imagesearch.activity.data.model.ContentModel
import com.android.hh_imagesearch.activity.presentation.main.MainViewModel
import com.android.hh_imagesearch.databinding.FragmentMyBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

//저장된 컨텐츠를 확인 및 삭제할 수 있는 프래그먼트
class MyFragment : Fragment() {

    companion object {
        private const val TAG = "MyFragment"
    }

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding as FragmentMyBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var myRecyclerViewAdapter: MyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //어댑터 초기화
        initAdapter()

        //휴지통 아이콘
        binding.myIvDelete.setOnClickListener{
            removeAllMyContent()
        }

        //보관함 옵저버 : 저장된 컨텐츠 변화를 감지해 반영
        sharedViewModel.myContentLiveData.observe(viewLifecycleOwner) {
            it.let {
                myRecyclerViewAdapter.apply {
                    submitList(it.sortedByDescending { it.dateTime })
                    notifyDataSetChanged()
                }
            }
        }
    }

    //어댑터 초기화 함수 : 저장된 컨텐츠를 리사이클러뷰로 보여주는 함수. 컨텐츠 클릭시 삭제 함수로 넘어간다.
    private fun initAdapter() {
        myRecyclerViewAdapter = MyRecyclerViewAdapter(itemClickListener = { item ->
            removeMyContent(item)
        },
            itemLongClickListener = { item, position ->
                makeMovie(item, position)
            })
        binding.myRecyclerView.adapter = myRecyclerViewAdapter
        binding.myRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
    }


    //저장된 컨텐츠 삭제 함수 : 다이얼로그로 삭제여부를 확인하고 클릭된 컨텐츠를 보관함에서 삭제해주는 함수
    private fun removeMyContent(content: ContentModel) {
        MaterialAlertDialogBuilder(
            requireContext(), R.style.my_remove_dialog
        )
            .setTitle("삭제하시겠습니까?")
            .setNegativeButton("삭제") { dialog, which ->
                sharedViewModel.removeContent(content)
            }
            .setPositiveButton("취소") { dialog, which ->
            }
            .show()
    }

    //저장된 컨텐츠 전체삭제 함수 : 다이얼로그로 삭제여부를 확인하고 클릭된 컨텐츠를 보관함에서 삭제해주는 함수
    private fun removeAllMyContent() {
        MaterialAlertDialogBuilder(
            requireContext(), R.style.my_remove_dialog
        )
            .setTitle("전부 삭제하시겠습니까?")
            .setNegativeButton("삭제") { dialog, which ->
                sharedViewModel.removeAllContent()
            }
            .setPositiveButton("취소") { dialog, which ->
            }
            .show()

    }


    //영화관으로  함수 : 다이얼로그로 삭제여부를 확인하고 클릭된 컨텐츠를 보관함에서 삭제해주는 함수
    private fun makeMovie(content: ContentModel, position: Int) {
        sharedViewModel.myContentLiveData.value?.get(position)

        Glide.with(requireContext())
            .load(content.thumbnail)
            .into(binding.myIvCinema)
    }


}