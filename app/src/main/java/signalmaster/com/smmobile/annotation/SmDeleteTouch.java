package signalmaster.com.smmobile.annotation;

import android.util.Log;

import com.scichart.charting.model.AnnotationCollection;
import com.scichart.charting.modifiers.TouchModifierBase;
import com.scichart.charting.visuals.annotations.IAnnotation;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.core.utility.touch.ModifierTouchEventArgs;

import java.util.ArrayList;

import signalmaster.com.smmobile.mock.PrChartFragmentNOTUSE;

public class SmDeleteTouch extends TouchModifierBase {
    private PrChartFragmentNOTUSE _chartFragment = null;
    private SmDeleteAnno _selDelete = null;
    private TextAnnotation _selectedAnnotation = null;

    public void set_chartFragment(PrChartFragmentNOTUSE _chartFragment){
        this._chartFragment = _chartFragment;
    }

    private TextAnnotation hitTest(double x, double y){
        Log.d(TAG,"texthit");
        if(_chartFragment == null)
            return null;
        ArrayList<SmDeleteAnno> _deleteList = _chartFragment.getDeleteList();
        if(_deleteList.size() == 0)
            return null;

        for(SmDeleteAnno delete : _deleteList){
            double xvalue = delete.get_x();
            double yvalue = delete.get_y();
            TextAnnotation anno = delete.get_dannotation();
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
                _selDelete = delete;
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

            //_chartFragment.deleteSelectedAnnotation();
            AnnotationCollection annoCollection = _chartFragment.getSurface().getAnnotations();
            if(annoCollection != null) {
                for (int i = annoCollection.size() - 1; i >= 0; i--) {
                    IAnnotation iAnnotation = annoCollection.get(i);
                    if (iAnnotation.isSelected()) {
                        annoCollection.remove(i);
                    }
                }
            }

            // _selectedAnnotation.setSelected(true);

            return true;
        } else
            return super.onTouchUp(args);
    }


}
