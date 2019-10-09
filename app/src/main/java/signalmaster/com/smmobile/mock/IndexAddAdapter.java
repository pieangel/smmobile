package signalmaster.com.smmobile.mock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RecyclerViewList;
import signalmaster.com.smmobile.SmIndexSelector;
import signalmaster.com.smmobile.index.IndexLineManager;

public class IndexAddAdapter extends RecyclerView.Adapter<IndexAddAdapter.ViewHolder> {

    int selectedPosition = 0;

    public IndexAddAdapter() {

    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public SmIndexSelector smIndexSelector = null;
    //RecyclerViewList recyclerViewList = new RecyclerViewList();
    //ArrayList<String> addIndicatorList = recyclerViewList.getAddIndicatorList();
    IndexLineManager indexLineManager = IndexLineManager.getInstance();
    ArrayList<String> addIndicatorList = indexLineManager.getAddIndicatorList();

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView settingName;
        LinearLayout closeLout;
        //ImageView indexSetting;
        //CheckBox checkBox;


        //public IndexAddAdapter parentAdapter = null;

        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            settingName = itemView.findViewById(R.id.settingName);
            closeLout = itemView.findViewById(R.id.closeLout);
            //indexSetting = itemView.findViewById(R.id.indexSetting);
            //checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });

        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    public IndexAddAdapter(ArrayList<String> list) {
        addIndicatorList = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public IndexAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.index_add_item,parent,false);
        IndexAddAdapter.ViewHolder vh = new IndexAddAdapter.ViewHolder(view);
        //vh.parentAdapter = this;

        return  vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final IndexAddAdapter.ViewHolder holder, final int position) {

        if(addIndicatorList == null){
            return;
        }

        String name = addIndicatorList.get(position);
        holder.settingName.setText(name);

        //position별 리스트 삭제
        holder.closeLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedPosition = position;
                addIndicatorList.remove(selectedPosition);
                notifyDataSetChanged();

                /*switch (position){
                    case 0:
                        addIndicatorList.remove(position);
                        break;
                    case 1:
                        break;
                }*/
            }
        });

        /*String textName = _indexSelectList.get(position);


        holder.indexName.setText(textName);*/

    }

    @Override
    public int getItemCount() {
        return addIndicatorList.size();
    }


}
