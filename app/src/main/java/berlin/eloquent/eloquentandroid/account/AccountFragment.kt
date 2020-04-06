package berlin.eloquent.eloquentandroid.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.databinding.AccountFragmentBinding
import javax.inject.Inject

class AccountFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: AccountViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Account"
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = AccountFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory).get(AccountViewModel::class.java)

        binding.accountViewModel = viewModel

        return binding.root
    }

}
