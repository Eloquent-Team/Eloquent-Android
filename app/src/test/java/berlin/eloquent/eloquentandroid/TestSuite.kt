package berlin.eloquent.eloquentandroid

import berlin.eloquent.eloquentandroid.database.RecordingDaoTest
import berlin.eloquent.eloquentandroid.main.repository.RecorderRepositoryTest
import berlin.eloquent.eloquentandroid.player.PlayerViewModelTest
import berlin.eloquent.eloquentandroid.recorder.RecorderViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RecordingDaoTest::class,
    RecorderRepositoryTest::class,
    PlayerViewModelTest::class,
    RecorderViewModelTest::class
)
class TestSuite
