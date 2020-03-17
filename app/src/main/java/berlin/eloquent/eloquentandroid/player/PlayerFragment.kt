package berlin.eloquent.eloquentandroid.player

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
        setHasOptionsMenu(true)

        /**
         * Sets a binding object between RecorderFragment and recorder_fragment for better
         * performance
         */
        val binding = PlayerFragmentBinding.inflate(layoutInflater)

        /**
         * Gets the viewModel object from RecorderViewModel to interact with its
         * Live Data
         */
        viewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)

        /**
         * Binds the viewModel to the layout representation of the viewModel
         */
        binding.playerViewModel = viewModel

        /**
         * Sets the RecorderFragment as the lifecycleOwner
         */
        binding.lifecycleOwner = this

        val safeArgs: PlayerFragmentArgs by navArgs()
        viewModel.setOutputFile(safeArgs.outputFile)


        return binding.root
    }

}