package berlin.eloquent.eloquentandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recording::class], version = 1, exportSchema = false)
abstract class EloquentDatabase : RoomDatabase() {

    abstract fun recordingDao(): RecordingDao

}
