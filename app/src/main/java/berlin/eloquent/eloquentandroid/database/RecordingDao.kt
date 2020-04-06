package berlin.eloquent.eloquentandroid.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecordingDao {

    @Insert
    fun insertRecording(recording: Recording)

    @Update
    fun updateRecording(recording: Recording)

    @Delete
    fun deleteRecording(recording: Recording)

    @Query(value = "SELECT * FROM recording_table WHERE recordingId = :id")
    fun getRecording(id: Long): Recording

    @Query(value = "SELECT * FROM recording_table ORDER BY recordingId DESC")
    fun getAllRecordings(): LiveData<List<Recording>>

    @Query(value = "SELECT * FROM recording_table ORDER BY recordingId DESC LIMIT 1")
    fun getNewestRecording(): Recording?

}
