package signalmaster.com.smmobile.annotation;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.scichart.charting.modifiers.TouchModifierBase;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.charting.visuals.axes.AxisAlignment;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.core.utility.touch.ModifierTouchEventArgs;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmArgument;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.SmSettingScreenActivity;

public class ChartSetting extends TouchModifierBase {
    private AxisAlignment yAlign = null;
    private IAxis yAxis = null;

    private SmChartFragment _chartFragment = null;
    private SmSetting _selSetting = null;
    private TextAnnotation _selectedAnnotation = null;

    public void set_chartFragment(SmChartFragment _chartFragment){
        this._chartFragment = _chartFragment;
    }


    private TextAnnotation hitTest(double x, double y){
       /* Log.d(TAG,"texthit");
        if(_chartFragment == null)
            return null;
        ArrayList<SmSetting> _settingList = _chartFragment.getSettingList();
        if(_settingList.size() == 0)
            return null;

        for(SmSetting setting : _settingList){
            double xvalue = setting.get_x();
            double yvalue = setting.get_y();
            TextAnnotation anno = setting.get_tannotation();
            if(anno == null)
                continue;
            double x_pos  = -100;
            double y_pos  = -100;
            *//*if (setting.getCoorMode() == 0) {
                x_pos = anno.getCoordinateMode();
            }*//*
           // anno.setCoordinateMode(AnnotationCoordinateMode.Absolute);
            //값 변환
            xvalue = getModifierSurface().getWidth()*xvalue;
            yvalue = getModifierSurface().getHeight()*yvalue;
            *//*x_pos = anno.getXAxis().getCurrentCoordinateCalculator().getCoordinate(xvalue);
            y_pos = anno.getYAxis().getCurrentCoordinateCalculator().getCoordinate(yvalue);*//*
            double xmin = x - 50 , xmax = x + 50 ;
            double ymin = y - 100 , ymax = y + 20 ;
            //anno.setCoordinateMode(AnnotationCoordinateMode.Relative);
            *//*PointF co = new PointF((float) x, (float) y);
            getModifierSurface().translatePoint(co,getModifierSurface());
            getPointRelativeTo(co, getModifierSurface());*//*

            if(yvalue >= ymin && yvalue <= ymax && xvalue >= xmin && xvalue <= xmax ){
                anno.setSelected(true);
                _selSetting = setting;
                return anno;
            }
        }*/
        return null;
    }

    @Override
    protected boolean onTouchDown(ModifierTouchEventArgs args) {
        final double x = args.e.getX();
        final double y = args.e.getY();

        _selectedAnnotation = hitTest(x, y);

        return true;
    }

    @Override
    protected boolean onTouchUp(ModifierTouchEventArgs args) {

        if (_selectedAnnotation != null) {
            final float x = args.e.getX();
            final float y = args.e.getY();

            Intent intent = new Intent(MainActivity.context, SmSettingScreenActivity.class);
            yAxis = _chartFragment.getyAxis();
            //yAlign = _chartFragment.yAxis.getAxisAlignment();
            intent.putExtra("y_click","y_click");

            SmArgManager argMgr = SmArgManager.getInstance();
            SmArgument arg = new SmArgument();
            //arg.add("yAlign",yAlign);
            arg.add("yAxis",yAxis);
            arg.add("_chartFragment",_chartFragment);
            argMgr.register("y_click",arg);

            MainActivity.context.startActivity(intent);
           // _selectedAnnotation.setSelected(true);

            return true;
        } else
            return super.onTouchUp(args);
    }

    private  void setNewPos(@NonNull TextAnnotation textAnnotation, float xEnd, float yEnd) {
        final Comparable xValue = textAnnotation.getXAxis().getDataValue(xEnd);
        final Comparable yValue = textAnnotation.getYAxis().getDataValue(yEnd);

        textAnnotation.setX1(xValue);
        textAnnotation.setY1(yValue);
        String xvalue = yValue.toString();
        String yvalue = yValue.toString();
        if (_selSetting != null) {
            _selSetting.set_x(Double.parseDouble(xvalue));
            _selSetting.set_y(Double.parseDouble(yvalue));
        }
    }
}
