package signalmaster.com.smmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SiseListAdapter extends RecyclerView.Adapter<SiseListAdapter.ViewHolder> {

    public SiseListAdapter() {

    }

    RecyclerViewList recyclerViewList = new RecyclerViewList();

    private ArrayList<String> _siseRecyclerViewList = recyclerViewList.get_siseRecyclerViewList();




    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView siseName;

        //public SiseListAdapter parentAdapter = null;

        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            siseName = itemView.findViewById(R.id.name);
        }
    }

    /*//생성자에서 데이터 리스트 객체를 전달받음.
    public SiseListAdapter(ArrayList<String> list) {
        _indexSelectList = list;
    }*/

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public SiseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.symbol_detail_listitem ,parent,false);
        SiseListAdapter.ViewHolder vh = new SiseListAdapter.ViewHolder(view);
        //vh.parentAdapter = this;

        return  vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SiseListAdapter.ViewHolder holder, final int position) {

        if(_siseRecyclerViewList == null){
            return;
        }
        String mainTextTitle = _siseRecyclerViewList.get(position);


        holder.siseName.setText(mainTextTitle);

       /* if (_symList == null)
            return;
        SmSymbol curSymbol = _symList.get(position);
        String textTitle = curSymbol.seriesNmKr;
        // String textTitle2 = symList.get(1).seriesNmKr;
        //   System.out.println(textTitle2);
        String[] splittextTitle = curSymbol.getSplitName(textTitle);
        String[] splittextContent = curSymbol.getSplitName(textTitle);
        holder.f_title.setText(splittextTitle[0]);
        holder.f_content.setText(splittextContent[1]);*/

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //#206CA6
            }
        });
    }

    @Override
    public int getItemCount() {
        return _siseRecyclerViewList.size();
    }


}
