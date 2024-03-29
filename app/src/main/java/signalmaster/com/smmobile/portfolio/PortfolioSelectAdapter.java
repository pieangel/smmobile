package signalmaster.com.smmobile.portfolio;

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

public class PortfolioSelectAdapter extends RecyclerView.Adapter<PortfolioSelectAdapter.ViewHolder> {

    public PortfolioSelectAdapter() {

    }

    private int selectedPosition = -1;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /*public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }*/

    //ArrayList <SmSymbol> symList = null;
    RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> portList = recyclerViewList.getPortfolioList();


    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout Lout;
        TextView title;


        //public PortfolioSelectAdapter parentAdapter = null;

        //public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            //mView = itemView;

            /*itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });*/

            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.title);
            Lout = itemView.findViewById(R.id.Lout);
            //indexSetting = itemView.findViewById(R.id.indexSetting);
        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    public PortfolioSelectAdapter(ArrayList<String> list) {
        portList = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public PortfolioSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.portfolio_select_item,parent,false);
        PortfolioSelectAdapter.ViewHolder vh = new PortfolioSelectAdapter.ViewHolder(view);
        //vh.parentAdapter = this;

        return  vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PortfolioSelectAdapter.ViewHolder holder, final int position) {

        if(portList == null){
            return;
        }

        String name = portList.get(position);
        holder.title.setText(name);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();

            }
        });

        //선택 항목 강조
        if(selectedPosition == position ){
            holder.itemView.setBackgroundResource(R.drawable.symbol_selector_button);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.symbol_selector_style);
        }



        /*holder.clickSymbol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context, SmSymbolSelector.class);
                MainActivity.context.startActivity(intent);
                SmArgManager argManager = SmArgManager.getInstance();
                argManager.registerToMap("mock_symbol_select","portfolio_fragment",this);
            }
        });*/



        /*holder.clickSymbol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/




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

    }

    @Override
    public int getItemCount() {
        return portList.size();
    }


}
