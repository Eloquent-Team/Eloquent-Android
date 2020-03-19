package berlin.eloquent.eloquentandroid.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.databinding.AccountFragmentBinding

class AccountFragment : Fragment() {

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Account"

        val binding = AccountFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        binding.accountViewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

}