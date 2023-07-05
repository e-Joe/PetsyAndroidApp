package com.bytecode.petsy.presentation.ui.commonui.custom.chart


import android.graphics.Typeface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.presentation.ui.theme.ChartMaxColumn
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.chart.composed.plus
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.dimensions.emptyDimensions
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import kotlin.math.roundToInt


@Composable
fun PetsieChart(chartEntryModelProducer: ComposedChartEntryModelProducer<ChartEntryModel>) {
    val thresholdLine = rememberThresholdLine()
    val thresholdLine2 = rememberThresholdLine()

    ProvideChartStyle(rememberChartStyle(chartColors)) {
        val defaultColumns = currentChartStyle.columnChart.columns
        val defaultColumns2 = currentChartStyle.columnChart.columns

        var chart2 = columnChart(
            columns = remember(defaultColumns2) {
                defaultColumns2.map { defaultColumn ->
                    LineComponent(
                        ChartMaxColumn.toArgb(),
                        13f,
                        Shapes.roundedCornerShape(100)
                    )
                }
            },
            spacing = 9.dp,
            innerSpacing = 3.dp,
            mergeMode = ColumnChart.MergeMode.Grouped,
            axisValuesOverrider = AxisValuesOverrider.fixed(0f, 6f, 0f, 150f),
            targetVerticalAxisPosition = AxisPosition.Vertical.Start,
            decorations = remember(thresholdLine) { listOf(thresholdLine) },

            )

        var chart1 = columnChart(
            columns = remember(defaultColumns) {
                defaultColumns.map { defaultColumn ->
                    LineComponent(
                        defaultColumn.color,
                        13f,
                        Shapes.roundedCornerShape(100)
                    )
                }
            },
            spacing = 9.dp,
            innerSpacing = 3.dp,
            mergeMode = ColumnChart.MergeMode.Grouped,
            axisValuesOverrider = AxisValuesOverrider.fixed(0f, 6f, 0f, 150f),
            targetVerticalAxisPosition = AxisPosition.Vertical.Start,
            decorations = remember(thresholdLine2) { listOf(thresholdLine) },
        )



        Chart(
            modifier = Modifier.fillMaxSize(),
            chart = remember(chart2, chart1) { chart2 + chart1 },
            chartModelProducer = chartEntryModelProducer,
            startAxis = startAxis(
                maxLabelCount = 5,
                axis = LineComponent(
                    color = Color.Transparent.toArgb(),
                    thicknessDp = 0f,
                    dynamicShader = null,
                    shape = currentChartStyle.axis.axisLineShape,
                    margins = emptyDimensions(),
                    strokeWidthDp = 0f,
                    strokeColor = Color.Transparent.toArgb(),
                ),
                tick = null
            ),
            bottomAxis = bottomAxis(
                valueFormatter = bottomAxisValueFormatter,
                axis = LineComponent(
                    color = Color.Transparent.toArgb(),
                    thicknessDp = currentChartStyle.axis.axisLineWidth.value,
                    dynamicShader = null,
                    shape = currentChartStyle.axis.axisLineShape,
                    margins = emptyDimensions(),
                    strokeWidthDp = 0f,
                    strokeColor = Color.Transparent.toArgb()
                ),
                guideline = null,
                tick = null
            ),
//            marker = rememberMarker(),
            isZoomEnabled = false
        )
    }
}

@Composable
private fun rememberThresholdLine(): ThresholdLine {
    val label = textComponent(
        color = Color.Transparent,
        background = shapeComponent(Shapes.rectShape, color4),
        padding = thresholdLineLabelPadding,
        margins = thresholdLineLabelMargins,
        typeface = Typeface.MONOSPACE,
    )
    val line = shapeComponent(color = thresholdLineColor)
    return remember(line) {
        ThresholdLine(
            thresholdRange = thresholdLineValueRange,
            lineComponent = line,
            thresholdLabel = ""
        )
    }
}

data class DottedShape(
    val step: Dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val stepPx = with(density) { step.toPx() }
        val stepsCount = (size.width / stepPx).roundToInt()
        val actualStep = size.width / stepsCount
        val dotSize = Size(width = actualStep / 2, height = size.height)
        for (i in 0 until stepsCount) {
            addRect(
                Rect(
                    offset = Offset(x = i * actualStep, y = 0f),
                    size = dotSize
                )
            )
        }
        close()
    })
}

private const val COLOR_1_CODE = 0xFF63C4AF //0xff3e6558
private const val COLOR_2_CODE = 0xFFF18989 //0xff5e836a
private const val COLOR_3_CODE = 0xFF25cdf0 //0xffa5ba8e
private const val COLOR_4_CODE = 0xffe9e5af
private const val THRESHOLD_LINE_VALUE_RANGE_START = 120f
private const val THRESHOLD_LINE_VALUE_RANGE_END = 155f
private const val THRESHOLD_LINE_ALPHA = .36f
private const val COLUMN_CORNER_CUT_SIZE_PERCENT = 100

private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)
private val color3 = Color(COLOR_3_CODE)
private val color4 = Color(COLOR_4_CODE)
private val chartColors = listOf(color1, color2, color3)
private val thresholdLineValueRange =
    THRESHOLD_LINE_VALUE_RANGE_START..THRESHOLD_LINE_VALUE_RANGE_END
private val thresholdLineLabelHorizontalPaddingValue = 8.dp
private val thresholdLineLabelVerticalPaddingValue = 2.dp
private val thresholdLineLabelMarginValue = 4.dp
private val thresholdLineLabelPadding =
    dimensionsOf(thresholdLineLabelHorizontalPaddingValue, thresholdLineLabelVerticalPaddingValue)
private val thresholdLineLabelMargins = dimensionsOf(thresholdLineLabelMarginValue)
private val thresholdLineColor = color4.copy(THRESHOLD_LINE_ALPHA)
private val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
private val bottomAxisValueFormatter =
    AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _ -> daysOfWeek[x.toInt() % daysOfWeek.size] }
