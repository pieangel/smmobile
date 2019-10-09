package signalmaster.com.smmobile.sise;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.market.SmMarket;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class SubbarAdapter extends RecyclerView.Adapter<SubbarAdapter.ViewHolder>  {

    private int selectedPosition = 0;

    private SubListAdapter _subListAdapter = null;

    public SubbarAdapter() {

    }

    private SmCurrentFragment _currentFragment = null;

    public void set_currentFragment(SmCurrentFragment _currentFragment) {
        this._currentFragment = _currentFragment;
    }

    public SmCurrentFragment get_currentFragment() {
        return _currentFragment;
    }

    private SmSubbarFragment parentFragment = null;
    SmMarketManager smMarketManager = SmMarketManager.getInstance();
    private ArrayList<SmMarket> _marketList= (smMarketManager.get_marketList());

    public SmSubbarFragment getParentFragment() {
        return parentFragment;
    }

    public void setParentFragment(SmSubbarFragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView sub;
        LinearLayout subbarLayout;

        public SubbarAdapter parentAdapter = null;
        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            sub = itemView.findViewById(R.id.name);
            subbarLayout = itemView.findViewById(R.id.subbarLayout);
        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    public SubbarAdapter(ArrayList<SmMarket> list) {
        _marketList = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public SubbarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.subbar_listitem,parent,false);
        SubbarAdapter.ViewHolder vh = new SubbarAdapter.ViewHolder(view);
        vh.parentAdapter = this;
        return  vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final SubbarAdapter.ViewHolder holder, final int position) {
        String text = _marketList.get(position).name;

        holder.sub.setText(text);


        if(selectedPosition == position){
            holder.subbarLayout.setBackgroundColor(Color.parseColor("#353535"));
            holder.sub.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.subbarLayout.setBackgroundColor(Color.parseColor("#121212"));
            holder.sub.setTextColor(Color.parseColor("#c0c0c0"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context context = view.getContext();
                // Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                // ((MainActivity)v.getContext()).onClickMarketItem(position);

                /*SubbarAdapter parent = holder.parentAdapter;
                if (parent == null || parent.get_currentFragment() == null)
                    return;
                parent.get_currentFragment().setMarketPosition(position);*/

                SubbarAdapter parent = holder.parentAdapter;
                if (parent == null || parent.get_currentFragment() == null)
                    return;
                parent.get_currentFragment().setMarketPosition(position);
                SmMarket mrkt = _marketList.get(position);

                //OnMarketChanged(symList);
                SmCurrentFragment fragment = get_currentFragment();
                if (fragment != null) {
                    ArrayList<SmSymbol> symList =  mrkt.getRecentSymbolListFromCategory();
                    _subListAdapter = new SubListAdapter(symList);
                    //notifyItemRemoved 를 해줘서 한번 비워준다.
                    _subListAdapter.notifyItemRemoved(position);
                    fragment.OnMarketChanged(symList);

                    //선택 항목 강조
                    selectedPosition = position;
                    notifyDataSetChanged();

                }
             }
        });


    }

    @Override
    public int getItemCount() {
        return _marketList.size();
    }

    /*public SubbarAdapter(Context context, ArrayList<String> name) {
        mNames = name;
       // smMarket.name = _marketList.get(0).name;
        mContext = context;
    }*/

    //항목 목록
    /*@Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subbar_listitem, parent, false);
        return new ViewHolder(view);
    }*/






}
