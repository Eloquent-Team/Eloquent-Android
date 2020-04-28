package berlin.eloquent.eloquentandroid

import berlin.eloquent.eloquentandroid.auth.AuthActivityTest
import berlin.eloquent.eloquentandroid.ui.MainActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AuthActivityTest::class,
    MainActivityTest::class
)
class AndroidTestSuite
