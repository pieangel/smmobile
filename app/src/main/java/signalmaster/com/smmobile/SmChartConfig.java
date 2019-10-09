package signalmaster.com.smmobile;

import android.graphics.Typeface;

import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.AnnotationCoordinateMode;
import com.scichart.charting.visuals.annotations.HorizontalAnchorPoint;
import com.scichart.charting.visuals.annotations.TextAnnotation;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Collections;

public class SmChartConfig {
    TextAnnotation j_annotation = null, delete_annotation = null;
    SciChartBuilder sciChartBuilder = SciChartBuilder.instance();


    SciChartSurface surface;

    public void j_annotation(){
        j_annotation = sciChartBuilder.newTextAnnotation()
                .withPosition(0.4, 0.85)
                .withFontStyle(Typeface.DEFAULT_BOLD,18,0x22FFFFFF)
                .withBackgroundDrawableId(R.drawable.smmobile_text_annotation_background)
                .withCoordinateMode(AnnotationCoordinateMode.Relative)
                .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                //.withVerticalAnchorPoint(VerticalAnchorPoint.Center)
                .withText("지표")
                .withPadding(6,8,6,4)
                //.withTextGravity(Gravity.CENTER)
                .build();

        if (j_annotation != null)
            Collections.addAll(surface.getAnnotations(), j_annotation);
    }

    public void delete_annotation(){
        delete_annotation = sciChartBuilder.newTextAnnotation()
                .withPosition(0.2, 0.85)
                .withFontStyle(Typeface.DEFAULT_BOLD,18,0x22FFFFFF)
                .withBackgroundDrawableId(R.drawable.smmobile_text_annotation_background)
                .withCoordinateMode(AnnotationCoordinateMode.Relative)
                .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                //.withVerticalAnchorPoint(VerticalAnchorPoint.Center)
                .withText("지표")
                .withPadding(6,8,6,4)
                //.withTextGravity(Gravity.CENTER)
                .build();
    }
}
