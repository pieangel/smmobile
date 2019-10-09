package signalmaster.com.smmobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
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
import com.scichart.charting.visuals.renderableSeries.FastCandlestickRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
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
import signalmaster.com.smmobile.Signal.SmSignal;
import signalmaster.com.smmobile.annotation.SmArrowView;
import signalmaster.com.smmobile.chart.SmChart;
import signalmaster.com.smmobile.data.DataManager;
import signalmaster.com.smmobile.data.PriceSeries;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.system.SmSystem;

public class SChartFragment extends Fragment  {

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
                        .withContent(new SmArrowView(getActivity(), 0, 0xFF0000ff, 0xff0000ff))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Bottom)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());
                /*surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex, sig.signalValue)
                        .withContent(new SChartFragment.CustomView1(getActivity()))
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());*/
            }
            else {
                //위로 향하는 화살표
                surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex,sig.signalValue)
                        .withContent(new SmArrowView(getActivity(), 1, 0xFFff0000, 0xffff0000))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Top)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());
                /*surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex, sig.signalValue)
                        .withContent(new SChartFragment.CustomView2(getActivity()))
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());*/
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

        IOhlcDataSeries<Date, Double> dataSeries = new OhlcDataSeries<>(Date.class, Double.class);
        dataSeries.append(_currentSeries.getDateData(), _currentSeries.getOpenData(), _currentSeries.getHighData(), _currentSeries.getLowData(), _currentSeries.getCloseData());

        CreateSignals();

        //DrawSignals();
        final FastCandlestickRenderableSeries rSeries = sciChartBuilder.newCandlestickSeries()
                .withStrokeUp(0xFFFF0000)
                .withFillUpColor(0x88FF0000)
                .withStrokeDown(0xFF0000FF)
                .withFillDownColor(0x880000FF)
                .withDataSeries(dataSeries)
                .build();

        //backcolor
        surface.setBackgroundColor(0xFF25304C);
        xAxis.setDrawMajorBands(true);
        BrushStyle bandStyle = new SolidBrushStyle(0xCC25304C);
        xAxis.setAxisBandsStyle(bandStyle);

        //x축 style
        // create a PenStyle for axis ticks
        PenStyle tickStyle = new SolidPenStyle(0xFFFFFFFF, true, 2, null);
        // apply the PenStyle
        xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        // create a PenStyle for grid lines
        PenStyle gridlineStyle = new SolidPenStyle(0xBBFFFFFF, true, 1, null);
        PenStyle gridlineStyle2 = new SolidPenStyle(0xBB25304C, true, 2, null);
        // apply the PenStyle
        xAxis.setMajorGridLineStyle(gridlineStyle);
        xAxis.setMinorGridLineStyle(gridlineStyle2);


        //y축 style
        yAxis.setDrawMajorBands(true);

        yAxis.setAxisBandsStyle(bandStyle);


        yAxis.setMinorTickLineStyle(tickStyle);
        yAxis.setMajorTickLineStyle(tickStyle);

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

        ArrayList<SmSignal> signalList = _curSystem.CreateRandomSignals(_currentSeries);
    }

    // 메인 차트 인덱스 . 차트 배열에서 이 인덱스를 가진 차트가 메인 차트가 된다.
    private int _mainChartIndex = 0;
    private String _mainChartDataSymbolCode = "data1";
    private SmChart _mainChart = null;
    void initChart() {
        SmChartDataManager chartDataMgr = SmChartDataManager.getInstance();
        SmChartData chartData = chartDataMgr.findChartData("data1");
        if (chartData != null)
            _mainChart.set_chartData(chartData);
    }

    public static class CustomView1 extends View {


        private final int FILL_COLOR = Color.parseColor("#FFFF0000");
        private final int STROKE_COLOR = Color.parseColor("#FF990000");

        private final Path path = new Path();
        private final Paint paintFill = new Paint();
        private final Paint paintStroke = new Paint();

        public CustomView1(Context context) {
            super(context);
            init();
        }

        public CustomView1(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            paintFill.setStyle(Paint.Style.FILL);
            paintFill.setColor(FILL_COLOR);
            paintStroke.setStyle(Paint.Style.STROKE);
            paintStroke.setColor(STROKE_COLOR);
            int longWidth = 20;
            int shortWidth = 10;
            int header = 20;
            int tail =  20;
            path.moveTo(0, 15);
            path.lineTo(15, 0);
            path.lineTo(30, 15);
            path.lineTo(20, 15);
            path.lineTo(20, 30);
            path.lineTo(10, 30);
            path.lineTo(10, 15);
            path.lineTo(0, 15);
            /*path.moveTo(shortWidth/2, 0);
            path.lineTo(shortWidth/2, tail);
            path.lineTo(longWidth/2, tail);
            path.lineTo(0, header+tail);
            path.lineTo(-longWidth/2, tail);
            path.lineTo(-shortWidth/2, tail);
            path.lineTo(-shortWidth/2, 0);
            path.lineTo(shortWidth/2, 0);*/

            //아래화살표
            /*path.moveTo(0, 0);
            path.lineTo(10, 0);
            path.lineTo(10, -15);
            path.lineTo(20, -15);
            path.lineTo(20, 0);
            path.lineTo(30, 0);
            path.lineTo(15, 15);
            path.lineTo(0, 0);*/
            /*path.lineTo(10, 30);
            path.lineTo(10, 15);
            path.lineTo(0, 0);*/

            setMinimumHeight(50);
            setMinimumWidth(50);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, paintFill);
            canvas.drawPath(path, paintStroke);
        }

    }


    public static class CustomView2 extends View {

        private final int FILL_COLOR = Color.parseColor("#571CB61C");
        private final int STROKE_COLOR = Color.parseColor("#FF00B400");


        private final Path path = new Path();
        private final Paint paintFill = new Paint();
        private final Paint paintStroke = new Paint();

        public CustomView2(Context context) {
            super(context);
            paintFill.setStyle(Paint.Style.FILL);
            paintFill.setColor(FILL_COLOR);
            paintStroke.setStyle(Paint.Style.STROKE);
            paintStroke.setColor(STROKE_COLOR);

            path.moveTo(0, 15);
            path.lineTo(10, 15);
            path.lineTo(10, 0);
            path.lineTo(20, 0);
            path.lineTo(20, 15);
            path.lineTo(30, 15);
            path.lineTo(15, 30);
            path.lineTo(0, 15);

            int longWidth = 20;
            int shortWidth = 10;
            int header = 20;
            int tail =  20;

            /*path.moveTo(shortWidth/2, 0);
            path.lineTo(shortWidth/2, tail);
            path.lineTo(longWidth/2, tail);
            path.lineTo(0, header+tail);
            path.lineTo(-longWidth/2, tail);
            path.lineTo(-shortWidth/2, tail);
            path.lineTo(-shortWidth/2, 0);
            path.lineTo(shortWidth/2, 0);*/

            setMinimumHeight(50);
            setMinimumWidth(50);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, paintFill);
            canvas.drawPath(path, paintStroke);
        }

    }

}
