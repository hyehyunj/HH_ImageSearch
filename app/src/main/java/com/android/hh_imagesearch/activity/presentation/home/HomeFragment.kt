package com.android.hh_imagesearch.activity.presentation.home

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.Gravity
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
import com.android.hh_imagesearch.activity.data.model.imageToContentModel
import com.android.hh_imagesearch.activity.data.model.videoToContentModel
import com.android.hh_imagesearch.activity.presentation.main.MainViewModel
import com.android.hh_imagesearch.activity.network.NetWorkClient.apiService
import com.android.hh_imagesearch.activity.presentation.main.SelectedInterface
import com.android.hh_imagesearch.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    private lateinit var searchWord: String
    private var selectedInter: SelectedInterface? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        selectedInter = context as SelectedInterface
    }


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

        //최상단이동버튼
        fabListener()

        //필터
        binding.homeToolbar.homeToolbarRgSearchFilter.setOnCheckedChangeListener { rg, id ->
            searchWord = binding.homeToolbar.homeToolbarEtSearch.text.toString()
            if (searchWord.isBlank()) {
                val toast = Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)
                toast.show()
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
            if (searchWord != "") {
                receiveAll(searchWord)
            }
        }
        //저장된 컨텐츠 옵저버 : 보관함에서 제거시 반영


        //필터 옵저버 : 검색 필터 클릭시 반영
        sharedViewModel.searchFilterLiveData.observe(viewLifecycleOwner) {
            when (sharedViewModel.searchFilterLiveData.value) {
                "전체" -> receiveAll(searchWord)
                "사진" -> receiveImage(searchWord)
                "영상" -> receiveVideo(searchWord)

            }
            //모델 옵저버 : 검색결과 변경시 반영
            sharedViewModel.searchLiveData.observe(viewLifecycleOwner) {
                homeRecyclerViewAdapter.submitList(
                    sharedViewModel.searchLiveData.value ?: mutableListOf()
                )
            }

        sharedViewModel.selectedLiveData.observe(viewLifecycleOwner) {
            homeRecyclerViewAdapter
            }
        }
    }


    //어댑터 초기화 함수 : 이미지 검색결과를 리사이클러뷰로 보여주는 함수
    private fun initAdapter() {
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(
            itemClickListener = { item ->
                val selected = item.copy(selectedContent = true)
                selectedInter?.select(selected)
            })
        binding.homeRecyclerView.adapter = homeRecyclerViewAdapter
        binding.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    //최상단이동버튼 함수
    private fun fabListener() {
        binding.homeFab.setOnClickListener() {
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


    //검색어 입력하는 함수
    private fun searchWithWord() {
        binding.homeToolbar.homeToolbarRbAll.isChecked = true
        binding.homeToolbar.homeToolbarRbImage.isChecked = false
        binding.homeToolbar.homeToolbarRbVideo.isChecked = false
        searchWord = binding.homeToolbar.homeToolbarEtSearch.text.toString()
        if (searchWord.isBlank()) {
            val toast = Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)
            toast.show()
        } else {
            sharedViewModel.updateSearchWord(searchWord)
            hideKeyboard()
        }
    }


    //Retrofit을 통해 검색어에 따른 전체 검색결과 불러오는 함수
    private fun receiveAll(query: String) = lifecycleScope.launch() {
        val receiveImage = async(Dispatchers.IO) { apiService.searchImage(query) }
        val receiveVideo = async(Dispatchers.IO) { apiService.searchVideo(query) }

        sharedViewModel.updateMixContentModel(
            imageToContentModel(receiveImage.await().imageDocuments).toMutableList(),
            videoToContentModel(receiveVideo.await().videoDocuments).toMutableList()
        )
    }

    //Retrofit을 통해 검색어에 따른 이미지 검색결과 불러오는 함수
    private fun receiveImage(query: String) = lifecycleScope.launch() {
        val searchImage = apiService.searchImage(query)
        sharedViewModel.updateContentModel(imageToContentModel(searchImage.imageDocuments).toMutableList())
//
//        binding.homeToolbar.homeToolbarFlowSearchFilter.isVisible = true
    }

    //Retrofit을 통해 검색어에 따른 동영상 검색결과 불러오는 함수
    private fun receiveVideo(query: String) = lifecycleScope.launch() {
        val searchVideo = apiService.searchVideo(query)
        sharedViewModel.updateContentModel(videoToContentModel(searchVideo.videoDocuments).toMutableList())


    }

//    private val scrollListener by lazy {
//    object  : RecyclerView.OnScrollListener() {
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            if (newState == RecyclerView.SCROLL_STATE_IDLE && !binding.homeRecyclerView.canScrollVertically(1))
//            {
//                if()
//            }        }
//    }
//    }


    //키보드 숨기는 함수
    private fun hideKeyboard() {
        val manager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.homeToolbar.homeToolbarEtSearch.windowToken, 0)
    }

    //보관된 이미지 표시해주는 함수


    override fun onResume() {
        super.onResume()
//        sharedViewModel.selectedLiveData.observe(viewLifecycleOwner) {
//            homeRecyclerViewAdapter.submitList(
//                sharedViewModel.selectedLiveData.value ?: mutableListOf()
//            )
//        }
    }

}

