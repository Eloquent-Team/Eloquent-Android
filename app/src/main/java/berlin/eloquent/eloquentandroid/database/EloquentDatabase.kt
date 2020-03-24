package berlin.eloquent.eloquentandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recording::class], version = 1, exportSchema = false)
abstract class EloquentDatabase: RoomDatabase() {

    abstract val recordingDao: RecordingDao

    companion object {

        @Volatile
        private var INSTANCE: EloquentDatabase? = null

        fun getInstance(context: Context): EloquentDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, EloquentDatabase::class.java, "eloquent_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}