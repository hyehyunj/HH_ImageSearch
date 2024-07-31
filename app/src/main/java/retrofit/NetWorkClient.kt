package retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {
    private const val IMAGE_BASE_URL = "https://dapi.kakao.com/v2/search/image"

//    private fun createOkHttpClient() : OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//
//        if(BuildConfig.DEBUG)
//            interceptor.level =
//                else
//                    interceptor.level -
//
//                            return OkHttpClient.Builder()
//                                .connectTimeout(20, TimeUnit.SECONDS)
//                                .readTimeout(20, TimeUnit.SECONDS)
//                                .writeTimeout(20, TimeUnit.SECONDS)
//                                .addNetworkInterceptor(interceptor)
//                                .build()
//
//    }

    private val imageRetrofit = Retrofit.Builder()
        .baseUrl(IMAGE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
//        .client(createOkHttpClient())
        .build()

            val imageNetWork : NetWorkInterface = imageRetrofit.create(NetWorkInterface::class.java)

}