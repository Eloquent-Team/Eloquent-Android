package berlin.eloquent.eloquentandroid.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.di.scopes.MainScope
import javax.inject.Inject
import javax.inject.Provider

class MainViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}
