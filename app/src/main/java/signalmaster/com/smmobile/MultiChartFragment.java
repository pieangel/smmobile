/*
package signalmaster.com.smmobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.scichart.charting.ClipMode;
import com.scichart.charting.Direction2D;
import com.scichart.charting.model.AnnotationCollection;
import com.scichart.charting.model.RenderableSeriesCollection;
import com.scichart.charting.model.dataSeries.OhlcDataSeries;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.model.dataSeries.XyyDataSeries;
import com.scichart.charting.modifiers.AxisDragModifierBase;
import com.scichart.charting.modifiers.LegendModifier;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.CategoryDateAxis;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.charting.visuals.renderableSeries.BaseRenderableSeries;
import com.scichart.charting.visuals.synchronization.SciChartVerticalGroup;
import com.scichart.core.common.Func1;
import com.scichart.core.utility.ListUtil;
import com.scichart.data.model.DoubleRange;
import com.scichart.drawing.common.BrushStyle;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.common.SolidBrushStyle;
import com.scichart.drawing.common.SolidPenStyle;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import signalmaster.com.smmobile.data.DataManager;
import signalmaster.com.smmobile.data.MovingAverage;
import signalmaster.com.smmobile.data.PriceSeries;

public class MultiChartFragment extends Fragment {
    private static final String VOLUME = "Volume";
    private static final String PRICES = "Prices";
    private static final String RSI = "RSI";
    private static final String MACD = "MACD";

    */
/*@BindView(R.id.priceChart)
    SciChartSurface priceChart;

    @BindView(R.id.macdChart)
    SciChartSurface macdChart;

    @BindView(R.id.rsiChart)
    SciChartSurface rsiChart;

    @BindView(R.id.volumeChart)
    SciChartSurface volumeChart;*//*



    protected final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();

    private final SciChartVerticalGroup verticalGroup = new SciChartVerticalGroup();

    private final DoubleRange sharedXRange = new DoubleRange();



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //inflate 해준 view 값을 return 해준다.
        View view = inflater.inflate(R.layout.multichart_fragment,container,false);


        //final BottomNavigationView bottom_navigation = (BottomNavigationView)getActivity().findViewById(R.id.bottom_navigation);

        //전체화면
        //위에서 inflate 된 view 값을 넣어준다. 그렇지않으면 null 값이 나옴 getView() x
         priceChart  = view.findViewById(R.id.priceChart);

        //final BottomNavigationView bottom_navigation = view2.findViewById(R.id.bottom_navigation);

         //레이아웃을 늘려주기위해 지정, 차트 surface 를 늘려주면 레이아웃은 늘어나지 않고 레이아웃안에서 차트만 늘어난다.
        final LinearLayout Lout1 = view.findViewById(R.id.Lout1);

         //라이너레이아웃에 크기를 늘려줄 변수를 생성해준다. view_p
        //객체 형이 다른 형일 때 맞지 않으면 ClassCastException 오류가 일어난다. ex) LinearLayout.LayoutParams <- 이부분
        final LinearLayout.LayoutParams view_p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        //add
        //final RelativeLayout.LayoutParams view_R = (RelativeLayout.LayoutParams) bottom_navigation.getLayoutParams();




        //OnTouchListener 생성
        priceChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                //switch case 방식
                */
/*switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP :
                        bottom_navigation.setVisibility(View.GONE);
                        int uiOptions = getActivity().getWindow().getDecorView().getSystemUiVisibility();
                        int newUiOption = uiOptions;
                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        Lout1.setLayoutParams(view_p);
                        newUiOption ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                        newUiOption ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
                        newUiOption ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(newUiOption);*//*


                //if else 방식
                int action = motionEvent.getAction();

                float curX = motionEvent.getX();
                float curY = motionEvent.getY();

                if (action == MotionEvent.ACTION_DOWN){
                   // println("손가락 눌림 : " + curX + "," + curY);
                }else if (action == MotionEvent.ACTION_MOVE){
                   // println("손가락 움직임 : " + curX + "," + curY);
                }else if (action == MotionEvent.ACTION_UP){
                    int uiOptions = getActivity().getWindow().getDecorView().getSystemUiVisibility();
                    int newUiOption = uiOptions;
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    Lout1.setLayoutParams(view_p);
                    newUiOption ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    newUiOption ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
                    newUiOption ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(newUiOption);

                    //안먹히는 부분
                    //bottom_navigation.setVisibility(View.GONE);
                    //add
                    //view_R.setBehavior(new BottomNavigationViewBehavior());


                }

                return false;
            }
        });




        ButterKnife.bind(this,view);

        //initExample
        final PriceSeries priceData = DataManager.getInstance().getPriceDataEurUsd(getActivity());

        final PricePaneModel pricePaneModel = new PricePaneModel(sciChartBuilder, priceData);
        final MacdPaneModel macdPaneModel = new MacdPaneModel(sciChartBuilder, priceData);
        final RsiPaneModel rsiPaneModel = new RsiPaneModel(sciChartBuilder, priceData);
        final VolumePaneModel volumePaneModel = new VolumePaneModel(sciChartBuilder, priceData);

        initChart(priceChart, pricePaneModel, true);
        initChart(macdChart, macdPaneModel, false);
        initChart(rsiChart, rsiPaneModel, false);
        initChart(volumeChart, volumePaneModel, false);


        return view;
    }

    private void initChart(SciChartSurface surface, BasePaneModel model, boolean isMainPane) {
        final CategoryDateAxis xAxis = sciChartBuilder.newCategoryDateAxis()
                .withVisibility(isMainPane ? View.VISIBLE : View.GONE)
                .withVisibleRange(sharedXRange)
                .withGrowBy(0, 0.05)
                .build();



        //background
        surface.setBackgroundColor(0xFFFFFFFF);

        xAxis.setDrawMajorBands(true);
        BrushStyle bandStyle = new SolidBrushStyle(0xFFFFFFFF);
        xAxis.setAxisBandsStyle(bandStyle);


        //x축 style
        // create a PenStyle for axis ticks
        PenStyle tickStyle = new SolidPenStyle(0xFF00FF00, true, 2, null);
        // apply the PenStyle
        xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        // create a PenStyle for grid lines
        PenStyle gridlineStyle = new SolidPenStyle(0x22222222, true, 2, null);
        // apply the PenStyle
        xAxis.setMajorGridLineStyle(gridlineStyle);
        xAxis.setMinorGridLineStyle(gridlineStyle);
        //





        surface.getXAxes().add(xAxis);
        surface.getYAxes().add(model.yAxis);

        surface.getRenderableSeries().addAll(model.renderableSeries);

        surface.getChartModifiers().add(sciChartBuilder
                .newModifierGroup()
                .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).withClipModeX(ClipMode.StretchAtExtents).build()
                .withPinchZoomModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
                .build());

        //레전드 색상
        LegendModifier legendModifier = new LegendModifier(getActivity());
        surface.getChartModifiers().add(legendModifier);
        legendModifier.setShowLegend(false);

        */
/*SciChartLegend legend = new SciChartLegend(getActivity());
        legend.setShowCheckboxes(false);
        legend.setBackgroundColor(0xFFFFFF00);*//*


        */
/*LegendModifier legendModifier = new LegendModifier(getActivity());
        surface.getChartModifiers().add(legendModifier);
        legendModifier.setShowLegend(true);*//*






        surface.setAnnotations(model.annotations);

        verticalGroup.addSurfaceToGroup(surface);



    }

    private abstract static class BasePaneModel {
        public final RenderableSeriesCollection renderableSeries;
        public final AnnotationCollection annotations;
        public final NumericAxis yAxis;
        public final String title;

        protected BasePaneModel(SciChartBuilder builder, String title, String yAxisTextFormatting, boolean isFirstPane) {
            this.title = title;
            this.renderableSeries = new RenderableSeriesCollection();
            this.annotations = new AnnotationCollection();

            this.yAxis = builder.newNumericAxis()
                    .withAxisId(title)
                    .withTextFormatting(yAxisTextFormatting)
                    .withAutoRangeMode(AutoRange.Always)
                    .withDrawMinorGridLines(true)
                    .withDrawMajorGridLines(true)
                    .withMinorsPerMajor(isFirstPane ? 4 : 2)
                    .withMaxAutoTicks(isFirstPane ? 8 : 4)
                    .withGrowBy(isFirstPane ? new DoubleRange(0.05d, 0.05d) : new DoubleRange(0d, 0d))
                    .build();

            //y축 style
            yAxis.setDrawMajorBands(true);
            BrushStyle bandStyle = new SolidBrushStyle(0xFFFFFFFF);
            yAxis.setAxisBandsStyle(bandStyle);

            PenStyle tickStyle = new SolidPenStyle(0xFF00FF00,true,2,null);
            yAxis.setMinorTickLineStyle(tickStyle);
            yAxis.setMajorTickLineStyle(tickStyle);

            PenStyle gridlineStyle = new SolidPenStyle(0x22222222,true,2,null);
            yAxis.setMajorGridLineStyle(gridlineStyle);
            yAxis.setMinorGridLineStyle(gridlineStyle);
        }

        final void addRenderableSeries(BaseRenderableSeries renderableSeries) {
            renderableSeries.setClipToBounds(true);
            this.renderableSeries.add(renderableSeries);
        }
    }

    private static class PricePaneModel extends BasePaneModel {

        public PricePaneModel(SciChartBuilder builder, PriceSeries prices) {
            super(builder, PRICES, "$0.0000", true);

            // Add the main OHLC chart
            final OhlcDataSeries<Date, Double> stockPrices = builder.newOhlcDataSeries(Date.class, Double.class).withSeriesName("EUR/USD").build();
            stockPrices.append(prices.getDateData(), prices.getOpenData(), prices.getHighData(), prices.getLowData(), prices.getCloseData());
            addRenderableSeries(builder.newCandlestickSeries().withDataSeries(stockPrices).withYAxisId(PRICES).build());

            final XyDataSeries<Date, Double> maLow = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("Low Line").build();
            maLow.append(prices.getDateData(), MovingAverage.movingAverage(prices.getCloseData(), 50));
            addRenderableSeries(builder.newLineSeries().withDataSeries(maLow).withStrokeStyle(0xFFFF3333, 1f).withYAxisId(PRICES).build());

            final XyDataSeries<Date, Double> maHigh = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("High Line").build();
            maHigh.append(prices.getDateData(), MovingAverage.movingAverage(prices.getCloseData(), 200));
            addRenderableSeries(builder.newLineSeries().withDataSeries(maHigh).withStrokeStyle(0xFF33DD33, 1f).withYAxisId(PRICES).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(stockPrices.getYValues().get(stockPrices.getCount() - 1)).withBackgroundColor(0xFFFF3333).withYAxisId(PRICES).build(),
                    builder.newAxisMarkerAnnotation().withY1(maLow.getYValues().get(maLow.getCount() - 1)).withBackgroundColor(0xFFFF3333).withYAxisId(PRICES).build(),
                    builder.newAxisMarkerAnnotation().withY1(maHigh.getYValues().get(maHigh.getCount() - 1)).withBackgroundColor(0xFF33DD33).withYAxisId(PRICES).build());

        }
    }

    private static class VolumePaneModel extends BasePaneModel {
        public VolumePaneModel(SciChartBuilder builder, PriceSeries prices) {
            super(builder, VOLUME, "###E+0", false);

            final XyDataSeries<Date, Double> volumePrices = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("Volume").build();
            volumePrices.append(prices.getDateData(), ListUtil.select(prices.getVolumeData(), new Func1<Long, Double>() {
                @Override
                public Double func(Long arg) {
                    return arg.doubleValue();
                }
            }));
            addRenderableSeries(builder.newColumnSeries().withDataSeries(volumePrices).withYAxisId(VOLUME).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(volumePrices.getYValues().get(volumePrices.getCount() - 1)).withYAxisId(VOLUME).build());
        }
    }

    private static class RsiPaneModel extends BasePaneModel {
        public RsiPaneModel(SciChartBuilder builder, PriceSeries prices) {
            super(builder, RSI, "0.0", false);

            final XyDataSeries<Date, Double> rsiSeries = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("RSI").build();
            rsiSeries.append(prices.getDateData(), MovingAverage.rsi(prices, 14));
            addRenderableSeries(builder.newLineSeries().withDataSeries(rsiSeries).withStrokeStyle(0xFFC6E6FF, 1f).withYAxisId(RSI).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(rsiSeries.getYValues().get(rsiSeries.getCount() - 1)).withYAxisId(RSI).build());
        }
    }

    private static class MacdPaneModel extends BasePaneModel {
        public MacdPaneModel(SciChartBuilder builder, PriceSeries prices) {
            super(builder, MACD, "0.00", false);

            final MovingAverage.MacdPoints macdPoints = MovingAverage.macd(prices.getCloseData(), 12, 25, 9);

            final XyDataSeries<Date, Double> histogramDataSeries = builder.newXyDataSeries(Date.class, Double.class).withSeriesName("Histogram").build();
            histogramDataSeries.append(prices.getDateData(), macdPoints.divergenceValues);
            addRenderableSeries(builder.newColumnSeries().withDataSeries(histogramDataSeries).withYAxisId(MACD).build());

            final XyyDataSeries<Date, Double> macdDataSeries = builder.newXyyDataSeries(Date.class, Double.class).withSeriesName("MACD").build();
            macdDataSeries.append(prices.getDateData(), macdPoints.macdValues, macdPoints.signalValues);
            addRenderableSeries(builder.newBandSeries().withDataSeries(macdDataSeries).withYAxisId(MACD).build());

            Collections.addAll(annotations,
                    builder.newAxisMarkerAnnotation().withY1(histogramDataSeries.getYValues().get(histogramDataSeries.getCount() - 1)).withYAxisId(MACD).build(),
                    builder.newAxisMarkerAnnotation().withY1(macdDataSeries.getYValues().get(macdDataSeries.getCount() - 1)).withYAxisId(MACD).build());
        }
    }
}
*/
