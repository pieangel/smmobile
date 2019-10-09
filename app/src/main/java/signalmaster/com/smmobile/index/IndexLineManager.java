package signalmaster.com.smmobile.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class IndexLineManager implements Serializable {

    private static volatile IndexLineManager mMoleInstance;

    public static IndexLineManager getInstance() {
        if (mMoleInstance == null) { //if there is no instance available... create new one
            synchronized (IndexLineManager.class) {
                if (mMoleInstance == null) mMoleInstance = new IndexLineManager();
            }
        }
        return mMoleInstance;
    }


    //내부 외부를 구분 못함
    private ArrayList<String> addIndicatorList = new ArrayList<>();

    public ArrayList<String> getAddIndicatorList() {
        return addIndicatorList;
    }

    public void setAddIndicatorList(ArrayList<String> addIndicatorList) {
        this.addIndicatorList = addIndicatorList;
    }


    private ArrayList<String> innerIndicatorList = new ArrayList<>();

    public ArrayList<String> getInnerIndicatorList() {
        return innerIndicatorList;
    }

    public void setInnerIndicatorList(ArrayList<String> innerIndicatorList) {
        this.innerIndicatorList = innerIndicatorList;
    }

    private  ArrayList<String> outIndicatorList = new ArrayList<>();

    public ArrayList<String> getOutIndicatorList() {
        return outIndicatorList;
    }

    public void setOutIndicatorList(ArrayList<String> outIndicatorList) {
        this.outIndicatorList = outIndicatorList;
    }

    //hashmap
    private ArrayList<HashMap<String,String>> addHashIndicatorList = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getAddHashIndicatorList() {
        return addHashIndicatorList;
    }

    public void setAddHashIndicatorList(ArrayList<HashMap<String, String>> addHashIndicatorList) {
        this.addHashIndicatorList = addHashIndicatorList;
    }
}
