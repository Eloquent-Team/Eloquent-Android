package berlin.eloquent.eloquentandroid.networking

data class AnalyzeRequest(
    val fileName: String,
    val totalTime: Int,
    val base64String: String,
    val languageCode: String
)