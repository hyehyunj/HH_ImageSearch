package com.android.hh_imagesearch.activity.presentation.home

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.hh_imagesearch.activity.data.model.SearchModel
import com.android.hh_imagesearch.activity.data.model.imageToSearchModel
import com.android.hh_imagesearch.activity.data.model.videoToSearchModel
import com.android.hh_imagesearch.activity.presentation.main.MainViewModel
import com.android.hh_imagesearch.activity.network.NetWorkClient.apiService
import com.android.hh_imagesearch.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    private var imageSearchModel = mutableListOf<SearchModel>()
    private var videoSearchModel = mutableListOf<SearchModel>()
    private var searchWord = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //어댑터 초기화
        initAdapter()

        //검색창에 최근 검색어 백업
        binding.homeToolbar.homeToolbarEtSearch.setText(sharedViewModel.searchWordLiveData.value)

        //검색어 입력 1.키보드 엔터키
        binding.homeToolbar.homeToolbarEtSearch.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchWithWord()
                return@setOnKeyListener false
            } else return@setOnKeyListener false
        }
        //검색어 입력 2.검색 아이콘 클릭
        binding.homeToolbar.homeToolbarIvSearch.setOnClickListener {
            searchWithWord()
        }

        //검색어 옵저버 : 검색어 변화를 감지해 검색결과 변경
        sharedViewModel.searchWordLiveData.observe(viewLifecycleOwner) {
            searchWord = it
            if (searchWord != "") {receiveVideo(searchWord)
            receiveImage(searchWord)}
        }
        //이미지 옵저버 : 이미지 보관함에서 제거시 반영
        sharedViewModel.imagesLiveData.observe(viewLifecycleOwner) {
        }


    }


    //어댑터 초기화 함수 : 이미지 검색결과를 리사이클러뷰로 보여주는 함수
    private fun initAdapter() {
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(imageSearchModel, itemClickListener = { item, position ->
            sharedViewModel.addImage(item)

        })
        binding.homeRecyclerView.adapter = homeRecyclerViewAdapter
        binding.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    //검색어 입력하는 함수
    private fun searchWithWord() {
            val searchWord = binding.homeToolbar.homeToolbarEtSearch.text.toString()
                if (searchWord.isBlank()) {
                    Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    sharedViewModel.updateSearchWord(searchWord)
                    hideKeyboard()
                }
    }

    //Retrofit을 통해 검색어에 따른 이미지 검색결과 불러오는 함수
    private fun receiveImage(query: String) = lifecycleScope.launch() {
        val searchImage = apiService.searchImage(query)
        imageSearchModel = imageToSearchModel(searchImage.imageDocuments).toMutableList()
        homeRecyclerViewAdapter.updateList(imageSearchModel)
    }

    //Retrofit을 통해 검색어에 따른 동영상 검색결과 불러오는 함수
    private fun receiveVideo(query: String) = lifecycleScope.launch() {
        val searchVideo = apiService.searchVideo(query)
        videoSearchModel = videoToSearchModel(searchVideo.videoDocuments).toMutableList()
        homeRecyclerViewAdapter.updateList(videoSearchModel)
    }

    private fun searchResults() {

    }


    //키보드 숨기는 함수
    private fun hideKeyboard() {
        val manager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.homeToolbar.homeToolbarEtSearch.windowToken, 0)
    }

    //보관된 이미지 표시해주는 함수


    override fun onResume() {
        super.onResume()
    }

}

