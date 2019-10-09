package signalmaster.com.smmobile.portfolio;

import java.io.Serializable;
import java.util.ArrayList;

import signalmaster.com.smmobile.index.IndexLineManager;

public class PortfolioManager implements Serializable {

    private static volatile PortfolioManager mMoleInstance;

    public static PortfolioManager getInstance() {
        if (mMoleInstance == null) { //if there is no instance available... create new one
            synchronized (PortfolioManager.class) {
                if (mMoleInstance == null) mMoleInstance = new PortfolioManager();
            }
        }
        return mMoleInstance;
    }

    public void portFolio(){

    }





    private ArrayList<Portfolio> portList = new ArrayList();

    public ArrayList<Portfolio> getPortList() {
        defaultPort();
        for(int i=0;i<titleList.size();i++) {
            Portfolio portfolio = new Portfolio(titleList.get(i), symbol1.get(i), symbol2.get(i),symbol3.get(i));
            portList.add(portfolio);
        }
        return portList;
    }

    public void setPortList(ArrayList<Portfolio> portList) {
        this.portList = portList;
    }

    public void defaultPort(){

        titleList.add("P1");
        titleList.add("P2");
        titleList.add("P3");

        symbol1.add("원유1-1");
        symbol1.add("원유1-2");
        symbol1.add("원유1-3");

        symbol2.add("원유2-1");
        symbol2.add("원유2-2");
        symbol2.add("원유2-3");

        symbol3.add("원유3-1");
        symbol3.add("원유3-2");
        symbol3.add("원유3-3");
    }


    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> symbol1 = new ArrayList<>();
    ArrayList<String> symbol2 = new ArrayList<>();
    ArrayList<String> symbol3 = new ArrayList<>();




}
