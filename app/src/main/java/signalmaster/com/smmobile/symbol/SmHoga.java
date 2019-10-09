package signalmaster.com.smmobile.symbol;
public class SmHoga {
    public SmHoga() {
        for(int i = 0; i < 5; ++i) {
            hogaItem[i] = new SmHogaItem();
        }
    }
    public String symbolCode;
    /// <summary>
    /// 시간 - 해외선물은 해외시간
    /// </summary>
    public String time = "";
    /// <summary>
    /// 국내날짜
    /// </summary>
    public String domesticDate = "";
    /// <summary>
    /// 국내 시간
    /// </summary>
    public String domesticTime = "";
    /// <summary>
    /// 매도총호가수량
    /// </summary>
    public int	totSellQty = 0;
    /// <summary>
    /// 매수총호가수량
    /// </summary>
    public int	totBuyQty = 0;
    /// <summary>
    /// 매도총호가건수
    /// </summary>
    public int	totSellCnt = 0;
    /// <summary>
    /// 매수총호가건수
    /// </summary>
    public int	totBuyCnt = 0;
    /// <summary>
    /// 호가 아이템
    /// </summary>
    public SmHogaItem[] hogaItem = new SmHogaItem[5];

}
