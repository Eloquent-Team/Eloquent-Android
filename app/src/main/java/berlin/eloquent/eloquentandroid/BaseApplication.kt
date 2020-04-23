package berlin.eloquent.eloquentandroid

import android.app.Application
import berlin.eloquent.eloquentandroid.di.AppComponent
import berlin.eloquent.eloquentandroid.di.DaggerAppComponent

open class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    open fun initAppComponent(){
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}
