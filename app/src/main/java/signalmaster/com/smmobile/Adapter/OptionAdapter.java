package signalmaster.com.smmobile.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.CustomItemClickListener;
import signalmaster.com.smmobile.R;

import signalmaster.com.smmobile.SmOptionList;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.chart.SmChartType;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataManager;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {

    private Context mContext;

    private SmChartFragment chartFragment = SmChartFragment.newInstance("mock_main");


    SmOptionList optionList = new SmOptionList();
    private ArrayList<String> _optionList = (optionList.get_optionList());

    CustomItemClickListener listener;

    private static SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);

    private int selectedPosition = 0;

    private ViewHolder viewHolder = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sm_option_item, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        setViewHolder(mViewHolder);
        return mViewHolder;
    }

    public  void setBorderColor(String selCole, String regColor, int position) {
        if (viewHolder == null)
            return;
        selectedPosition = position;

        //강제로 onvindViewholder를 거치게하기위해서
        notifyDataSetChanged();
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String text = _optionList.get(position);
        holder.option.setText(text);

        //선택항목 강조
        if(selectedPosition == position){
            holder.optionLayout.setBackgroundResource(R.drawable.option_item_button_select_color);
            holder.option.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.optionLayout.setBackgroundResource(R.drawable.option_item_button_color);
            holder.option.setTextColor(Color.parseColor("#c0c0c0"));
        }


       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택항목 강조
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });*/

        /*if(!TextUtils.isEmpty(_optionList.get(position))){

        } else {

        }*/

        /*String text = _optionList.get(position);

        holder.option.setText(text);

        //선택 항목 강조
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.option_item_button_select_color);
            holder.option.setTextColor(Color.parseColor("#1E1E1E"));
        } else {
            holder.itemView.setBackgroundResource(R.drawable.option_item_button_color);
        }*/


        /*holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return _optionList.size();
    }

    public OptionAdapter(Context mContext, ArrayList<String> optionList, CustomItemClickListener listener) {
        this._optionList = optionList;
        this.mContext = mContext;
        this.listener = listener;
    }

    public ViewHolder getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(ViewHolder viewHoder) {
        this.viewHolder = viewHoder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView option;
        public LinearLayout optionLayout;


        ViewHolder(View v) {
            super(v);
            option = (TextView) v.findViewById(R.id.option);
            optionLayout = (LinearLayout) v.findViewById(R.id.optionLayout);
        }

    }


    public void setChangeStyle(int position,ViewHolder holder){
        //선택항목 강조
        if(selectedPosition == position){
            holder.optionLayout.setBackgroundResource(R.drawable.option_item_button_select_color);
            holder.option.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.optionLayout.setBackgroundResource(R.drawable.option_item_button_color);
            holder.option.setTextColor(Color.parseColor("#c0c0c0"));
        }
    }


    /*public OptionAdapter(Context context, ArrayList<String> data) {

        mListener = listener;
    }

    public void updateData(ArrayList<String> optionList) {
        _optionList.clear();
        _optionList.addAll(optionList);
        notifyDataSetChanged();
    }


    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView option;

        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            option = itemView.findViewById(R.id.option);
        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    public OptionAdapter(ArrayList<String> mainList) {
        _optionList = mainList;
    }*/

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    /*@Override
    public OptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        *//*LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.sm_option_item,parent,false);
        OptionAdapter.ViewHolder vh = new OptionAdapter.ViewHolder(view);*//*

        View v = LayoutInflater.from(context).inflate(R.layout.sm_option_item, parent, false);


        return new RowViewHolder(v, mListener);
    }*/



   /* @Override
    public int getItemCount() {
        return _optionList.size();
    }*/


}
