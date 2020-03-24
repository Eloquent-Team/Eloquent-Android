package berlin.eloquent.eloquentandroid.player

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.database.EloquentDatabase
import berlin.eloquent.eloquentandroid.databinding.PlayerFragmentBinding
import berlin.eloquent.eloquentandroid.recorder.RecorderViewModel
import berlin.eloquent.eloquentandroid.recorder.RecorderViewModelFactory

class PlayerFragment : Fragment() {

    private lateinit var viewModel: PlayerViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_player_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Player"
        setHasOptionsMenu(true)

        val binding = PlayerFragmentBinding.inflate(layoutInflater)

        val application = requireNotNull(this.activity).application

        val dataSource = EloquentDatabase.getInstance(application).recordingDao

        val viewModelFactory = PlayerViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayerViewModel::class.java)

        binding.playerViewModel = viewModel

        binding.lifecycleOwner = this

        Log.i("Test", "Helloooooaoaoökaejsnbf cliaweghuvblauwe")
        val safeArgs: PlayerFragmentArgs by navArgs()
        viewModel.setRecording(safeArgs.recordingId)

        viewModel.playingState.observe(viewLifecycleOwner, Observer {
            binding.controlPlayback.setImageResource(
                if (it == PlayingState.PLAYING) R.drawable.ic_pause else R.drawable.ic_play_arrow
            )
        })

        binding.analyzeRecording.setOnClickListener {
            viewModel.analyzeRecording(binding.recordingTitle.text.toString(), binding.recordingTags.text.toString())
            val action = PlayerFragmentDirections.actionPlayerFragmentToFeedbackFragment(viewModel.recording.value!!.recordingId)
            findNavController().navigate(action)
        }

        return binding.root
    }

}