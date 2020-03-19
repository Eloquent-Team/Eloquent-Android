package berlin.eloquent.eloquentandroid.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.databinding.FeedbackFragmentBinding

class FeedbackFragment : Fragment() {

    private lateinit var viewModel: FeedbackViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // Sets a binding object between FeedbackFragment and recorder_fragment
        // for better performance
        val binding = FeedbackFragmentBinding.inflate(layoutInflater)

        // Gets the viewModel object from FeedbackViewModel to interact with its
        // Live Data
        viewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)

        // Binds the viewModel to the layout representation of the viewModel
        binding.feedbackViewModel = viewModel

        // Sets the FeedbackFragment as the lifecycleOwner
        binding.lifecycleOwner = this

        return binding.root
    }

}