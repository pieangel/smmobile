package signalmaster.com.smmobile.OrderUI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.autoorder.SmAutoFragment;
import signalmaster.com.smmobile.mock.SmPrChartFragment;
import signalmaster.com.smmobile.order.SmOrder;
import signalmaster.com.smmobile.order.SmOrderState;
import signalmaster.com.smmobile.order.SmTotalOrderManager;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmPositionType;
import signalmaster.com.smmobile.position.SmTotalPositionManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    public OrderAdapter() {
    }
    SmTotalOrderManager smTotalOrderManager = SmTotalOrderManager.getInstance();
    SmPrChartFragment prChartFragment;
    SmAutoFragment autoFragment;
    TextView textView;
    /*public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }*/

    //private ArrayList<String> mData = null;

    /*RecyclerViewList recyclerViewList = new RecyclerViewList();
    private ArrayList<String> nonData = recyclerViewList.getNonList();*/

    ArrayList<SmOrder> orderArrayList = smTotalOrderManager.getOrderList();

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView symbolNameTxt,positionTypeTxt,orderPricetext,orderAmountText,orderStateText;
        //LinearLayout;
        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });*/

            symbolNameTxt = itemView.findViewById(R.id.order_symbol_code_text);
            positionTypeTxt = itemView.findViewById(R.id.order_position_text);
            orderPricetext = itemView.findViewById(R.id.order_price_text);
            orderAmountText = itemView.findViewById(R.id.order_amount_text);
            orderStateText = itemView.findViewById(R.id.order_state_text);

            // 뷰 객체에 대한 참조. (hold strong reference)
            /*content = itemView.findViewById(R.id.content);
            title = itemView.findViewById(R.id.title);*/
            // = itemView.findViewById(R.id.);
        }
    }

    private int selectedPosition = 0;


    //생성자에서 데이터 리스트 객체를 전달받음.
    public OrderAdapter(ArrayList<SmOrder> orders) {
        this.orderArrayList = orders;
        notifyDataSetChanged();
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.order_layout, parent, false);
        OrderAdapter.ViewHolder vh = new OrderAdapter.ViewHolder(view);

        return vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, final int position) {
        /*String title = mData.get(position);
        holder.title.setText(title); */

        //String content = nonData.get(position);
        SmSymbolManager symbolManager = SmSymbolManager.getInstance();
        final SmSymbol symbol = symbolManager.findSymbol(orderArrayList.get(position).symbolCode);


        String symbolCode = orderArrayList.get(position).symbolCode;
        String positionType = orderArrayList.get(position).positionType.toString();
        double div = Math.pow(10, symbol.decimal);
        double orderValue = (orderArrayList.get(position).orderPrice / div);
        String order_price = String.format(Locale.getDefault(), symbol.getFormat() ,orderValue);
        String order_amount = Integer.toString(orderArrayList.get(position).orderAmount);
        SmOrderState order_state = orderArrayList.get(position).orderState;

        //수량
        if(orderArrayList.get(position).positionType == SmPositionType.Buy){
            holder.orderAmountText.setTextColor(Color.parseColor("#B14333"));
        } else {
            holder.orderAmountText.setTextColor(Color.parseColor("#3880E0"));
        }

        //구분
        if(orderArrayList.get(position).positionType == SmPositionType.Buy){
            positionType = "매수";
            holder.positionTypeTxt.setTextColor(Color.parseColor("#B14333"));
        } else {
            positionType = "매도";
            holder.positionTypeTxt.setTextColor(Color.parseColor("#46962B"));
        }


        //holder.symbolNameTxt.setText(smTotalPositionManager.getPositionList());
        holder.symbolNameTxt.setText(symbolCode);
        holder.positionTypeTxt.setText(positionType);
        holder.orderPricetext.setText(order_price);
        holder.orderAmountText.setText(order_amount);
        switch (order_state) {
            case None:
                holder.orderStateText.setText("상태없음");
                break;
            case Ledger:
                holder.orderStateText.setText("원장접수");
                break;
            case ConfirmNew:
                holder.orderStateText.setText("신규주문확인");
                break;
            case ConfirmModify:
                holder.orderStateText.setText("정정주문확인");
                break;
            case ConfirmCancel:
                holder.orderStateText.setText("취소주문확인");
                break;
            case Accepted:
                holder.orderStateText.setText("접수확인");
                break;
            case Filled:
                holder.orderStateText.setText("체결확인");
                break;
            case Settled:
                holder.orderStateText.setText("청산확인");
                break;
        }


        holder.symbolNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //positionHashMap.get(position).symbolCode
                /*SmArgManager smArgManager = SmArgManager.getInstance();
                smArgManager.registerToMap("");*/
                SmArgManager argManager = SmArgManager.getInstance();
                prChartFragment = (SmPrChartFragment)argManager.getVal("positionList","prFragment");
                if(prChartFragment != null){
                    //prChartFragment.onChangeSymbol(positionHashMap.get(position).symbolCode);
                    //textView = (TextView)argManager.getVal("positionList","mockTxt");
                    prChartFragment.onChangeSymbol(symbol);
                    ((Activity)v.getContext()).finish();
                }
                autoFragment = (SmAutoFragment)argManager.getVal("positionList","auto_fragment");
                if(autoFragment != null){
                    textView = (TextView)argManager.getVal("positionList","orderTxt");
                    autoFragment.onAutoSymbolChanged(symbol);
                    ((Activity)v.getContext()).finish();
                }

            }
        });

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
        return orderArrayList.size();
    }

}
