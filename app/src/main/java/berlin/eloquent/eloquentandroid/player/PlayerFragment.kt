package berlin.eloquent.eloquentandroid.player

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.PlayerFragmentBinding
import berlin.eloquent.eloquentandroid.feedback.FeedbackFragmentArgs
import javax.inject.Inject

class PlayerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: PlayerViewModel

    // Attributes
    private val args: PlayerFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_player_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Player"
        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)

        val binding = PlayerFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayerViewModel::class.java)

        binding.playerViewModel = viewModel

        viewModel.setRecording(args.recordingId)

        binding.recordingTitle.setText(viewModel.recording.value!!.title)
        binding.recordingTags.setText(viewModel.recording.value!!.tags)

        viewModel.playingState.observe(viewLifecycleOwner, Observer {
            binding.controlPlayback.setImageResource(
                if (it == PlayingState.PLAYING) R.drawable.ic_pause else R.drawable.ic_play_arrow
            )
        })

        binding.analyzeRecording.setOnClickListener {
            viewModel.analyzeRecording(binding.recordingTitle.text.toString(), binding.recordingTags.text.toString())
            findNavController().navigate(PlayerFragmentDirections.actionPlayerDestToFeedbackDest(args.recordingId))
            onDestroy()
        }

        return binding.root
    }

}
