package com.froschprog.tongps.utilsmpandroid

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

// Format the % value in the Pie chart (add the % sign, without decimal digit)
// Removes value if 0%
class MyPercentFormatter() : ValueFormatter() {
    var mFormat: DecimalFormat
    private var pieChart: PieChart? = null

    constructor(pieChart: PieChart?) : this() {
        this.pieChart = pieChart
    }

    override fun getFormattedValue(value: Float): String {
        val s: String
        if (value < 1F) s = "" else s = mFormat.format(value).toString() + "%"
        return s
    }

    override fun getPieLabel(value: Float, pieEntry: PieEntry): String {
        return if (pieChart != null && pieChart!!.isUsePercentValuesEnabled) {
            // Converted to percent
            getFormattedValue(value)
        } else {
            // raw value, skip percent sign
            mFormat.format(value)
        }
    }

    init {
        mFormat = DecimalFormat("########")
    }
}