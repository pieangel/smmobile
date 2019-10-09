package signalmaster.com.smmobile.crobo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.OperationLevelDialog;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;

public class SmCroboResultFragment extends Fragment {

    public static SmCroboResultFragment newInstance(){
        SmCroboResultFragment fragment = new SmCroboResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    double resultValue;
    TextView changeType,changeSubText,resultNTxt,resultYTxt;
    ImageView changeImg;
    LinearLayout resultBackLout;

    SmCroboResultFragment self = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sm_crobo_result_fragment, null);

        changeType = (TextView)view.findViewById(R.id.changeType);
        changeSubText = (TextView)view.findViewById(R.id.changeSubText);
        resultNTxt = (TextView) view.findViewById(R.id.resultNTxt);
        resultYTxt = (TextView) view.findViewById(R.id.resultYTxt);
        resultBackLout = (LinearLayout)view.findViewById(R.id.resultBackLout);
        changeImg = (ImageView)view.findViewById(R.id.changeImg);

        final SmArgManager smArgManager = SmArgManager.getInstance();
        resultValue = (Double) smArgManager.getVal("survey","result_value");



        if(resultValue <= 40 && resultValue >= 0){
            changeType.setText("안정형");
            changeSubText.setText("안정형의 자산투자는 주식형 위주로 투자하고 \n 채권형에는 소액 투자해요.");
            changeImg.setImageResource(R.drawable.safety);
        } else if(resultValue <=50 && resultValue >= 41){
            changeType.setText("안정추구형");
            changeSubText.setText("안정추구형의 자산투자는 주식형 위주로 투자하고 \n 채권형에는 소액 투자해요.");
            changeImg.setImageResource(R.drawable.coffe);
        } else if(resultValue <=65 && resultValue >= 51){
            changeType.setText("위험중립형");
            changeSubText.setText("위험중립형의 자산투자는 주식형 위주로 투자하고 \n 채권형에는 소액 투자해요.");
            changeImg.setImageResource(R.drawable.justice);
        } else if(resultValue <=80 && resultValue >= 66){
            changeType.setText("적극투자형");
            changeSubText.setText("적극투자형의 자산투자는 주식형 위주로 투자하고 \n 채권형에는 소액 투자해요.");
            changeImg.setImageResource(R.drawable.wallet);
        } else if(resultValue <=100 && resultValue >= 81){
            changeType.setText("공격투자형");
            changeSubText.setText("공격투자형의 자산투자는 주식형 위주로 투자하고 \n 채권형에는 소액 투자해요.");
            changeImg.setImageResource(R.drawable.kickboxing);
        }

        smArgManager.registerToMap("survey","changeType",changeType.getText());

        resultNTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context, OperationLevelDialog.class);
                intent.putExtra("y/n", false);
                startActivity(intent);
                //smArgManager.registerToMap("crobo_survey","fragment",self);
            }
        });

        resultYTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context, OperationLevelDialog.class);
                intent.putExtra("y/n", true);
                startActivity(intent);
                //smArgManager.registerToMap("crobo_survey","fragment",self);
            }
        });

        /*resultBackLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(Sm.newInstance());
            }
        });*/

        return view;
    }

    public void changeLevelFragment(){
        ((MainActivity)getActivity()).replaceFragment(SmCroboLevelFragment.newInstance());
    }

    SmCroboQuestionFragment smCroboQuestionFragment = SmCroboQuestionFragment.newInstance();

    public void changeStartFragment(){
        ((MainActivity)getActivity()).replaceFragment(SmCroboStartFragment.newInstance());
        smCroboQuestionFragment.resultValue = 0;
        smCroboQuestionFragment.getAnswerList().clear();


    }

}
