package signalmaster.com.smmobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.autoorder.SmAutoFragment;
import signalmaster.com.smmobile.mock.SmPrChartFragment;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmTotalPositionManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;

public class RightOptionAdapter extends RecyclerView.Adapter<RightOptionAdapter.ViewHolder> {

    public RightOptionAdapter() {
    }
    SmTotalPositionManager smTotalPositionManager = SmTotalPositionManager.getInstance();
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

    ArrayList<SmPosition> positionHashMap = smTotalPositionManager.getPositionList();

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView symbolNameTxt,positionTypeTxt,avgPriceTxt,openQtyTxt,openPLTxt;
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

            symbolNameTxt = itemView.findViewById(R.id.symbolNameTxt);
            positionTypeTxt = itemView.findViewById(R.id.positionTypeTxt);
            avgPriceTxt = itemView.findViewById(R.id.avgPriceTxt);
            openQtyTxt = itemView.findViewById(R.id.openQtyTxt);
            openPLTxt = itemView.findViewById(R.id.openPLTxt);

            // 뷰 객체에 대한 참조. (hold strong reference)
            /*content = itemView.findViewById(R.id.content);
            title = itemView.findViewById(R.id.title);*/
            // = itemView.findViewById(R.id.);
        }
    }

    private int selectedPosition = 0;


    //생성자에서 데이터 리스트 객체를 전달받음.
    RightOptionAdapter(ArrayList<SmPosition> positionHashMap) {
        this.positionHashMap = positionHashMap;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public RightOptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.right_option_item, parent, false);
        RightOptionAdapter.ViewHolder vh = new RightOptionAdapter.ViewHolder(view);

        return vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RightOptionAdapter.ViewHolder holder, final int position) {
        /*String title = mData.get(position);
        holder.title.setText(title); */

        //String content = nonData.get(position);
        SmSymbolManager symbolManager = SmSymbolManager.getInstance();
        final SmSymbol symbol = symbolManager.findSymbol(positionHashMap.get(position).symbolCode);

        String symbolCode = positionHashMap.get(position).symbolCode;
        String positionType = positionHashMap.get(position).positionType.toString();
        String avgPrice = String.format(Locale.getDefault(),"%.2f",positionHashMap.get(position).avgPrice);
        String openQty = Integer.toString(positionHashMap.get(position).openQty);
        String openPL = String.format(Locale.getDefault(),"%.2f",positionHashMap.get(position).openPL);

        //수량
        if(positionHashMap.get(position).openQty > 0){
            holder.openQtyTxt.setTextColor(Color.parseColor("#B14333"));
        } else {
            holder.openQtyTxt.setTextColor(Color.parseColor("#3880E0"));
        }

        //순손익
        if(positionHashMap.get(position).openPL > 0){
            holder.openPLTxt.setTextColor(Color.parseColor("#46962B"));
        }  else if(positionHashMap.get(position).openPL == 0){
            holder.openPLTxt.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.openPLTxt.setTextColor(Color.parseColor("#B14333"));
        }


        //구분
        if(positionType.equals("Buy")){
            positionType = "매수";
            holder.positionTypeTxt.setTextColor(Color.parseColor("#B14333"));
        } else {
            positionType = "매도";
            holder.positionTypeTxt.setTextColor(Color.parseColor("#46962B"));
        }


        //holder.symbolNameTxt.setText(smTotalPositionManager.getPositionList());
        holder.symbolNameTxt.setText(symbolCode);
        holder.positionTypeTxt.setText(positionType);
        holder.avgPriceTxt.setText(avgPrice);
        holder.openQtyTxt.setText(openQty);
        holder.openPLTxt.setText(openPL);

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
        return positionHashMap.size();
    }

}
