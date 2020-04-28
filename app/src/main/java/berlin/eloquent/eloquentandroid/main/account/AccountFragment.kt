package berlin.eloquent.eloquentandroid.main.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.databinding.AccountFragmentBinding
import javax.inject.Inject

class AccountFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: AccountViewModel by viewModels { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Account"
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = AccountFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

        binding.accountViewModel = viewModel

        return binding.root
    }

}
