package berlin.eloquent.eloquentandroid.home.models

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import berlin.eloquent.eloquentandroid.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.layout_home_list_item.view.*

class RecordingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.recording_title
    private val date = itemView.recording_date
    private val length = itemView.recording_length

    fun bind(recording: Recording) {
        title.text = recording.title
        date.text = recording.date
        length.text = recording.length
    }
}