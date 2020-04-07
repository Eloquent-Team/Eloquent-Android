package berlin.eloquent.eloquentandroid.di.main

import berlin.eloquent.eloquentandroid.database.RecordingDao
import berlin.eloquent.eloquentandroid.di.scopes.MainScope
import berlin.eloquent.eloquentandroid.main.repository.IRecorderRepository
import berlin.eloquent.eloquentandroid.main.repository.RecorderRepository
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @MainScope
    @Provides
    @JvmStatic
    fun provideRecorderRepository(dao: RecordingDao): IRecorderRepository {
        return RecorderRepository(dao)
    }

}
