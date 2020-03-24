package berlin.eloquent.eloquentandroid.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.database.EloquentDatabase
import berlin.eloquent.eloquentandroid.databinding.HomeFragmentBinding
import berlin.eloquent.eloquentandroid.feedback.FeedbackViewModel
import berlin.eloquent.eloquentandroid.feedback.FeedbackViewModelFactory
import berlin.eloquent.eloquentandroid.home.models.RecordingRecyclerAdapter
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

        val binding = HomeFragmentBinding.inflate(layoutInflater)

        val application = requireNotNull(this.activity).application

        val dataSource = EloquentDatabase.getInstance(application).recordingDao

        val viewModelFactory = HomeViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel

        binding.lifecycleOwner = this

        ArrayAdapter.createFromResource(this.context!!, R.array.sort_by_options_array , android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sortBySpinner.adapter = adapter
        }

        val recordingRecyclerAdapter = RecordingRecyclerAdapter()
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

}
