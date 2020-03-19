package berlin.eloquent.eloquentandroid.player

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.PlayerFragmentBinding

class PlayerFragment : Fragment() {

    private lateinit var viewModel: PlayerViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.toolbar_player_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Player"
        setHasOptionsMenu(true)

        val binding = PlayerFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)

        binding.playerViewModel = viewModel

        binding.lifecycleOwner = this

        val safeArgs: PlayerFragmentArgs by navArgs()
        viewModel.setRecording(safeArgs.recording)

        viewModel.playingState.observe(viewLifecycleOwner, Observer {
            if (it == PlayingState.PLAYING) {
                binding.controlPlayback.setImageResource(R.drawable.ic_pause)
            } else {
                binding.controlPlayback.setImageResource(R.drawable.ic_play_arrow)
            }
        })

        return binding.root
    }

}