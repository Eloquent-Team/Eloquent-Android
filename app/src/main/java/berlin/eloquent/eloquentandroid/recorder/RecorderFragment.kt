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

        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(RecorderViewModel::class.java)

        binding.recorderViewModel = viewModel

        binding.lifecycleOwner = this


        viewModel.recordingState.observe(viewLifecycleOwner, Observer {
            when (it) {
                RecordingState.RECORDING -> {
                    binding.pauseResumeRecording.setImageResource(R.drawable.ic_pause)
                    binding.startStopRecording.setImageResource(R.drawable.ic_stop)
                }
                RecordingState.PAUSED -> {
                    binding.pauseResumeRecording.setImageResource(R.drawable.ic_refresh)
                }
                RecordingState.STOPPED -> {
                    val action = RecorderFragmentDirections.nextAction(viewModel.recording.value!!)
                    findNavController().navigate(action)
                }
            }
        })

        return binding.root
    }
}