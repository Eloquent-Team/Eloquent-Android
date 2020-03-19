package berlin.eloquent.eloquentandroid.home.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import berlin.eloquent.eloquentandroid.R

class RecordingRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Recording> = ArrayList()

    fun submitList(recordingList: List<Recording>) {
        items = recordingList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecordingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_list_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is RecordingViewHolder -> {
                holder.bind(items[position])
            }
        }
    }
}