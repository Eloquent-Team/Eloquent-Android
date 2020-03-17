package berlin.eloquent.eloquentandroid.home.models

data class Recording(
    var title: String,
    var date: String,
    var length: String,
    var tags: Array<String>
)