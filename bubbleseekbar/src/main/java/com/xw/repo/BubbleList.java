package com.xw.repo;

import java.util.ArrayList;

public class BubbleList {

    private ArrayList<String> oneQuestion = new ArrayList<>();
    private ArrayList<String> twoQuestion = new ArrayList<>();
    private ArrayList<String> threeQuestion = new ArrayList<>();
    private ArrayList<String> fourQuestion = new ArrayList<>();
    private ArrayList<String> fiveQuestion = new ArrayList<>();
    private ArrayList<String> sixQuestion = new ArrayList<>();
    private ArrayList<String> sevenQuestion = new ArrayList<>();


    private ArrayList<String> levelQuestion = new ArrayList<>();
    public void setOneQuestion(ArrayList<String> oneQuestion) {
        this.oneQuestion = oneQuestion;
    }
    public void setTwoQuestion(ArrayList<String> twoQuestion) {
        this.twoQuestion = twoQuestion;
    }
    public void setThreeQuestion(ArrayList<String> threeQuestion) {
        this.threeQuestion = threeQuestion;
    }
    public void setFourQuestion(ArrayList<String> fourQuestion) {
        this.fourQuestion = fourQuestion;
    }
    public void setFiveQuestion(ArrayList<String> fiveQuestion) {
        this.fiveQuestion = fiveQuestion;
    }
    public void setSixQuestion(ArrayList<String> sixQuestion) {
        this.sixQuestion = sixQuestion;
    }
    public void setSevenQuestion(ArrayList<String> sevenQuestion) {
        this.sevenQuestion = sevenQuestion;
    }
    public void setLevelQuestion(ArrayList<String> levelQuestion) {
        this.levelQuestion = levelQuestion;
    }
    public ArrayList<String> getOneQuestion() {
        oneQ();
        return oneQuestion;
    }

    public ArrayList<String> getTwoQuestion() {
        twoQ();
        return twoQuestion;
    }

    public ArrayList<String> getThreeQuestion() {
        threeQ();
        return threeQuestion;
    }

    public ArrayList<String> getFourQuestion() {
        fourQ();
        return fourQuestion;
    }

    public ArrayList<String> getFiveQuestion() {
        fiveQ();
        return fiveQuestion;
    }

    public ArrayList<String> getSixQuestion() {
        sixQ();
        return sixQuestion;
    }

    public ArrayList<String> getSevenQuestion() {
        sevenQ();
        return sevenQuestion;
    }

    public ArrayList<String> getLevelQuestion() {
        level();
        return levelQuestion;
    }

    public void level(){
        levelQuestion.add("level1");
        levelQuestion.add("level2");
        levelQuestion.add("level3");
        levelQuestion.add("level4");
        levelQuestion.add("level5");
    }

    public void oneQ(){
        oneQuestion.add("10~20대");
        oneQuestion.add("30대");
        oneQuestion.add("40대");
        oneQuestion.add("50대");
        oneQuestion.add("60대 이상");
    }

    public void twoQ(){
        twoQuestion.add("2500만원 이하");
        twoQuestion.add("2500만원 ~ 5000만원");
        twoQuestion.add("5000만원 ~ 7500만원");
        twoQuestion.add("7500만원 ~ 1억원");
        twoQuestion.add("1억원 이상");
    }
    public void threeQ(){
        threeQuestion.add("1천만원 이하");
        threeQuestion.add("2천만원");
        threeQuestion.add("3천만원");
        threeQuestion.add("5천만원");
        threeQuestion.add("1억 이상");

    }
    public void fourQ(){
        fourQuestion.add("단기투자");
        fourQuestion.add("1년 정도");
        fourQuestion.add("2년 정도");
        fourQuestion.add("3년 정도");
        fourQuestion.add("장기 투자");

    }
    public void fiveQ(){
        fiveQuestion.add("저수익");
        fiveQuestion.add("저수익 저위험");
        fiveQuestion.add("중간수익 중간위험");
        fiveQuestion.add("고수익 고위험");
        fiveQuestion.add("고수익");

    }
    public void sixQ(){
        sixQuestion.add("5% 미만");
        sixQuestion.add("5%");
        sixQuestion.add("10%");
        sixQuestion.add("20%");
        sixQuestion.add("30% 이상");

    }
    public void sevenQ(){
        sevenQuestion.add("경험 없음");
        sevenQuestion.add("1년 미만");
        sevenQuestion.add("3년 미만");
        sevenQuestion.add("5년 미만");
        sevenQuestion.add("5년 이상");
    }



    public void listadd(){

        oneQuestion.add("10~20대");
        oneQuestion.add("30대");
        oneQuestion.add("40대");
        oneQuestion.add("50대");
        oneQuestion.add("60대 이상");

        twoQuestion.add("2500만원 이하");
        twoQuestion.add("2500만원 이상 5000만원 이하");
        twoQuestion.add("5000만원 이상 7500만원 이하");
        twoQuestion.add("7500만원 이상 1억원 이하");
        twoQuestion.add("1억원 이상~");

        threeQuestion.add("1천만원 이하");
        threeQuestion.add("2천만원");
        threeQuestion.add("3천만원");
        threeQuestion.add("5천만원");
        threeQuestion.add("1억 이상");

        fourQuestion.add("단기투자");
        fourQuestion.add("1년 정도");
        fourQuestion.add("2년 정도");
        fourQuestion.add("3년 정도");
        fourQuestion.add("장기 투자");

        fiveQuestion.add("저수익");
        fiveQuestion.add("저수익 저위험");
        fiveQuestion.add("중간수익 중간위험");
        fiveQuestion.add("고수익 고위험");
        fiveQuestion.add("고수익");

        sixQuestion.add("5% 미만");
        sixQuestion.add("5%");
        sixQuestion.add("10%");
        sixQuestion.add("20%");
        sixQuestion.add("30% 이상~");

        sevenQuestion.add("경험 없음");
        sevenQuestion.add("1년 미만");
        sevenQuestion.add("3년 미만");
        sevenQuestion.add("5년 미만");
        sevenQuestion.add("5년 이상");
    }

}
