package berlin.eloquent.eloquentandroid.main.home.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.database.Recording

class RecordingRecyclerAdapter(private val onRecordingClickListener: OnRecordingClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var recordingList: List<Recording> = ArrayList()

    fun submitList(recordingList: List<Recording>) {
        this.recordingList = recordingList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecordingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_list_item_layout, parent, false),
            onRecordingClickListener
        )
    }

    override fun getItemCount(): Int {
        return recordingList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is RecordingViewHolder -> {
                holder.bind(recordingList[position])
            }
        }
    }

    interface OnRecordingClickListener {
        fun onClick(position: Int)
    }

}
