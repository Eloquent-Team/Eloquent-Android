package berlin.eloquent.eloquentandroid.di

import android.app.Application
import androidx.room.Room
import berlin.eloquent.eloquentandroid.database.EloquentDatabase
import berlin.eloquent.eloquentandroid.database.RecordingDao
import berlin.eloquent.eloquentandroid.main.feedback.retrofit.AudioService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppDb(app: Application): EloquentDatabase {
        return Room
            .databaseBuilder(app, EloquentDatabase::class.java, "eloquent_db")
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideAuthTokenDao(db: EloquentDatabase): RecordingDao {
        return db.recordingDao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideAudioService(): AudioService {
        return AudioService.create()
    }

}
