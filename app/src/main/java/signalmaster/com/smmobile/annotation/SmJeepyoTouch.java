package signalmaster.com.smmobile.annotation;

import android.content.Intent;

import com.scichart.charting.modifiers.TouchModifierBase;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.core.utility.touch.ModifierTouchEventArgs;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmArgument;
import signalmaster.com.smmobile.chart.SmChartFragment;

public class SmJeepyoTouch extends TouchModifierBase {
    private SmChartFragment _chartFragment = null;
    private SmJeePyo _selJeepyo = null;
    private TextAnnotation _selectedAnnotation = null;

    public void set_chartFragment(SmChartFragment _chartFragment){
        this._chartFragment = _chartFragment;
    }

    private TextAnnotation hitTest(double x, double y){
        /*Log.d(TAG,"texthit");
        if(_chartFragment == null)
            return null;
        ArrayList<SmJeePyo> _jeepyoList = _chartFragment.getJeepyoList();
        if(_jeepyoList.size() == 0)
            return null;

        for(SmJeePyo jeepyo : _jeepyoList){
            double xvalue = jeepyo.get_x();
            double yvalue = jeepyo.get_y();
            TextAnnotation anno = jeepyo.get_jannotation();
            if(anno == null)
                continue;

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
                _selJeepyo = jeepyo;
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

            Intent intent = new Intent(MainActivity.context, SmJeepyoSwitch.class);
            intent.putExtra("j_click","j_click");

            SmArgManager argMgr = SmArgManager.getInstance();
            SmArgument arg = new SmArgument();
            arg.add("_chartFragment",_chartFragment);
            argMgr.register("j_click",arg);

            MainActivity.context.startActivity(intent);

            // _selectedAnnotation.setSelected(true);

            return true;
        } else
            return super.onTouchUp(args);
    }
}
