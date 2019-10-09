//******************************************************************************
// SCICHART® Copyright SciChart Ltd. 2011-2017. All rights reserved.
//
// Web: http://www.scichart.com
// Support: support@scichart.com
// Sales:   sales@scichart.com
//
// PriceBar.java is part of the SCICHART® Examples. Permission is hereby granted
// to modify, create derivative works, distribute and publish any part of this source
// code whether for commercial, private or personal use.
//
// The SCICHART® examples are distributed in the hope that they will be useful, but
// without any warranty. It is provided "AS IS" without warranty of any kind, either
// expressed or implied.
//******************************************************************************

package signalmaster.com.smmobile.data;

import java.util.Date;

public class PriceBar {
    private boolean added = false;
    private String symbolCode;
    private Date date = new Date();
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;

    private long _decimal;

    private long test;
    private int cycle = 1;


    public boolean isEqual(PriceBar src) {
        if (src.close != this.close)
            return false;
        if (src.high != this.high)
            return false;
        if (src.low != this.low)
            return false;
        if (src.open != this.open)
            return false;
        if (src.volume != this.volume)
            return false;
        return true;
    }


    public PriceBar(double open, double high, double low, double close, long volume) {
        this.setOpen(open);
        this.setHigh(high);
        this.setLow(low);
        this.setClose(close);
        this.setVolume(volume);
    }

    public PriceBar(Date date, double open, double high, double low, double close, long volume) {
        this.setDate(date);
        this.setOpen(open);
        this.setHigh(high);
        this.setLow(low);
        this.setClose(close);
        this.setVolume(volume);
    }

    public PriceBar(boolean add, Date date, double open, double high, double low, double close, long volume) {
        this.setAdded(add);
        this.setDate(date);
        this.setOpen(open);
        this.setHigh(high);
        this.setLow(low);
        this.setClose(close);
        this.setVolume(volume);
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public long getVolume() {
        return volume;
    }

    public long get_decimal() {
        return _decimal;
    }

    public void set_decimal(long _decimal) {
        this._decimal = _decimal;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getTest() {
        return test;
    }

    public void setTest(long test) {
        this.test = test;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public String getSymbolCode() {
        return symbolCode;
    }

    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }
}
