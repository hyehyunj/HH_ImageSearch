package retrofit

import android.media.Image
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("")
    suspend fun getImage(@QueryMap param: HashMap<String, String>) : Image
}