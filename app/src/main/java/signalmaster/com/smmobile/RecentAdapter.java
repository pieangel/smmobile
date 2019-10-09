package signalmaster.com.smmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    public RecentAdapter() {
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    //private ArrayList<String> mData = null;
    RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> recentTitle = recyclerViewList.getRecentTitle();
    ArrayList<String> recentContent = recyclerViewList.getRecentContent();
    ArrayList<String> recentRevenue = recyclerViewList.getRecentRevenue();
    ArrayList<String> recentInvest = recyclerViewList.getRecentInvest();

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        //추후에 바꿔야함  cf_close_value,cf_close_ratio 이름
        TextView title,content,recentRevenue,recentInvest;
        ImageView arrowImg;


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
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            recentRevenue = itemView.findViewById(R.id.recentRevenue);
            recentInvest = itemView.findViewById(R.id.recentInvest);
            arrowImg = itemView.findViewById(R.id.arrowImg);

        }
    }

    private int selectedPosition = 0;


    //생성자에서 데이터 리스트 객체를 전달받음.
    RecentAdapter(ArrayList<String> list) {
        recentTitle = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public RecentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recent_adpater_item, parent, false);
        RecentAdapter.ViewHolder vh = new RecentAdapter.ViewHolder(view);

        return vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecentAdapter.ViewHolder holder, final int position) {
        String title = recentTitle.get(position);
        String content = recentContent.get(position);
        String revenue = recentRevenue.get(position);
        String invest = recentInvest.get(position);
        holder.title.setText(title);
        holder.content.setText(content);
        holder.recentRevenue.setText(revenue);
        holder.recentInvest.setText(invest);

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
        return recentTitle.size();
    }

}
