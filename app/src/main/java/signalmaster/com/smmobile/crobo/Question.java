package signalmaster.com.smmobile.crobo;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {
    /*private String title;
    private int index = 0;
    private ArrayList<Content> contentList = new ArrayList<>();*/

    private HashMap<Integer,String> questionList = new HashMap<>();

    public HashMap<Integer, String> getQuestionList() {
        addTitle();
        return questionList;
    }

    public void setQuestionList(HashMap<Integer, String> questionList) {
        this.questionList = questionList;
    }

    public void addTitle(){
        questionList.put(1,"내 연령대를 선택하세요.");
        questionList.put(2,"위험과 수익의 밸런스");
        questionList.put(3,"손실 회피 성향");
        questionList.put(4,"파생 상품 투자 경험");
        questionList.put(5,"예상 투자 기간");
        questionList.put(6,"예상 투자 금액");
    }
}
