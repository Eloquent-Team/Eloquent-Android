package berlin.eloquent.eloquentandroid.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recording(
    var title: String = "",
    var date: String = "",
    var length: Long = 0L,
    var tags: List<String> = listOf(),
    var fileUrl: String = ""
) : Parcelable