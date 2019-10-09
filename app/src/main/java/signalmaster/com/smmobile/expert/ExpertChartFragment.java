package signalmaster.com.smmobile.expert;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;


import com.scichart.charting.model.RenderableSeriesCollection;
import com.scichart.charting.model.dataSeries.IOhlcDataSeries;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.model.dataSeries.OhlcDataSeries;
import com.scichart.charting.modifiers.OnAnnotationCreatedListener;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.AnnotationCoordinateMode;
import com.scichart.charting.visuals.annotations.HorizontalAnchorPoint;
import com.scichart.charting.visuals.annotations.HorizontalLineAnnotation;
import com.scichart.charting.visuals.annotations.IAnnotation;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.charting.visuals.annotations.VerticalAnchorPoint;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.AxisAlignment;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.axes.IAxisCore;
import com.scichart.charting.visuals.axes.VisibleRangeChangeListener;
import com.scichart.charting.visuals.renderableSeries.FastCandlestickRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastMountainRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastOhlcRenderableSeries;
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
import signalmaster.com.smmobile.Signal.SmSignal;
import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.system.SmSystem;
import signalmaster.com.smmobile.annotation.ChartSetting;
import signalmaster.com.smmobile.annotation.ChartTouchModifier;
import signalmaster.com.smmobile.annotation.SmArrowView;
import signalmaster.com.smmobile.annotation.SmCreateAnno;
import signalmaster.com.smmobile.annotation.SmCreateTouch;
import signalmaster.com.smmobile.annotation.SmDeleteAnno;
import signalmaster.com.smmobile.annotation.SmDeleteTouch;
import signalmaster.com.smmobile.annotation.SmJeePyo;
import signalmaster.com.smmobile.annotation.SmJeepyoTouch;
import signalmaster.com.smmobile.annotation.SmSetting;
import signalmaster.com.smmobile.chart.SmChart;
import signalmaster.com.smmobile.chart.SmChartManager;
import signalmaster.com.smmobile.data.DataManager;
import signalmaster.com.smmobile.data.PriceSeries;


public class ExpertChartFragment extends Fragment implements OnAnnotationCreatedListener {


    public HorizontalLineAnnotation annotation = null;

    TextAnnotation t_annotation = null;
    TextAnnotation j_annotation = null;
    TextAnnotation d_annotation = null;
    TextAnnotation c_annotation = null;

    private ArrayList<SmSetting> _settingList = new ArrayList<>();
    private ArrayList<SmJeePyo> _jeepyoList = new ArrayList<>();
    private ArrayList<SmDeleteAnno> _deleteList = new ArrayList<>();
    private ArrayList<SmCreateAnno> _createList = new ArrayList<>();


    public ArrayList<SmSetting> getSettingList() {
        return _settingList;
    }

    public ArrayList<SmJeePyo> getJeepyoList() {
        return _jeepyoList;
    }


    public ArrayList<SmDeleteAnno> getDeleteList() {
        return _deleteList;
    }

    public ArrayList<SmCreateAnno> getCreateList() {
        return _createList;
    }

    //Annotation Touch
    private ChartTouchModifier _touchModifier = null;
    private ChartSetting _touchSetting = null;
    private SmJeepyoTouch _touchJeepyo = null;
    private SmDeleteTouch _touchDelete = null;
    private SmCreateTouch _touchCreate = null;


    private IAxis xAxis = null;
    private IAxis yAxis = null;

    public IAxis getxAxis() {
        return xAxis;
    }

    public void setxAxis(IAxis xAxis) {
        this.xAxis = xAxis;
    }

    public IAxis getyAxis() {
        return yAxis;
    }

    public void setyAxis(IAxis yAxis) {
        this.yAxis = yAxis;
    }

    public final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();
    private PriceSeries _currentSeries = null;
    private SmSystem _curSystem = null;

    private void CreateSystem() {
        _curSystem = new SmSystem("system1");
    }

    @BindView(R.id.chart_layout)
    public SciChartSurface surface;

    public SciChartSurface getSurface() {
        return surface;
    }

    public void setSurface(SciChartSurface surface) {
        this.surface = surface;
    }


    private SmChart expert_chart = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateSystem();

    }

    public ExpertChartFragment() {
        SmChartManager smChartManager = SmChartManager.getInstance();


        expert_chart = smChartManager.getChart("expert_chart");
        if (expert_chart == null) {
            expert_chart = new SmChart();

            expert_chart.upStrokeColor = 0xffff0000;
            expert_chart.downStrokeColor = 0xff0000ff;
            expert_chart.upArrowType = 0;
            expert_chart.downArrowType = 1;
            expert_chart.fillUpColor = 0x88FF0000;
            expert_chart.fillDownColor = 0x880000FF;
            expert_chart.fillcolor = 0xFF0000ff;
            expert_chart.withStrokeUp = 0xFFFF0000;
            expert_chart.withFillUpColor = 0x88FF0000;
            expert_chart.withStrokeDown = 0xFF0000FF;
            expert_chart.withFillDownColor = 0x880000FF;
            expert_chart.withStrokeStyle = 0xFF00AA00;
            expert_chart.mountSeriesColor = 0xAAAFE1FF;
            expert_chart.GradientColors = 0xAA4375DB;
            expert_chart.GradientColorsEnd = 0x88090E11;
            expert_chart.lineSeriesColor = 0xFF279B27;
            expert_chart.strokeThickness = 1f;
            expert_chart.antiAliasing = true;

            expert_chart.tickStyle = 0xFFFFFFFF;
            expert_chart.gridlineStyle = 0x33FFFFFF;
            expert_chart.gridlineStyle2 = 0x0025304C;
            expert_chart.gridlineStyle3 = 0x00FFFFFF;
            expert_chart.bandStyle = 0xFF000000;
            expert_chart.setBackgroundColor = 0xFF000000;

            expert_chart.annoColor = 0x22FFFFFF;
            expert_chart.annoTextSize = 14;


            smChartManager.AddChart("expert_chart", expert_chart);
        }
    }

    public void DrawSignals() {
        if (_curSystem == null || _currentSeries == null || xAxis == null || yAxis == null)
            return;


        ArrayList<SmSignal> signalList = _curSystem.get_signalList();
        for (SmSignal sig : signalList) {
            if (sig.signalType == SmGlobal.SmSignalType.Buy) {

                //아래로 향하는 화살표
                surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex, sig.signalValue)
                        .withContent(new SmArrowView(getActivity(), expert_chart.downArrowType, expert_chart.downStrokeColor, expert_chart.downStrokeColor))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Bottom)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());

            } else {
                //위로 향하는 화살표
                surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex, sig.signalValue)
                        .withContent(new SmArrowView(getActivity(), expert_chart.upArrowType, expert_chart.upStrokeColor, expert_chart.upStrokeColor))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Top)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());

            }
        }
    }

    private void ChangeToCandleStick() {
        PriceSeries priceSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        IOhlcDataSeries<Date, Double> dataSeries = new OhlcDataSeries<>(Date.class, Double.class);
        dataSeries.append(priceSeries.getDateData(), priceSeries.getOpenData(), priceSeries.getHighData(), priceSeries.getLowData(), priceSeries.getCloseData());

        final FastCandlestickRenderableSeries rSeries = sciChartBuilder.newCandlestickSeries()
                .withStrokeUp(expert_chart.withStrokeUp)
                .withFillUpColor(expert_chart.withFillUpColor)
                .withStrokeDown(expert_chart.withStrokeDown)
                .withFillDownColor(expert_chart.withFillDownColor)
                .withDataSeries(dataSeries)
                .build();


        RenderableSeriesCollection col = surface.getRenderableSeries();
        if (col.size() > 0) {
            col.remove(0);
            col.add(rSeries);
        }
    }

    private void ChangeToMountain() {
        final PriceSeries priceData = DataManager.getInstance().getPriceDataIndu(getActivity());
        final IXyDataSeries<Date, Double> dataSeries = sciChartBuilder.newXyDataSeries(Date.class, Double.class).build();
        dataSeries.append(priceData.getDateData(), priceData.getCloseData());

        final FastMountainRenderableSeries rSeries = sciChartBuilder.newMountainSeries()
                .withDataSeries(dataSeries)
                .withStrokeStyle(expert_chart.mountSeriesColor, expert_chart.strokeThickness, expert_chart.antiAliasing)
                .withAreaFillLinearGradientColors(expert_chart.GradientColors, expert_chart.GradientColorsEnd)
                .build();

        RenderableSeriesCollection col = surface.getRenderableSeries();
        if (col.size() > 0) {
            col.remove(0);
            col.add(rSeries);
        }
    }

    private void ChangeToCloseLine() {
        final PriceSeries priceData = DataManager.getInstance().getPriceDataIndu(getActivity());
        final IXyDataSeries<Date, Double> dataSeries = sciChartBuilder.newXyDataSeries(Date.class, Double.class).build();
        dataSeries.append(priceData.getDateData(), priceData.getCloseData());

        final FastLineRenderableSeries rSeries = sciChartBuilder.newLineSeries()
                .withDataSeries(dataSeries)
                .withStrokeStyle(expert_chart.lineSeriesColor, expert_chart.strokeThickness, expert_chart.antiAliasing)
                .build();

        RenderableSeriesCollection col = surface.getRenderableSeries();
        if (col.size() > 0) {
            col.remove(0);
            col.add(rSeries);
        }
    }

    private void ChangeToOHLC() {
        PriceSeries priceSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        final IOhlcDataSeries<Date, Double> ohlcDataSeries = sciChartBuilder.newOhlcDataSeries(Date.class, Double.class).build();
        ohlcDataSeries.append(priceSeries.getDateData(), priceSeries.getOpenData(), priceSeries.getHighData(), priceSeries.getLowData(), priceSeries.getCloseData());
        final FastOhlcRenderableSeries ohlc = sciChartBuilder.newOhlcSeries()
                .withStrokeUp(expert_chart.withStrokeUp, expert_chart.strokeThickness)
                .withStrokeDown(expert_chart.withStrokeDown, expert_chart.strokeThickness)
                .withStrokeStyle(expert_chart.withStrokeStyle)
                .withDataSeries(ohlcDataSeries)
                .build();

        RenderableSeriesCollection col = surface.getRenderableSeries();
        if (col.size() > 0) {
            col.remove(0);
            col.add(ohlc);
        }
    }

    public void ChangeToType(int chartType) {

        switch (chartType) {
            case 0:
                ChangeToCandleStick();
                break;
            case 1:
                ChangeToMountain();
                break;
            case 2:
                ChangeToCloseLine();
                break;
            case 3:
                ChangeToOHLC();
                break;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.sm_chart, null);
        ButterKnife.bind(this, view);

        initModifier();
        setBackground();

        _currentSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        int size = _currentSeries.size();
        xAxis = sciChartBuilder.newCategoryDateAxis().withVisibleRange(size - 30, size).withGrowBy(0, 0.1).build();
        yAxis = sciChartBuilder.newNumericAxis().withGrowBy(0d, 0.1d).withAxisAlignment(AxisAlignment.Right).withAutoRangeMode(AutoRange.Always).build();
        setXAxis();
        setYAxis();
        IOhlcDataSeries<Date, Double> dataSeries = new OhlcDataSeries<>(Date.class, Double.class);
        dataSeries.append(_currentSeries.getDateData(), _currentSeries.getOpenData(), _currentSeries.getHighData(), _currentSeries.getLowData(), _currentSeries.getCloseData());
        CreateSignals();
        //Candle
        final FastCandlestickRenderableSeries rSeries = sciChartBuilder.newCandlestickSeries()
                .withStrokeUp(expert_chart.withStrokeUp)
                .withFillUpColor(expert_chart.fillUpColor)
                .withStrokeDown(expert_chart.withStrokeDown)
                .withFillDownColor(expert_chart.withFillDownColor)
                .withDataSeries(dataSeries)
                .build();

        setUpdateSuspender(rSeries);
        //x축 visibleRange 제한
        setVisibleRange();

        return view;
    }

    void setVisibleRange() {
        xAxis.setVisibleRangeChangeListener(new VisibleRangeChangeListener() {
            @Override
            public void onVisibleRangeChanged(IAxisCore axis, IRange oldRange, IRange newRange, boolean isAnimating) {

                // TODO handle range changes here
                if (newRange.getMinAsDouble() < 0d && newRange.getMaxAsDouble() > (double) _currentSeries.size()) {
                    DoubleRange visibleRange = new DoubleRange(0d, (double) _currentSeries.size() - 1);
                    xAxis.setVisibleRange(visibleRange);
                } else if (newRange.getMinAsDouble() < 0d) {
                    DoubleRange visibleRange = new DoubleRange(0d, newRange.getMaxAsDouble());
                    xAxis.setVisibleRange(visibleRange);
                } else if (newRange.getMaxAsDouble() >= (double) _currentSeries.size()) {
                    DoubleRange visibleRange = new DoubleRange(newRange.getMinAsDouble(), (double) _currentSeries.size() - 1);
                    xAxis.setVisibleRange(visibleRange);
                }
            }
        });
    }

    void setXAxis() {

        PenStyle tickStyle = new SolidPenStyle(expert_chart.tickStyle, true, 2, null);
        PenStyle gridlineStyle3 = new SolidPenStyle(expert_chart.gridlineStyle3, true, 1, null);
        PenStyle gridlineStyle2 = new SolidPenStyle(expert_chart.gridlineStyle2, true, 2, null);
        BrushStyle bandStyle = new SolidBrushStyle(expert_chart.bandStyle);

        xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        xAxis.setDrawMajorBands(true);
        xAxis.setAxisBandsStyle(bandStyle);
        xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        xAxis.setMajorGridLineStyle(gridlineStyle3);
        xAxis.setMinorGridLineStyle(gridlineStyle2);
    }

    void setYAxis() {
        PenStyle tickStyle = new SolidPenStyle(expert_chart.tickStyle, true, 2, null);
        BrushStyle bandStyle = new SolidBrushStyle(expert_chart.bandStyle);
        PenStyle gridlineStyle2 = new SolidPenStyle(expert_chart.gridlineStyle2, true, 2, null);
        PenStyle gridlineStyle = new SolidPenStyle(expert_chart.gridlineStyle, true, 1, new float[]{10, 10});


        yAxis.setAxisBandsStyle(bandStyle);
        yAxis.setDrawMajorBands(true);
        yAxis.setMinorTickLineStyle(tickStyle);
        yAxis.setMajorTickLineStyle(tickStyle);


        yAxis.setMajorGridLineStyle(gridlineStyle);
        yAxis.setMinorGridLineStyle(gridlineStyle2);
    }

    void setBackground() {
        surface.setBackgroundColor(expert_chart.setBackgroundColor);
    }


    void setUpdateSuspender(final FastCandlestickRenderableSeries rSeries) {
        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {


                Collections.addAll(surface.getXAxes(), xAxis);
                Collections.addAll(surface.getYAxes(), yAxis);
                Collections.addAll(surface.getRenderableSeries(), rSeries);
                DrawSignals();

                // Watermark
                t_annotation = sciChartBuilder.newTextAnnotation()
                        .withPosition(0.2, 0.85)
                        .withFontStyle(Typeface.DEFAULT_BOLD, 14, 0x22FFFFFF)
                        .withBackgroundDrawableId(R.drawable.smmobile_text_annotation_background)
                        .withCoordinateMode(AnnotationCoordinateMode.Relative)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        //.withVerticalAnchorPoint(VerticalAnchorPoint.Center)
                        .withText("설정")
                        .withPadding(6, 8, 6, 4)
                        //.withTextGravity(Gravity.CENTER)
                        .build();

                SmSetting smSetting = new SmSetting();
                smSetting.set_x(0.2);
                smSetting.set_y(0.85);
                smSetting.set_tannotation(t_annotation);
                smSetting.setCoorMode(0);
                _settingList.add(smSetting);

                j_annotation = sciChartBuilder.newTextAnnotation()
                        .withPosition(0.4, 0.85)
                        .withFontStyle(Typeface.DEFAULT_BOLD, 14, 0x22FFFFFF)
                        .withBackgroundDrawableId(R.drawable.smmobile_text_annotation_background)
                        .withCoordinateMode(AnnotationCoordinateMode.Relative)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        //.withVerticalAnchorPoint(VerticalAnchorPoint.Center)
                        .withText("지표")
                        .withPadding(6, 8, 6, 4)
                        //.withTextGravity(Gravity.CENTER)
                        .build();

                SmJeePyo smJeePyo = new SmJeePyo();
                smJeePyo.set_x(0.4);
                smJeePyo.set_y(0.85);
                smJeePyo.set_jannotation(j_annotation);
                smJeePyo.setCoorMode(0);
                _jeepyoList.add(smJeePyo);

                d_annotation = sciChartBuilder.newTextAnnotation()
                        .withPosition(0.15, 0.1)
                        .withFontStyle(Typeface.DEFAULT_BOLD, 14, 0x22FFFFFF)
                        .withBackgroundDrawableId(R.drawable.smmobile_text_annotation_background)
                        .withCoordinateMode(AnnotationCoordinateMode.Relative)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        //.withVerticalAnchorPoint(VerticalAnchorPoint.Center)
                        .withText("delete")
                        .withPadding(6, 8, 6, 4)
                        //.withTextGravity(Gravity.CENTER)
                        .build();

                SmDeleteAnno smDeleteAnno = new SmDeleteAnno();
                smDeleteAnno.set_x(0.15);
                smDeleteAnno.set_y(0.1);
                smDeleteAnno.set_dannotation(d_annotation);
                smDeleteAnno.setCoorMode(0);
                _deleteList.add(smDeleteAnno);

                c_annotation = sciChartBuilder.newTextAnnotation()
                        .withPosition(0.4, 0.1)
                        .withFontStyle(Typeface.DEFAULT_BOLD, 14, 0x22FFFFFF)
                        .withBackgroundDrawableId(R.drawable.smmobile_text_annotation_background)
                        .withCoordinateMode(AnnotationCoordinateMode.Relative)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        //.withVerticalAnchorPoint(VerticalAnchorPoint.Center)
                        .withText("create")
                        .withPadding(6, 8, 6, 4)
                        //.withTextGravity(Gravity.CENTER)
                        .build();

                SmCreateAnno smCreateAnno = new SmCreateAnno();
                smCreateAnno.set_x(0.4);
                smCreateAnno.set_y(0.1);
                smCreateAnno.set_cannotation(c_annotation);
                smCreateAnno.setCoorMode(0);
                _createList.add(smCreateAnno);


                if (t_annotation != null)
                    Collections.addAll(surface.getAnnotations(), t_annotation);
                if (j_annotation != null)
                    Collections.addAll(surface.getAnnotations(), j_annotation);
                if (d_annotation != null)
                    Collections.addAll(surface.getAnnotations(), d_annotation);
                if (c_annotation != null)
                    Collections.addAll(surface.getAnnotations(), c_annotation);

                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());
                sciChartBuilder.newAnimator(rSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
            }
        });
    }

    void initModifier() {
        //final HorizontalLineAnnotation horizontalLineAnnotation = new HorizontalLineAnnotation(view.getContext());
        //createAnnotation click event -> 따로 클래스로 빼야한다.
        _touchModifier = new ChartTouchModifier();

        /*_touchModifier.set_chartFragment(this);
        surface.getChartModifiers().add(_touchModifier);

        _touchSetting = new ChartSetting();
        _touchSetting.set_chartFragment(this);
        surface.getChartModifiers().add(_touchSetting);

        _touchJeepyo = new SmJeepyoTouch();
        _touchJeepyo.set_chartFragment(this);
        surface.getChartModifiers().add(_touchJeepyo);

        _touchDelete = new SmDeleteTouch();
        _touchDelete.set_chartFragment(this);
        surface.getChartModifiers().add(_touchDelete);

        _touchCreate = new SmCreateTouch();
        _touchCreate.set_chartFragment(this);
        surface.getChartModifiers().add(_touchCreate);*/
    }


    void CreateSignals() {
        if (_currentSeries == null || _curSystem == null)
            return;
    }

    @Override
    public void onAnnotationCreated(@NonNull IAnnotation newAnnotation) {
        newAnnotation.setIsEditable(true);
    }
}



