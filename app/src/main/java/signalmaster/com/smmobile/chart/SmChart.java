package signalmaster.com.smmobile.chart;


//import com.example.smmobile.SmChartContainer;


import android.support.v4.app.Fragment;

import java.util.ArrayList;

import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.SmOption;
import signalmaster.com.smmobile.annotation.ChartSetting;
import signalmaster.com.smmobile.annotation.ChartTouchModifier;
import signalmaster.com.smmobile.annotation.SmCreateAnno;
import signalmaster.com.smmobile.annotation.SmCreateTouch;
import signalmaster.com.smmobile.annotation.SmDeleteAnno;
import signalmaster.com.smmobile.annotation.SmDeleteTouch;
import signalmaster.com.smmobile.annotation.SmJeePyo;
import signalmaster.com.smmobile.annotation.SmJeepyoTouch;
import signalmaster.com.smmobile.annotation.SmSetting;

public class SmChart {
    //series 0 : candlestick , 1 : mountain, 2 : line;
    public int mainSeriesType = 0;

    public class SmYAxisSettings {
        int yAxisWidth = 2;
        int yAxisColor = 678;
    }

    public void Copy(SmChart target) {
        target.upStrokeColor = this.upStrokeColor;
    }

    private Fragment _ParentChartFragment = null;
    private ArrayList<SmSetting> _settingList = new ArrayList<>();
    private ArrayList<SmJeePyo> _jeepyoList = new ArrayList<>();
    private ArrayList<SmDeleteAnno> _deleteList = new ArrayList<>();
    private ArrayList<SmCreateAnno> _createList = new ArrayList<>();
    private ArrayList<SmOption> _optionlist = new ArrayList<>();


    public ArrayList<SmOption> get_optionlist() {
        return _optionlist;
    }

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


    public int upStrokeColor = (0xffff0000);
    public int downStrokeColor = (0xff0000ff);
    public int upArrowType = 0;
    public int downArrowType = 1;


    public int fillUpColor = (0x88FF0000);
    public int fillDownColor = (0x880000FF);

    public int fillcolor = (0xFF0000ff);

    public int withStrokeUp = (0xFFFF0000);
    public int withFillUpColor = (0x88FF0000);
    public int withStrokeDown = (0xFF0000FF);
    public int withFillDownColor = (0x880000FF);
    public int withStrokeStyle = (0xFF00AA00);
    //strokeStyle
    public int mountSeriesColor = (0xAAFFC9A8);
    //withAreaFillLinearGradientColors
    public int GradientColors = (0xAAFF8D42);
    public int CurrentLineColors = (0xFF259CF6);
    public int GradientColorsEnd = (0x88090E11);
    public int lineSeriesColor = (0xFF279B27);

    public float strokeThickness = 1f;
    public boolean antiAliasing = true;




    public int tickStyle = (0xFFFFFFFF);
    public int gridlineStyle = (0x33FFFFFF);
    public int gridlineStyle2 = (0x0025304C);
    public int gridlineStyle3 = (0x00FFFFFF);
    public int bandStyle = (0xFF000000);

    public int setBackgroundColor = (0xFF1E1E1E);


    //annotation color, textSize
    public int annoColor = (0x22FFFFFF);
    public int annoTextSize = 14;


    private int _width = 0;
    private int _height = 0;
    private SmChartData _chartData = null;

    //x
    private int _majorGridLineColorX = (0xFF000000);
    private int _minorGridLineColorX = (0xFF000000);

    private int _majorTickColorX = (0xFFFFFFFF);
    private int _minorTickColorX = (0xFFFFFFFF);

    private int _majorGridFirstColorX = (0xFF000000);

    private int _minorGridFirstColorX = (0xFF000000);


    private int _majorGridSecondColorX = (0xFF000000);

    private int _minorGridSecondColorX = (0xFF000000);


    // y
    private int _majorGridLineColorY = (0xFF000000);

    private int _minorGridLineColorY = (0xFF000000);


    private int _majorTickColorY = (0xFFFFFFFF);

    private int _minorTickColorY = (0xFFFFFFFF);


    private int _majorGridFirstColorY = (0xFF000000);

    private int _minorGridFirstColorY = (0xFF000000);


    private int _majorGridSecondColorY = (0xFF000000);

    private int _minorGridSecondColorY = (0xFF000000);


    //color
    //private int _strokeUp

    public SmChartData get_chartData() {
        return _chartData;
    }

    public void set_chartData(SmChartData _chartData) {
        this._chartData = _chartData;
    }

    public int get_majorGridLineColorX() {
        return _majorGridLineColorX;
    }

    public void set_majorGridLineColorX(int _majorGridLineColorX) {
        this._majorGridLineColorX = _majorGridLineColorX;
    }

    public int get_minorGridLineColorX() {
        return _minorGridLineColorX;
    }

    public void set_minorGridLineColorX(int _minorGridLineColorX) {
        this._minorGridLineColorX = _minorGridLineColorX;
    }

    public int get_majorTickColorX() {
        return _majorTickColorX;
    }

    public void set_majorTickColorX(int _majorTickColorX) {
        this._majorTickColorX = _majorTickColorX;
    }

    public int get_minorTickColorX() {
        return _minorTickColorX;
    }

    public void set_minorTickColorX(int _minorTickColorX) {
        this._minorTickColorX = _minorTickColorX;
    }

    public int get_majorGridFirstColorX() {
        return _majorGridFirstColorX;
    }

    public void set_majorGridFirstColorX(int _majorGridFirstColorX) {
        this._majorGridFirstColorX = _majorGridFirstColorX;
    }

    public int get_minorGridFirstColorX() {
        return _minorGridFirstColorX;
    }

    public void set_minorGridFirstColorX(int _minorGridFirstColorX) {
        this._minorGridFirstColorX = _minorGridFirstColorX;
    }

    public int get_majorGridSecondColorX() {
        return _majorGridSecondColorX;
    }

    public void set_majorGridSecondColorX(int _majorGridSecondColorX) {
        this._majorGridSecondColorX = _majorGridSecondColorX;
    }

    public int get_minorGridSecondColorX() {
        return _minorGridSecondColorX;
    }

    public void set_minorGridSecondColorX(int _minorGridSecondColorX) {
        this._minorGridSecondColorX = _minorGridSecondColorX;
    }

    public int get_majorGridLineColorY() {
        return _majorGridLineColorY;
    }

    public void set_majorGridLineColorY(int _majorGridLineColorY) {
        this._majorGridLineColorY = _majorGridLineColorY;
    }

    public int get_minorGridLineColorY() {
        return _minorGridLineColorY;
    }

    public void set_minorGridLineColorY(int _minorGridLineColorY) {
        this._minorGridLineColorY = _minorGridLineColorY;
    }

    public int get_majorTickColorY() {
        return _majorTickColorY;
    }

    public void set_majorTickColorY(int _majorTickColorY) {
        this._majorTickColorY = _majorTickColorY;
    }

    public int get_minorTickColorY() {
        return _minorTickColorY;
    }

    public void set_minorTickColorY(int _minorTickColorY) {
        this._minorTickColorY = _minorTickColorY;
    }

    public int get_majorGridFirstColorY() {
        return _majorGridFirstColorY;
    }

    public void set_majorGridFirstColorY(int _majorGridFirstColorY) {
        this._majorGridFirstColorY = _majorGridFirstColorY;
    }

    public int get_minorGridFirstColorY() {
        return _minorGridFirstColorY;
    }

    public void set_minorGridFirstColorY(int _minorGridFirstColorY) {
        this._minorGridFirstColorY = _minorGridFirstColorY;
    }

    public int get_majorGridSecondColorY() {
        return _majorGridSecondColorY;
    }

    public void set_majorGridSecondColorY(int _majorGridSecondColorY) {
        this._majorGridSecondColorY = _majorGridSecondColorY;
    }

    public int get_minorGridSecondColorY() {
        return _minorGridSecondColorY;
    }

    public void set_minorGridSecondColorY(int _minorGridSecondColorY) {
        this._minorGridSecondColorY = _minorGridSecondColorY;
    }


    //==========================
    /*public SmChart() {
    }

    final SmChartContainer ParentView() {
        return ParentView();
    }
    public void ParentView(SmChartContainer val) {

    }

    int CtrlHeight(){
        return CtrlHeight();
    }


    //HdChartFrm* ChartFrm() const { return _ChartFrm; }
    //void ChartFrm(HdChartFrm* val) { _ChartFrm = val; }

    private int CtrlHeight;
    private String ChartDataKey;
    private String RealtimeDataKey;

    public int getCtrlHeight() {
        return CtrlHeight;
    }

    public void setCtrlHeight(int ctrlHeight) {
        CtrlHeight = ctrlHeight;
    }

    public String getChartDataKey() {
        return ChartDataKey;
    }

    public void setChartDataKey(String chartDataKey) {
        ChartDataKey = chartDataKey;
    }

    public String getRealtimeDataKey() {
        return RealtimeDataKey;
    }

    public void setRealtimeDataKey(String realtimeDataKey) {
        RealtimeDataKey = realtimeDataKey;
    }

    //final VtAttachDirection AttachDirection()  { return _AttachDirection; }
    //void AttachDirection(VtAttachDirection val) { _AttachDirection = val; }

    void ShowChartCtrl(){

    }
    void ShowChartCtrl(boolean flag){

    }
    final boolean Active()  {
        return Active();
    }

    void Active(boolean val) {
        val=false;
    }

    private String SelectedDataSetName;

    public String getSelectedDataSetName() {
        return SelectedDataSetName;
    }

    public void setSelectedDataSetName(String selectedDataSetName) {
        SelectedDataSetName = selectedDataSetName;
    }

    final SmSystem System() {
        return System();
    }
    void System(SmSystem val) {
        val= null;
    }*/

}
