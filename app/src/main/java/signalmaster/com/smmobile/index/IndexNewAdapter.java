package signalmaster.com.smmobile.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RecyclerViewList;
import signalmaster.com.smmobile.SmIndexSelector;

public class IndexNewAdapter extends RecyclerView.Adapter<IndexNewAdapter.ViewHolder> {

    public IndexNewAdapter() {

    }

    public interface OnItemClickListener {
        void onItemClick(View v,int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public SmIndexSelector smIndexSelector = null;
    //IndexLineManager indexLineManager = new IndexLineManager();
    RecyclerViewList recyclerViewList = new RecyclerViewList();
    private ArrayList<String> _indexSelectList = (recyclerViewList.get_indexSelectList());

    public ArrayList<String> get_indexSelectList() {
        return _indexSelectList;
    }

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView indexName;
        //ImageView indexSetting;
        //CheckBox checkBox;


        //public IndexNewAdapter parentAdapter = null;

        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            indexName = itemView.findViewById(R.id.indexName);
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
    public IndexNewAdapter(ArrayList<String> list) {
        _indexSelectList = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public IndexNewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.index_new_item,parent,false);
        IndexNewAdapter.ViewHolder vh = new IndexNewAdapter.ViewHolder(view);
        //vh.parentAdapter = this;

        return  vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final IndexNewAdapter.ViewHolder holder, final int position) {

        if(_indexSelectList == null){
            return;
        }
        String textName = _indexSelectList.get(position);


        holder.indexName.setText(textName);

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

       //체크박스
       /*holder.checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               *//*switch (position){
                   case 0:
                       if(holder.checkBox.isChecked()){

                       }
                       break;
               }*//*
               //holder.checkBox.isChecked();
               Toast.makeText(v.getContext(),position + "번 체크",Toast.LENGTH_SHORT).show();
               if (smIndexSelector != null && smIndexSelector.smChartFragment != null) {
                   smIndexSelector.smChartFragment.addMA(20, "ma20", 0xffff0000, 1.0f);
                   smIndexSelector.smChartFragment.addMACD(12, 25, 9);
               }
           }
       });*/


        //설정 톱니바퀴
        /*holder.indexSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context,IndexSettingPopup.class);
                intent.putExtra("jipyoName",_indexSelectList.get(position));
                MainActivity.context.startActivity(intent);
                SmIndexSelector smIndexSelector = new SmIndexSelector();
                smIndexSelector.finish();
            }
        });*/


        /*holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(MainActivity.context, IndicatorSettingDialog.class);
                MainActivity.context.startActivity(intent);

                SmArgManager smArgManager = SmArgManager.getInstance();
                smArgManager.registerToMap("Indicator","name",_indexSelectList.get(position));

                if(smIndexSelector != null){
                    //바로 종료.
                    smIndexSelector.finish();
                    //다시 보여주기 위해
                    *//*smIndexSelector.setVisible(false);
                    smArgManager.registerToMap("Indicator","activity",smIndexSelector);*//*
                }

                //스위치가 필요 없을 수도 있다. _indexSelectList.get(position) 값으로 구분한다음에 IndicatorSettingDialog 에서 처리해준다.
                *//*switch (position){
                    case 0:
                        //MA

                        break;
                    case 1:
                        //스토캐스틱
                        break;
                    case 2:
                        //MACD
                        break;
                    case 3:
                        //RSI
                        break;
                    case 4:
                        //CCI
                        break;
                    case 5:
                        //볼린져밴드
                        break;
                    case 6:
                        //파러볼릭 SAR
                        break;
                    case 7:
                        //MOMENTUM
                        break;
                    case 8:
                        //ATR
                        break;
                }*//*
                //#206CA6
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return _indexSelectList.size();
    }


}
