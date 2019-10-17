package signalmaster.com.smmobile.chart;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.scichart.charting.ClipMode;
import com.scichart.charting.Direction2D;
import com.scichart.charting.model.AnnotationCollection;
import com.scichart.charting.model.RenderableSeriesCollection;
import com.scichart.charting.model.dataSeries.IDataSeries;
import com.scichart.charting.model.dataSeries.IOhlcDataSeries;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.model.dataSeries.OhlcDataSeries;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.model.dataSeries.XyyDataSeries;
import com.scichart.charting.modifiers.AnnotationCreationModifier;
import com.scichart.charting.modifiers.AxisDragModifierBase;
import com.scichart.charting.modifiers.DefaultAnnotationFactory;
import com.scichart.charting.modifiers.ExecuteOn;
import com.scichart.charting.modifiers.IAnnotationFactory;
import com.scichart.charting.modifiers.ZoomExtentsModifier;
import com.scichart.charting.numerics.labelProviders.ICategoryLabelProvider;
import com.scichart.charting.numerics.labelProviders.NumericLabelProvider;
import com.scichart.charting.visuals.ISciChartSurface;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.AnnotationCoordinateMode;
import com.scichart.charting.visuals.annotations.AxisMarkerAnnotation;
import com.scichart.charting.visuals.annotations.CustomAnnotation;
import com.scichart.charting.visuals.annotations.HorizontalAnchorPoint;
import com.scichart.charting.visuals.annotations.HorizontalLineAnnotation;
import com.scichart.charting.visuals.annotations.IAnnotation;
import com.scichart.charting.visuals.annotations.LabelPlacement;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.charting.visuals.annotations.VerticalAnchorPoint;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.AxisAlignment;
import com.scichart.charting.visuals.axes.CategoryDateAxis;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.charting.visuals.renderableSeries.BaseRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastCandlestickRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastMountainRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastOhlcRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries;
import com.scichart.charting.visuals.synchronization.SciChartVerticalGroup;
import com.scichart.core.common.Action1;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.data.model.DoubleRange;
import com.scichart.data.model.IRange;
import com.scichart.data.numerics.SearchMode;
import com.scichart.drawing.common.BrushStyle;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.common.SolidBrushStyle;
import com.scichart.drawing.common.SolidPenStyle;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.annotation.SmCurrentValueView;
import signalmaster.com.smmobile.annotation.SmOrderView;
import signalmaster.com.smmobile.annotation.SmValueAnnotation;
import signalmaster.com.smmobile.data.MovingAverage;
import signalmaster.com.smmobile.data.PriceBar;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataItem;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.global.SmConst;
import signalmaster.com.smmobile.index.SmIndex;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.network.SmServiceManager;
import signalmaster.com.smmobile.order.SmOrder;
import signalmaster.com.smmobile.order.SmOrderState;
import signalmaster.com.smmobile.order.SmTotalOrderManager;
import signalmaster.com.smmobile.position.SmPositionType;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;
import signalmaster.com.smmobile.system.SmSystem;
import signalmaster.com.smmobile.annotation.ChartSetting;
import signalmaster.com.smmobile.annotation.ChartTouchModifier;
import signalmaster.com.smmobile.annotation.SmCreateTouch;
import signalmaster.com.smmobile.annotation.SmDeleteTouch;
import signalmaster.com.smmobile.annotation.SmJeepyoTouch;
import signalmaster.com.smmobile.data.PriceSeries;

import static com.scichart.core.utility.Dispatcher.runOnUiThread;
import static java.lang.Math.min;

enum SmChartStyle {
    mock_main,
    mock_left,
    mock_right,
    current_top,
    current_middle,
    current_bottom,
    signal_upper,
    signal_lower
}


public class SmChartFragment extends Fragment {

    private static final String PRICES = "Prices";
    private static final String RSI = "RSI";
    private static final String MACD = "MACD";
    private static final String CCI = "CCI";
    private static final String MOMENTUM = "MOMENTUM";
    private static final String ATR = "ATR";
    private static final String Stochastic = "Stochastic";

    /*@BindView(R.id.priceChart)
    SciChartSurface priceChart;*/

    @BindView(R.id.chart_layout)
    SciChartSurface surface;

    //chart dynamic add
    //@BindView(R.id.addChart)
    SciChartSurface addSurface;
    LinearLayout chartLinear;



    /*@BindView(R.id.macdChart)
    SciChartSurface macdChart;

    @BindView(R.id.rsiChart)
    SciChartSurface rsiChart;

    @BindView(R.id.volumeChart)
    SciChartSurface volumeChart;*/

    private final SciChartVerticalGroup verticalGroup = new SciChartVerticalGroup();

    private DoubleRange sharedXRange = new DoubleRange();


    public SmSeriesType getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(SmSeriesType seriesType) {
        this.seriesType = seriesType;
    }

    public String getSymbolCode() {
        return symbolCode;
    }

    public void setSymbolCode(String symbol_code) {
        this.symbolCode = symbol_code;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    private String symbolName;
    private String chartTitle;

    //소숫점
    public static class NumericLabelProviderEx extends NumericLabelProvider {
        String axisLabelFormat;

        public NumericLabelProviderEx(String axisLabelFormat) {
            super();
            this.axisLabelFormat = axisLabelFormat;
        }

        @Override
        public String formatLabel(Comparable dataValue) {
            // return a formatting string for tick labels
            return String.format(axisLabelFormat, (Double) dataValue);
        }

        @Override
        public String formatCursorLabel(Comparable dataValue) {
            // return a formatting string for modifiers' axis labels
            return formatLabel(dataValue);
        }
    }


    private IAxis xAxis = null;
    private IAxis yAxis = null;
    private String style = "mock_main";
    private int chartCycle = 1;
    public void setChartCycle(int cycle) {
        chartCycle = cycle;
    }
    private SmChartType chartType = SmChartType.MIN;
    public void setChartType(SmChartType chartType) {
        this.chartType = chartType;
    }
    private SmChart _chartSetting = null;
    private final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();

    // 현재 차트가 선택한 심볼의 코드
    private String symbolCode;

    public IAxis getyAxis() {
        return yAxis;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static SmChartFragment newInstance(String style) {
        Bundle bundle = new Bundle();
        bundle.putString("chart_style", style);

        SmChartFragment smChartFragment = new SmChartFragment();
        smChartFragment.setArguments(bundle);
        return smChartFragment;
    }

    public SmChartFragment() {
    }

    public void hideChart() {

        surface.setVisibility(View.INVISIBLE);
        /*macdChart.setVisibility(View.GONE);
        rsiChart.setVisibility(View.GONE);
        volumeChart.setVisibility(View.GONE);*/
        //priceChart.setRenderSurface(null);
    }

    public void showChart() {
        surface.setVisibility(View.VISIBLE);

    }

    private abstract static class BasePaneModel {
        public final RenderableSeriesCollection renderableSeries;
        public final AnnotationCollection annotations;
        public final NumericAxis yAxis;
        public final String title;

        protected BasePaneModel(SciChartBuilder builder, String title, String yAxisTextFormatting) {
            this.title = title;
            this.renderableSeries = new RenderableSeriesCollection();
            this.annotations = new AnnotationCollection();

            this.yAxis = builder.newNumericAxis()
                    .withAxisId(title)
                    .withTextFormatting(yAxisTextFormatting)
                    .withAutoRangeMode(AutoRange.Always)
                    .withDrawMinorGridLines(true)
                    .withDrawMajorGridLines(true)
                    .withMinorsPerMajor(2)
                    .withMaxAutoTicks(4)
                    .withGrowBy(new DoubleRange(0d, 0d))
                    .build();
        }

        final void addRenderableSeries(BaseRenderableSeries renderableSeries) {
            renderableSeries.setClipToBounds(true);
            this.renderableSeries.add(renderableSeries);
        }
    }

    private static class MacdPaneModel extends BasePaneModel {
        // 12, 25, 9
        public MacdPaneModel(SciChartBuilder builder, SmChartData chartData, int slow, int fast, int signal) {

            super(builder, MACD, "0.00");

            final MovingAverage.MacdPoints macdPoints = MovingAverage.macd(chartData.getClose(), slow, fast, signal);

            final XyDataSeries<Date, Double> histogramDataSeries = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("Histogram").build();
            histogramDataSeries.setAcceptsUnsortedData(true);
            histogramDataSeries.append(chartData.getDate(), macdPoints.divergenceValues);
            addRenderableSeries(builder.newColumnSeries().withDataSeries(histogramDataSeries).withYAxisId(MACD).build());

            final XyyDataSeries<Date, Double> macdDataSeries = builder.newXyyDataSeries(Date.class, Double.class).withSeriesName("MACD").build();
            macdDataSeries.setAcceptsUnsortedData(true);
            macdDataSeries.append(chartData.getDate(), macdPoints.macdValues, macdPoints.signalValues);
            addRenderableSeries(builder.newBandSeries().withDataSeries(macdDataSeries).withYAxisId(MACD).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(histogramDataSeries.getYValues().get(histogramDataSeries.getCount() - 1)).withYAxisId(MACD).build(),
                    builder.newAxisMarkerAnnotation().withY1(macdDataSeries.getYValues().get(macdDataSeries.getCount() - 1)).withYAxisId(MACD).build());
        }
    }

    private static class RsiPaneModel extends BasePaneModel {
        public RsiPaneModel(SciChartBuilder builder, SmChartData chartData, int period, int color, float thickness) {
            super(builder, RSI, "0.0");

            final XyDataSeries<Date, Double> rsiSeries = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("RSI").build();
            //rsiSeries.append(chartData.getDate(), MovingAverage.rsi(chartData.getClose(), 14));
            addRenderableSeries(builder.newLineSeries().withDataSeries(rsiSeries).withStrokeStyle(color, thickness).withYAxisId(RSI).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(rsiSeries.getYValues().get(rsiSeries.getCount() - 1)).withYAxisId(RSI).build());
        }
    }

    private static class StochasticPaneModel extends BasePaneModel {
        public StochasticPaneModel(SciChartBuilder builder, SmChartData chartData, int period, int color, float thickness) {
            super(builder, Stochastic, "0.0");

            final XyDataSeries<Date, Double> rsiSeries = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("Stochastic").build();
            //rsiSeries.append(chartData.getDate(), MovingAverage.rsi(chartData.getClose(), 14));
            addRenderableSeries(builder.newLineSeries().withDataSeries(rsiSeries).withStrokeStyle(color, thickness).withYAxisId(Stochastic).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(rsiSeries.getYValues().get(rsiSeries.getCount() - 1)).withYAxisId(Stochastic).build());
        }
    }

    private static class MomentumPaneModel extends BasePaneModel {
        public MomentumPaneModel(SciChartBuilder builder, SmChartData chartData, int period, int color, float thickness) {
            super(builder, MOMENTUM, "0.0");

            final XyDataSeries<Date, Double> rsiSeries = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("MOMENTUM").build();
            //rsiSeries.append(chartData.getDate(), MovingAverage.rsi(chartData.getClose(), 14));
            addRenderableSeries(builder.newLineSeries().withDataSeries(rsiSeries).withStrokeStyle(color, thickness).withYAxisId(MOMENTUM).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(rsiSeries.getYValues().get(rsiSeries.getCount() - 1)).withYAxisId(MOMENTUM).build());
        }
    }

    private static class CciPaneModel extends BasePaneModel {
        public CciPaneModel(SciChartBuilder builder, SmChartData chartData, int period, int color, float thickness) {
            super(builder, CCI, "0.0");

            final XyDataSeries<Date, Double> rsiSeries = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("CCI").build();
            //rsiSeries.append(chartData.getDate(), MovingAverage.rsi(chartData.getClose(), 14));
            addRenderableSeries(builder.newLineSeries().withDataSeries(rsiSeries).withStrokeStyle(color, thickness).withYAxisId(CCI).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(rsiSeries.getYValues().get(rsiSeries.getCount() - 1)).withYAxisId(CCI).build());
        }
    }

    private static class AtrPaneModel extends BasePaneModel {
        public AtrPaneModel(SciChartBuilder builder, SmChartData chartData, int period, int color, float thickness) {
            super(builder, ATR, "0.0");

            final XyDataSeries<Date, Double> rsiSeries = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("ATR").build();
            //rsiSeries.append(chartData.getDate(), MovingAverage.rsi(chartData.getClose(), 14));
            addRenderableSeries(builder.newLineSeries().withDataSeries(rsiSeries).withStrokeStyle(color, thickness).withYAxisId(ATR).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(rsiSeries.getYValues().get(rsiSeries.getCount() - 1)).withYAxisId(ATR).build());
        }
    }

    //surface.getRenderableSeries().remove(0);
    public void addMA(int period, String name, int color, float thickness) {
        if (this.chartData == null)
            return;

        final XyDataSeries<Date, Double> ma = sciChartBuilder.newXyDataSeries(Date.class, Double.class).withSeriesName(name).build();
        ma.setAcceptsUnsortedData(true);
        ma.append(chartData.getDate(), MovingAverage.movingAverage(chartData.getClose(), period));
        int size = surface.getRenderableSeries().size();
        surface.getRenderableSeries().add(sciChartBuilder.newLineSeries().withDataSeries(ma).withStrokeStyle(color, thickness).build());
    }

    //리스트 값에 따라 비교해준다.
    public void removeMA(SciChartSurface surface) {
        if (surface.getRenderableSeries().size() > 0) {
            surface.getRenderableSeries().remove(0);
        }
    }

    // 내부지표
    public void addBolligerBand(int uppperPeriod, int middlePeriod, int lowerPeriod, String name) {
        if (this.chartData == null)
            return;

        final XyDataSeries<Date, Double> ma = sciChartBuilder.newXyDataSeries(Date.class, Double.class).withSeriesName(name).build();
        ma.append(chartData.getDate(), MovingAverage.movingAverage(chartData.getClose(), uppperPeriod));
        surface.getRenderableSeries().add(sciChartBuilder.newLineSeries().withDataSeries(ma).withStrokeStyle(0xFFFF0000, 1.0f).build());
    }

    // 내부 지표
    public void addParabolicSAR() {
        if (this.chartData == null)
            return;

        int period = 25;
        String name = "parabolic SAR";
        int color = 0xFFFF0000;
        float thickness = 1.0f;

        final XyDataSeries<Date, Double> ma = sciChartBuilder.newXyDataSeries(Date.class, Double.class).withSeriesName(name).build();
        ma.append(chartData.getDate(), MovingAverage.movingAverage(chartData.getClose(), period));
        surface.getRenderableSeries().add(sciChartBuilder.newLineSeries().withDataSeries(ma).withStrokeStyle(color, thickness).build());
    }

    private SciChartSurface stochasticChart = null;
    // 외부 지표 원래는 int length
    public void addStochastic(int slow, int fast, int signal,float mainWeight, float subWeight) {
        if (this.chartData == null || stochasticChart != null || this.chart_layout == null)
            return;
        //int slow = 12, fast = 25, signal = 9;
        MacdPaneModel model = new MacdPaneModel(sciChartBuilder, this.chartData, slow, fast, signal);
        stochasticChart = new SciChartSurface(this.getContext());

        // Add the SciChartSurface to the layout
        this.chart_layout.addView(stochasticChart);

        // Set layout parameters for both surfaces
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, mainWeight);

        surface.setLayoutParams(layoutParams1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, subWeight);
        stochasticChart.setLayoutParams(layoutParams2);

        final CategoryDateAxis xAxis = sciChartBuilder.newCategoryDateAxis()
                .withVisibility(View.GONE)
                .withVisibleRange(sharedXRange)
                .withGrowBy(0, 0.05)
                .build();

        stochasticChart.getXAxes().add(xAxis);
        stochasticChart.getYAxes().add(model.yAxis);

        stochasticChart.getRenderableSeries().addAll(model.renderableSeries);

        stochasticChart.getChartModifiers().add(sciChartBuilder
                .newModifierGroup()
                .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).withClipModeX(ClipMode.ClipAtExtents).build()
                .withPinchZoomModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
                .withLegendModifier().withShowCheckBoxes(false).build()
                .build());

        stochasticChart.setAnnotations(model.annotations);

        verticalGroup.addSurfaceToGroup(stochasticChart);
    }

    private SciChartSurface macdChart = null;
    // 12, 25, 9
    //외부지표
    public void addMACD(int slow, int fast, int signal,float mainWeight, float subWeight) {
        if (this.chartData == null || macdChart != null || this.chart_layout == null)
            return;

        MacdPaneModel model = new MacdPaneModel(sciChartBuilder, this.chartData, slow, fast, signal);
        macdChart = new SciChartSurface(this.getContext());

        // Add the SciChartSurface to the layout
        this.chart_layout.addView(macdChart);

        // Set layout parameters for both surfaces
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, mainWeight);

        surface.setLayoutParams(layoutParams1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, subWeight);
        macdChart.setLayoutParams(layoutParams2);

        final CategoryDateAxis xAxis = sciChartBuilder.newCategoryDateAxis()
                .withVisibility(View.GONE)
                .withVisibleRange(sharedXRange)
                .withGrowBy(0, 0.05)
                .build();

        macdChart.getXAxes().add(xAxis);
        macdChart.getYAxes().add(model.yAxis);

        macdChart.getRenderableSeries().addAll(model.renderableSeries);

        macdChart.getChartModifiers().add(sciChartBuilder
                .newModifierGroup()
                .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).withClipModeX(ClipMode.ClipAtExtents).build()
                .withPinchZoomModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
                .withLegendModifier().withShowCheckBoxes(false).build()
                .build());

        macdChart.setAnnotations(model.annotations);

        verticalGroup.addSurfaceToGroup(macdChart);
    }


    private SciChartSurface rsiChart = null;
    //외부지표
    public void addRSI(int period, int color, float thickness, float mainWeight, float subWeight ) {
        if (this.chartData == null || rsiChart != null || this.chart_layout == null)
            return;

        RsiPaneModel model = new RsiPaneModel(sciChartBuilder, this.chartData, period, color, thickness);
        rsiChart = new SciChartSurface(this.getContext());

        // Add the SciChartSurface to the layout
        this.chart_layout.addView(rsiChart);

        // Set layout parameters for both surfaces
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, mainWeight);

        surface.setLayoutParams(layoutParams1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, subWeight);
        rsiChart.setLayoutParams(layoutParams2);

        final CategoryDateAxis xAxis = sciChartBuilder.newCategoryDateAxis()
                .withVisibility(View.GONE)
                .withVisibleRange(sharedXRange)
                .withGrowBy(0, 0.05)
                .build();

        rsiChart.getXAxes().add(xAxis);
        rsiChart.getYAxes().add(model.yAxis);

        rsiChart.getRenderableSeries().addAll(model.renderableSeries);

        rsiChart.getChartModifiers().add(sciChartBuilder
                .newModifierGroup()
                .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).withClipModeX(ClipMode.ClipAtExtents).build()
                .withPinchZoomModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
                .withLegendModifier().withShowCheckBoxes(false).build()
                .build());

        rsiChart.setAnnotations(model.annotations);

        verticalGroup.addSurfaceToGroup(rsiChart);
    }


    private SciChartSurface momentumChart = null;
    // 외부 지표
    public void addMomentum(int period, int color, int thickness) {
        if (this.chartData == null || momentumChart != null || this.chart_layout == null)
            return;

        RsiPaneModel model = new RsiPaneModel(sciChartBuilder, this.chartData, period, color, thickness);
        momentumChart = new SciChartSurface(this.getContext());

        // Add the SciChartSurface to the layout
        this.chart_layout.addView(momentumChart);

        // Set layout parameters for both surfaces
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.1f);

        surface.setLayoutParams(layoutParams1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.9f);
        momentumChart.setLayoutParams(layoutParams2);

        final CategoryDateAxis xAxis = sciChartBuilder.newCategoryDateAxis()
                .withVisibility(View.GONE)
                .withVisibleRange(sharedXRange)
                .withGrowBy(0, 0.05)
                .build();

        momentumChart.getXAxes().add(xAxis);
        momentumChart.getYAxes().add(model.yAxis);

        momentumChart.getRenderableSeries().addAll(model.renderableSeries);

        momentumChart.getChartModifiers().add(sciChartBuilder
                .newModifierGroup()
                .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).withClipModeX(ClipMode.ClipAtExtents).build()
                .withPinchZoomModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
                .withLegendModifier().withShowCheckBoxes(false).build()
                .build());

        momentumChart.setAnnotations(model.annotations);

        verticalGroup.addSurfaceToGroup(momentumChart);
    }

    private SciChartSurface cciChart = null;
    // 외부 지표
    public void addCCI(int period, int color, int thickness) {
        if (this.chartData == null || cciChart != null || this.chart_layout == null)
            return;

        RsiPaneModel model = new RsiPaneModel(sciChartBuilder, this.chartData, period, color, thickness);
        cciChart = new SciChartSurface(this.getContext());

        // Add the SciChartSurface to the layout
        this.chart_layout.addView(cciChart);

        // Set layout parameters for both surfaces
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.1f);

        surface.setLayoutParams(layoutParams1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.9f);
        cciChart.setLayoutParams(layoutParams2);

        final CategoryDateAxis xAxis = sciChartBuilder.newCategoryDateAxis()
                .withVisibility(View.GONE)
                .withVisibleRange(sharedXRange)
                .withGrowBy(0, 0.05)
                .build();

        cciChart.getXAxes().add(xAxis);
        cciChart.getYAxes().add(model.yAxis);

        cciChart.getRenderableSeries().addAll(model.renderableSeries);

        cciChart.getChartModifiers().add(sciChartBuilder
                .newModifierGroup()
                .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).withClipModeX(ClipMode.ClipAtExtents).build()
                .withPinchZoomModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
                .withLegendModifier().withShowCheckBoxes(false).build()
                .build());

        cciChart.setAnnotations(model.annotations);

        verticalGroup.addSurfaceToGroup(cciChart);
    }

    private SciChartSurface atrChart = null;
    // 외부지표
    public void ATR(int period, int color, int thickness) {
        if (this.chartData == null || atrChart != null || this.chart_layout == null)
            return;

        RsiPaneModel model = new RsiPaneModel(sciChartBuilder, this.chartData, period, color, thickness);
        atrChart = new SciChartSurface(this.getContext());

        // Add the SciChartSurface to the layout
        this.chart_layout.addView(atrChart);

        // Set layout parameters for both surfaces
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.1f);

        surface.setLayoutParams(layoutParams1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.9f);
        atrChart.setLayoutParams(layoutParams2);

        final CategoryDateAxis xAxis = sciChartBuilder.newCategoryDateAxis()
                .withVisibility(View.GONE)
                .withVisibleRange(sharedXRange)
                .withGrowBy(0, 0.05)
                .build();

        atrChart.getXAxes().add(xAxis);
        atrChart.getYAxes().add(model.yAxis);

        atrChart.getRenderableSeries().addAll(model.renderableSeries);

        atrChart.getChartModifiers().add(sciChartBuilder
                .newModifierGroup()
                .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).withClipModeX(ClipMode.ClipAtExtents).build()
                .withPinchZoomModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
                .withLegendModifier().withShowCheckBoxes(false).build()
                .build());

        atrChart.setAnnotations(model.annotations);

        verticalGroup.addSurfaceToGroup(atrChart);
    }

    public void newHorizontalAnnotation(){

        /*DefaultAnnotationFactory annotationFactory = new DefaultAnnotationFactory();
        annotationFactory.setFactoryForAnnotationType(DefaultAnnotationFactory.HORIZONTAL_LINE_ANNOTATION, new IAnnotationFactory() {
            @NonNull
            @Override
            public IAnnotation createAnnotation(@NonNull ISciChartSurface iSciChartSurface, int i) {
                return sciChartBuilder.newHorizontalLineAnnotation()
                        .withPosition(0,11000d)
                        .withStroke(2, ColorUtil.Red)
                        .withHorizontalGravity(Gravity.RIGHT)
                        .withIsEditable(true)
                        .withAnnotationLabel(LabelPlacement.Axis)
                        .build();
            }
        });
        annotationCreationModifier.setAnnotationFactory(annotationFactory);*/
        /*
        if(chartData != null) {
            final PriceBar price = new PriceBar(
                    chartData.getLastDate(),
                    chartData.getLastOpen(),
                    chartData.getLastHigh(),
                    chartData.getLastLow(),
                    chartData.getLastClose(),
                    (long) chartData.getLastVolume());


            Collections.addAll(surface.getAnnotations(), sciChartBuilder.newHorizontalLineAnnotation()
                    .withY1(price.getClose()-10)
                    .withStroke(2, ColorUtil.Red)
                    .withAnnotationLabel()
                    .withAnnotationLabel(LabelPlacement.TopLeft,Double.toString(price.getClose()))
                    .withIsEditable(true)
                    .build());
            //Double.toString(price.getClose())
        }
        */

        //surface.getChartModifiers().add(sciChartBuilder.newModifierGroup().withModifier(annotationCreationModifier).build());
        surface.getChartModifiers().add(sciChartBuilder.newModifierGroupWithDefaultModifiers().build());
    }

    public void newVerticalLineAnnotation(){

        /*SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        Date date = format.parse(order.tradeDate);
        int index = categoryLabelProvider.transformDataToIndex(date, SearchMode.Nearest);
        annotation.setX1(index);*/
        //ICategoryLabelProvider categoryLabelProvider = (ICategoryLabelProvider)xAxis.getLabelProvider();


        if(chartData != null) {

            Collections.addAll(surface.getAnnotations(), sciChartBuilder.newVerticalLineAnnotation()
                    .withX1(xAxis.getVisibleRange().getMinAsDouble()/2)
                    .withStroke(2, ColorUtil.Orange)
                    .withAnnotationLabel()
                    .withAnnotationLabel(LabelPlacement.TopRight,"",90)
                    .withIsEditable(true)
                    .build());
        }

        //surface.getChartModifiers().add(sciChartBuilder.newModifierGroup().withModifier(annotationCreationModifier).build());
        surface.getChartModifiers().add(sciChartBuilder.newModifierGroupWithDefaultModifiers().build());
    }





    /*final void addRenderableSeries(BaseRenderableSeries renderableSeries) {
        renderableSeries.setClipToBounds(true);
        this.renderableSeries.add(renderableSeries);
    }

    private void addMA() {
        final XyDataSeries<Date, Double> maLow = sciChartBuilder.newXyDataSeries(Date.class, Double.class).withSeriesName("Low Line").build();
        maLow.append(prices.getDateData(), MovingAverage.movingAverage(prices.getCloseData(), 50));
        addRenderableSeries(sciChartBuilder.newLineSeries().withDataSeries(maLow).withStrokeStyle(0xFFFF3333, 2f).withYAxisId(PRICES).build());
    }*/

    private SmChartData chartData = null;

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            setStyle(bundle.getString("chart_style"));
        }
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    LinearLayout chart_layout = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sm_chart, container, false);
        ButterKnife.bind(this, view);

        chart_layout = view.findViewById(R.id.chart_linear);

        readBundle(getArguments());

        initChart();
        verticalGroup.addSurfaceToGroup(surface);
        setUpdateSuspender();

        return view;
    }

    private void initChart() {
        // 차트 스타일을 설정한다.
        initChartStyle();
        // 차트 배경을 설정한다.
        setBackground();
        // 차트 데이터를 설정한다.
        initChartData();
        // 차트 타이틀을 설정한다.
        createChartTitle();
        // 시리즈 타입을 설정한다.
        initSeriesType();
        // x축을 설정한다.
        setXAxis();
        // y축을 설정한다.
        setYAxis();
        // 어노테이션을 생성한다.
        createAnnotation();
        // 데이터 시리즈를 생성한다.
        createDataSeries();
        // 렌더러블 시리즈를 생성한다.
        createRenderableSeries();
        // 차트모디파이어를 생성한다.
        createModifier();
        // 차트 콜백 이벤트를 등록해 준다.
        registerChartDataCallback();
        // 차트 데이터를 설정한다.
        setChartData();
    }

    private boolean chartDataInit = false;

    private void setChartData() {
        SmChartDataManager chartDataManager = SmChartDataManager.getInstance();
        // 차트데이터를 만든다.
        this.chartData = chartDataManager.createChartData(this.symbolCode , this.chartType, this.chartCycle);
        // 차트타이틀을 만들고 차트 타이틀을 설정한다.
        createChartTitle();
        setChartTitle();
        // 최신 데이터가 아니면 차트데이터를 요청한다.
        if (this.chartData.getCount() < SmConst.TempDataSize) {
            // 서버에 차트 데이터를 요청한다.
            chartDataManager.requestChartData(this.chartData);
        }
        else {
            // 차트데이터를 설정한다.
            setChartData(this.chartData);
            // 주문을 설정한다.
            loadOrders();
        }
    }

    ZoomExtentsModifier zoomExtentsModifier = null;
    private void createModifier() {
        zoomExtentsModifier = new ZoomExtentsModifier();
        zoomExtentsModifier.setDirection(Direction2D.XyDirection);
        zoomExtentsModifier.setExecuteOn(ExecuteOn.LongPress);
        zoomExtentsModifier.setIsAnimated(true);
    }

    // 차트 데이터를 요청한다.
    public void requestChartData() {
        SmChartDataManager chartDataManager = SmChartDataManager.getInstance();
        // 차트데이터를 만든다.
        this.chartData = chartDataManager.createChartData(this.symbolCode , this.chartType, this.chartCycle);
        // 최신 데이터가 아니면 차트데이터를 요청한다.
        if (!this.chartData.isUptodate()) {
            // 서버에 차트 데이터를 요청한다.
            chartDataManager.requestChartData(this.chartData);
        }
        else {
            setChartData(this.chartData);
            createChartTitle();
            setChartTitle();
            loadOrders();
        }
    }

    // 차트 콜백은 데이터 키로 한다. 같은 데이터 키를 가진 프레임은 모두 전달해 준다.
    public void registerChartDataCallback() {
        SmChartDataService chartDataService = SmChartDataService.getInstance();
        chartDataService.subscribeSise(onUpdateSise(), this.style);
        chartDataService.subscribeHoga(onUpdateHoga(), this.style);
        chartDataService.subscribeNewData(onNewData(), this.style);
        chartDataService.subscribeUpdateData(onUpdateData(), this.style);
    }

    // 차트 타이틀을 생성한다.
    public void createChartTitle() {
        chartTitle = makeChartTitle();
    }

    // 차트 데이터를 초기화 한다.
    // 차트 종목, 차트 타입, 주기를 설정한다.
    public void initChartData() {
        Intent i = getActivity().getIntent();
        // 여기서 저장된 차트 데이터 정보를 가져온다.
        // 액티비티에서 건네준 종목 명을 받는다.
        String symbol_code = i.getStringExtra("symbolCode");
        if (symbol_code == null) {
            // 종목 명이 없는 경우 기본 종목을 가져 온다.
            SmMarketManager marketManager = SmMarketManager.getInstance();
            symbol_code = marketManager.getDefaultSymbol().code;
            symbolName = marketManager.getDefaultSymbol().seriesNmKr;
        }

        symbolCode = symbol_code;
        //chartType = SmChartType.MIN;
        //chartCycle = 1;

        SmSymbolManager symbolManager = SmSymbolManager.getInstance();
        SmSymbol symbol = symbolManager.findSymbol(symbolCode);
        if (symbol != null)
            symbolName = symbol.seriesNmKr;

        if (style == "mock_left") {
            chartType = SmChartType.MIN;
            chartCycle = 5;
        }

        if (style == "mock_right") {
            chartType = SmChartType.MIN;
            chartCycle = 15;
        }
    }

    private void initChartStyle() {
        SmChartManager chartMgr = SmChartManager.getInstance();
        _chartSetting = chartMgr.getChart(getStyle());
        if (_chartSetting == null) {
            _chartSetting = new SmChart();
            chartMgr.AddChart(getStyle(), _chartSetting);
        } else {
            _chartSetting = chartMgr.getChart(getStyle());
        }

        switch (getStyle()) {
            case "signal_premium_upper":
                SetSignalStyle();
                break;
            case "signal_premium_middle":
                SetSignalStyle();
                break;
            case "signal_premium_lower":
                SetSignalStyle();
                break;
            case "signal_regular_upper":
                SetSignalStyle();
                break;
            case "signal_regular_lower":
                SetSignalStyle();
                break;
            case "sise_main":
                SetSiseMainStyle();
                break;
            case "sise_sub":
                SetSiseMainStyle();
                break;
            case "sise_history":
                SetSiseHistoryStyle();
                break;
            case "mock_main":
                SetMockMainStyle();
                break;
            case "mock_left":
                SetMockLeftStyle();
                break;
            case "mock_right":
                SetMockRightStyle();
                break;
            case "current_top":
                SetCurrentTopStyle();
                break;
            case "current_middle":
                SetCurrentMiddleStyle();
                break;
            case "current_bottom":
                SetCurrentBottomStyle();
                break;
            case "signal_upper":
                SetSignalUpperStyle();
                break;
            case "signal_lower":
                SetSignalLowerStyle();
                break;
            case "expert_main":
                SetExpertStyle();
            case "income_chart":
                SetIncomChartStyle();
        }
    }

    private void initSeriesType() {
        switch (getStyle()) {
            case "signal_premium_upper":
                seriesType = SmSeriesType.MountainView;
                break;
            case "signal_premium_middle":
                seriesType = SmSeriesType.MountainView;
                break;
            case "signal_premium_lower":
                seriesType = SmSeriesType.MountainView;
                break;
            case "signal_regular_upper":
                seriesType = SmSeriesType.MountainView;
                break;
            case "signal_regular_lower":
                seriesType = SmSeriesType.MountainView;
                break;
            case "sise_main":
                seriesType = SmSeriesType.CandleStick;
                break;
            case "sise_sub":
                seriesType = SmSeriesType.CandleStick;
                break;
            case "sise_history":
                seriesType = SmSeriesType.CandleStick;
                break;
            case "mock_main":
                seriesType = SmSeriesType.MountainView;
                break;
            case "mock_left":
                seriesType = SmSeriesType.MountainView;
                break;
            case "mock_right":
                seriesType = SmSeriesType.MountainView;
                break;
            case "current_top":
                seriesType = SmSeriesType.MountainView;
                break;
            case "current_middle":
                seriesType = SmSeriesType.CandleStick;
                break;
            case "current_bottom":
                seriesType = SmSeriesType.CandleStick;
                break;
            case "signal_upper":
                seriesType = SmSeriesType.MountainView;
                break;
            case "signal_lower":
                seriesType = SmSeriesType.MountainView;
                break;
            case "expert_main":
                seriesType = SmSeriesType.CandleStick;
            case "income_chart":
                seriesType = SmSeriesType.MountainView;
        }
        setSeriesType(seriesType);
    }

    private void createRenderableSeries() {
        createCandleStickSeries();
        createOhlcSeries();
        createMountainViewSeries();
        createLineSeries();
    }

    private void createDataSeries() {
        createOhlcDataSeries();
        createXyDataSeries();
    }

    private void clearDataSeries() {
        ohlcDataSeries.clear();
        xyDataSeries.clear();
    }

    private void SetIncomChartStyle(){
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;


        //background
        _chartSetting.setBackgroundColor = 0xFF1E1E1E;
        _chartSetting.bandStyle = 0xFF1E1E1E;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
    }

    private void SetSiseHistoryStyle(){
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;


        //background
        _chartSetting.setBackgroundColor = 0xFF1E1E1E;
        _chartSetting.bandStyle = 0xFF1E1E1E;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
    }

    private void SetSignalStyle(){
        seriesType = SmSeriesType.MountainView;
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;


        //background
        _chartSetting.setBackgroundColor = 0xFF1E1E1E;
        _chartSetting.bandStyle = 0xFF1E1E1E;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
    }

    private void SetExpertStyle(){
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;


        //background
        _chartSetting.setBackgroundColor = 0xFF1E1E1E;
        _chartSetting.bandStyle = 0xFF1E1E1E;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
    }

    private void SetSiseMainStyle() {
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;


        //background
        _chartSetting.setBackgroundColor = 0xFF1E1E1E;
        _chartSetting.bandStyle = 0xFF1E1E1E;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
    }

    private void SetMockMainStyle() {
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;


        //background
        _chartSetting.setBackgroundColor = 0xFF1E1E1E;
        _chartSetting.bandStyle = 0xFF1E1E1E;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
    }

    private void SetMockLeftStyle() {
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;
        _chartSetting.bandStyle = 0xFF000000;
        _chartSetting.setBackgroundColor = 0xFF000000;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
        _chartSetting.mainSeriesType = 1;
    }

    private void SetMockRightStyle() {
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;
        _chartSetting.bandStyle = 0xFF000000;
        _chartSetting.setBackgroundColor = 0xFF000000;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
        _chartSetting.mainSeriesType = 1;
    }

    private void SetCurrentTopStyle() {

    }

    private void SetCurrentMiddleStyle() {

    }

    private void SetCurrentBottomStyle() {

    }

    private void SetSignalUpperStyle() {
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;
        _chartSetting.bandStyle = 0xFF000000;
        _chartSetting.setBackgroundColor = 0xFF000000;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;
    }

    private void SetSignalLowerStyle() {
        _chartSetting.upStrokeColor = 0xffff0000;
        _chartSetting.downStrokeColor = 0xff0000ff;
        _chartSetting.upArrowType = 0;
        _chartSetting.downArrowType = 1;
        _chartSetting.fillUpColor = 0x88FF0000;
        _chartSetting.fillDownColor = 0x880000FF;
        _chartSetting.fillcolor = 0xFF0000ff;
        _chartSetting.withStrokeUp = 0xFFFF0000;
        _chartSetting.withFillUpColor = 0x88FF0000;
        _chartSetting.withStrokeDown = 0xFF0000FF;
        _chartSetting.withFillDownColor = 0x880000FF;
        _chartSetting.withStrokeStyle = 0xFF00AA00;
        _chartSetting.mountSeriesColor = 0xAAAFE1FF;
        _chartSetting.GradientColors = 0xAA4375DB;
        _chartSetting.GradientColorsEnd = 0x88090E11;
        _chartSetting.lineSeriesColor = 0xFF279B27;
        _chartSetting.strokeThickness = 1f;
        _chartSetting.antiAliasing = true;

        _chartSetting.tickStyle = 0xFFFFFFFF;
        _chartSetting.gridlineStyle = 0x33FFFFFF;
        _chartSetting.gridlineStyle2 = 0x0025304C;
        _chartSetting.gridlineStyle3 = 0x00FFFFFF;
        _chartSetting.bandStyle = 0xFF000000;
        _chartSetting.setBackgroundColor = 0xFF000000;

        _chartSetting.annoColor = 0x22FFFFFF;
        _chartSetting.annoTextSize = 14;


    }

    public SmChartData getChartData() {
        return chartData;
    }


    private IOhlcDataSeries<Date, Double> candleStickSeries = null;
    //private IDataSeries<Date, Double> dataSeries = null;
    //private IRenderableSeries renderableSeries = null;
    private SmSeriesType seriesType = SmSeriesType.CandleStick;

    private FastCandlestickRenderableSeries fastCandlestickRenderableSeries = null;
    private FastMountainRenderableSeries fastMountainRenderableSeries = null;
    private FastLineRenderableSeries fastLineRenderableSeries = null;
    private FastOhlcRenderableSeries fastOhlcRenderableSeries = null;
    private XyDataSeries<Date, Double> xyDataSeries = null;
    private OhlcDataSeries<Date, Double> ohlcDataSeries = null;


    private void createCandleStickSeries() {
        if (fastCandlestickRenderableSeries != null)
            return;
        fastCandlestickRenderableSeries = sciChartBuilder.newCandlestickSeries()
                .withStrokeUp(_chartSetting.withStrokeUp)
                .withFillUpColor(_chartSetting.withFillUpColor)
                .withStrokeDown(_chartSetting.withStrokeDown)
                .withFillDownColor(_chartSetting.withFillDownColor)
                .withDataSeries(ohlcDataSeries)
                .build();
    }

    private void createMountainViewSeries() {
        if (fastMountainRenderableSeries != null)
            return;
        fastMountainRenderableSeries = sciChartBuilder.newMountainSeries()
                .withDataSeries(xyDataSeries)
                .withStrokeStyle(_chartSetting.mountSeriesColor, _chartSetting.strokeThickness, _chartSetting.antiAliasing)
                .withAreaFillLinearGradientColors(_chartSetting.GradientColors, _chartSetting.GradientColorsEnd)
                .build();
    }

    private void createLineSeries() {
        if (fastLineRenderableSeries != null)
            return;
        fastLineRenderableSeries = sciChartBuilder.newLineSeries()
                .withDataSeries(xyDataSeries)
                .withStrokeStyle(_chartSetting.lineSeriesColor, _chartSetting.strokeThickness, _chartSetting.antiAliasing)
                .build();
    }

    private void createOhlcSeries() {
        if (fastOhlcRenderableSeries != null)
            return;
        fastOhlcRenderableSeries = sciChartBuilder.newOhlcSeries()
                .withStrokeUp(_chartSetting.withStrokeUp, _chartSetting.strokeThickness)
                .withStrokeDown(_chartSetting.withStrokeDown, _chartSetting.strokeThickness)
                .withStrokeStyle(_chartSetting.withStrokeStyle)
                .withDataSeries(ohlcDataSeries)
                .build();
    }

    private void createXyDataSeries() {
        if (xyDataSeries == null)
            xyDataSeries = sciChartBuilder.newXyDataSeries(Date.class, Double.class).build();
        xyDataSeries.setAcceptsUnsortedData(true);
        xyDataSeries.setFifoCapacity(SmConst.ChartDataSize);
    }

    private void createOhlcDataSeries() {
        if (ohlcDataSeries == null)
            ohlcDataSeries = new OhlcDataSeries<>(Date.class, Double.class);
        ohlcDataSeries.setAcceptsUnsortedData(true);
        //데이터 size
        ohlcDataSeries.setFifoCapacity(SmConst.ChartDataSize);
    }



    public void changeSeriesType(SmSeriesType sType) {
        if (seriesType == sType)
            return;

        switch (sType) {

            case CandleStick:
                setSeriesType(SmSeriesType.CandleStick);
                if (surface.getRenderableSeries().size() > 0) {
                    surface.getRenderableSeries().remove(0);
                    surface.getRenderableSeries().add(fastCandlestickRenderableSeries);
                }

                //캔들스틱 시리즈로 캐스팅.
                sciChartBuilder.newAnimator(fastCandlestickRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
                break;

            case MountainView:
                setSeriesType(SmSeriesType.MountainView);
                if (surface.getRenderableSeries().size() > 0) {
                    surface.getRenderableSeries().remove(0);
                    surface.getRenderableSeries().add(fastMountainRenderableSeries);
                }

                sciChartBuilder.newAnimator(fastMountainRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
                break;

            case Line:
                setSeriesType(SmSeriesType.Line);
                if (surface.getRenderableSeries().size() > 0) {
                    surface.getRenderableSeries().remove(0);
                    surface.getRenderableSeries().add(fastLineRenderableSeries);
                }

                sciChartBuilder.newAnimator(fastLineRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
                break;

            case Ohlc:
                setSeriesType(SmSeriesType.Ohlc);
                if (surface.getRenderableSeries().size() > 0) {
                    surface.getRenderableSeries().remove(0);
                    surface.getRenderableSeries().add(fastOhlcRenderableSeries);
                }

                sciChartBuilder.newAnimator(fastOhlcRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
                break;
        }
    }

    private int defaultChartDataSize = 30;
    private void setChartData(SmChartData chart_data) {
        try {
            if (chart_data == null)
                return;
            /*
            SortedMap<String, SmChartDataItem> dataMap = chart_data.getDataMap();
            int total_count = dataMap.size();
            int index = 0;
            for(SmChartDataItem item : dataMap.values()) {
                index++;
                if (index == total_count)
                    item.isLast = true;
                else
                    item.isLast = false;
                addChartData(item);
            }
            */

            // 봉데이터 추가
            if (ohlcDataSeries != null) {
                ohlcDataSeries.append(chart_data.getDate(), chart_data.getOpen(), chart_data.getHigh(), chart_data.getLow(), chart_data.getClose());
            }
            // 라인데이터 추가
            if (xyDataSeries != null) {
                xyDataSeries.append(chart_data.getDate(), chart_data.getClose());
            }

            // 레이블을 갱신해 준다.
            ICategoryLabelProvider categoryLabelProvider = (ICategoryLabelProvider)xAxis.getLabelProvider();
            categoryLabelProvider.update();

            // 주기 데이터가 아닐때만 줌을 맞춰 준다.
            surface.zoomExtents();

            loadOrders();
            SmSymbol symbol = SmSymbolManager.getInstance().findSymbol(chartData.symbolCode);
            if (symbol != null) {
                updateSise(symbol);
            }

        } catch (Exception e) {
            String msg = e.getMessage();
        }

    }

    public void changeChartData(final SmChartData chart_data) {
        if (chart_data == null)
            return;
        changeChartData(chart_data.symbolCode, chart_data.chartType, chart_data.cycle);
    }



    public void setUpdateSuspender() {
        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {
                surface.getChartModifiers().add(zoomExtentsModifier);
                if (seriesType == SmSeriesType.MountainView) {
                    Collections.addAll(surface.getRenderableSeries(), fastMountainRenderableSeries);
                    sciChartBuilder.newAnimator(fastMountainRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
                }
                else if (seriesType == SmSeriesType.CandleStick) {
                    Collections.addAll(surface.getRenderableSeries(), fastCandlestickRenderableSeries);
                    sciChartBuilder.newAnimator(fastCandlestickRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
                }
                else if (seriesType == SmSeriesType.Ohlc) {
                    Collections.addAll(surface.getRenderableSeries(), fastOhlcRenderableSeries);
                    sciChartBuilder.newAnimator(fastOhlcRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
                }
                else {
                    Collections.addAll(surface.getRenderableSeries(), fastLineRenderableSeries);
                    sciChartBuilder.newAnimator(fastLineRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
                }
                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());
            }
        });
    }

    void setXAxis() {
        xAxis = sciChartBuilder.newCategoryDateAxis().withGrowBy(0, 0.1).build();
        sharedXRange = new DoubleRange((double)0, (double)SmConst.ChartDataSize);

        xAxis.setMinimalZoomConstrain(1d);
        xAxis.setVisibleRangeLimit(sharedXRange);
        PenStyle tickStyle = new SolidPenStyle(_chartSetting.tickStyle, true, 2, null);
        PenStyle gridlineStyle3 = new SolidPenStyle(_chartSetting.gridlineStyle3, true, 1, null);
        PenStyle gridlineStyle2 = new SolidPenStyle(_chartSetting.gridlineStyle2, true, 2, null);
        PenStyle gridlineStyle = new SolidPenStyle(_chartSetting.gridlineStyle, true, 1, null);
        BrushStyle bandStyle = new SolidBrushStyle(_chartSetting.bandStyle);

        //xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        xAxis.setDrawMajorBands(true);
        xAxis.setAxisBandsStyle(bandStyle);

        xAxis.setMajorGridLineStyle(gridlineStyle);
        xAxis.setMinorGridLineStyle(gridlineStyle2);
        Collections.addAll(surface.getXAxes(), xAxis);

        if (getStyle() == "mock_left" || getStyle() == "mock_right" || getStyle() == "income_chart") {
            xAxis.setVisibility(View.GONE);
        }
    }

    void setYAxis() {
        yAxis = sciChartBuilder.newNumericAxis().withGrowBy(0d, 0.1d).withAxisAlignment(AxisAlignment.Right).withAutoRangeMode(AutoRange.Always).build();

        PenStyle tickStyle = new SolidPenStyle(_chartSetting.tickStyle, true, 2, null);
        BrushStyle bandStyle = new SolidBrushStyle(_chartSetting.bandStyle);
        PenStyle gridlineStyle2 = new SolidPenStyle(_chartSetting.gridlineStyle2, true, 2, null);
        // PenStyle gridlineStyle = new SolidPenStyle(_chartSetting.gridlineStyle, true, 1, new float[]{10, 10});
        PenStyle gridlineStyle = new SolidPenStyle(_chartSetting.gridlineStyle, true, 1, null);

        yAxis.setAxisBandsStyle(bandStyle);
        yAxis.setDrawMajorBands(true);
        //yAxis.setMinorTickLineStyle(tickStyle);
        yAxis.setMajorTickLineStyle(tickStyle);


        yAxis.setMajorGridLineStyle(gridlineStyle);
        yAxis.setMinorGridLineStyle(gridlineStyle2);

        setYAxisFormat(yAxis);

        Collections.addAll(surface.getYAxes(), yAxis);

        if (getStyle() == "mock_left" || getStyle() == "mock_right" || getStyle() == "income_chart") {
            yAxis.setVisibility(View.GONE);
        }

        if (getStyle() == "mock_main") {
            yAxis.setVisibility(View.GONE);
            yAxis.setAxisAlignment(AxisAlignment.Left);

        }
    }

    private void setYAxisFormat(IAxis yAxis) {
        if (yAxis == null)
            return;

        String yFormat = "%.";
        SmSymbol symbol = SmSymbolManager.getInstance().findSymbol(this.symbolCode);
        if (symbol != null) {
            yFormat += Integer.toString(symbol.decimal);
            yFormat += "f";
        } else {
            yFormat += "2f";
        }

        yAxis.setLabelProvider(new NumericLabelProviderEx(yFormat));
    }


    private void setBackground() {
        surface.setBackgroundColor(_chartSetting.setBackgroundColor);
    }


    private void setChartTitle() {
        symbolTitleAnnotation.setText(this.chartTitle);
    }

    private String makeChartTitle() {
        String title = "";
        if (!(style == "mock_left" || style == "mock_right" || style == "mock_main")) {
            title += this.symbolName;
            title += " : ";
        }
        title += Integer.toString(chartCycle);
        switch (chartType) {
            case TICK:
                title += "틱";
                break;
            case MIN:
                title += "분";
                break;
            case DAY:
                title += "일";
                break;
            case WEEK:
                title += "주";
                break;
            case MON:
                title += "월";
                break;
            case YEAR:
                title += "년";
                break;
        }
        chartTitle = title;
        return title;
    }

    // 차트 데이터를 변경해 준다.
    public void changeChartData(String symbol_code, SmChartType chartType, int cycle) {
        if (chartData != null) {
            // 기존의 차트데이터를 없앤다.
            //SmChartDataManager.getInstance().removeChartData(chartData.getDataKey());
            // 기존의 차트데이터 서비스도 없앤다.
            SmChartDataService.getInstance().unsubscribeChartData(chartData.getDataKey());
        }
        // 기존 데이터 시리즈를 지워준다.
        clearDataSeries();
        this.symbolCode = symbol_code;
        this.chartType = chartType;
        this.chartCycle = cycle;
        // 차트데이터를 설정한다.
        setChartData();
    }

    public void onSymbolChanged(String newSymbolCode) {
        if (newSymbolCode == null) {
            return;
        }
        SmSymbolManager symMgr = SmSymbolManager.getInstance();
        SmSymbol sym = symMgr.findSymbol(newSymbolCode);
        if (sym != null) {
            // 데이터 시리즈의 값들을 모두 없앤다.
            clearDataSeries();
            // 기존 주문을 없애 준다.
            clearOrderAnnotations();

            symbolCode = sym.code;
            symbolName = sym.seriesNmKr;
            // 차트데이터 변경
            changeChartData(newSymbolCode, this.chartType, this.chartCycle);
            // 호가 변경
            updateHoga(sym);
            // 시세 변경
            updateSise(sym);
        }
    }

    public void loadOrders(String symbolCode) {
        if (symbolCode == null)
            return;
        SmAccount account = SmAccountManager.getInstance().getDefaultAccount();
        if (account == null)
            return;

        ArrayList<SmOrder> filledOrderList = SmTotalOrderManager.getInstance().getFilledOrderList(account.accountNo, symbolCode);
        for(SmOrder order : filledOrderList) {
            createOrderAnnotion(order);
        }
    }

    private void loadOrders() {
        if (this.symbolCode == null)
            return;
        SmAccount account = SmAccountManager.getInstance().getDefaultAccount();
        if (account == null)
            return;

        ArrayList<SmOrder> filledOrderList = SmTotalOrderManager.getInstance().getFilledOrderList(account.accountNo, this.symbolCode);
        for(SmOrder order : filledOrderList) {
            createOrderAnnotion(order);
        }
    }

    public static final int  STROKE_DOWN_COLOR = 0xFF00AA00;
    public static final int STOKE_UP_COLOR  = 0xFFFF0000;
    private SmValueAnnotation currentTextAnnotation = null;
    private HorizontalLineAnnotation currentLineAnnotation = null;
    private AxisMarkerAnnotation currentValueAnnotation = null;
    private ArrayList<SmValueAnnotation> buyHogaAnnotations = new ArrayList<>();
    private ArrayList<SmValueAnnotation> sellHogaAnnotations = new ArrayList<>();
    private TextAnnotation symbolTitleAnnotation = null;

    private void createSymbolTitleAnnotation() {
        symbolTitleAnnotation = sciChartBuilder.newTextAnnotation()
                .withX1(0.005)
                .withY1(0.005)
                .withFontStyle(Typeface.DEFAULT_BOLD, 10, 0xFFFFFFFF)
                .withCoordinateMode(AnnotationCoordinateMode.Relative)
                .withHorizontalAnchorPoint(HorizontalAnchorPoint.Left)
                .withVerticalAnchorPoint(VerticalAnchorPoint.Top)
                .withText(makeChartTitle())
                .withTextGravity(Gravity.LEFT)
                .build();
        surface.getAnnotations().add(symbolTitleAnnotation);
    }

    private int hogaShowCount = 1;
    private void createHogaAnnotations() {
        SmCurrentValueView valueView = null;
        for(int i = 0; i < hogaShowCount; ++i) {
            SmValueAnnotation hogaAnnotation = new SmValueAnnotation(getActivity());
            valueView = new SmCurrentValueView(getActivity(), SmPositionType.Buy, Double.toString(0));
            hogaAnnotation.setValueView(valueView);
            hogaAnnotation.setContentView(valueView);
            hogaAnnotation.setVerticalAnchorPoint(VerticalAnchorPoint.Center);
            hogaAnnotation.setHorizontalAnchorPoint(HorizontalAnchorPoint.Left);
            surface.getAnnotations().add(0,hogaAnnotation);
            buyHogaAnnotations.add(hogaAnnotation);
        }

        for(int i = 0; i < hogaShowCount; ++i) {
            SmValueAnnotation hogaAnnotation = new SmValueAnnotation(getActivity());
            valueView = new SmCurrentValueView(getActivity(), SmPositionType.Sell, Double.toString(0));
            hogaAnnotation.setValueView(valueView);
            hogaAnnotation.setContentView(valueView);
            hogaAnnotation.setVerticalAnchorPoint(VerticalAnchorPoint.Center);
            hogaAnnotation.setHorizontalAnchorPoint(HorizontalAnchorPoint.Left);
            surface.getAnnotations().add(0,hogaAnnotation);
            sellHogaAnnotations.add(hogaAnnotation);
        }
    }

    private void createCurrentAnnotation() {
        currentTextAnnotation = new SmValueAnnotation(getActivity());
        SmCurrentValueView valueView = new SmCurrentValueView(getActivity(), SmPositionType.None, Double.toString(0));
        currentTextAnnotation.setValueView(valueView);
        currentTextAnnotation.setContentView(valueView);
        currentTextAnnotation.setVerticalAnchorPoint(VerticalAnchorPoint.Center);
        currentTextAnnotation.setHorizontalAnchorPoint(HorizontalAnchorPoint.Left);
        Collections.addAll(surface.getAnnotations(), currentTextAnnotation);
    }

    private void createCurrentLineAnnotation() {
        SolidPenStyle solidPenStyle = new SolidPenStyle (_chartSetting.CurrentLineColors, true, 4, new float[]{5, 10, 5, 10});
        currentLineAnnotation = sciChartBuilder.newHorizontalLineAnnotation().withStroke(solidPenStyle).withY1(0d).build();
        Collections.addAll(surface.getAnnotations(), currentLineAnnotation);
    }

    private void createCurrentValueAnnotation() {
        currentValueAnnotation = sciChartBuilder.newAxisMarkerAnnotation().withY1(2d).withBackgroundColor(STOKE_UP_COLOR).build();
        Collections.addAll(surface.getAnnotations(), currentValueAnnotation);
    }

    private void createAnnotation() {
        try {
            createCurrentLineAnnotation();
            if (style == "mock_main")
                createHogaAnnotations();
            if (!(style == "mock_left" || style == "mock_right") )
                createCurrentAnnotation();
            createCurrentValueAnnotation();
            createSymbolTitleAnnotation();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    SmChartDataItem lastDataItem = null;

    private void updateSise(final SmSymbol symbol) {
        // 아직 데이터가 채워지지 않았을 때는 실행하지 않는다.
        if (symbol == null)
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 어노테이션 뷰 업데이트
                updateCurrentAnnotaionView(symbol);
            }
        });
        // 어노테이션 업데이트
        updateCurrentAnnotation(symbol);
    }

    @NonNull
    private synchronized Action1<SmSymbol> onUpdateSise() {
        return new Action1<SmSymbol>() {
            @Override
            public void execute(final SmSymbol symbol) {
                if (lastDataItem == null)
                    return;
                if (symbol.code.compareTo(symbolCode) != 0)
                    return;
                double div = Math.pow(10, symbol.decimal);
                double vc = (symbol.quote.C / div);
                lastDataItem.close = vc;
                updateLastData(lastDataItem);
                updateSise(symbol);
            }
        };
    }

    @NonNull
    private synchronized Action1<SmSymbol> onUpdateHoga() {
        return new Action1<SmSymbol>() {
            @Override
            public void execute(final SmSymbol symbol) {
                if (symbol.code.compareTo(symbolCode) != 0)
                    return;
                updateHoga(symbol);
            }
        };
    }

    public void createOrderAnnotations() {
        SmTotalOrderManager totalOrderManager = SmTotalOrderManager.getInstance();
        SortedMap<Integer, SmOrder> totalOrder = totalOrderManager.getTotalOrderMap();

        for (Map.Entry<Integer, SmOrder> entry : totalOrder.entrySet()) {
            SmOrder order = entry.getValue();
            if (!orderHashMap.containsValue(order)) {
                createOrderAnnotion(order);
            }
        }

    }


    private boolean existOrder(SmOrder order) {
        for (Map.Entry<SmOrder , CustomAnnotation> entry : orderHashMap.entrySet()) {
            SmOrder item = entry.getKey();
            if (item.orderNo == order.orderNo)
                return true;
        }

        return false;
    }

    private CustomAnnotation findOrderAnnotation(SmOrder order) {
        for (Map.Entry<SmOrder , CustomAnnotation> entry : orderHashMap.entrySet()) {
            SmOrder item = entry.getKey();
            if (item.orderNo == order.orderNo)
                return entry.getValue();
        }

        return null;
    }



    public void refreshOrderAnnotation() {
        for (Map.Entry<SmOrder , CustomAnnotation> entry : orderHashMap.entrySet()) {
            CustomAnnotation annotation = entry.getValue();
            SmOrder order = entry.getKey();
            try {
                String filled_date_time = order.filledDate + order.filledTime;
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = format.parse(filled_date_time);
                // get ICategoryLabelProvider to convert Date values to Indexes and vice versa
                ICategoryLabelProvider categoryLabelProvider = (ICategoryLabelProvider)xAxis.getLabelProvider();
                int index = categoryLabelProvider.transformDataToIndex(date, SearchMode.Nearest);
                annotation.setX1(index);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }




    public void clearOrderAnnotations() {
        for (Map.Entry<SmOrder , CustomAnnotation> entry : orderHashMap.entrySet()) {
            CustomAnnotation order_anno = entry.getValue();
            surface.getAnnotations().remove(order_anno);
        }
        orderHashMap.clear();
    }

    private void updateCurrentAnnotation(final SmSymbol symbol) {
        if (symbol == null)
            return;

        double div = Math.pow(10, symbol.decimal);
        double vc = (symbol.quote.C / div);

        if (currentValueAnnotation != null) {
            currentValueAnnotation.setY1(vc);
        }
        if (currentTextAnnotation != null) {
            currentTextAnnotation.setY1(vc);
        }
        if (currentLineAnnotation != null) {
            currentLineAnnotation.setY1(vc);
        }
    }

    private void updateCurrentAnnotaionView(final SmSymbol symbol) {
        if (symbol == null || lastDataItem == null)
            return;

        if (currentValueAnnotation != null)
            currentValueAnnotation.setBackgroundColor(lastDataItem.close >= lastDataItem.open ? STOKE_UP_COLOR : STROKE_DOWN_COLOR);
        if (currentTextAnnotation != null) {
            double div = Math.pow(10, symbol.decimal);
            double vc = (symbol.quote.C / div);
            SmCurrentValueView currentValueView = currentTextAnnotation.getValueView();
            currentValueView.setValueText(Double.toString(vc));
            currentValueView.invalidate();
        }
    }

    private void updateCurrentValue(final SmSymbol symbol) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateCurrentAnnotaionView(symbol);
            }
        });

        updateCurrentAnnotation(symbol);
    }


    @NonNull
    private synchronized Action1<SmChartDataItem> onNewData() {
        return new Action1<SmChartDataItem>() {
            @Override
            public void execute(final SmChartDataItem dataItem) {
                if (dataItem == null || chartData == null)
                    return;
                addChartData(dataItem);
                refreshOrderAnnotation();
            }
        };
    }

    private void addChartData(SmChartDataItem dataItem) {
        String key_out = dataItem.getDataKey();
        String key_in = chartData.getDataKey();
        if (key_in.compareTo(key_out) != 0)
            return;
        if (lastDataItem != null && lastDataItem.date.compareTo(dataItem.date) == 0)
            return;

        // 봉데이터 추가
        if (ohlcDataSeries != null) {
            ohlcDataSeries.append(dataItem.date, dataItem.open, dataItem.high, dataItem.low, dataItem.close);
        }
        // 라인데이터 추가
        if (xyDataSeries != null) {
            xyDataSeries.append(dataItem.date, dataItem.close);
        }

        // 레이블을 갱신해 준다.
        ICategoryLabelProvider categoryLabelProvider = (ICategoryLabelProvider)xAxis.getLabelProvider();
        categoryLabelProvider.update();
        xAxis.invalidateElement();

        // 주기 데이터가 아닐때만 줌을 맞춰 준다.
        if (!dataItem.periodic)
            surface.zoomExtents();

        if (dataItem.isLast) {
            loadOrders();
            SmSymbol symbol = SmSymbolManager.getInstance().findSymbol(chartData.symbolCode);
            if (symbol != null) {
                updateSise(symbol);
            }
        }

        lastDataItem = dataItem;
    }

    // 날짜에 상관없이 무조건 업데이트 한다.
    private void updateLastData(SmChartDataItem dataItem) {
        if (dataItem == null)
            return;

        int lastIndex = ohlcDataSeries.getCount() - 1;
        ohlcDataSeries.update(lastIndex, dataItem.open, dataItem.high, dataItem.low, dataItem.close);
        xyDataSeries.updateYAt(lastIndex, dataItem.close);
        lastDataItem = dataItem;
    }

    @NonNull
    private synchronized Action1<SmChartDataItem> onUpdateData() {
        return new Action1<SmChartDataItem>() {
            @Override
            public void execute(final SmChartDataItem dataItem) {
                if (dataItem == null || chartData == null)
                    return;
                String key_out = dataItem.getDataKey();
                String key_in = chartData.getDataKey();
                if (key_in.compareTo(key_out) != 0)
                    return;

                updateLastData(dataItem);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        String chart_style =  getArguments().getString("chart_style");
        SmChartDataService chartDataService = SmChartDataService.getInstance();
        chartDataService.unsubscribePriceUpdate(chart_style);
        chartDataService.unsubscribePriceNew(chart_style);
        chartDataService.unsubscribeChartData(chart_style);
    }

    public int upStrokeColor = (0xffff0000);
    public int downStrokeColor = (0xff0000ff);
    public int upArrowType = 0;
    public int downArrowType = 1;

    private HashMap<SmOrder, CustomAnnotation> orderHashMap = new HashMap<>();

    private CustomAnnotation createOrderAnnotion(SmOrder order) {
        if (xAxis == null || order == null || ohlcDataSeries.getCount() == 0 || xyDataSeries.getCount() == 0)
            return null;

        try {
            if (existOrder(order))
                return null;
            // 이미 청산된 주문은 생성하지 않는다.
            if (order.orderState == SmOrderState.Settled)
                return null;
            String filled_date_time = order.filledDate + order.filledTime;
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = format.parse(filled_date_time);
            // get ICategoryLabelProvider to convert Date values to Indexes and vice versa
            if (xAxis == null)
                return null;

            ICategoryLabelProvider categoryLabelProvider = (ICategoryLabelProvider)xAxis.getLabelProvider();
            if (categoryLabelProvider == null)
                return null;

            int index = categoryLabelProvider.transformDataToIndex(date, SearchMode.Nearest);
            if (getSeriesType() == SmSeriesType.CandleStick || getSeriesType() == SmSeriesType.Ohlc) {
                index = min(index, ohlcDataSeries.getCount() - 1);
            } else {
                index = min(index, xyDataSeries.getCount() - 1);
            }

            SmSymbolManager symbolManager = SmSymbolManager.getInstance();
            SmSymbol symbol = symbolManager.findSymbol(order.symbolCode);
            if (symbol == null)
                return null;

            double div = Math.pow(10, symbol.decimal);
            double filledValue = (double)(order.filledPrice / div);

            if (order.positionType == SmPositionType.Sell) {
                CustomAnnotation annotation = sciChartBuilder.newCustomAnnotation()
                        .withPosition(index, filledValue )
                        .withContent(new SmOrderView(getActivity(), SmPositionType.Sell, Double.toString(filledValue)))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Bottom)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build();
                //아래로 향하는 화살표
                surface.getAnnotations().add(annotation);
                orderHashMap.put(order, annotation);

                return annotation;
            }
            else {
                CustomAnnotation annotation = sciChartBuilder.newCustomAnnotation()
                        .withPosition(index, filledValue)
                        .withContent(new SmOrderView(getActivity(), SmPositionType.Buy, Double.toString(filledValue)))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Top)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build();
                //위로 향하는 화살표
                surface.getAnnotations().add(annotation);
                orderHashMap.put(order ,annotation);

                return annotation;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onNewOrder(SmOrder order) {
        if (order == null || order.filledDate.length() == 0)
            return;
        if (order.orderState == SmOrderState.Filled)
            createOrderAnnotion(order);
        else if (order.orderState == SmOrderState.Settled)
            removeOrderAnnotation(order);
    }

    // 청산된 주문 어노테이션을 없앤다.
    private void removeOrderAnnotation(SmOrder order) {
        CustomAnnotation ordrer_anno = findOrderAnnotation(order);
        if (ordrer_anno != null) {
            surface.getAnnotations().remove(ordrer_anno);
        }
    }

    private void updateHogaView(SmSymbol symbol) {
        if (symbol == null)
            return;
        if (buyHogaAnnotations.size() > 0) {
            for (int i = 0; i < hogaShowCount; ++i) {
                SmValueAnnotation annotation = buyHogaAnnotations.get(i);
                SmCurrentValueView currentValueView = annotation.getValueView();
                double div = Math.pow(10, symbol.decimal);
                double hogaValue = (symbol.hoga.hogaItem[i].buyPrice / div);
                currentValueView.setValueText(Double.toString(hogaValue));
                currentValueView.invalidate();
            }
        }

        if (sellHogaAnnotations.size() > 0) {
            for (int i = 0; i < hogaShowCount; ++i) {
                SmValueAnnotation annotation = sellHogaAnnotations.get(i);
                SmCurrentValueView currentValueView = annotation.getValueView();
                double div = Math.pow(10, symbol.decimal);
                double hogaValue = (symbol.hoga.hogaItem[i].sellPrice / div);
                currentValueView.setValueText(Double.toString(hogaValue));
                currentValueView.invalidate();
            }
        }
    }

    private void updateHogaAnnotation(SmSymbol symbol) {
        if (symbol == null)
            return;
        if (buyHogaAnnotations.size() > 0) {
            for (int i = 0; i < hogaShowCount; ++i) {
                SmValueAnnotation annotation = buyHogaAnnotations.get(i);
                double div = Math.pow(10, symbol.decimal);
                double hogaValue = (symbol.hoga.hogaItem[i].buyPrice / div);
                annotation.setY1(hogaValue);
            }
        }

        if (sellHogaAnnotations.size() > 0) {
            for (int i = 0; i < hogaShowCount; ++i) {
                SmValueAnnotation annotation = sellHogaAnnotations.get(i);
                double div = Math.pow(10, symbol.decimal);
                double hogaValue = (symbol.hoga.hogaItem[i].sellPrice / div);
                annotation.setY1(hogaValue);
            }
        }
    }

    private void updateHoga(final  SmSymbol symbol) {
        if (symbol == null)
            return;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateHogaView(symbol);
            }
        });
        updateHogaAnnotation(symbol);
    }

    public void onUpdateSymbol(final SmSymbol symbol) {
        if (symbol == null || buyHogaAnnotations.size() == 0 || sellHogaAnnotations.size() == 0)
            return;
        if (symbol.code.compareTo(symbolCode) != 0)
            return;

        updateHoga(symbol);

    }


}
