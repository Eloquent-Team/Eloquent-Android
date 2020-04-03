package berlin.eloquent.eloquentandroid.feedback

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.databinding.FeedbackFragmentBinding
import javax.inject.Inject

class FeedbackFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: FeedbackViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
        (activity as AppCompatActivity).supportActionBar?.title = "Feedback"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FeedbackFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory).get(FeedbackViewModel::class.java)

        binding.feedbackViewModel = viewModel

        viewModel.setRecording()

        return binding.root
    }

}
