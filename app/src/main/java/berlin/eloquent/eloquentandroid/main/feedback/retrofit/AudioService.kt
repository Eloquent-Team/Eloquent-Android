package berlin.eloquent.eloquentandroid.main.feedback.retrofit

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface AudioService {
    @GET("api/v1/analyze/3")
    suspend fun getAnalysis(): Response<dataModel>

    companion object{
        fun create(): AudioService {
            return Retrofit.Builder()
                .baseUrl("http://161.35.197.123:8080/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AudioService::class.java)
        }
    }

}