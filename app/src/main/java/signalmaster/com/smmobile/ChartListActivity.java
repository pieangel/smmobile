package signalmaster.com.smmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.scichart.extensions.builders.SciChartBuilder;

import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.chart.SmChartType;

public class ChartListActivity extends AppCompatActivity {


    private SmChartFragment smChartFragment1 = null;
    private SmChartFragment smChartFragment2 = null;


    public ChartListActivity() {
        smChartFragment1 = SmChartFragment.newInstance("signal_regular_upper");

        smChartFragment2 = SmChartFragment.newInstance("signal_regular_lower");

    }

    // private Intent intent;
    // private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);

        //SciChart Build.
        SciChartBuilder.init(this);
        setUpSciChartLicense();


        // MainActivity에서 보낸 imgRes를 받기위해 getIntent()로 초기화
        //intent = getIntent();
        // imageView = (ImageView) findViewById(R.id.imageView);

        // "imgRes" key로 받은 값은 int 형이기 때문에 getIntExtra(key, defaultValue);
        // 받는 값이 String 형이면 getStringExtra(key);
        //imageView.setImageResource(intent.getIntExtra("imgRes", 0));


        FragmentManager fragmentManager = getSupportFragmentManager();


        /*MChartFragment mChartFragment = new MChartFragment();
        MChartFragment2 mChartFragment2 = new MChartFragment2();
        Bundle bundle = new Bundle();
        mChartFragment.setArguments(bundle);*/

        Intent intent = getIntent();
        String symbolCode = intent.getExtras().getString("symbolCode");

        smChartFragment1.setSymbolCode(symbolCode);
        smChartFragment1.setChartType(SmChartType.MIN);
        smChartFragment1.setChartCycle(5);
        smChartFragment2.setSymbolCode(symbolCode);
        smChartFragment2.setChartType(SmChartType.MIN);
        smChartFragment2.setChartCycle(30);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,smChartFragment1);
        fragmentTransaction.replace(R.id.fragment_container2,smChartFragment2);
        fragmentTransaction.commit();


        /*FragmentManager fragmentManager2 = getSupportFragmentManager();


        MChartFragment2 mChartFragment2 = new MChartFragment2();
        Bundle bundle2 = new Bundle();
        mChartFragment.setArguments(bundle2);

        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
        fragmentTransaction2.replace(R.id.fragment_container,mChartFragment2);
        fragmentTransaction2.commit();*/

    }

    //라이센스 키
    private void setUpSciChartLicense() {
        // Set your license code here to license the SciChart Android Examples app
        // You can get a trial license key from https://www.scichart.com/licensing-scichart-android/
        // Purchased license keys can be viewed at https://www.scichart.com/profile
        //
        // e.g.
        //
        try {
            com.scichart.charting.visuals.SciChartSurface.setRuntimeLicenseKey(
                    "<LicenseContract>"+
                            "<Customer>Signal Master</Customer>"+
                            "<OrderId>ABT190415-9572-80110</OrderId>"+
                            "<LicenseCount>1</LicenseCount>"+
                            "<IsTrialLicense>false</IsTrialLicense>"+
                            " <SupportExpires>04/14/2020 00:00:00</SupportExpires>"+
                            " <ProductCode>SC-IOS-ANDROID-2D-ENTERPRISE-SRC</ProductCode>"+
                            "<KeyCode>827b6998e9f182adb4247cd45e54e4e8387271ef1f06d73b9152eb35f068754036cbe8f5a51a7448e6f8d758f3155284e93b68a30d64eaa8224580f8bc93a5d4d83b07f5b280fd32608232966f75d268bc98f29dee7876f60ad547a2c61da45505693e705231f06ee4d529fa19b230c3c211741619c5fa1a64439fa84ef4cd31d85062363306e8a0048907081012b5d09da4a427fe7ba4ec2aa21e514f2912f5b4748f456e4d27e8f868e28ab58580a6fbd9b8526db36253c0</KeyCode>"+
                            "</LicenseContract>"
            );
        } catch (Exception e) {
            Log.e("SciChart", "Error when setting the license", e);
        }
    }

}
