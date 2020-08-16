package com.example.sohaibrabbani.psychx

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_reporting.*
import java.util.*

class ReportingAllEmotionFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle("Emotion Report")
    }
    override fun onStart() {
        super.onStart()
        PsychxWebService().getEmotions(this, 8)
    }


    fun populateGraph(anger_list: ArrayList<Double>, disgust_list: ArrayList<Double>,
                      fear_list: ArrayList<Double>, joy_list: ArrayList<Double>,
                      sadness_list: ArrayList<Double>, surprise_list: ArrayList<Double>) {
        populateJoy("Joy", joy_list, graph_joy as GraphView, Color.YELLOW)
        populateJoy("Anger", anger_list, graph_anger as GraphView, Color.RED)
        populateJoy("Disgust", disgust_list, graph_disgust as GraphView, Color.MAGENTA)
        populateJoy("Fear", fear_list, graph_fear as GraphView, Color.GREEN)
        populateJoy("Sadness", sadness_list, graph_sadness as GraphView, Color.BLUE)
        populateJoy("Surprise", surprise_list, graph_surprise as GraphView, Color.CYAN)
    }

    private fun populateJoy(title: String, emotion_list: ArrayList<Double>, graph_view: GraphView, color: Int) {
        var emotionList = ArrayList<DataPoint>()

        for (i in emotion_list.indices) {
            emotionList.add(DataPoint(i.toDouble(), emotion_list[i]))
        }

        val emotionArray = arrayOfNulls<DataPoint>(emotion_list.size)

        emotionList.toArray(emotionArray)

        var graph = graph_view

        var emotionSeries = LineGraphSeries<DataPoint>(emotionArray)
        emotionSeries.color = color

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX(emotion_list.size.toDouble())
        graph.title = title
        graph.titleColor = Color.BLACK
        graph.gridLabelRenderer.horizontalAxisTitle = "Messages"
        graph.gridLabelRenderer.horizontalAxisTitleTextSize = 12f

        graph.gridLabelRenderer.verticalAxisTitle = "Emotions Valence"
        graph.gridLabelRenderer.verticalAxisTitleTextSize = 12f

        var maxY = Collections.max(emotion_list)
        maxY += (10 - (maxY % 10))
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.setMinY(0.0)
        graph.viewport.setMaxY(maxY.toDouble())
        graph.addSeries(emotionSeries)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reporting_all_chart, container, false)
    }

}
