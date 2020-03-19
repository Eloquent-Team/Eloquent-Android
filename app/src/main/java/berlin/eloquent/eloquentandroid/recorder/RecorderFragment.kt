package berlin.eloquent.eloquentandroid.recorder

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.RecorderFragmentBinding

class RecorderFragment : Fragment() {

    private lateinit var viewModel: RecorderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Recorder"

        // Sets a binding object between FeedbackFragment and recorder_fragment
        // for better performance
        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        // Gets the viewModel object from RecorderViewModel to interact with its
        // Live Data
        viewModel = ViewModelProvider(this).get(RecorderViewModel::class.java)

        // Binds the viewModel to the layout representation of the viewModel
        binding.recorderViewModel = viewModel

        // Sets the RecorderFragment as the lifecycleOwner
        binding.lifecycleOwner = this

        // Sets the imagebutton resource depending on the current recording state
        viewModel.recordingState.observe(viewLifecycleOwner, Observer {
            if (it == RecordingState.PAUSED) {
                binding.pauseRecording.setImageResource(R.drawable.ic_refresh)
            } else {
                binding.pauseRecording.setImageResource(R.drawable.ic_pause)
            }
        })

        // Sets a onClickListener to call the stopRecording function and then do the navigation.
        binding.stopRecording.setOnClickListener {
            viewModel.stopRecording()
            val action = RecorderFragmentDirections.nextAction(viewModel.outputFile.value!!)
            findNavController().navigate(action)
        }

        return binding.root
    }
}