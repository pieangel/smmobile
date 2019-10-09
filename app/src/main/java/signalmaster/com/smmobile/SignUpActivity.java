package signalmaster.com.smmobile;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import signalmaster.com.smmobile.network.SmServiceManager;

public class SignUpActivity extends AppCompatActivity {

    ImageView backImg;
    EditText registerEmail,registerPwd;
    Button registerBtn;

    SmServiceManager smServiceManager;

    String email,pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        smServiceManager = SmServiceManager.getInstance();

        backImg = (ImageView)findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        registerEmail = (EditText)findViewById(R.id.registerEmail);
        registerPwd = (EditText)findViewById(R.id.registerPwd);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = registerEmail.getText().toString();
                pwd = registerPwd.getText().toString();

                if(email != null && pwd != null) {

                    smServiceManager.requestRegisterUser(email, pwd);
                    saveLogin();
                    //Toast.makeText(getApplicationContext(),"등록 되었습니다.",Toast.LENGTH_LONG).show();
                    alertMessage();

                }


            }
        });

    }

    public void alertMessage(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alert.setMessage("등록이 성공적으로 완료 되었습니다.");
        alert.show();
    }

    //값 저장
    public void saveLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("pwd", pwd);
        editor.apply();
    }

    //값 불러올때
    public void loadUserInfo(){
        SharedPreferences sf = getSharedPreferences("loginInfo",MODE_PRIVATE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
