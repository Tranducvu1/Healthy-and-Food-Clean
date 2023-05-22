package com.example.healthyandfoodclean.fragment


import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.example.healthyandfoodclean.data.Run
import com.example.healthyandfoodclean.other.TrackingUtility
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

// Cửa sổ bật lên, khi chúng ta nhấp vào một thanh trong biểu đồ thanh
@SuppressLint("ViewConstructor")
class CustomMarkerView(
    val runs: List<Run>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {
private lateinit var  tvDate:TextView
private lateinit var tvAvgSpeed:TextView
    private lateinit var  tvDistance:TextView
    private lateinit var   tvDuration:TextView
    private lateinit var tvCaloriesBurned:TextView

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if(e == null) {
            return
        }
        val curRunId = e.x.toInt()
        val run = runs[curRunId]
        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        tvDate.text = dateFormat.format(calendar.time)

        "${run.avgSpeedInKMH}km/h".also {
            tvAvgSpeed.text = it
        }
        "${run.distanceInMeters / 1000f}km".also {
            tvDistance.text = it
        }
        tvDuration.text =
            TrackingUtility.getFormattedStopWatchTime(
                run.timeInMillis
            )
        "${run.caloriesBurned}kcal".also {
            tvCaloriesBurned.text = it
        }
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }
}