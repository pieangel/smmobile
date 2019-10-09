package signalmaster.com.smmobile.crobo;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xw.repo.BubbleList;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;

public class SmCroboFragment extends Fragment {

    public static SmCroboFragment newInstance() {
        SmCroboFragment fragment = new SmCroboFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Button survayStartBtn;

    SQLiteDatabase db;

    LinearLayout firstLout, secondLout, backLout, resultLout, resultBackLout, operationLevelLout, operationBackLout, nextLout;
    TextView nextTxt, progressTxt, titleTxt, resultNTxt, resultYTxt;
    TextView firstCt, secondCt, thirdCt, fourthCt, fifthCt, startTxt;
    //TextView firstTxt,secondTxt;
    BubbleSeekBar seekBar, seekBar2;
    BubbleList bubbleList = new BubbleList();

    int pageSection = 0;

    private Activity mActivity;
    private HashMap<Integer, String> questionList = null;
    private HashMap<String, String> contentList = null;
    private ArrayList<ArrayList<String>> questionArray = new ArrayList<>();

    private void initQuestionArray() {
        questionArray.add(bubbleList.getOneQuestion());
        questionArray.add(bubbleList.getTwoQuestion());
        questionArray.add(bubbleList.getThreeQuestion());
        questionArray.add(bubbleList.getFourQuestion());
        questionArray.add(bubbleList.getFiveQuestion());
        questionArray.add(bubbleList.getSixQuestion());
        questionArray.add(bubbleList.getSevenQuestion());
    }

    private ArrayList<String> progressList = new ArrayList<>();

    private void addProgressText() {
        progressList.add("1");
        progressList.add("2");
        progressList.add("3");
        progressList.add("4");
        progressList.add("5");
        progressList.add("6");
        progressList.add("7");
    }

    //Fragment smCroboStartFragment;
    SmCroboStartFragment smCroboStartFragment;
    SmCroboQuestionFragment smCroboQuestionFragment;
    SmCroboResultFragment smCroboResultFragment;
    SmCroboLevelFragment smCroboLevelFragment;
    boolean test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initQuestionArray();
        addProgressText();
        View view = inflater.inflate(R.layout.sm_crobo_fragment, null);

        smCroboStartFragment = SmCroboStartFragment.newInstance();
        /*smCroboQuestionFragment = SmCroboQuestionFragment.newInstance();
        smCroboResultFragment = SmCroboResultFragment.newInstance();
        smCroboLevelFragment = SmCroboLevelFragment.newInstance();*/

        SmArgManager smArgManager = SmArgManager.getInstance();
        smArgManager.registerToMap("survey","fragment",this);

        setChildFragment(smCroboStartFragment);

        return view;
    }


    public void setChildFragment(Fragment child){
        FragmentTransaction childFg = getChildFragmentManager().beginTransaction();

        if(!child.isAdded()){
            childFg.replace(R.id.child_fragment_container , child);
            childFg.addToBackStack(null);
            childFg.commit();
        } else {
            childFg.show(child);
        }
    }

    /*public void showFragment(SmCroboQuestionFragment smCroboQuestionFragment){
        if(!smCroboQuestionFragment.isAdded()){
            smCroboQuestionFragment.seekBar.setmShowBubble(false);
        }
    }*/

    //백그라운드로 갈 때
    /*@Override
    public void onPause() {
        super.onPause();
        seekBar.setVisibility(View.GONE);
    }

    // 다시 돌아올 때
    @Override
    public void onStart(){
        super.onStart();
        seekBar.setVisibility(View.VISIBLE);
    }*/

    @Override
    public void onStop(){
        super.onStop();
    }

}
