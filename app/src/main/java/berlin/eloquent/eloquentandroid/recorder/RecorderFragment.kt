package berlin.eloquent.eloquentandroid.recorder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.RecorderFragmentBinding

class RecorderFragment : Fragment() {

    private lateinit var viewModel: RecorderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        /**
         * Sets a binding object between RecorderFragment and recorder_fragment for better
         * performance
         */
        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        /**
         * Gets the viewModel object from RecorderViewModel to interact with its
         * Live Data
         */
        viewModel = ViewModelProvider(this).get(RecorderViewModel::class.java)

        /**
         * Binds the viewModel to the layout representation of the viewModel
         */
        binding.recorderViewModel = viewModel

        /**
         * Sets the RecorderFragment as the lifecycleOwner
         */
        binding.lifecycleOwner = this

        /**
         * Sets the imagebutton resource depending on the current recording state
         */
        viewModel.recordingState.observe(viewLifecycleOwner, Observer {
            if (it == RecordingState.PAUSED) {
                binding.pauseRecording.setImageResource(R.drawable.ic_refresh)
            } else {
                binding.pauseRecording.setImageResource(R.drawable.ic_pause)
            }
        })

        binding.stopRecording.setOnClickListener {
            Log.i("RecorderFragment", "listener")
            viewModel.stopRecording()
            val outputFile = viewModel.outputFile.value!!
            val action = RecorderFragmentDirections.nextAction(outputFile)
            findNavController().navigate(action)
        }

        return binding.root
    }
}