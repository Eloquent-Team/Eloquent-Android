package berlin.eloquent.eloquentandroid.di

import android.app.Application
import berlin.eloquent.eloquentandroid.ui.MainActivityTest
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class, SubComponentsModule::class])
interface TestAppComponent : AppComponent  {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): TestAppComponent
    }

    fun inject(mainActivityTest: MainActivityTest)

}
