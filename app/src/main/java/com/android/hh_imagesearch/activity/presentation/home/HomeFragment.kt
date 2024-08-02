package com.android.hh_imagesearch.activity.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.hh_imagesearch.activity.data.model.Documents
import com.android.hh_imagesearch.activity.presentation.main.MainViewModel
import com.android.hh_imagesearch.activity.network.NetWorkClient.apiService
import com.android.hh_imagesearch.databinding.FragmentImageSearchBinding
import kotlinx.coroutines.launch


//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//interface FragmentDataListener {
//    fun onDataReceived(data: Documents)
//}

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "ImageSearchFragment"
    }

    private var _binding : FragmentImageSearchBinding? = null
    private val binding get() = _binding as FragmentImageSearchBinding
    private val sharedViewModel : MainViewModel by activityViewModels()
//    private var dataListener : FragmentDataListener? = null
    private lateinit var adapter : HomeRecyclerViewAdapter
    private var imageItem = mutableListOf<Documents>()

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        if(context is FragmentDataListener) {
//            dataListener = context
//        } else throw RuntimeException("context must implement FragmentDataListener")
//    }


//    private var param1: String? = null
//    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicateNetWork("아이브")


    }


    private fun communicateNetWork(query: String) = lifecycleScope.launch() {
        val responseData = apiService.getImage(query)
        imageItem = responseData.documents



        adapter = HomeRecyclerViewAdapter(imageItem, itemClickListener = { item, position ->
            Log.d(TAG, "클릭감지")
            addImage(item)

//            dataListener?.onDataReceived(item)
        })



        getAdapter()
    }

    //ImageModel(
    // meta=Meta(total_count=1122448, pageable_count=3880, is_end=false),
    // documents=[
    // Documents(collection=cafe,
    // thumbnail_url=https://search4.kakaocdn.net/argon/130x130_85_c/AZWMVtlB4G5,
    // image_url=https://t1.daumcdn.net/cafeattach/1ZCQy/d23d1e8771eae3a99fe6df3a3e69be9b24817ee6,
    // width=1024, height=1365,
    // display_sitename=Daum카페,
    // doc_url=https://cafe.daum.net/IVEstarship/Y1ra/14291,
    // datetime=2024-05-17T22:01:19.000+09:00),

    // Documents(collection=news,
    // thumbnail_url=https://search1.kakaocdn.net/argon/130x130_85_c/IMZFAjJpAJV, image_url=https://t1.daumcdn.net/news/202203/26/spotvnews/20220326212016197gpoq.jpg, width=600, height=900, display_sitename=스포티비뉴스, doc_url=http://v.media.daum.net/v/20220326212010014, datetime=2022-03-26T21:20:10.000+09:00), Documents(collection=news, thumbnail_url=https://search1.kakaocdn.net/argon/130x130_85_c/7QrHtSM4olp, image_url=https://t1.daumcdn.net/news/202209/04/JTBC/20220904153633264ikax.jpg, width=560, height=791, display_sitename=JTBC, doc_url=http://v.media.daum.net/v/20220903145403137, datetime=2022-09-03T14:54:03.000+09:00), Documents(collection=news, thumbnail_url=https://search2.kakaocdn.net/argon/130x130_85_c/FaUEUIiwIDX, image_url=https://t1.daumcdn.net/news/202310/08/HockeyNewsKorea/20231008163008652lheb.jpg, width=720, height=1080, display_sitename=MHN스포츠, doc_url=http://v.media.daum.net/v/20231008163005340, datetime=2023-10-08T16:30:05.000+09:00), Documents(collection=news, thumbnail_url=https://search3.kakaocdn.net/argon/130x130_85_c/3xhDMDuKsT1, image_url=https://t1.daumcdn.net/news/202304/28/tvdaily/20230428153125977hioa.jpg, width=658, height=788, display_sitename=티브이데일리, doc_url=http://v.media.daum.net/v/20230428153124816, datetime=2023-04-28T15:31:24.000+09:00), Documents(collection=news, thumbnail_url=https://search1.kakaocdn.net/argon/130x130_85_c/92jCvwm354y, image_url=https://t1.daumcdn.net/news/202204/19/JTBC/20220419170008881ssaw.jpg, width=560, height=560, display_sitename=JTBC, doc_url=http://v.media.daum.net/v/20220419170007674, datetime=2022-04-19T17:00:07.000+09:00), Documents(collection=news, thumbnail_url=https://search4.kakaocdn.net/argon/130x130_85_c/3TWjfvyyWyw, image_url=https://t1.daumcdn.net/news/202304/30/starnews/20230430230106066jbca.jpg, width=1024, height=1441, display_sitename=스타뉴스, doc_url=http://v.media.daum.net/v/20230430230104973, datetime=2023-04-30T23:01:04.000+09:00), Documents(collection=news, thumbnail_url=https://search1.kakaocdn.net/argon/130x130_85_c/9sfmPgdqPXp, image_url=https://t1.daumcdn.net/news/202209/01/tvdaily/20220901105322597rtzl.jpg, width=658, height=877, display_sitename=티브이데일리, doc_url=http://v.media.daum.net/v/20220901105321579, datetime=2022-09-01T10:53:21.000+09:00), Documents(collection=news, thumbnail_url=https://search2.kakaocdn.net/argon/130x130_85_c/96Xn8isoBqk, image_url=https://t1.daumcdn.net/news/202304/06/newsen/20230406125440624mndz.jpg, width=1000, height=648, display_sitename=뉴스엔, doc_url=http://v.media.daum.net/v/20230406125440458, datetime=2023-04-06T12:54:40.000+09:00), Documents(collection=news, thumbnail_url=https://search2.kakaocdn.net/argon/130x130_85_c/15XP6yxIFCY, image_url=https://t1.daumcdn.net/news/202305/10/JTBC/20230510154946787qnrv.jpg, width=560, height=746, display_sitename=JTBC, doc_url=http://v.media.daum.net/v/20230510154944062, datetime=2023-05-10T15:49:44.000+09:00), Documents(collection=news, thumbnail_url=https://search4.kakaocdn.net/argon/130x130_85_c/CnkpZCASuLB, image_url=https://t1.daumcdn.net/news/202203/26/hankooki/20220326175047862nykf.jpg, width=640, height=636, display_sitename=한국일보, doc_url=http://v.media.daum.net/v/20220326175045664, datetime=2022-03-26T17:50:45.000+09:00), Documents(collection=cafe, thumbnail_url=https://search2.kakaocdn.net/argon/130x130_85_c/5fCGJTQDEto, image_url=https://t1.daumcdn.net/cafeattach/1D7bO/d56bbe6eb7d3156dee5e5397d4fb9fbdb86578ee, width=1024, height=553, display_sitename=Daum카페, doc_url=https://cafe.daum.net/SoulDresser/FLTB/876909, d


    private fun getAdapter() {
        binding.fragImageSearchRecyclerView.adapter = adapter
        binding.fragImageSearchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//            addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))
    }

fun addImage(selectedImage : Documents) {
    sharedViewModel.setImage(selectedImage)
}


    override fun onResume() {
        super.onResume()

    }


}


//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ImageSearchFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
