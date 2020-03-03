package berlin.eloquent.eloquentandroid.recorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.RecorderFragmentBinding

class RecorderFragment : Fragment() {

    private lateinit var viewModel: RecorderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(RecorderViewModel::class.java)

        binding.recorderViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.timeStamp.observe(viewLifecycleOwner, Observer { timestamp ->
            viewModel.outputFile = activity?.getExternalFilesDir(null)?.absolutePath + "/recording_$timestamp"
        })

        viewModel.recordingPaused.observe(viewLifecycleOwner, Observer { recordingPaused ->
            if (recordingPaused) {
                binding.pauseRecording.setImageResource(R.drawable.ic_refresh)
            } else {
                binding.pauseRecording.setImageResource(R.drawable.ic_pause)
            }

        })

        return binding.root
    }
}