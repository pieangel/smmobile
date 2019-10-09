package signalmaster.com.smmobile;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainRightOptionAdapter extends RecyclerView.Adapter<MainRightOptionAdapter.ViewHolder> {

    public MainRightOptionAdapter() {
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    private ArrayList<String> mData = null;

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        //LinearLayout;
        //public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            //mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });

            // 뷰 객체에 대한 참조. (hold strong reference)
            content = itemView.findViewById(R.id.content);
            // = itemView.findViewById(R.id.);
        }
    }

    private int selectedPosition = 0;


    //생성자에서 데이터 리스트 객체를 전달받음.
    MainRightOptionAdapter(ArrayList<String> list) {
        mData = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public MainRightOptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_right_option_item, parent, false);
        MainRightOptionAdapter.ViewHolder vh = new MainRightOptionAdapter.ViewHolder(view);

        return vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MainRightOptionAdapter.ViewHolder holder, final int position) {
        String text = mData.get(position);
        holder.content.setText(text);

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
        return mData.size();
    }

}
