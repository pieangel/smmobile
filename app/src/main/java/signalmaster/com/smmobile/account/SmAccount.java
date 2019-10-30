package signalmaster.com.smmobile.account;
/*
	std::string _AccountNo;
	std::string _AccountName;
	// 사용자 아이디 - 반드시 이메일로 한다.
	std::string _UserID;
	std::map<std::string, SmPosition*> _PositionMap;
	// 기초자산은 1억원
	double _InitialBalance = 100000000;
	// 매매로 이루어진 수익
	double _TradePL = 0.0;
	// 현재 청산되지 않은 주문으로 인한 평가 손익
	double _OpenPL = 0.0;
	std::string _Password;
 */

public class SmAccount {
    public String accountNo;
    public String accountName;
    public double inital_balance;
    public double trade_pl;
    public double open_pl;
    //총손익
    public double total_pl;
    public String password;
    public double feeCount;
    public double total_trade_pl;
    // 계좌 타입
    public int acccountType;
}
