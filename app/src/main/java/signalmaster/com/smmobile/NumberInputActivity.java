package signalmaster.com.smmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import signalmaster.com.smmobile.Util.SmArgManager;

public class NumberInputActivity extends AppCompatActivity {

    TextView one, two, three, four, five, six, seven, eight, nine, zero;
    TextView feesTxt,indicatorPeriod;
    EditText inputNumberTxt;
    ImageView backSpaceImg, checkImg;

    String fees_value = null;
    String indicatorPeriodValue = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_input_activity);


        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        five = (TextView) findViewById(R.id.five);
        six = (TextView) findViewById(R.id.six);
        seven = (TextView) findViewById(R.id.seven);
        eight = (TextView) findViewById(R.id.eight);
        nine = (TextView) findViewById(R.id.nine);
        zero = (TextView) findViewById(R.id.zero);
        inputNumberTxt = (EditText) findViewById(R.id.inputNumberTxt);

        backSpaceImg = (ImageView) findViewById(R.id.backSpaceImg);
        checkImg = (ImageView) findViewById(R.id.checkImg);

        //수수료 설정
        SmArgManager argManager = SmArgManager.getInstance();
        fees_value = (String)argManager.getVal("input","fees_value");
        if(fees_value != null) {
            inputNumberTxt.setText(fees_value);
        }

        //지표 기간 설정
        indicatorPeriodValue = (String)argManager.getVal("input","periodValue");
        if(indicatorPeriodValue != null){
            inputNumberTxt.setText(indicatorPeriodValue);
        }


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "1");
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "2");
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "3");
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "4");
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "5");
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "6");
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "7");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "9");
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNumberTxt.setText(inputNumberTxt.getText() + "0");
            }
        });
        backSpaceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String data = inputNumberTxt.getText().toString();
                data = data.substring(0,data.lastIndexOf(""));
                inputNumberTxt.setText(data);*/
                int length = inputNumberTxt.getText().length();
                if (length > 0) {
                    inputNumberTxt.getText().delete(length - 1, length);
                }
            }
        });
        checkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmArgManager argManager = SmArgManager.getInstance();
                //수수료 설정
                feesTxt = (TextView)argManager.getVal("input","feesTxt");
                if(feesTxt != null) {
                    feesTxt.setText(inputNumberTxt.getText());
                }

                //지표 기간 설정
                indicatorPeriod = (TextView)argManager.getVal("input","periodTxt");
                if(indicatorPeriod != null){
                    indicatorPeriod.setText(inputNumberTxt.getText());
                }
                finish();
            }
        });


    }
}
