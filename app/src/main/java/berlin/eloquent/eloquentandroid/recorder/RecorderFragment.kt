package berlin.eloquent.eloquentandroid.recorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
         * Observing the timestamp Live Data object to set the outputFile Live Data object when
         * startRecording is called
         */
        viewModel.timeStamp.observe(viewLifecycleOwner, Observer {
            viewModel.setOutputFile(activity?.getExternalFilesDir(null)!!.absolutePath)
        })

        return binding.root
    }
}