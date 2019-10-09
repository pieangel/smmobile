package signalmaster.com.smmobile;

import java.util.ArrayList;
import java.util.HashMap;

import signalmaster.com.smmobile.crobo.Question;

public class QuestionManager {
    private ArrayList<Question> questionList = new ArrayList<>();

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public void questionList(){

   }

   public void questionContent(){
       HashMap<Integer,String> questionContent = new HashMap<>(0);
       questionContent.put(0,"10~20대");
       questionContent.put(1,"30대");
       questionContent.put(2,"40대");
       questionContent.put(3,"50대");
       questionContent.put(4,"60대 이상~");
   }
}
