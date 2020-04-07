package berlin.eloquent.eloquentandroid.home.models

import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import berlin.eloquent.eloquentandroid.database.Recording
import berlin.eloquent.eloquentandroid.home.models.RecordingRecyclerAdapter.OnRecordingClickListener
import kotlinx.android.synthetic.main.home_list_item_layout.view.*

class RecordingViewHolder(itemView: View, private var onRecordingClickListener: OnRecordingClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val title = itemView.recording_title
    private val date = itemView.recording_date
    private val length = itemView.recording_length

    fun bind(recording: Recording) {
        title.text = recording.title
        date.text = recording.date
        length.text = DateUtils.formatElapsedTime(recording.length)

        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onRecordingClickListener.onClick(adapterPosition)
    }

}
