package berlin.eloquent.eloquentandroid.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.account.AccountViewModel
import berlin.eloquent.eloquentandroid.di.scopes.MainScope
import berlin.eloquent.eloquentandroid.feedback.FeedbackViewModel
import berlin.eloquent.eloquentandroid.home.HomeViewModel
import berlin.eloquent.eloquentandroid.player.PlayerViewModel
import berlin.eloquent.eloquentandroid.recorder.RecorderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule{

    @MainScope
    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(RecorderViewModel::class)
    abstract fun bindRecorderFragmentViewModel(recorderViewModel: RecorderViewModel) : ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(PlayerViewModel::class)
    abstract fun bindPlayerFragmentViewModel(playerViewModel: PlayerViewModel) : ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(AccountViewModel::class)
    abstract fun bindAccountFragmentViewModel(accountViewModel: AccountViewModel) : ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(FeedbackViewModel::class)
    abstract fun bindFeedbackFragmentViewModel(feedbackViewModel: FeedbackViewModel) : ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(HomeViewModel::class)
    abstract fun bindHomeFragmentViewModel(homeViewModel: HomeViewModel) : ViewModel

}
