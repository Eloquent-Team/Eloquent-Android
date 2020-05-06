package berlin.eloquent.eloquentandroid.main.recorder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private val viewModel: RecorderViewModel by viewModels { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Recorder"
        super.onCreateView(inflater, container, savedInstanceState)

        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this.requireActivity(), permissions, 0)
        }

        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

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
