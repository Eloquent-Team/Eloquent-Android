package berlin.eloquent.eloquentandroid.feedback

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.database.EloquentDatabase
import berlin.eloquent.eloquentandroid.databinding.FeedbackFragmentBinding
import berlin.eloquent.eloquentandroid.player.PlayerViewModel
import javax.inject.Inject

class FeedbackFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: FeedbackViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FeedbackFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, viewModelFactory).get(FeedbackViewModel::class.java)

        binding.feedbackViewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.setRecording()

        return binding.root
    }

}