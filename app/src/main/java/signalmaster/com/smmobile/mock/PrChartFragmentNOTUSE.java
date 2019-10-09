package signalmaster.com.smmobile.mock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.scichart.charting.model.AnnotationCollection;
import com.scichart.charting.model.RenderableSeriesCollection;
import com.scichart.charting.model.dataSeries.IOhlcDataSeries;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.model.dataSeries.OhlcDataSeries;
import com.scichart.charting.modifiers.AnnotationCreationModifier;
import com.scichart.charting.modifiers.OnAnnotationCreatedListener;
import com.scichart.charting.visuals.SciChartSurface;
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
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import signalmaster.com.smmobile.annotation.ChartSetting;
import signalmaster.com.smmobile.annotation.ChartTouchModifier;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.annotation.SmArrowView;
import signalmaster.com.smmobile.chart.SmChart;
import signalmaster.com.smmobile.SmChartConfig;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.annotation.SmCreateAnno;
import signalmaster.com.smmobile.annotation.SmCreateTouch;
import signalmaster.com.smmobile.annotation.SmDeleteAnno;
import signalmaster.com.smmobile.annotation.SmDeleteTouch;
import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.annotation.SmJeePyo;
import signalmaster.com.smmobile.annotation.SmJeepyoTouch;
import signalmaster.com.smmobile.SmOption;
import signalmaster.com.smmobile.annotation.SmSetting;
import signalmaster.com.smmobile.Signal.SmSignal;
import signalmaster.com.smmobile.system.SmSystem;
import signalmaster.com.smmobile.chart.SmChartManager;
import signalmaster.com.smmobile.data.DataManager;
import signalmaster.com.smmobile.data.PriceSeries;

public class PrChartFragmentNOTUSE extends Fragment implements OnAnnotationCreatedListener {

    private static final String VOLUME = "Volume";

    private final static String Y_LEFT_AXIS = "yRightAxis";
    public HorizontalLineAnnotation annotation = null;

    TextAnnotation t_annotation = null;
    TextAnnotation j_annotation = null;
    TextAnnotation d_annotation = null;
    TextAnnotation c_annotation = null;

    HashMap<String, TextAnnotation> _AnnotationMap = new HashMap<>();

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
    private SmOption _touchOption = null;

    private SmChartConfig smChartConfig = null;
    //그리기
    private final AnnotationCreationModifier annotationCreationModifier = new AnnotationCreationModifier();

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

    public  void UpdateChart() {

    }

    private SmChart mock_main = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateSystem();
    }

    public PrChartFragmentNOTUSE() {
        SmChartManager smChartManager = SmChartManager.getInstance();


        mock_main = smChartManager.getChart("mock_main");
        if (mock_main == null) {
            mock_main = new SmChart();

            mock_main.upStrokeColor = 0xffff0000;
            mock_main.downStrokeColor = 0xff0000ff;
            mock_main.upArrowType = 0;
            mock_main.downArrowType = 1;
            mock_main.fillUpColor = 0x88FF0000;
            mock_main.fillDownColor = 0x880000FF;
            mock_main.fillcolor = 0xFF0000ff;
            mock_main.withStrokeUp = 0xFFFF0000;
            mock_main.withFillUpColor = 0x88FF0000;
            mock_main.withStrokeDown = 0xFF0000FF;
            mock_main.withFillDownColor = 0x880000FF;
            mock_main.withStrokeStyle = 0xFF00AA00;
            mock_main.mountSeriesColor = 0xAAAFE1FF;
            mock_main.GradientColors = 0xAA4375DB;
            mock_main.GradientColorsEnd = 0x88090E11;
            mock_main.lineSeriesColor = 0xFF279B27;
            mock_main.strokeThickness = 1f;
            mock_main.antiAliasing = true;

            //가로세로  눈금
            mock_main.tickStyle = 0x44FFFFFF;

            //실선
            mock_main.gridlineStyle = 0x33FFFFFF;

            mock_main.gridlineStyle2 = 0x0025304C;
            mock_main.gridlineStyle3 = 0x00FFFFFF;


            //background
            mock_main.setBackgroundColor = 0xFF1E1E1E;
            mock_main.bandStyle = 0xFF1E1E1E;

            mock_main.annoColor = 0x22FFFFFF;
            mock_main.annoTextSize = 14;


            smChartManager.AddChart("mock_main", mock_main);
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
                        .withContent(new SmArrowView(getActivity(), mock_main.downArrowType, mock_main.downStrokeColor, mock_main.downStrokeColor))
                        .withVerticalAnchorPoint(VerticalAnchorPoint.Bottom)
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());
                /*surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex, sig.signalValue)
                        .withContent(new SChartFragment.CustomView1(getActivity()))
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());*/
            } else {
                //위로 향하는 화살표
                surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        .withPosition(sig.signalIndex, sig.signalValue)
                        .withContent(new SmArrowView(getActivity(), mock_main.upArrowType, mock_main.upStrokeColor, mock_main.upStrokeColor))
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

    private void ChangeToCandleStick() {
        PriceSeries priceSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        IOhlcDataSeries<Date, Double> dataSeries = new OhlcDataSeries<>(Date.class, Double.class);
        dataSeries.append(priceSeries.getDateData(), priceSeries.getOpenData(), priceSeries.getHighData(), priceSeries.getLowData(), priceSeries.getCloseData());

        final FastCandlestickRenderableSeries rSeries = sciChartBuilder.newCandlestickSeries()
                .withStrokeUp(mock_main.withStrokeUp)
                .withFillUpColor(mock_main.withFillUpColor)
                .withStrokeDown(mock_main.withStrokeDown)
                .withFillDownColor(mock_main.withFillDownColor)
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
                .withStrokeStyle(mock_main.mountSeriesColor, mock_main.strokeThickness, mock_main.antiAliasing)
                .withAreaFillLinearGradientColors(mock_main.GradientColors, mock_main.GradientColorsEnd)
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
                .withStrokeStyle(mock_main.lineSeriesColor, mock_main.strokeThickness, mock_main.antiAliasing)
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
                .withStrokeUp(mock_main.withStrokeUp, mock_main.strokeThickness)
                .withStrokeDown(mock_main.withStrokeDown, mock_main.strokeThickness)
                .withStrokeStyle(mock_main.withStrokeStyle)
                .withDataSeries(ohlcDataSeries)
                .build();

        RenderableSeriesCollection col = surface.getRenderableSeries();
        if (col.size() > 0) {
            col.remove(0);
            col.add(ohlc);
        }
    }

    public void ChangeToType(int chartType) {
        /*if (chartType == 0) {
            ChangeToMountain();
        }*/
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

        //마운틴
        dataSeries.append(_currentSeries.getDateData(), _currentSeries.getOpenData(), _currentSeries.getHighData(), _currentSeries.getLowData(), _currentSeries.getCloseData());
        final FastMountainRenderableSeries rSeries = sciChartBuilder.newMountainSeries()
                .withDataSeries(dataSeries)
                .withStrokeStyle(mock_main.mountSeriesColor, mock_main.strokeThickness, mock_main.antiAliasing)
                .withAreaFillLinearGradientColors(mock_main.GradientColors, mock_main.GradientColorsEnd)
                .build();

        //캔들스틱
        /*dataSeries.append(_currentSeries.getDateData(), _currentSeries.getOpenData(), _currentSeries.getHighData(), _currentSeries.getLowData(), _currentSeries.getCloseData());
        CreateSignals();
        //Candle
        final FastCandlestickRenderableSeries rSeries = sciChartBuilder.newCandlestickSeries()
                .withStrokeUp(mock_main.withStrokeUp)
                .withFillUpColor(mock_main.fillUpColor)
                .withStrokeDown(mock_main.withStrokeDown)
                .withFillDownColor(mock_main.withFillDownColor)
                .withDataSeries(dataSeries)
                .build();*/

        setUpdateSuspender(rSeries);
        //x축 visibleRange 제한
        setVisibleRange();


        //Create Button
       /* Button crtBtn = (Button) view.findViewById(R.id.createAnnotation);
        crtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                annotation = sciChartBuilder.newHorizontalLineAnnotation()
                        .withPosition(1, 12000)
                        .withStroke(2, ColorUtil.Orange)
                        //수치를 나타내줌
                        .withAnnotationLabel(LabelPlacement.Axis)
                        //.withIsEditable(true)
                        .build();

                SmOrder smOrder = new SmOrder();
                smOrder.set_annotation(annotation);
                smOrder.set_x(1);
                smOrder.set_value(12000);

                _orderList.add(smOrder);

                Collections.addAll(surface.getAnnotations(), annotation);

               *//* /////어노테이션
                Collections.addAll(surface.getAnnotations(),
                        sciChartBuilder.newHorizontalLineAnnotation()
                                //position 값 맞지않음
                                .withPosition(1,12000)
                                .withStroke(2, ColorUtil.Orange)
                                .withAnnotationLabel(LabelPlacement.Axis)
                                .withIsEditable(true)
                                .build());*//*

                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());
            }
        });*/


        //DrawSignals();


        //차트 타입설정
        /*SmChart chart_config;
        if (chart_config.get_chartType() == 0) {
            ChangeToCandleStick();
        }*/


        //화면이동
        //surface.getChartModifiers().add(sciChartBuilder.newModifierGroupWithDefaultModifiers().build());

        //annotationCreationModifier.setAnnotationCreationListener(this);
        //화면 그리기
        //surface.getChartModifiers().add(sciChartBuilder.newModifierGroup().withModifier(annotationCreationModifier).build());
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

        PenStyle tickStyle = new SolidPenStyle(mock_main.tickStyle, true, 2, null);
        PenStyle gridlineStyle3 = new SolidPenStyle(mock_main.gridlineStyle3, true, 1, null);
        PenStyle gridlineStyle2 = new SolidPenStyle(mock_main.gridlineStyle2, true, 2, null);
        BrushStyle bandStyle = new SolidBrushStyle(mock_main.bandStyle);
        PenStyle gridlineStyle = new SolidPenStyle(mock_main.gridlineStyle, true, 1, null);

        xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        xAxis.setDrawMajorBands(true);
        xAxis.setAxisBandsStyle(bandStyle);
        xAxis.setMinorTickLineStyle(tickStyle);
        xAxis.setMajorTickLineStyle(tickStyle);
        //MajoGrid -> 큰 grid
        xAxis.setMajorGridLineStyle(gridlineStyle);
        xAxis.setMinorGridLineStyle(gridlineStyle2);
    }

    void setYAxis() {
        PenStyle tickStyle = new SolidPenStyle(mock_main.tickStyle, true, 2, null);
        BrushStyle bandStyle = new SolidBrushStyle(mock_main.bandStyle);
        PenStyle gridlineStyle2 = new SolidPenStyle(mock_main.gridlineStyle2, true, 2, null);
        //PenStyle gridlineStyle = new SolidPenStyle(mock_main.gridlineStyle, true, 1, new float[]{10, 10});
        PenStyle gridlineStyle = new SolidPenStyle(mock_main.gridlineStyle, true, 1, null);

        yAxis.setAxisBandsStyle(bandStyle);
        yAxis.setDrawMajorBands(true);
        yAxis.setMinorTickLineStyle(tickStyle);
        yAxis.setMajorTickLineStyle(tickStyle);

        //MajoGrid -> 큰 grid
        yAxis.setMajorGridLineStyle(gridlineStyle);
        yAxis.setMinorGridLineStyle(gridlineStyle2);
    }

    void setBackground() {
        surface.setBackgroundColor(mock_main.setBackgroundColor);
    }

    void setChartData() {

    }

    void setUpdateSuspender(final FastMountainRenderableSeries rSeries) {
        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {

                /*annotationTypeSelector.setOnItemClickListener(new ItemSelectedListenerBase() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        annotationCreationModifier.setAnnotationType(position);
                    }
                });*/

                Collections.addAll(surface.getXAxes(), xAxis);
                Collections.addAll(surface.getYAxes(), yAxis);
                Collections.addAll(surface.getRenderableSeries(), rSeries);
                DrawSignals();

                // Watermark
                /*t_annotation = sciChartBuilder.newTextAnnotation()
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
                        .build();*/

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

                //커스텀 어노테이션
                /*surface.getAnnotations().add(sciChartBuilder.newCustomAnnotation()
                        //.withPosition(0, 11500,1499,11500)
                        .withPosition(23,12050)
                        .withContent(new PrChartFragmentNOTUSE.CustomLineAnnotation(getActivity()))
                        .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                        .build());*/

                // Create and configure a HorizontalLineAnnotation
                //클래스 어노테이션
                /*HorizontalLineAnnotation horizontalLineAnnotation = new SmOrderAnnotation(getActivity());
                horizontalLineAnnotation.setX1(-100);
                horizontalLineAnnotation.setY1(12000);
                horizontalLineAnnotation.setStroke(new SolidPenStyle(ColorUtil.Orange, false, 2.0f, null));
                horizontalLineAnnotation.setHorizontalGravity(Gravity.RIGHT);*/

// Add the annotation to the AnnotationsCollection of a surface

                //annotationCreationModifier.setAnnotationFactory(annotationFactory);
               /* ArrayList<SmSignal> signalList = _curSystem.get_signalList();
                for(SmSignal sig : signalList) {

                    surface.getAnnotations().add(sciChartBuilder.newHorizontalLineAnnotation()
                            .withPosition(sig.signalIndex, sig.signalValue)
                            .withStroke(2, ColorUtil.Orange)
                            .withAnnotationLabel(LabelPlacement.Axis)
                            .build());
                }*/

                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());
                sciChartBuilder.newAnimator(rSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(0).withStartDelay(0).start();
            }
        });
    }

    void initModifier() {
        //final HorizontalLineAnnotation horizontalLineAnnotation = new HorizontalLineAnnotation(view.getContext());
        //createAnnotation click event -> 따로 클래스로 빼야한다.
        /*_touchModifier = new ChartTouchModifier();
        _touchModifier.set_chartFragment(this);
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


    //x
    public static class CustomLineAnnotation extends View {

        //private final int FILL_COLOR = Color.parseColor("#FF32CD32");
        private final int STROKE_COLOR = Color.parseColor("#FF32CD32");


        private final Path path = new Path();
        private final Paint paintFill = new Paint();
        private final Paint paintStroke = new Paint();


        public CustomLineAnnotation(Context context) {
            super(context);
            paintFill.setStyle(Paint.Style.FILL);
            // paintFill.setColor(FILL_COLOR);
            paintStroke.setStyle(Paint.Style.STROKE);
            paintStroke.setColor(STROKE_COLOR);

            //this.setLayoutParams(new ViewGroup.LayoutParams(getWidth(), getHeight()));
            int garo = 50;
            int sero = 80;

            /*path.moveTo(0, 0);
            path.lineTo(sero/2, 0);
            path.lineTo(sero/2,garo);
            path.lineTo(sero*2,garo);
            path.lineTo(sero*2,0);
            path.lineTo(sero/2,0);
            path.lineTo(sero/2,garo/2);
            path.moveTo(sero*2,garo/2);
            path.lineTo(sero*10,garo/2);*/

            path.moveTo(0, garo);
            path.lineTo(sero, garo);
            path.lineTo(sero, garo * 2);
            path.lineTo(sero * 4, garo * 2);
            path.lineTo(sero * 4, 0);
            path.lineTo(sero, 0);
            path.lineTo(sero, garo);
            path.moveTo(sero * 4, garo);
            path.lineTo(sero * 20, garo);
            // path.lineTo(80,15);

            int longWidth = 20;
            int shortWidth = 10;
            int header = 20;
            int tail = 20;

            /*path.moveTo(shortWidth/2, 0);
            path.lineTo(shortWidth/2, tail);
            path.lineTo(longWidth/2, tail);
            path.lineTo(0, header+tail);
            path.lineTo(-longWidth/2, tail);
            path.lineTo(-shortWidth/2, tail);
            path.lineTo(-shortWidth/2, 0);
            path.lineTo(shortWidth/2, 0);*/

            setMinimumHeight(200);
            setMinimumWidth(1500);


        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, paintFill);
            canvas.drawPath(path, paintStroke);
        }
    }

    /*//삭제
    @OnClick(R.id.deleteAnnotation)*/
    void deleteSelectedAnnotation() {
        final AnnotationCollection annotations = surface.getAnnotations();
        for (int i = annotations.size() - 1; i >= 0; i--) {
            final IAnnotation annotation = annotations.get(i);
            if (annotation.isSelected())
                annotations.remove(i);
        }
    }



    /*@OnClick(R.id.createAnnotation)
    void createAnnotation() {

    }*/

    @Override
    public void onAnnotationCreated(@NonNull IAnnotation newAnnotation) {
        newAnnotation.setIsEditable(true);
    }
}





    /*public static class CustomView1 extends View {


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
            path.lineTo(15, 20);
            path.lineTo(30, 15);
            path.lineTo(20, 15);
            path.lineTo(20, 30);
            path.lineTo(10, 30);
            path.lineTo(10, 15);
            path.lineTo(0, 15);

            *//*path.moveTo(shortWidth/2, 0);
            path.lineTo(shortWidth/2, tail);
            path.lineTo(longWidth/2, tail);
            path.lineTo(0, header+tail);
            path.lineTo(-longWidth/2, tail);
            path.lineTo(-shortWidth/2, tail);
            path.lineTo(-shortWidth/2, 0);
            path.lineTo(shortWidth/2, 0);*//*

            //아래화살표
            *//*path.moveTo(0, 0);
            path.lineTo(10, 0);
            path.lineTo(10, -15);
            path.lineTo(20, -15);
            path.lineTo(20, 0);
            path.lineTo(30, 0);
            path.lineTo(15, 15);
            path.lineTo(0, 0);*//*
 *//*path.lineTo(10, 30);
            path.lineTo(10, 15);
            path.lineTo(0, 0);*//*

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

            *//*path.moveTo(shortWidth/2, 0);
            path.lineTo(shortWidth/2, tail);
            path.lineTo(longWidth/2, tail);
            path.lineTo(0, header+tail);
            path.lineTo(-longWidth/2, tail);
            path.lineTo(-shortWidth/2, tail);
            path.lineTo(-shortWidth/2, 0);
            path.lineTo(shortWidth/2, 0);*//*

            setMinimumHeight(50);
            setMinimumWidth(50);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, paintFill);
            canvas.drawPath(path, paintStroke);
        }
    }*/

