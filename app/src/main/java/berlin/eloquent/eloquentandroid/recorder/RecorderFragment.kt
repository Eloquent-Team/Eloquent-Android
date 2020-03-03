package berlin.eloquent.eloquentandroid.recorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import berlin.eloquent.eloquentandroid.databinding.RecorderFragmentBinding

class RecorderFragment : Fragment() {

    private lateinit var viewModel: RecorderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = RecorderFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(RecorderViewModel::class.java)
        binding.recorderViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}