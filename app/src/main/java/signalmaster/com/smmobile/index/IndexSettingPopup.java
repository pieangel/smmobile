package signalmaster.com.smmobile.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scichart.charting.ClipMode;
import com.scichart.charting.Direction2D;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.modifiers.AxisDragModifierBase;
import com.scichart.charting.modifiers.ModifierGroup;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.charting.visuals.synchronization.SciChartVerticalGroup;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.zip.Inflater;

import signalmaster.com.smmobile.ChangeJipyoType;
import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.data.DataManager;
import signalmaster.com.smmobile.data.DoubleSeries;

public class IndexSettingPopup extends AppCompatActivity {

    LinearLayout chartLinear;
    SciChartSurface addSurface;

    TextView indexName,saveText;
    ImageView closeImg;

    private String jipyoStype = "RSI";

    private final SciChartVerticalGroup verticalGroup = new SciChartVerticalGroup();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_setting_popup);

        saveText = (TextView)findViewById(R.id.saveText);
        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        closeImg = (ImageView)findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void ChangeJipyoType(ChangeJipyoType jipyoType){
        Intent intent = getIntent();
        String jipyoName = intent.getExtras().getString("jipyoName","지표");

        if(jipyoName == jipyoType.toString()) {
            switch (jipyoType) {
                case RSI:


                    indexName = (TextView) findViewById(R.id.indexName);
                    indexName.setText(jipyoName);

                    saveText = (TextView) findViewById(R.id.saveText);

                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.sm_chart, null);

                    //chart add..
                    chartLinear = (LinearLayout) view.findViewById(R.id.chartLinear);
                    chartLinear.setVisibility(View.VISIBLE);
                    addSurface = new SciChartSurface(MainActivity.context);
                    addSurface.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2f));
                    chartLinear.addView(addSurface);

                    //SciChartBuilder.init(MainActivity.context);
                    final SciChartBuilder addSciChartBuilder = SciChartBuilder.instance();
                    final IAxis addxAxis = addSciChartBuilder.newNumericAxis().withGrowBy(0.1d, 0.1d).withVisibleRange(1.1, 2.7).build();
                    final IAxis addyAxis = addSciChartBuilder.newNumericAxis().withGrowBy(0.1d, 0.1d).build();

                    final DoubleSeries fourierSeries = DataManager.getInstance().getFourierSeries(1.0, 0.1, 5000);
                    final IXyDataSeries<Double, Double> dataSeries = addSciChartBuilder.newXyDataSeries(Double.class, Double.class).build();
                    dataSeries.append(fourierSeries.xValues, fourierSeries.yValues);

                    final FastLineRenderableSeries rSeries = addSciChartBuilder.newLineSeries().withDataSeries(dataSeries).withStrokeStyle(0xFF279B27, 1f, true).build();

                    final ModifierGroup chartModifiers = addSciChartBuilder.newModifierGroup()
                            .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).withClipModeX(ClipMode.StretchAtExtents).build()
                            .withPinchZoomModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                            .withZoomPanModifier().withReceiveHandledEvents(true).build()
                            .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
                            .withLegendModifier().withShowCheckBoxes(false).build()
                            .build();


        /*Collections.addAll(addSurface.getXAxes(),addxAxis);
        Collections.addAll(addSurface.getYAxes(),addyAxis);
        Collections.addAll(addSurface.getChartModifiers(),chartModifiers);*/
                    // DoubleSeries fourierSeries = DataManager.getInstance().getFourierSeries(1.0, 0.1, 5000);
        /*PriceSeries priceSeries = DataManager.getInstance().getPriceDataIndu(getActivity());
        XyDataSeries<Date, Double> rsiSeries = addSciChartBuilder.newXyDataSeries(Date.class, Double.class).withSeriesName("RSI").build();
        rsiSeries.append(priceSeries.getDateData(), MovingAverage.rsi(priceSeries, 14));*/
        /*rsiSeries.setFifoCapacity(SmConst.ChartDataSize);
        rsiSeries.setAcceptsUnsortedData(true);*/

                    //((IXyDataSeries) dataSeries).append(chartData.getDateData(), chartData.getCloseData());

        /*final IRenderableSeries renderableSeries = sciChartBuilder.newLineSeries()
                .withDataSeries(rsiSeries)
                .withStrokeStyle(0xFFC6E6FF, 1f)
                .withYAxisId(RSI)
                .build();*/

                    UpdateSuspender.using(addSurface, new Runnable() {
                        @Override
                        public void run() {

                            addSurface.getXAxes().add(addxAxis);
                            addSurface.getYAxes().add(addyAxis);
                            addSurface.getChartModifiers().add(chartModifiers);
                            addSurface.getRenderableSeries().add(rSeries);

                            addSciChartBuilder.newAnimator(rSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
                        }
                    });

                    verticalGroup.addSurfaceToGroup(addSurface);
                    break;
                case MACD:

                    break;
            }
        }
    }


}
