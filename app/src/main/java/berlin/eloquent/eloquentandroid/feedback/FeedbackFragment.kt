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

        val binding = FeedbackFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)

        binding.feedbackViewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

}