package berlin.eloquent.eloquentandroid

import berlin.eloquent.eloquentandroid.di.DaggerTestAppComponent

class TestBaseApplication : BaseApplication() {

    override fun initAppComponent() {
        appComponent = DaggerTestAppComponent
            .builder()
            .application(this)
            .build()
    }

}