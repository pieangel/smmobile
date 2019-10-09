package signalmaster.com.smmobile.mock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.scichart.charting.model.dataSeries.IOhlcDataSeries;
import com.scichart.charting.model.dataSeries.OhlcDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.HorizontalAnchorPoint;
import com.scichart.charting.visuals.annotations.VerticalAnchorPoint;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.axes.IAxisCore;
import com.scichart.charting.visuals.axes.VisibleRangeChangeListener;
import com.scichart.charting.visuals.renderableSeries.FastMountainRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.data.model.DoubleRange;
import com.scichart.data.model.IRange;
import com.scichart.drawing.common.BrushStyle;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.common.SolidBrushStyle;
import com.scichart.drawing.common.SolidPenStyle;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.annotation.SmArrowView;
import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.Signal.SmSignal;
import signalmaster.com.smmobile.system.SmSystem;
import signalmaster.com.smmobile.chart.SmChart;
import signalmaster.com.smmobile.chart.SmChartManager;
import signalmaster.com.smmobile.data.DataManager;
import signalmaster.com.smmobile.data.PriceSeries;

public class SmLeftChartFragment extends Fragment {
    IAxis xAxis = null;
    IAxis yAxis = null;

    protected final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();
    private PriceSeries _currentSeries = null;
    private SmSystem _curSystem = null;
    private void CreateSystem() {
        _curSystem = new SmSystem("system1");
    }
    @BindView(R.id.chart_layout)
    SciChartSurface surface;

    private SmChart mock_left = null;

    public SmLeftChartFragment() {
        SmChartManager smChartManager = SmChartManager.getInstance();

            mock_left = smChartManager.getChart("mock_left");
            if (mock_left == null) {
                mock_left = new SmChart();

                mock_left.upStrokeColor = 0xffff0000;
                mock_left.downStrokeColor = 0xff0000ff;
                mock_left.upArrowType = 0;
                mock_left.downArrowType = 1;
                mock_left.fillUpColor = 0x88FF0000;
                mock_left.fillDownColor = 0x880000FF;
                mock_left.fillcolor = 0xFF0000ff;
                mock_left.withStrokeUp = 0xFFFF0000;
                mock_left.withFillUpColor = 0x88FF0000;
                mock_left.withStrokeDown = 0xFF0000FF;
                mock_left.withFillDownColor = 0x880000FF;
                mock_left.withStrokeStyle = 0xFF00AA00;
                mock_left.mountSeriesColor = 0xAAAFE1FF;
                mock_left.GradientColors = 0xAA4375DB;
                mock_left.GradientColorsEnd = 0x88090E11;
                mock_left.lineSeriesColor = 0xFF279B27;
                mock_left.strokeThickness = 1f;
                mock_left.antiAliasing = true;

                mock_left.tickStyle = 0xFFFFFFFF;
                mock_left.gridlineStyle = 0x33FFFFFF;
                mock_left.gridlineStyle2 = 0x0025304C;
                mock_left.gridlineStyle3 = 0x00FFFFFF;
                mock_left.bandStyle = 0xFF000000;
                mock_left.setBackgroundColor = 0xFF000000;

                mock_left.annoColor = 0x22FFFFFF;
                mock_left.annoTextSize = 14;


                smChartManager.AddChart("mock_left", mock_left);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        CreateSystem();
    }

    private void DrawSignals() {
        if (_curSystem == null || _currentSeries == null || xAxis == null || yAxis == null)
            return;


        ArrayList<SmSignal> signalList = _curSystem.get_signalList();
        for(SmSignal sig : signalList) {
            if (sig.signalType == SmGlobal.SmSignalType.Buy) {

                //아래로 향하는 화살표
                surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex,sig.signalValue )
                        .withContent(new SmArrowView(getActivity(), mock_left.downArrowType, mock_left.downStrokeColor, mock_left.downStrokeColor))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Bottom)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());
            }
            else {
                //위로 향하는 화살표
                surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex,sig.signalValue)
                        .withContent(new SmArrowView(getActivity(), mock_left.upArrowType, mock_left.upStrokeColor, mock_left.upStrokeColor))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Top)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());
            }

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.sm_chart,null);
        ButterKnife.bind(this,view);

        //click event



        _currentSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        int size = _currentSeries.size();

        xAxis = sciChartBuilder.newCategoryDateAxis().withVisibleRange(size - 30, size).withGrowBy(0, 0.1).build();
        yAxis = sciChartBuilder.newNumericAxis().withGrowBy(0d, 0.1d).withAutoRangeMode(AutoRange.Always).build();

        PriceSeries priceSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        IOhlcDataSeries<Date, Double> dataSeries = new OhlcDataSeries<>(Date.class, Double.class);
        dataSeries.append(priceSeries.getDateData(), priceSeries.getOpenData(), priceSeries.getHighData(), priceSeries.getLowData(), priceSeries.getCloseData());

        CreateSignals();

        //DrawSignals();
        final FastMountainRenderableSeries rSeries = sciChartBuilder.newMountainSeries()
                .withDataSeries(dataSeries)
                .withStrokeStyle(mock_left.mountSeriesColor, 1f, true)
                .withAreaFillLinearGradientColors(mock_left.GradientColors, mock_left.GradientColorsEnd)
                .build();

        yAxis.setVisibility(View.GONE);
        xAxis.setVisibility(View.GONE);

        //x축 visibleRange 제한
        xAxis.setVisibleRangeChangeListener(new VisibleRangeChangeListener() {
            @Override
            public void onVisibleRangeChanged(IAxisCore axis, IRange oldRange, IRange newRange, boolean isAnimating) {

                // TODO handle range changes here
                if (newRange.getMinAsDouble() < 0d && newRange.getMaxAsDouble() > (double) _currentSeries.size()) {
                    DoubleRange visibleRange = new DoubleRange(0d, (double)_currentSeries.size() -1);
                    xAxis.setVisibleRange(visibleRange);
                }
                else if (newRange.getMinAsDouble() < 0d ) {
                    DoubleRange visibleRange = new DoubleRange(0d, newRange.getMaxAsDouble());
                    xAxis.setVisibleRange(visibleRange);
                } else if (newRange.getMaxAsDouble() >= (double) _currentSeries.size()) {
                    DoubleRange visibleRange = new DoubleRange(newRange.getMinAsDouble(), (double)_currentSeries.size() -1);
                    xAxis.setVisibleRange(visibleRange);
                }
            }
        });

        //backcolor
        surface.setBackgroundColor(mock_left.setBackgroundColor);
        xAxis.setDrawMajorBands(true);
        BrushStyle bandStyle = new SolidBrushStyle(mock_left.bandStyle);
        xAxis.setAxisBandsStyle(bandStyle);

        //x축 style
        // create a PenStyle for axis ticks
        PenStyle tickStyle = new SolidPenStyle(mock_left.tickStyle, true, 2, null);
        // apply the PenStyle
        xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        // create a PenStyle for grid lines

        PenStyle gridlineStyle3 = new SolidPenStyle(mock_left.gridlineStyle3, true, 1, null);
        PenStyle gridlineStyle2 = new SolidPenStyle(mock_left.gridlineStyle2, true, 2, null);
        // apply the PenStyle
        xAxis.setMajorGridLineStyle(gridlineStyle3);
        xAxis.setMinorGridLineStyle(gridlineStyle2);

        //y축 style
        yAxis.setDrawMajorBands(true);
        yAxis.setAxisBandsStyle(bandStyle);

        yAxis.setMinorTickLineStyle(tickStyle);
        yAxis.setMajorTickLineStyle(tickStyle);

        PenStyle gridlineStyle = new SolidPenStyle(mock_left.gridlineStyle, true, 1, new float[]{10, 10});
        yAxis.setMajorGridLineStyle(gridlineStyle);
        yAxis.setMinorGridLineStyle(gridlineStyle2);

        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {



                Collections.addAll(surface.getXAxes(), xAxis);
                Collections.addAll(surface.getYAxes(), yAxis);
                Collections.addAll(surface.getRenderableSeries(), rSeries);
                DrawSignals();

                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());

                sciChartBuilder.newAnimator(rSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
            }
        });


        return view;
    }



    void CreateSignals() {
        if (_currentSeries == null || _curSystem == null)
            return;

    }
}
