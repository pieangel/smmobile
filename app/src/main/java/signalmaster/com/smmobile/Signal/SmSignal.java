package signalmaster.com.smmobile.Signal;

import java.util.Date;

import signalmaster.com.smmobile.global.SmGlobal;

public final class SmSignal {
    public SmGlobal.SmSignalSource source = SmGlobal.SmSignalSource.FromAI;
    public Date signalTime;
    public int signalIndex = 0;
    public double signalValue = 0;

    public SmGlobal.SmSignalType signalType = SmGlobal.SmSignalType.None;
}
