package signalmaster.com.smmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SiseShowAdapter extends RecyclerView.Adapter<SiseShowAdapter.ViewHolder> {



    private Context mContext;


    RecyclerViewList recyclerViewList = new RecyclerViewList();

    //private ArrayList<String> _indexSelectList = (indexLineManager.get_indexSelectList());
    private ArrayList<String> _mainTitleList = recyclerViewList.get_mainTitleList();
    private ArrayList<String> _subTitleList = recyclerViewList.get_subTitleList();

    CustomItemClickListener listener;

    @Override
    public SiseShowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sm_sise_show_ltem, parent, false);
        final SiseShowAdapter.ViewHolder mViewHolder = new SiseShowAdapter.ViewHolder(mView);
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
    public void onBindViewHolder(SiseShowAdapter.ViewHolder holder, int position) {
        if(_mainTitleList == null){
            return;
        }
        String mainTextTitle = _mainTitleList.get(position);

        if(_subTitleList == null){
            return;
        }
        String subSiseShow = _subTitleList.get(position);

        holder.mainSiseShow.setText(mainTextTitle);
        holder.subSiseShow.setText(subSiseShow);
    }

    @Override
    public int getItemCount() {
        return _mainTitleList.size();
    }

    public SiseShowAdapter(Context mContext, ArrayList<String> _mainTitleList,ArrayList<String> _subTitleList ,CustomItemClickListener listener) {
        this._mainTitleList = _mainTitleList;
        this._subTitleList = _subTitleList;
        this.mContext = mContext;
        this.listener = listener;
    }

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mainSiseShow;
        TextView subSiseShow;

        ViewHolder(View itemView) {
            super(itemView);

            mainSiseShow = itemView.findViewById(R.id.mainSiseShow);
            subSiseShow = itemView.findViewById(R.id.subSiseShow);
        }
    }

}
