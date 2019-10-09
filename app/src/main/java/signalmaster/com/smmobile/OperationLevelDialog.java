package signalmaster.com.smmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.crobo.SmCroboFragment;
import signalmaster.com.smmobile.crobo.SmCroboLevelFragment;
import signalmaster.com.smmobile.crobo.SmCroboResultFragment;
import signalmaster.com.smmobile.crobo.SmCroboStartFragment;

public class OperationLevelDialog extends AppCompatActivity {


    TextView startOperationTxt, changeTxt, subTxt, cancel;
    boolean getBoolean;

    //SmCroboLevelFragment smCroboLevelFragment;
    SmCroboResultFragment smCroboResultFragment;

    SmCroboFragment smCroboFragment;
    SmCroboStartFragment smCroboStartFragment;
    SmCroboLevelFragment smCroboLevelFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation_level_dialog);

        subTxt = findViewById(R.id.subTxt);
        changeTxt = findViewById(R.id.changeTxt);

       getBoolean = getIntent().getBooleanExtra("y/n", false);
       smCroboFragment = SmCroboFragment.newInstance();
       smCroboLevelFragment = SmCroboLevelFragment.newInstance();
       smCroboStartFragment = SmCroboStartFragment.newInstance();


        if (getBoolean) {
            changeTxt.setText("다음단계 넘어가기");
            subTxt.setText("지금보다 공격적으로 투자하려면 향후\n 커스터마이징으로 더 다양한 투자를\n 추가할 수 있어요.");
            changeTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.mainListContainer,SmCroboLevelFragment.newInstance()).commit();*/
                    //운용레벨 건너띈 다음 화면
                }
            });
        } else {
            changeTxt.setText("설문 다시하기");
            subTxt.setText("공격적인 투자 성향으로 변경하려면\n처음부터 다시 설문을 진행해주세요.");
            changeTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //설문 처음으로
                    SmArgManager smArgManager = SmArgManager.getInstance();
                    /*smCroboResultFragment =(SmCroboResultFragment)smArgManager.getVal("crobo_survey","fragment");
                    smCroboResultFragment.changeStartFragment();*/
                    smCroboFragment = (SmCroboFragment)smArgManager.getVal("survey","fragment");
                    smCroboFragment.setChildFragment(smCroboStartFragment);
                    finish();
                }
            });
        }

        startOperationTxt = findViewById(R.id.startOperationTxt);
        startOperationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainListContainer,smCroboLevelFragment).commit();*/
                SmArgManager smArgManager = SmArgManager.getInstance();
                /*smCroboResultFragment =(SmCroboResultFragment)smArgManager.getVal("crobo_survey","fragment");
                smCroboResultFragment.changeLevelFragment();*/
                smCroboFragment = (SmCroboFragment)smArgManager.getVal("survey","fragment");
                smCroboFragment.setChildFragment(smCroboLevelFragment);
                finish();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*startOperationTxt = findViewById(R.id.startOperationTxt);
        subTxt = findViewById(R.id.subTxt);
        changeTxt = findViewById(R.id.changeTxt);
        changeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getBoolean) {

                } else {

                }
            }
        });*/


    }
}
