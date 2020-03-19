package berlin.eloquent.eloquentandroid.home.models

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingDecoration(private val paddingTop: Int,private val paddingRight: Int, private val paddingBottom: Int, private val paddingLeft: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = paddingTop
        outRect.right = paddingRight
        outRect.bottom = paddingBottom
        outRect.left = paddingLeft
    }
}