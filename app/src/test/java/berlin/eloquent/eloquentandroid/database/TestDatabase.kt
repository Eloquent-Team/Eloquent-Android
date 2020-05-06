package berlin.eloquent.eloquentandroid.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class TestDatabase {

    val recording = Recording(
        title = "TestRecording",
        tags = "test more tests",
        date = "2020-03-24 12:24:34",
        length = 100L,
        fileUrl = "fileUrl"
    )

    lateinit var eloquentDatabase: EloquentDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        eloquentDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EloquentDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        eloquentDatabase.close()
    }

}
