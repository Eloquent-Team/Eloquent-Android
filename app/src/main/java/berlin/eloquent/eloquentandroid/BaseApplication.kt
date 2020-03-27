package berlin.eloquent.eloquentandroid

import android.app.Application
import berlin.eloquent.eloquentandroid.di.AppComponent
import berlin.eloquent.eloquentandroid.di.DaggerAppComponent

class BaseApplication : Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }


    fun initAppComponent(){
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }


}