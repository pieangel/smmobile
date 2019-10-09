package signalmaster.com.smmobile.crobo;

import java.util.ArrayList;
import java.util.HashMap;

public class Content {
    /*private HashMap<Integer,String> contentList = new HashMap<>();

    public HashMap<Integer, String> getContentList() {
        addContent();
        return contentList;
    }

    public void setContentList(HashMap<Integer, String> contentList) {
        this.contentList = contentList;
    }

    public void addList(){

    }

    public void addContent(){
        contentList.put(1,"10~20대");
        contentList.put(1,"30대");
        contentList.put(1,"40대");
        contentList.put(1,"50대");
        contentList.put(1,"60대 이상");
        contentList.put(2,"저수익");
        contentList.put(2,"저 수익 저 위험");
        contentList.put(2,"중간 수익 중간 위험");
        contentList.put(2,"고 수익 고위험");
        contentList.put(2,"고수익");
        contentList.put(3,"5%미만");
        contentList.put(3,"5%");
        contentList.put(3,"10%");
        contentList.put(3,"15%");
        contentList.put(3,"20%이상");
        contentList.put(4,"경험없음");
        contentList.put(4,"1년 미만");
        contentList.put(4,"3년 미만");
        contentList.put(4,"5년 미만");
        contentList.put(4,"5년 이상");
        contentList.put(5,"단기 투자");
        contentList.put(5,"1년 정도");
        contentList.put(5,"2년 정도");
        contentList.put(5,"3년 정도");
        contentList.put(5,"장기 투자");
        contentList.put(6,"1천만원 이하");
        contentList.put(6,"2천 만원");
        contentList.put(6,"3천 만원");
        contentList.put(6,"5천 만원");
        contentList.put(6,"1억 이상");
    }*/
    //제목 , 문제 목록
    //private HashMap<String,ArrayList<String>> contentList = new HashMap<>();
    private HashMap<String,String> contentList = new HashMap<>();

    public HashMap<String, String> getContentList() {
        addContent();
        return contentList;
    }

    public void setContentList(HashMap<String, String> contentList) {
        this.contentList = contentList;
    }

    public void addContent(){
        contentList.put("age","10대~20대");
        contentList.put("age","30대");
        contentList.put("age","40대");
        contentList.put("age","50대");
        contentList.put("age","60대 이상");

        contentList.put("danger","저수익");
        contentList.put("danger","저 수익 \n 저 위험");
        contentList.put("danger","중간 수익 \\n 중간 위험");
        contentList.put("danger","고 수익 \\n 고 위험");
        contentList.put("danger","고수익");

        contentList.put("avoid","5%미만");
        contentList.put("avoid","5%");
        contentList.put("avoid","10%");
        contentList.put("avoid","15%");
        contentList.put("avoid","20%이상");

        contentList.put("exp","경험 없음");
        contentList.put("exp","1년 미만");
        contentList.put("exp","3년 미만");
        contentList.put("exp","5년 미만");
        contentList.put("exp","5년 이상");

        contentList.put("investTerm","단기투자");
        contentList.put("investTerm","1년 정도");
        contentList.put("investTerm","2년 정도");
        contentList.put("investTerm","3년 정도");
        contentList.put("investTerm","장기 투자");

        contentList.put("investMoney","1천 만원 \n 이하");
        contentList.put("investMoney","2천 만원");
        contentList.put("investMoney","3천 만원");
        contentList.put("investMoney","5천 만원");
        contentList.put("investMoney","1억 이상");
    }

    /*public void addTitleList(){
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("내 연령대를 선택하세요.");
        titleList.add("위험과 수익의 밸런스");
        titleList.add("손실 회피 성향");
        titleList.add("파생 상품 투자 경험");
        titleList.add("예상 투자 기간");
        titleList.add("예상 투자 금액");
    }*/

    /*public void addContenList(){
        ArrayList<String> arrayAge = new ArrayList();
        arrayAge.add("10대~20대");
        arrayAge.add("30대");
        arrayAge.add("40대");
        arrayAge.add("50대");
        arrayAge.add("60대 이상");

        ArrayList<String> arrayDanger = new ArrayList<>();
        arrayDanger.add("저수익");
        arrayDanger.add("저 수익 \n 저 위험");
        arrayDanger.add("중간 수익 \n 중간 위험");
        arrayDanger.add("고 수익 \n 고 위험");
        arrayDanger.add("고수익");

        ArrayList<String> arrayAvoid = new ArrayList<>();
        arrayAvoid.add("5%미만");
        arrayAvoid.add("5%");
        arrayAvoid.add("10%");
        arrayAvoid.add("15%");
        arrayAvoid.add("20%이상");

        ArrayList<String> arrayExp = new ArrayList<>();
        arrayExp.add("경험 없음");
        arrayExp.add("1년 미만");
        arrayExp.add("3년 미만");
        arrayExp.add("5년 미만");
        arrayExp.add("5년 이상");

    }*/
}
