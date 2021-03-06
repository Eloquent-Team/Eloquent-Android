package berlin.eloquent.eloquentandroid.main.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.HomeFragmentBinding
import berlin.eloquent.eloquentandroid.main.MainActivity
import berlin.eloquent.eloquentandroid.main.home.models.RecordingRecyclerAdapter
import berlin.eloquent.eloquentandroid.main.home.models.RecordingRecyclerAdapter.OnRecordingClickListener
import berlin.eloquent.eloquentandroid.main.home.models.SpacingDecoration
import javax.inject.Inject

class HomeFragment : Fragment(), OnRecordingClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.toolbar_home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)

        val binding = HomeFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this

        binding.homeViewModel = viewModel

        ArrayAdapter.createFromResource(this.context!!, R.array.sort_by_options_array , android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sortBySpinner.adapter = it
        }

        val recordingRecyclerAdapter = RecordingRecyclerAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            val topSpacingDecorator = SpacingDecoration(25, 40, 25, 40)
            addItemDecoration(topSpacingDecorator)
            adapter = recordingRecyclerAdapter
        }

        viewModel.allRecordings.observe(viewLifecycleOwner, Observer {
            recordingRecyclerAdapter.submitList(ArrayList(it))
        })

        return binding.root
    }

    override fun onClick(position: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeDestToPlayerDest(viewModel.allRecordings.value!![position].recordingId))
    }

}
