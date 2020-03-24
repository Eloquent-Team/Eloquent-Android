package berlin.eloquent.eloquentandroid.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecordingDao {

    @Insert
    fun insert(recording: Recording)

    @Update
    fun update(recording: Recording)

    @Delete
    fun delete(recording: Recording)

    @Query(value = "SELECT * FROM recording_table WHERE recordingId = :id")
    fun get(id: Long): Recording

    @Query(value = "SELECT * FROM recording_table ORDER BY recordingId DESC")
    fun getAllRecordings(): LiveData<List<Recording>>

    @Query(value = "SELECT * FROM recording_table ORDER BY recordingId DESC LIMIT 1")
    fun getNewestRecording(): Recording?

}