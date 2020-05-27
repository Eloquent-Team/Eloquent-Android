package berlin.eloquent.eloquentandroid.main.feedback

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import berlin.eloquent.eloquentandroid.databinding.FeedbackFragmentBinding
import berlin.eloquent.eloquentandroid.main.MainActivity
import javax.inject.Inject

class FeedbackFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private  val viewModel: FeedbackViewModel by viewModels { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Feedback"
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FeedbackFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

        binding.feedbackViewModel = viewModel

        val args: FeedbackFragmentArgs by navArgs()
        viewModel.setRecording(args.recordingId)

        return binding.root
    }

}
