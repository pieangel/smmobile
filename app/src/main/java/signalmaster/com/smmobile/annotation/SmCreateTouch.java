package signalmaster.com.smmobile.annotation;

import android.util.Log;

import com.scichart.charting.modifiers.TouchModifierBase;
import com.scichart.charting.visuals.annotations.LabelPlacement;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.core.utility.touch.ModifierTouchEventArgs;
import com.scichart.drawing.utility.ColorUtil;

import java.util.ArrayList;
import java.util.Collections;

import signalmaster.com.smmobile.mock.PrChartFragmentNOTUSE;

public class SmCreateTouch extends TouchModifierBase {
    private PrChartFragmentNOTUSE _chartFragment = null;
    private SmCreateAnno _selCreate = null;
    private TextAnnotation _selectedAnnotation = null;

    //new


    public void set_chartFragment(PrChartFragmentNOTUSE _chartFragment){
        this._chartFragment = _chartFragment;
    }

    private TextAnnotation hitTest(double x, double y){
        Log.d(TAG,"texthit");
        if(_chartFragment == null)
            return null;
        ArrayList<SmCreateAnno> _createList = _chartFragment.getCreateList();
        if(_createList.size() == 0)
            return null;

        for(SmCreateAnno create : _createList){
            double xvalue = create.get_x();
            double yvalue = create.get_y();
            TextAnnotation anno = create.get_cannotation();
            if(anno == null)
                continue;

            /*if (setting.getCoorMode() == 0) {
                x_pos = anno.getCoordinateMode();
            }*/
            // anno.setCoordinateMode(AnnotationCoordinateMode.Absolute);
            //값 변환
            xvalue = getModifierSurface().getWidth()*xvalue;
            yvalue = getModifierSurface().getHeight()*yvalue;
            /*x_pos = anno.getXAxis().getCurrentCoordinateCalculator().getCoordinate(xvalue);
            y_pos = anno.getYAxis().getCurrentCoordinateCalculator().getCoordinate(yvalue);*/
            double xmin = x - 50 , xmax = x + 50 ;
            double ymin = y - 100 , ymax = y + 20 ;
            //anno.setCoordinateMode(AnnotationCoordinateMode.Relative);
            /*PointF co = new PointF((float) x, (float) y);
            getModifierSurface().translatePoint(co,getModifierSurface());
            getPointRelativeTo(co, getModifierSurface());*/

            if(yvalue >= ymin && yvalue <= ymax && xvalue >= xmin && xvalue <= xmax ){
                anno.setSelected(true);
                _selCreate = create;
                return anno;
            }
        }
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

            _chartFragment.annotation = _chartFragment.sciChartBuilder.newHorizontalLineAnnotation()
                    .withPosition(1, 12000)
                    .withStroke(2, ColorUtil.Orange)
                    //수치를 나타내줌
                    .withAnnotationLabel(LabelPlacement.Axis)
                    //.withIsEditable(true)
                    .build();


            Collections.addAll(_chartFragment.surface.getAnnotations(), _chartFragment.annotation);
            Collections.addAll(_chartFragment.surface.getChartModifiers(), _chartFragment.sciChartBuilder.newModifierGroupWithDefaultModifiers().build());


            return true;
        } else
            return super.onTouchUp(args);
    }
}
