package berlin.eloquent.eloquentandroid.recorder

import android.content.Context
import android.os.Bundle
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
        (activity as AppCompatActivity).supportActionBar?.title = "Recorder"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory).get(RecorderViewModel::class.java)

        binding.recorderViewModel = viewModel

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
                    //val action = RecorderFragmentDirections.actionRecorderFragmentToPlayerFragment()

                }
                else -> {

                }
            }
        })

        binding.navigate.setOnClickListener {
            findNavController().navigate(R.id.action_recorderFragment_to_playerFragment)
        }

        return binding.root
    }

}
