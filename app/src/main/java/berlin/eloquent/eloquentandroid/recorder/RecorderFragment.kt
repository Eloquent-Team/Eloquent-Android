package berlin.eloquent.eloquentandroid.recorder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.RecorderFragmentBinding
import javax.inject.Inject

class RecorderFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: RecorderViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Recorder"
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory).get(RecorderViewModel::class.java)

        binding.recorderViewModel = viewModel

        viewModel.recordingState.observe(viewLifecycleOwner, Observer {
            when (it) {
                RecordingState.NOT_STARTED -> {
                    binding.startStopRecording.visibility = View.VISIBLE
                    binding.pauseResumeRecording.visibility = View.VISIBLE
                    binding.navigate.visibility = View.GONE
                }
                RecordingState.RECORDING -> {
                    binding.startStopRecording.setImageResource(R.drawable.ic_stop)
                    binding.pauseResumeRecording.setImageResource(R.drawable.ic_pause)
                }
                RecordingState.PAUSED -> {
                    binding.pauseResumeRecording.setImageResource(R.drawable.ic_refresh)
                }
                RecordingState.STOPPED -> {
                    binding.startStopRecording.visibility = View.GONE
                    binding.pauseResumeRecording.visibility = View.GONE
                    binding.navigate.visibility = View.VISIBLE
                }
                else -> {}
            }
        })

        binding.navigate.setOnClickListener {
            findNavController().navigate(RecorderFragmentDirections.actionRecorderDestToPlayerDest(viewModel.createdRecordingId.value!!))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetViewModel()
    }

}
