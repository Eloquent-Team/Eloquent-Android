package berlin.eloquent.eloquentandroid.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        /**
         * Sets a binding object between RecorderFragment and recorder_fragment for better
         * performance
         */
        val binding = HomeFragmentBinding.inflate(layoutInflater)

        /**
         * Gets the viewModel object from RecorderViewModel to interact with its
         * Live Data
         */
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        /**
         * Binds the viewModel to the layout representation of the viewModel
         */
        binding.homeViewModel = viewModel

        /**
         * Sets the RecorderFragment as the lifecycleOwner
         */
        binding.lifecycleOwner = this

        return binding.root
    }

}
