package signalmaster.com.smmobile.autoorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.Adapter.MainbarAdapter;
import signalmaster.com.smmobile.Adapter.OptionAdapter;
import signalmaster.com.smmobile.CustomItemClickListener;
import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RecyclerViewList;
import signalmaster.com.smmobile.SmMainbarList;
import signalmaster.com.smmobile.SmOptionList;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;
import signalmaster.com.smmobile.symbol.SmSymbolSelector;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    /*SmOptionList optionList = new SmOptionList();
    private ArrayList<String> _optionList = (optionList.get_optionList());*/
    RecyclerViewList recyclerViewList = new RecyclerViewList();

    private ArrayList<String> orderTitle = recyclerViewList.getOrderTitleList();
    private ArrayList<String> orderTitleValue = (recyclerViewList.getOrderTitleValueList());

    CustomItemClickListener listener;

    @Override
    public OrderRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        final OrderRecyclerViewAdapter.ViewHolder mViewHolder = new OrderRecyclerViewAdapter.ViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }



    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(OrderRecyclerViewAdapter.ViewHolder holder, int position) {
        String textTitle = orderTitle.get(position);
        holder.title.setText(textTitle);

        String textValue = orderTitleValue.get(position);
        holder.value.setText(textValue);
    }

    @Override
    public int getItemCount() {
        return orderTitle.size();
    }

    public OrderRecyclerViewAdapter(Context mContext, ArrayList<String> orderTitle,ArrayList<String> orderTitleValue ,CustomItemClickListener listener) {
        this.orderTitle = orderTitle;
        this.orderTitleValue = orderTitleValue;
        this.mContext = mContext;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView value;

        ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.orderTitle);
            value = (TextView)v.findViewById(R.id.orderValue);
        }
    }





}