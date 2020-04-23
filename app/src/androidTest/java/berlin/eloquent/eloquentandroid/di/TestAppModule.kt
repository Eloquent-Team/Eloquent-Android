package berlin.eloquent.eloquentandroid.di

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import berlin.eloquent.eloquentandroid.database.EloquentDatabase
import berlin.eloquent.eloquentandroid.database.RecordingDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestAppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideTestAppDb(app: Application): EloquentDatabase {
        return Room
            .inMemoryDatabaseBuilder(
                app,
                EloquentDatabase::class.java
            )
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideAuthTokenDao(db: EloquentDatabase): RecordingDao {
        return db.recordingDao()
    }
}