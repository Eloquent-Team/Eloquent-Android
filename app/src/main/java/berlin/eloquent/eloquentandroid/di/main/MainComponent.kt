package berlin.eloquent.eloquentandroid.di.main

import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.account.AccountFragment
import berlin.eloquent.eloquentandroid.di.scopes.MainScope
import berlin.eloquent.eloquentandroid.feedback.FeedbackFragment
import berlin.eloquent.eloquentandroid.home.HomeFragment
import berlin.eloquent.eloquentandroid.player.PlayerFragment
import berlin.eloquent.eloquentandroid.recorder.RecorderFragment
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [MainModule::class, MainViewModelModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(recorderFragment: RecorderFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(playerFragment: PlayerFragment)
    fun inject(feedbackFragment: FeedbackFragment)
    fun inject(accountFragment: AccountFragment)

}