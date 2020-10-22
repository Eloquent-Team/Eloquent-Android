package berlin.eloquent.eloquentandroid.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recording_table")
data class Recording(

        @PrimaryKey(autoGenerate = true)
        var recordingId: Long = 0L,

        @ColumnInfo(name = "title")
        var title: String = "",

        @ColumnInfo(name = "date")
        var date: String = "",

        @ColumnInfo(name = "length")
        var length: Long = 0L,

        @ColumnInfo(name = "tags")
        var tags: String = "",

        @ColumnInfo(name = "file_url")
        var fileUrl: String = "",

        @ColumnInfo(name = "base64Content")
        var base64Content: String = ""

) {

    override fun toString(): String {
        return "ID:$recordingId | TITLE:$title | DATE:$date | length:$length | tags:$tags | fileUrl:$fileUrl"
    }
}
