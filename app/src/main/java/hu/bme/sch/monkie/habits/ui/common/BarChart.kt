package hu.bme.sch.monkie.habits.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import java.time.DayOfWeek
import java.time.LocalDateTime
import kotlin.random.Random

@Composable
fun BarchartWithSolidBars(modifier: Modifier = Modifier, labels: List<String>, numbers: List<Float>) {
    val maxRange = 15
    val barData = getBarChartData2(labels, numbers, BarChartType.VERTICAL, DataCategoryOptions())
    val yStepSize = 15

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(barData.size - 1)
        .bottomPadding(40.dp)
        .axisLabelAngle(20f)
        .backgroundColor(Color.Gray)
        .startDrawPadding(48.dp)
        .labelData { index -> barData[index].label }
        .build()
    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .backgroundColor(Color.Gray)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }
        .build()
    val barChartData = BarChartData(
        chartData = barData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(
            paddingBetweenBars = 20.dp,
            barWidth = 25.dp
        ),
        showYAxis = true,
        showXAxis = true,
        horizontalExtraSpace = 10.dp,
    )
    BarChart(modifier = Modifier.height(350.dp), barChartData = barChartData)
}


fun getBarChartData2(
    labels: List<String>,
    numbers: List<Float>,
    barChartType: BarChartType,
    dataCategoryOptions: DataCategoryOptions
): List<BarData> {
    if (labels.size != numbers.size) {
        throw IllegalArgumentException("Labels and numbers must have the same size")
    }

    val list = arrayListOf<BarData>()
    for (index in labels.indices) {
        val point = when (barChartType) {
            BarChartType.VERTICAL -> {
                Point(index.toFloat(), numbers[index])
            }

            BarChartType.HORIZONTAL -> {
                Point(numbers[index], index.toFloat())
            }
        }

        list.add(
            BarData(
                point = point,
                color = Color(
                    Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
                ),
                dataCategoryOptions = dataCategoryOptions,
                label = labels[index],
            )
        )
    }
    return list
}

fun countDatesByDayOfWeek(dates: List<LocalDateTime>): Map<DayOfWeek, Int> {
    return dates.groupBy { it.dayOfWeek }.mapValues { it.value.size }
}

fun countDatesByHourOfDay(dates: List<LocalDateTime>): Map<Int, Int> {
    return dates.groupBy { it.hour }.mapValues { it.value.size }
}