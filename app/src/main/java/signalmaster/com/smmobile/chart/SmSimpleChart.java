package signalmaster.com.smmobile.chart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.numerics.labelProviders.ICategoryLabelProvider;
import com.scichart.charting.numerics.labelProviders.ILabelFormatter;
import com.scichart.charting.numerics.labelProviders.NumericLabelProvider;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.CategoryDateAxis;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.core.common.Action1;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.core.utility.ComparableUtil;
import com.scichart.core.utility.DateIntervalUtil;
import com.scichart.core.utility.Guard;
import com.scichart.core.utility.NumberUtil;
import com.scichart.data.model.DateRange;
import com.scichart.data.model.IRange;
import com.scichart.drawing.common.BrushStyle;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.common.SolidBrushStyle;
import com.scichart.drawing.common.SolidPenStyle;
import com.scichart.extensions.builders.SciChartBuilder;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmStockFunc;
import signalmaster.com.smmobile.data.DataManager;
import signalmaster.com.smmobile.data.PriceSeries;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.global.SmConst;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.system.SmSystem;

/*
public static class TradeChartAxisLabelProviderDateTime extends NumericLabelProvider {

    public class TradeChartAxisLabelFormatterDateTime implements ILabelFormatter<CategoryDateAxis> {
        private final SimpleDateFormat labelFormat, cursorLabelFormat;

        private TradeChartAxisLabelFormatterDateTime() {
            labelFormat = new SimpleDateFormat(CategoryDateAxis.DEFAULT_TEXT_FORMATTING, Locale.getDefault());
            cursorLabelFormat = new SimpleDateFormat(CategoryDateAxis.DEFAULT_TEXT_FORMATTING, Locale.getDefault());
        }

        @Override
        public void update(CategoryDateAxis axis) {
            final ICategoryLabelProvider labelProvider = Guard.instanceOfAndNotNull(axis.getLabelProvider(), ICategoryLabelProvider.class);

            // this is range of indices which are drawn by CategoryDateAxis
            final IRange<Double> visibleRange = axis.getVisibleRange();

            // convert indicies to range of dates
            final DateRange dateRange = new DateRange(
                    ComparableUtil.toDate(labelProvider.transformIndexToData((int) NumberUtil.constrain(Math.floor(visibleRange.getMin()), 0, Integer.MAX_VALUE))),
                    ComparableUtil.toDate(labelProvider.transformIndexToData((int) NumberUtil.constrain(Math.ceil(visibleRange.getMax()), 0, Integer.MAX_VALUE))));

            if (dateRange.getIsDefined()) {
                long ticksInViewport = dateRange.getDiff().getTime();

                // select formatting based on diff in time between Min and Max
                if (ticksInViewport > DateIntervalUtil.fromYears(1)) {
                    // apply year formatting
                    labelFormat.applyPattern("");
                    cursorLabelFormat.applyPattern("");
                } else if (ticksInViewport > DateIntervalUtil.fromMonths(1)) {
                    // apply month formatting
                    labelFormat.applyPattern("");
                    cursorLabelFormat.applyPattern("");
                } else if (ticksInViewport > DateIntervalUtil.fromMonths(1)) {
                    // apply day formatting
                    labelFormat.applyPattern("");
                    cursorLabelFormat.applyPattern("");
                }
            }
        }

        @Override
        public CharSequence formatLabel(Comparable dataValue) {
            final Date valueToFormat = ComparableUtil.toDate(dataValue);
            return labelFormat.format(valueToFormat);
        }

        @Override
        public CharSequence formatCursorLabel(Comparable dataValue) {
            final Date valueToFormat = ComparableUtil.toDate(dataValue);
            return cursorLabelFormat.format(valueToFormat);
        }
    }
}
*/


public class SmSimpleChart extends Fragment {
    private String style = "history_volatility";
    IAxis xAxis = null;
    IAxis yAxis = null;
    private XyDataSeries<Date, Double> xyDataSeries = null;

    protected final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();
    private PriceSeries _currentSeries = null;
    private SmSystem _curSystem = null;
    private void CreateSystem() {
        _curSystem = new SmSystem("system1");
    }
    @BindView(R.id.chart_layout2)
    SciChartSurface surface;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        CreateSystem();
    }

    private String symbolCode;

    private void registerCallback() {
        SmChartDataService chartDataService = SmChartDataService.getInstance();
        chartDataService.subscribeChartData(onChartData(), this.style);
    }

    @NonNull
    private synchronized Action1<SmChartData> onChartData() {
        return new Action1<SmChartData>() {
            @Override
            public void execute(final SmChartData chartData) {
                if (chartData.symbolCode.compareTo(symbolCode) != 0 ||
                chartData.chartType != SmChartType.DAY)
                    return;
                setHV(chartData);
            }
        };
    }

    public void setHV(SmChartData chartData) {
        if (chartData == null || chartData.chartType != SmChartType.DAY)
            return;

        SmStockFunc func = new SmStockFunc();
        if (xyDataSeries != null) {
            xyDataSeries.clear();
            double [] hs = func.GetHistoricVolatility(chartData.getClose(), 20);
            LinkedList<Date> date_list = chartData.getDate();
            for(int i = 0; i < date_list.size(); ++i) {
                if (hs[i] !=  0.0)
                    xyDataSeries.append(date_list.get(i), hs[i]);
                else
                    Log.d("TAG", "setHV" + "  code" + chartData.symbolCode + " : " + date_list.get(i) + " : " + hs[i]);
                Log.d("TAG", "setHV" + "  code" + chartData.symbolCode + " : " + date_list.get(i) + " : " + hs[i]);
            }
            //xyDataSeries.append(dataItem.date, dataItem.close);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sm_simple_chart_layout,null);
        ButterKnife.bind(this,view);

        //click event

        if (xyDataSeries == null)
            xyDataSeries = sciChartBuilder.newXyDataSeries(Date.class, Double.class).build();
        xyDataSeries.setAcceptsUnsortedData(true);
        xyDataSeries.setFifoCapacity(SmConst.ChartDataSize);

        _currentSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        int size = _currentSeries.size();

        xAxis = sciChartBuilder.newCategoryDateAxis().withVisibleRange(size - 30, size).withGrowBy(0, 0.1).build();
        yAxis = sciChartBuilder.newNumericAxis().withGrowBy(0d, 0.1d).withAutoRangeMode(AutoRange.Always).build();

        /*
        IOhlcDataSeries<Date, Double> dataSeries = new OhlcDataSeries<>(Date.class, Double.class);
        dataSeries.append(_currentSeries.getDateData(), _currentSeries.getOpenData(), _currentSeries.getHighData(), _currentSeries.getLowData(), _currentSeries.getCloseData());

        //CreateSignals();

        //DrawSignals();
        final FastLineRenderableSeries rSeries = sciChartBuilder.newLineSeries()
                .withStrokeStyle(new SolidPenStyle(0xFFFFFFFF, true, 2, null))
                .withDataSeries(dataSeries)
                .build();
        */

        final FastLineRenderableSeries fastMountainRenderableSeries = sciChartBuilder.newLineSeries()
                .withDataSeries(xyDataSeries)
                .withStrokeStyle(0xAAFFC9A8, 1.0f, true)
                .build();
        //backcolor
        surface.setBackgroundColor(0xFF1E1E1E);
        xAxis.setDrawMajorBands(true);
        xAxis.setTextFormatting("yyyy/MM");
        //BrushStyle bandStyle = new SolidBrushStyle(0xCC25304C);
        //xAxis.setAxisBandsStyle(bandStyle);

        //x축 style
        // create a PenStyle for axis ticks
        PenStyle tickStyle = new SolidPenStyle(0xFFFFFFFF, true, 2, null);
        // apply the PenStyle
        xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        // create a PenStyle for grid lines
        PenStyle gridlineStyle = new SolidPenStyle(0x33FFFFFF, true, 1, null);
        PenStyle gridlineStyle2 = new SolidPenStyle(0x33FFFFFF, true, 2, null);
        // apply the PenStyle
        xAxis.setMajorGridLineStyle(gridlineStyle);
        xAxis.setMinorGridLineStyle(gridlineStyle2);


        //y축 style
        //yAxis.setDrawMajorBands(true);

        //yAxis.setAxisBandsStyle(bandStyle);


        //yAxis.setMinorTickLineStyle(tickStyle);
        //yAxis.setMajorTickLineStyle(tickStyle);

        yAxis.setMajorGridLineStyle(gridlineStyle);
        //yAxis.setMinorGridLineStyle(gridlineStyle2);

        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {



                Collections.addAll(surface.getXAxes(), xAxis);
                Collections.addAll(surface.getYAxes(), yAxis);
                Collections.addAll(surface.getRenderableSeries(), fastMountainRenderableSeries);
                //DrawSignals();

                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());

                sciChartBuilder.newAnimator(fastMountainRenderableSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
            }
        });

        // 차트 데이터 도착 이벤트 콜백 등록
        registerCallback();

        return view;
    }

    public String getSymbolCode() {
        return symbolCode;
    }

    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
        // 차트데이터를 만든다.
        SmChartData smChartData = SmChartDataManager.getInstance().createChartData(this.symbolCode , SmChartType.DAY, 1);

        // 최신 데이터가 아니면 차트데이터를 요청한다.
        if (smChartData.getCount() < SmConst.TempDataSize) {
            // 서버에 차트 데이터를 요청한다.
            SmChartDataManager.getInstance().requestChartData(smChartData);
        }
        else {
            setHV(smChartData);
        }
    }
}
