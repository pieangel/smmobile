package signalmaster.com.smmobile.annotation;

import android.support.annotation.NonNull;

import com.scichart.charting.modifiers.TouchModifierBase;
import com.scichart.charting.visuals.annotations.HorizontalLineAnnotation;
import com.scichart.charting.visuals.annotations.LineAnnotation;
import com.scichart.core.utility.touch.ModifierTouchEventArgs;
import com.scichart.drawing.common.PenStyle;

import signalmaster.com.smmobile.chart.SmChartFragment;

public class ChartTouchModifier extends TouchModifierBase {
    private final PenStyle srokeStyle = null;
    private LineAnnotation lineAnnotation;
    private SmChartFragment _chartFragment = null;

    private HorizontalLineAnnotation _selectedAnnotation = null;

    public void set_chartFragment(SmChartFragment _chartFragment) {
        this._chartFragment = _chartFragment;
    }



    private HorizontalLineAnnotation hitTest(float x, float y) {
        /*Log.d(TAG, "hittest");
        if (_chartFragment == null)
            return null;
        ArrayList<SmOrder> _orderList = _chartFragment.getOrderList();
        if (_orderList.size() == 0)
            return null;

        for (SmOrder order : _orderList) {
            int xvalue = order.get_x();
            float yvalue = order.get_value();
            HorizontalLineAnnotation anno = order.get_annotation();
            if (anno == null)
                continue;
            float x_pos =  anno.getXAxis().getCurrentCoordinateCalculator().getCoordinate(xvalue);
            float y_pos = anno.getYAxis().getCurrentCoordinateCalculator().getCoordinate(yvalue);
            float xmin = x - 10, xmax = x + 10;
            float ymin = y - 22, ymax = y + 22;
            if (y_pos >= ymin && y_pos <= ymax) {
                anno.setSelected(true);
                //anno.setIsEditable(true);
                _selOrder = order;
                return anno;
            }
        }*/

        return null;
    }

    @Override
    protected boolean onTouchDown(ModifierTouchEventArgs args) {
        final float x = args.e.getX();
        final float y = args.e.getY();

        _selectedAnnotation = hitTest(x, y);

        return true;
    }

    @Override
    protected boolean onTouchMove(ModifierTouchEventArgs args) {
        if (_selectedAnnotation != null) {
            final float x = args.e.getX();
            final float y = args.e.getY();

            setNewPos(_selectedAnnotation, x, y);
            _selectedAnnotation.setSelected(true);
            return true;
        } else
            return super.onTouchMove(args);
    }

    @Override
    protected boolean onTouchUp(ModifierTouchEventArgs args) {
        if (_selectedAnnotation != null) {
        final float x = args.e.getX();
        final float y = args.e.getY();

        setNewPos(_selectedAnnotation, x, y);

        _selectedAnnotation.setSelected(true);

        return true;
    } else
            return super.onTouchUp(args);
}

    private  void setLineStart(@NonNull LineAnnotation lineAnnotation, float xStart, float yStart) {
        final Comparable xValue = lineAnnotation.getXAxis().getDataValue(xStart);
        final Comparable yValue = lineAnnotation.getYAxis().getDataValue(yStart);

        lineAnnotation.setX1(xValue);
        lineAnnotation.setY1(yValue);
    }

    private  void setLineEnd(@NonNull LineAnnotation lineAnnotation, float xEnd, float yEnd) {
        final Comparable xValue = lineAnnotation.getXAxis().getDataValue(xEnd);
        final Comparable yValue = lineAnnotation.getYAxis().getDataValue(yEnd);

        lineAnnotation.setX2(xValue);
        lineAnnotation.setY2(yValue);
    }

    private  void setNewPos(@NonNull HorizontalLineAnnotation lineAnnotation, float xEnd, float yEnd) {
        final Comparable xValue = lineAnnotation.getXAxis().getDataValue(xEnd);
        final Comparable yValue = lineAnnotation.getYAxis().getDataValue(yEnd);

        //lineAnnotation.setX2(xValue);
        lineAnnotation.setY1(yValue);
        String value = yValue.toString();

    }

}
