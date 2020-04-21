package berlin.eloquent.eloquentandroid.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.main.account.AccountViewModel
import berlin.eloquent.eloquentandroid.di.scopes.MainScope
import berlin.eloquent.eloquentandroid.main.feedback.FeedbackViewModel
import berlin.eloquent.eloquentandroid.main.home.HomeViewModel
import berlin.eloquent.eloquentandroid.main.player.PlayerViewModel
import berlin.eloquent.eloquentandroid.main.recorder.RecorderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule{

    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @MainViewModelKey(RecorderViewModel::class)
    abstract fun bindRecorderFragmentViewModel(recorderViewModel: RecorderViewModel) : ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(PlayerViewModel::class)
    abstract fun bindPlayerFragmentViewModel(playerViewModel: PlayerViewModel) : ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(AccountViewModel::class)
    abstract fun bindAccountFragmentViewModel(accountViewModel: AccountViewModel) : ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(FeedbackViewModel::class)
    abstract fun bindFeedbackFragmentViewModel(feedbackViewModel: FeedbackViewModel) : ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(HomeViewModel::class)
    abstract fun bindHomeFragmentViewModel(homeViewModel: HomeViewModel) : ViewModel

}
