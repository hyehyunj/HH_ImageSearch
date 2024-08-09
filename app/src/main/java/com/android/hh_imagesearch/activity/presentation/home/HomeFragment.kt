package com.android.hh_imagesearch.activity.presentation.home

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hh_imagesearch.R
import com.android.hh_imagesearch.activity.data.model.ContentModel
import com.android.hh_imagesearch.activity.data.model.imageToContentModel
import com.android.hh_imagesearch.activity.data.model.videoToContentModel
import com.android.hh_imagesearch.activity.presentation.main.MainViewModel
import com.android.hh_imagesearch.activity.network.NetWorkClient.apiService
import com.android.hh_imagesearch.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//검색창에 입력한 검색어 결과(사진, 영상)를 리스트형태로 보여주는 프래그먼트
class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    private lateinit var searchWord: String
    private val coroutineExceptionHandler = CoroutineExceptionHandler {_, throwable -> println("Caught: ${throwable.message}") }

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

        //최상단이동버튼
        fabListener()

        //스크롤 하단감지 (무한스크롤 시도)
        scrollEndListener()

        //검색필터
        filterListener()

        //검색창에 최근 검색어 백업
        binding.homeToolbar.homeToolbarEtSearch.setText(sharedViewModel.searchWordLiveData.value)

        //검색어 입력 후
        // 1.키보드 엔터키
        binding.homeToolbar.homeToolbarEtSearch.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchWithWord()
                return@setOnKeyListener false
            } else return@setOnKeyListener false
        }
        //2.돋보기 아이콘
        binding.homeToolbar.homeToolbarIvSearch.setOnClickListener {
            searchWithWord()
        }

        //검색어 옵저버 : 검색어 변화를 감지해 검색
        sharedViewModel.searchWordLiveData.observe(viewLifecycleOwner) {
            searchWord = it
            if (searchWord != "") {
                receiveAll(searchWord)
            }
        }

        //필터 옵저버 : 검색 필터 변화를 감지해 반영
        sharedViewModel.searchFilterLiveData.observe(viewLifecycleOwner) {
            if (searchWord != "") {
            when (sharedViewModel.searchFilterLiveData.value) {
                "전체" -> receiveAll(searchWord)
                "사진" -> receiveImage(searchWord)
                "영상" -> receiveVideo(searchWord)
            }
            }
        }

        //검색결과 옵저버 : 검색결과 변화를 감지해 반영
        sharedViewModel.searchResultLiveData.observe(viewLifecycleOwner) {
            sharedViewModel.findMyContent()
            homeRecyclerViewAdapter.submitList(
                sharedViewModel.searchResultLiveData.value?.sortedByDescending { it.dateTime }
                    ?: mutableListOf()
            )
        }

        //보관함 옵저버 : 저장된 컨텐츠 변화를 감지해 반영
        sharedViewModel.myContentLiveData.observe(viewLifecycleOwner) {
            sharedViewModel.findMyContent()
            homeRecyclerViewAdapter.notifyDataSetChanged()
        }
    }


    //어댑터 초기화 함수 : 검색결과를 리사이클러뷰로 보여주는 함수. 컨텐츠 클릭시 보관함에 담긴다.
    private fun initAdapter() {
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(
            itemClickListener = { item ->
                if (!item.selectedContent) {
                    item.selectedContent = true
                    sharedViewModel.addContent(item)
                } else Toast.makeText(requireContext(), "이미 보관함에 저장된 컨텐츠입니다.", Toast.LENGTH_SHORT)
                    .show()
            })
        binding.homeRecyclerView.adapter = homeRecyclerViewAdapter
        binding.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    //최상단이동버튼 함수
    private fun fabListener() {
        binding.homeFab.setOnClickListener {
            binding.homeRecyclerView.smoothScrollToPosition(0)
        }
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true
        binding.homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.homeRecyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.homeFab.startAnimation(fadeOut)
                    binding.homeFab.visibility = View.GONE
                    isTop = true
                } else {
                    if (isTop) {
                        binding.homeFab.visibility = View.VISIBLE
                        binding.homeFab.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })
    }

    //스크롤 끝 도달여부 확인 함수
    private fun scrollEndListener() {
        var isLoading = false
        binding.homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (!binding.homeRecyclerView.canScrollVertically(1)) {
                        isLoading = true
                    }
                }
            }
        })
    }

    //검색필터 함수 : 전체, 사진, 영상 중 선택한 필터에 따라 검색결과를 보여준다. 디폴트는 전체
    private fun filterListener() {
        binding.homeToolbar.homeToolbarRgSearchFilter.setOnCheckedChangeListener { rg, id ->
            searchWord = binding.homeToolbar.homeToolbarEtSearch.text.toString()
            if (searchWord.isBlank() || binding.homeToolbar.homeToolbarEtSearch.text.isBlank()) {
                Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else when (id) {
                R.id.home_toolbar_rb_all ->
                    sharedViewModel.updateFilter("전체")

                R.id.home_toolbar_rb_image -> {
                    binding.homeToolbar.homeToolbarRbAll.isChecked = false
                    sharedViewModel.updateFilter("사진")
                }

                R.id.home_toolbar_rb_video -> {
                    binding.homeToolbar.homeToolbarRbAll.isChecked = false
                    sharedViewModel.updateFilter("영상")
                }
            }
        }
    }

    //검색창 함수 : 검색어를 입력받는다.
    private fun searchWithWord() {
        binding.homeToolbar.homeToolbarRbAll.isChecked = true
        binding.homeToolbar.homeToolbarRbImage.isChecked = false
        binding.homeToolbar.homeToolbarRbVideo.isChecked = false
        searchWord = binding.homeToolbar.homeToolbarEtSearch.text.toString()
        if (searchWord.isBlank()) {
            Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            sharedViewModel.updateSearchWord(searchWord)
            hideKeyboard()
        }
    }

    //전체 검색결과 함수 : Retrofit을 통해 검색어에 따른 사진 및 영상 검색결과를 불러오는 함수
    private fun receiveAll(query: String) = lifecycleScope.launch {
        if(query != "") {
        val receiveImage = async(Dispatchers.IO) { apiService.searchImage(query) }
            val receiveVideo = async(Dispatchers.IO) { apiService.searchVideo(query) }

            sharedViewModel.receivedMergedSearchResult(
                imageToContentModel(receiveImage.await().imageDocuments).toMutableList(),
                videoToContentModel(receiveVideo.await().videoDocuments).toMutableList()
            )}
    }

    //사진 검색결과 함수 : Retrofit을 통해 검색어에 따른 이미지 검색결과를 불러오는 함수
    private fun receiveImage(query: String) = lifecycleScope.launch() {
        val searchImage = apiService.searchImage(query)
        sharedViewModel.receivedSearchResult(imageToContentModel(searchImage.imageDocuments).toMutableList())
    }

    //영상 검색결과 함수 : Retrofit을 통해 검색어에 따른 동영상 검색결과를 불러오는 함수
    private fun receiveVideo(query: String) = lifecycleScope.launch() {
        val searchVideo = apiService.searchVideo(query)
        sharedViewModel.receivedSearchResult(videoToContentModel(searchVideo.videoDocuments).toMutableList())
    }

    //키보드 숨기는 함수
    private fun hideKeyboard() {
        val manager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.homeToolbar.homeToolbarEtSearch.windowToken, 0)
    }


}

