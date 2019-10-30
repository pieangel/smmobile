package signalmaster.com.smmobile;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.global.SmConst;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmTotalPositionManager;

public class RightOptionAdapter2 extends RecyclerView.Adapter<RightOptionAdapter2.ViewHolder> {

    public RightOptionAdapter2() {
    }

    //SmTotalPositionManager smTotalPositionManager = SmTotalPositionManager.getInstance();
    ArrayList<SmPosition> positionHashMap = new ArrayList<>();

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView symbolNameTxt,totalPLTxt,feeTxt,openPLTxt;
        //LinearLayout;
        //public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            //mView = itemView;


            symbolNameTxt = itemView.findViewById(R.id.symbolNameTxt);
            totalPLTxt = itemView.findViewById(R.id.totalPLTxt);
            feeTxt = itemView.findViewById(R.id.feeTxt);
            openPLTxt = itemView.findViewById(R.id.openPLTxt);

            // 뷰 객체에 대한 참조. (hold strong reference)
            /*content = itemView.findViewById(R.id.content);
            title = itemView.findViewById(R.id.title);*/
            // = itemView.findViewById(R.id.);
        }
    }

    private int selectedPosition = 0;


    //생성자에서 데이터 리스트 객체를 전달받음.
    RightOptionAdapter2(ArrayList<SmPosition> positionList) {
        positionHashMap = positionList;
    }

    public void setPositionHashMap(ArrayList<SmPosition> positionArrayList) {
        positionHashMap = positionArrayList;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public RightOptionAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.right_option_item2, parent, false);
        RightOptionAdapter2.ViewHolder vh = new RightOptionAdapter2.ViewHolder(view);

        return vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RightOptionAdapter2.ViewHolder holder, final int position) {
        /*String title = mData.get(position);
        holder.title.setText(title); */

        //String tradePL = Double.toString()

        /*holder.symbolNameTxt.setText(positionHashMap.get(position).symbolCode);
        holder.totalPLTxt.setText(Double.toString(accountList.get(position).trade_pl+accountList.get(position).open_pl));
        holder.feeTxt.setText("0");
        holder.openPLTxt.setText(Double.toString(accountList.get(position).trade_pl+accountList.get(position).open_pl-0));*/
        String symbolCode = positionHashMap.get(position).symbolCode;
        String totalPL = String.format(Locale.getDefault(),"%.2f",positionHashMap.get(position).tradePL + positionHashMap.get(position).openPL);
        double fee = positionHashMap.get(position).feeCount * SmConst.getFee(symbolCode);
        //총손익 - 수수료 = 순손익
        double pure_profit_loss = positionHashMap.get(position).tradePL + positionHashMap.get(position).openPL - fee;
        String openPL = String.format(Locale.getDefault(),"%.0f",pure_profit_loss);
        String symbol_fee = String.format(Locale.getDefault(),"%.0f", fee);
        holder.symbolNameTxt.setText(symbolCode);
        holder.totalPLTxt.setText(totalPL);
        holder.feeTxt.setText(symbol_fee);
        holder.openPLTxt.setText(openPL);

        String purePL = String.format(Locale.getDefault(),"%.0f",pure_profit_loss);
        //순손익
        if(pure_profit_loss < 0){
            holder.openPLTxt.setTextColor(Color.parseColor("#46962B"));
        }  else if(pure_profit_loss == 0){
            holder.openPLTxt.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.openPLTxt.setTextColor(Color.parseColor("#B14333"));
        }

        //선택항목 강조
        /*if(selectedPosition == position){
            holder.mainbarLayout.setBackgroundColor(Color.parseColor("#2292E6"));
            holder.main.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.mainbarLayout.setBackgroundColor(Color.parseColor("#2B3652"));
            holder.main.setTextColor(Color.parseColor("#7A849D"));
        }*/

    }

    @Override
    public int getItemCount() {
        return positionHashMap.size();
    }

}
