package berlin.eloquent.eloquentandroid.main.feedback.retrofit

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "http://161.35.197.123:8080/"
const val ANALYZE_ENDPOINT = "api/v1/analyze/3"
const val UPLOAD_ENDPOINT = ""

interface AudioService {
    @GET(ANALYZE_ENDPOINT)
    suspend fun getAnalysis(): Response<DataModel>

    companion object{
        fun create(): AudioService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AudioService::class.java)
        }
    }

}