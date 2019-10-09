package signalmaster.com.smmobile.crobo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xw.repo.BubbleList;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Locale;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;

public class SmCroboQuestionFragment extends Fragment {

    public static SmCroboQuestionFragment newInstance(){
        return new SmCroboQuestionFragment();
    }

    SmCroboFragment smCroboFragment;
    SmCroboStartFragment smCroboStartFragment;
    SmCroboResultFragment smCroboResultFragment;

    TextView nextTxt,progressTxt,titleTxt;
    ImageView changeImg;
    public BubbleSeekBar seekBar;
    LinearLayout backLout;
    int pageSection = 0;
    public double resultValue = 0;

    BubbleList bubbleList = new BubbleList();
    private ArrayList<String> progressList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<ArrayList<String>> questionArray = new ArrayList<>();
    private ArrayList<String> answerList = new ArrayList<>();

    public ArrayList<String> getAnswerList() {
        return answerList;
    }


    private ArrayList<String> moveList = new ArrayList<>();

    private void listAdd() {
        progressList.add("1");
        progressList.add("2");
        progressList.add("3");
        progressList.add("4");
        progressList.add("5");
        progressList.add("6");
        progressList.add("7");

        titleList.add("내 연령대를 선택하세요.");
        titleList.add("연 소득 규모는?");
        titleList.add("예상 투자 금액은?");
        titleList.add("예상 투자 기간은?");
        titleList.add("위험과 수익의 밸런스는?");
        titleList.add("손실 감수 수준은?");
        titleList.add("파생 투자 경험은?");

        questionArray.add(bubbleList.getOneQuestion());
        questionArray.add(bubbleList.getTwoQuestion());
        questionArray.add(bubbleList.getThreeQuestion());
        questionArray.add(bubbleList.getFourQuestion());
        questionArray.add(bubbleList.getFiveQuestion());
        questionArray.add(bubbleList.getSixQuestion());
        questionArray.add(bubbleList.getSevenQuestion());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sm_crobo_question_fragment, container, false);

        //시크바 제거하기 위해
        SmArgManager smArgManager = SmArgManager.getInstance();
        smArgManager.registerToMap("questionFragment","fragment",this);

        smCroboStartFragment = SmCroboStartFragment.newInstance();
        smCroboResultFragment = SmCroboResultFragment.newInstance();

        seekBar = (BubbleSeekBar)view.findViewById(R.id.seekbar);
        progressTxt = (TextView)view.findViewById(R.id.progressTxt);
        titleTxt = (TextView)view.findViewById(R.id.titleTxt);
        nextTxt = (TextView)view.findViewById(R.id.nextTxt);
        backLout = (LinearLayout)view.findViewById(R.id.backLout);
        changeImg = (ImageView)view.findViewById(R.id.changeImg);
        listAdd();
        seekBar.setmShowBubble(true);
        seekBar.getConfigBuilder().progress(2).build();


        //처음 화면일 때
        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                //String s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat);
                String s = String.format(Locale.CHINA, "%d", progress, progressFloat);
                moveList.add(s);
                ImageChange(Integer.parseInt(s));
                        /*if(moveList.size() == 0){
                            answerList.add("2");
                        }*/
            }
        });
        if (moveList.size() == 0) {
            answerList.add("2");
        } else {
            answerList.add(moveList.get(moveList.size() - 1));
            moveList.clear();
        }

        if(answerList.get(0).equals("2")){
            answerList.remove(0);
        }

        nextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 초기화
                changeImg.setImageResource(R.drawable.yellowbulb);
                //마지막 문제
                if(pageSection == 6){
                    /*secondLout.setVisibility(View.GONE);
                    nextLout.setVisibility(View.GONE);
                    resultLout.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.GONE);*/
                    seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
                        @Override
                        public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                            //String s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat);
                            String s = String.format(Locale.CHINA, "%d", progress, progressFloat);
                            ImageChange(Integer.parseInt(s));
                            moveList.add(s);
                        /*if(moveList.size() == 0){
                            answerList.add("2");
                        }*/
                            //moveList.clear();
                        }
                    });
                    if(moveList.size() == 0){
                        answerList.add("2");
                    } else {
                        answerList.add(moveList.get(moveList.size()-1));
                    }

                    logic(answerList);
                    SmArgManager smArgManager = SmArgManager.getInstance();
                    smCroboFragment = (SmCroboFragment)smArgManager.getVal("survey","fragment");
                    smCroboFragment.setChildFragment(smCroboResultFragment);
                    //((MainActivity)getActivity()).replaceFragment(SmCroboResultFragment.newInstance());
                    seekBar.setmShowBubble(false);

                    smArgManager.registerToMap("survey","result_value",resultValue);


                    return;
                }


                seekBar.getConfigBuilder().progress(2).build();
                progressTxt.setText(progressList.get(pageSection+1));
                titleTxt.setText(titleList.get(pageSection+1));
                seekBar.setQuestionList(questionArray.get(pageSection+1));

                seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
                    @Override
                    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                        //String s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat);
                        String s = String.format(Locale.CHINA, "%d", progress, progressFloat);
                        moveList.add(s);
                        ImageChange(Integer.parseInt(s));
                        /*if(moveList.size() == 0){
                            answerList.add("2");
                        }*/
                    }
                });
                if(moveList.size() == 0){
                    answerList.add("2");
                } else {
                    answerList.add(moveList.get(moveList.size()-1));
                    moveList.clear();
                }
                pageSection++;

            }
        });

        backLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(pageSection == 6){
                    secondLout.setVisibility(View.GONE);
                    nextLout.setVisibility(View.GONE);
                    resultLout.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.GONE);
                    return;
                } else if(pageSection == 1){

                }*/
                if(pageSection == 0){
                    //다시 SmCroboStratFragment로 되돌린다. AnswerList 는 clear 시켜준다.
                    /*firstLout.setVisibility(View.VISIBLE);
                    secondLout.setVisibility(View.GONE);
                    startTxt.setVisibility(View.VISIBLE);
                    nextTxt.setVisibility(View.GONE);
                    seekBar.setmShowBubble(false);*/
                    //seekBar.getConfigBuilder().progress(2).build();
                    SmArgManager smArgMaager = SmArgManager.getInstance();
                    smCroboFragment = (SmCroboFragment)smArgMaager.getVal("survey","fragment");
                    smCroboFragment.setChildFragment(smCroboStartFragment);
                    //((MainActivity)getActivity()).replaceFragment(SmCroboStartFragment.newInstance());
                    answerList.clear();

                    return;
                }


                seekBar.getConfigBuilder().progress(2).build();
                progressTxt.setText(progressList.get(pageSection-1));
                titleTxt.setText(titleList.get(pageSection-1));
                seekBar.setQuestionList(questionArray.get(pageSection-1));
                pageSection--;
                if(answerList.size() == 0)
                    return;

                answerList.remove(answerList.size()-1);

            }
        });

        return view;
    }

    public void ImageChange(int position){
        //pagesection도 추가
        switch (position){
            case 0:
                changeImg.setImageResource(R.drawable.bluebulb);
                break;
            case 1:
                changeImg.setImageResource(R.drawable.greenbulb);
                break;
            case 2:
                changeImg.setImageResource(R.drawable.yellowbulb);
                break;
            case 3:
                changeImg.setImageResource(R.drawable.redbulb);
                break;
            case 4:
                changeImg.setImageResource(R.drawable.blackbulb);
                break;
        }

    }

    public void logic(ArrayList<String> answerList) {
        //null 일떄
        if (answerList.size() == 0) {
            return;
        }

        if (answerList.get(0).equals("0")) {
            resultValue = resultValue + 5;
        } else if (answerList.get(0).equals("1")) {
            resultValue = resultValue + 7.5;
        } else if (answerList.get(0).equals("2")) {
            resultValue = resultValue + 10;
        } else if (answerList.get(0).equals("3")) {
            resultValue = resultValue + 5;
        } else if (answerList.get(0).equals("4")) {
            resultValue = resultValue + 2.5;
        }

        if (answerList.get(1) .equals("0")) {
            resultValue = resultValue + 1;
        } else if (answerList.get(1) .equals("1")) {
            resultValue = resultValue + 2;
        } else if (answerList.get(1).equals("2")) {
            resultValue = resultValue + 3;
        } else if (answerList.get(1).equals("3")) {
            resultValue = resultValue + 4;
        } else if (answerList.get(1).equals("4")) {
            resultValue = resultValue + 5;
        }

        if (answerList.get(2) .equals("0")) {
            resultValue = resultValue + 2;
        } else if (answerList.get(2) .equals("1")) {
            resultValue = resultValue + 4;
        } else if (answerList.get(2).equals("2")) {
            resultValue = resultValue + 6;
        } else if (answerList.get(2).equals("3")) {
            resultValue = resultValue + 8;
        } else if (answerList.get(2).equals("4")) {
            resultValue = resultValue + 10;
        }

        if (answerList.get(3) .equals("0")) {
            resultValue = resultValue + 2;
        } else if (answerList.get(3) .equals("1")) {
            resultValue = resultValue + 4;
        } else if (answerList.get(3).equals("2")) {
            resultValue = resultValue + 6;
        } else if (answerList.get(3).equals("3")) {
            resultValue = resultValue + 8;
        } else if (answerList.get(3).equals("4")) {
            resultValue = resultValue + 10;
        }

        if (answerList.get(4) .equals("0")) {
            resultValue = resultValue + 5;
        } else if (answerList.get(4) .equals("1")) {
            resultValue = resultValue + 10;
        } else if (answerList.get(4).equals("2")) {
            resultValue = resultValue + 15;
        } else if (answerList.get(4).equals("3")) {
            resultValue = resultValue + 20;
        } else if (answerList.get(4).equals("4")) {
            resultValue = resultValue + 25;
        }

        if (answerList.get(5) .equals("0")) {
            resultValue = resultValue + 5;
        } else if (answerList.get(5) .equals("1")) {
            resultValue = resultValue + 10;
        } else if (answerList.get(5) .equals("2")) {
            resultValue = resultValue + 15;
        } else if (answerList.get(5).equals("3")) {
            resultValue = resultValue + 20;
        } else if (answerList.get(5).equals("4")) {
            resultValue = resultValue + 25;
        }

        if (answerList.get(6) .equals("0")) {
            resultValue = resultValue + 3;
        } else if (answerList.get(6) .equals("1")) {
            resultValue = resultValue + 6;
        } else if (answerList.get(6) .equals("2")) {
            resultValue = resultValue + 9;
        } else if (answerList.get(6).equals("3")) {
            resultValue = resultValue + 12;
        } else if (answerList.get(6).equals("4")) {
            resultValue = resultValue + 15;
        }

    }

    @Override
    public void onStop(){
        super.onStop();
        seekBar.setmShowBubble(false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /*SmArgManager smArgManager = SmArgManager.getInstance();
        smArgManager.registerToMap("SmCroboStartFragment","fragment",this);*/
    }


}
