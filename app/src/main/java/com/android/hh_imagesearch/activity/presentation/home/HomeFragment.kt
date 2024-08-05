package com.android.hh_imagesearch.activity.presentation.home

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
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
import com.android.hh_imagesearch.activity.data.model.Documents
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
    private lateinit var adapter: HomeRecyclerViewAdapter
    private var imageSearchResult = mutableListOf<Documents>()
    private var searchWord = "검색어"

//    override fun onAttach(context: Context) {
//        super.onAttach(context)

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

        //옵저버 : 검색어 변화를 감지해 검색결과를 바꿔준다.
        sharedViewModel.searchWordLiveData.observe(viewLifecycleOwner) {
            searchWord = it
            receiveImage(searchWord)
        }

        inputSearchWord()

    }

    //검색어 입력하는 함수
    private fun inputSearchWord() {
        binding.homeToolbar.homeToolbarEtSearch.setOnKeyListener { _, keyCode, _ ->
            val searchWord = binding.homeToolbar.homeToolbarEtSearch.text.toString()
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (searchWord.isBlank()) {
                    Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    sharedViewModel.updateSearchWord(searchWord)
                    hideKeyboard()
                }
                return@setOnKeyListener true
            } else return@setOnKeyListener false
        }
    }

    //Retrofit을 통해 검색어에 따른 이미지 검색결과 불러오는 함수
    private fun receiveImage(query: String) = lifecycleScope.launch() {
        val searchImage = apiService.searchImage(query)
        imageSearchResult = searchImage.documents
        initAdapter()
    }

    //어댑터 초기화 함수 : 이미지 검색결과를 리사이클러뷰로 보여주는 함수
    private fun initAdapter() {
        adapter = HomeRecyclerViewAdapter(imageSearchResult, itemClickListener = { item, position ->
            sharedViewModel.addImage(item)
        })
        binding.homeRecyclerView.adapter = adapter
        binding.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//        addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))
    }

    //키보드 숨기는 함수
    private fun hideKeyboard() {
        val manager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.homeToolbar.homeToolbarEtSearch.getWindowToken(), 0)
    }

    override fun onResume() {
        super.onResume()
    }

}

