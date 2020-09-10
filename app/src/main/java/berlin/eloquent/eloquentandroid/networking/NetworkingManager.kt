package berlin.eloquent.eloquentandroid.networking

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val BASE_URL = ""
private const val ANALYZE = "$BASE_URL/speechtotext"

object NetworkingManager {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer {
                serializeNulls()
                disableHtmlEscaping()
            }
        }
    }

    internal fun analyzeAudio(fileName: String, totalTime: Int, encoded: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response: String = client.post(ANALYZE) {
                body = AnalyzeRequest(fileName, totalTime, encoded, "en-US")
            }
        }
    }
}