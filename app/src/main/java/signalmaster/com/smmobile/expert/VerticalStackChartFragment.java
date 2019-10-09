package signalmaster.com.smmobile.expert;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.scichart.charting.layoutManagers.ChartLayoutState;
import com.scichart.charting.layoutManagers.DefaultLayoutManager;
import com.scichart.charting.layoutManagers.VerticalAxisLayoutStrategy;
import com.scichart.charting.model.RenderableSeriesCollection;
import com.scichart.charting.model.dataSeries.IOhlcDataSeries;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.model.dataSeries.OhlcDataSeries;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.AxisAlignment;
import com.scichart.charting.visuals.axes.AxisLayoutState;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.BaseRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastCandlestickRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.SmOptionFragment;
import signalmaster.com.smmobile.data.DataManager;
import signalmaster.com.smmobile.data.DoubleSeries;
import signalmaster.com.smmobile.data.MovingAverage;
import signalmaster.com.smmobile.data.PriceSeries;

public class VerticalStackChartFragment extends Fragment {

    public static VerticalStackChartFragment newInstance(){
        VerticalStackChartFragment fragment = new VerticalStackChartFragment();
        Bundle args =  new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    protected final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();

    SciChartSurface surface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vertical_stack_chart,null);

        surface = (SciChartSurface)view.findViewById(R.id.chart);

        final List<IXyDataSeries<Double, Double>> dataSeries = new ArrayList<>();

        PriceSeries priceSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        IOhlcDataSeries<Date, Double> dataSeriesCandle = new OhlcDataSeries<>(Date.class, Double.class);
        dataSeriesCandle.append(priceSeries.getDateData(), priceSeries.getOpenData(), priceSeries.getHighData(), priceSeries.getLowData(), priceSeries.getCloseData());

        for (int i = 0; i < 5; i++) {
            final XyDataSeries<Double, Double> ds = new XyDataSeries<>(Double.class, Double.class);
            dataSeries.add(ds);

            final DoubleSeries sineWave = DataManager.getInstance().getSinewave(3, i, 1000);
            ds.append(sineWave.xValues, sineWave.yValues);
        }

        final FastLineRenderableSeries ch0 = sciChartBuilder.newLineSeries().withDataSeries(dataSeries.get(0)).withStrokeStyle(0xFFFF1919, 1f, true).withYAxisId("Ch0").build();
        final FastLineRenderableSeries ch1 = sciChartBuilder.newLineSeries().withDataSeries(dataSeries.get(1)).withStrokeStyle(0xFFFC9C29, 1f, true).withYAxisId("Ch1").build();
        final FastLineRenderableSeries ch2 = sciChartBuilder.newLineSeries().withDataSeries(dataSeries.get(2)).withStrokeStyle(0xFFFF1919, 1f, true).withYAxisId("Ch2").build();
        final FastLineRenderableSeries ch3 = sciChartBuilder.newLineSeries().withDataSeries(dataSeries.get(3)).withStrokeStyle(0xFFFC9C29, 1f, true).withYAxisId("Ch3").build();
        final FastCandlestickRenderableSeries rSeries = sciChartBuilder.newCandlestickSeries()
                .withStrokeUp(0xFF00AA00)
                .withFillUpColor(0x8800AA00)
                .withStrokeDown(0xFFFF0000)
                .withFillDownColor(0x88FF0000)
                .withDataSeries(dataSeriesCandle)
                .withYAxisId("candle")
                .build();

        // .withIsVisible(false)

        /*final XyDataSeries<Date, Double> rsiSeries = sciChartBuilder.newLineSeries().withDataSeries().withStrokeStyle(0xFFC6E6FF, 1f).withYAxisId("RSI").build();
        rsiSeries.append(priceSeries.getDateData(), MovingAverage.rsi(priceSeries, 14));*/
        int size = priceSeries.size();
        final IAxis xAxis = sciChartBuilder.newCategoryDateAxis().withVisibleRange(size - 30, size).withGrowBy(0, 0.1).build();
        final IAxis yAxis0 = sciChartBuilder.newNumericAxis().withAxisAlignment(AxisAlignment.Left).withAxisId("Ch0").withAxisTitle("Ch0").withVisibleRange(-2, 2).withAutoRangeMode(AutoRange.Never).withDrawMajorGridLines(false).withDrawMinorGridLines(false).withDrawMajorBands(false).build();
        final IAxis yAxis1 = sciChartBuilder.newNumericAxis().withAxisAlignment(AxisAlignment.Left).withAxisId("Ch1").withAxisTitle("Ch1").withVisibleRange(-2, 2).withAutoRangeMode(AutoRange.Never).withDrawMajorGridLines(false).withDrawMinorGridLines(false).withDrawMajorBands(false).build();
        final IAxis yAxis2 = sciChartBuilder.newNumericAxis().withAxisAlignment(AxisAlignment.Left).withAxisId("Ch2").withAxisTitle("Ch2").withVisibleRange(-2, 2).withAutoRangeMode(AutoRange.Never).withDrawMajorGridLines(false).withDrawMinorGridLines(false).withDrawMajorBands(false).build();
        final IAxis yAxis3 = sciChartBuilder.newNumericAxis().withAxisAlignment(AxisAlignment.Left).withAxisId("Ch3").withAxisTitle("Ch3").withVisibleRange(-2, 2).withAutoRangeMode(AutoRange.Never).withDrawMajorGridLines(false).withDrawMinorGridLines(false).withDrawMajorBands(false).build();
        final IAxis yAxis4 = sciChartBuilder.newNumericAxis().withAxisAlignment(AxisAlignment.Left).withAxisId("candle").withAxisTitle("candle").withVisibleRange(-2, 2).withAutoRangeMode(AutoRange.Never).withDrawMajorGridLines(false).withDrawMinorGridLines(false).withDrawMajorBands(false).build();

        //yAxis4.setVisibility(View.GONE);

        final DefaultLayoutManager layoutManager = new DefaultLayoutManager.Builder().setLeftOuterAxesLayoutStrategy(new LeftAlignedOuterVerticallyStackedYAxisLayoutStrategy()).build();

        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {
                surface.setLayoutManager(layoutManager);

                Collections.addAll(surface.getXAxes(), xAxis);
                Collections.addAll(surface.getYAxes(), yAxis0, yAxis1, yAxis2, yAxis3,yAxis4);
                //Collections.replaceAll(surface.getXAxes(),yAxis0, yAxis1);
                Collections.addAll(surface.getRenderableSeries(), ch0, ch1, ch2, ch3,rSeries);
                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());

                sciChartBuilder.newAnimator(ch0).withSweepTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
                sciChartBuilder.newAnimator(ch1).withSweepTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
                sciChartBuilder.newAnimator(ch2).withSweepTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
                sciChartBuilder.newAnimator(ch3).withSweepTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
                sciChartBuilder.newAnimator(rSeries).withSweepTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
            }
        });



        return view;
    }


}

 class LeftAlignedOuterVerticallyStackedYAxisLayoutStrategy extends VerticalAxisLayoutStrategy {
    @Override
    public void measureAxes(int availableWidth, int availableHeight, ChartLayoutState chartLayoutState) {
        for (int i = 0, size = axes.size(); i < size; i++) {
            final IAxis axis = axes.get(i);

            axis.updateAxisMeasurements();

            final AxisLayoutState axisLayoutState = axis.getAxisLayoutState();

            chartLayoutState.leftOuterAreaSize = Math.max(getRequiredAxisSize(axisLayoutState), chartLayoutState.leftOuterAreaSize);
        }
    }

    @Override
    public void layoutAxes(int left, int top, int right, int bottom) {
        final int size = axes.size();

        final int height = bottom - top;
        final int axisSize = height / size;

        int topPlacement = top;

        for (int i = 0; i < size; i++) {
            final IAxis axis = axes.get(i);

            final AxisLayoutState axisLayoutState = axis.getAxisLayoutState();

            final int bottomPlacement = topPlacement + axisSize;

            axis.layoutArea(right - getRequiredAxisSize(axisLayoutState) + axisLayoutState.additionalLeftSize, topPlacement, right - axisLayoutState.additionalRightSize, bottomPlacement);

            topPlacement = bottomPlacement;
        }
    }
}
