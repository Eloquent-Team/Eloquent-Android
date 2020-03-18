package berlin.eloquent.eloquentandroid.home

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.HomeFragmentBinding
import berlin.eloquent.eloquentandroid.home.models.SpacingDecoration

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.toolbar_home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        setHasOptionsMenu(true)

        // Sets a binding object between RecorderFragment and recorder_fragment for better performance
        val binding = HomeFragmentBinding.inflate(layoutInflater)

        // Gets the viewModel object from RecorderViewModel to interact with it Live Data
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Binds the viewModel to the layout representation of the viewModel
        binding.homeViewModel = viewModel

        // Sets the RecorderFragment as the lifecycleOwner
        binding.lifecycleOwner = this

        //
        ArrayAdapter.createFromResource(this.context, R.array.sort_by_options_array , android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sortBySpinner.adapter = adapter
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            val topSpacingDecorator = SpacingDecoration(25, 40, 25, 40)
            addItemDecoration(topSpacingDecorator)
            adapter = viewModel.recordingAdapter.value
        }

        viewModel.addDataSet()

        return binding.root
    }

}
