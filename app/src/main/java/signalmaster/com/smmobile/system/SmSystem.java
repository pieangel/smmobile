package signalmaster.com.smmobile.system;

import java.util.ArrayList;
import java.util.Random;

import signalmaster.com.smmobile.Signal.SmSignal;
import signalmaster.com.smmobile.data.PriceBar;
import signalmaster.com.smmobile.data.PriceSeries;
import signalmaster.com.smmobile.global.SmGlobal;

public class SmSystem {

    private final Random random = new Random();

    //고유코드
    private String _code = "";

    public SmSystem(String code_) {
        _code = code_;
    }

    private ArrayList<SmSignal> _signalList = new ArrayList<>();

    public ArrayList<SmSignal> get_signalList() {
        return _signalList;
    }

    public void set_signalList(ArrayList<SmSignal> _signalList) {
        this._signalList = _signalList;
    }

    public ArrayList<SmSignal> CreateRandomSignals(PriceSeries series) {
        int signalCount = 80;
        SmGlobal.SmSignalType oldSignalType = SmGlobal.SmSignalType.Buy;
        int accIndex = 0;
        for(int i = 0; i < signalCount; ++i) {
            int ranint = random.nextInt(20);
            accIndex += ranint;
            if (accIndex >= series.size())
                break;

            PriceBar bar = series.get(accIndex);
            SmSignal signal = new SmSignal();
            signal.signalIndex = accIndex;

            if (oldSignalType == SmGlobal.SmSignalType.Buy) {
                signal.signalType = SmGlobal.SmSignalType.Sell;
                signal.signalValue = bar.getLow();
            }
            else {
                signal.signalType = SmGlobal.SmSignalType.Buy;
                signal.signalValue = bar.getHigh();
            }
            oldSignalType = signal.signalType;
            _signalList.add(signal);
        }

        return _signalList;
    }
}
