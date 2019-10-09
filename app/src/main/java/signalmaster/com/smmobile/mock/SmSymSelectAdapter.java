package signalmaster.com.smmobile.mock;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmLayoutManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolSelector;

public class SmSymSelectAdapter extends RecyclerView.Adapter<SmSymSelectAdapter.ViewHolder> {

    private ViewHolder viewHolder = null;

    private ArrayList<SmSymbol> _symList =  null;


    public ArrayList<SmSymbol> get_symList() {
        return _symList;
    }

    private HashMap<String, SmSymbol> symbolHashMap = new HashMap<>();

    public SmSymbolSelector symbolSelector = null;

    public SmSymSelectAdapter() {

    }

    private int selectedPosition = 0;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;

    }

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView symbolName;
        ImageView arrowImg;
        TextView textCloseRatioFirst;
        TextView textCloseRatioSecond;

        public SmSymSelectAdapter parentAdapter = null;

        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            symbolName = itemView.findViewById(R.id.symbolName);
            arrowImg = itemView.findViewById(R.id.arrowImg);
            textCloseRatioFirst = itemView.findViewById(R.id.textCloseRatioFirst);
            textCloseRatioSecond = itemView.findViewById(R.id.textCloseRatioSecond);
        }
    }

    public HashMap<String, SmSymbol> getSymbolHashMap() {
        return symbolHashMap;
    }
    //생성자에서 데이터 리스트 객체를 전달받음.
    public SmSymSelectAdapter(ArrayList<SmSymbol> list) {
        _symList = list;
        for (int i = 0; i < _symList.size(); ++i) {
            SmSymbol sym = _symList.get(i);
            getSymbolHashMap().put(sym.code, sym);
        }
        SmLayoutManager arg = SmLayoutManager.getInstance();
        arg.register("R.id.SubListAdapter", this);
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public SmSymSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.symbol_list_item,parent,false);
        SmSymSelectAdapter.ViewHolder vh = new SmSymSelectAdapter.ViewHolder(view);
        vh.parentAdapter = this;

        return  vh;
    }

    private SmSymbol selectSymbol = null;

    public SmSymbol getSelectSymbol() {
        selectSymbol = _symList.get(selectedPosition);
        return selectSymbol;
    }

    public void setSelect(int position){
        if(viewHolder == null){
            return;
        }
        selectedPosition = position;
        notifyDataSetChanged();
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SmSymSelectAdapter.ViewHolder holder, final int position) {

        /*SharedPreferences selectPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Object resultSelect = selectPrefs.getString("selectObject","0");
        String transSelect = (String)resultSelect;
        selectedPosition = (Integer.parseInt(transSelect));*/

        if(_symList == null){
            return;
        }
        final  SmSymbol curSymbol = _symList.get(position);
        String textSymbolName = curSymbol.seriesNmKr;

        String[] splitSymbolName = curSymbol.getSplitName(textSymbolName);

        holder.symbolName.setText(splitSymbolName[0]);

        double close = curSymbol.quote.C;
        close = close / Math.pow(10, curSymbol.decimal);

        double gap = curSymbol.quote.gapToPreday / Math.pow(10, curSymbol.decimal);
        String textCloseRatioFirstText = curSymbol.quote.sign_to_preday;
        textCloseRatioFirstText += Double.toString(gap);

        holder.textCloseRatioFirst.setText(textCloseRatioFirstText);

        String textCloseRatioSecondText = curSymbol.quote.ratioToPrday + "%";
        holder.textCloseRatioSecond.setText(textCloseRatioSecondText);


        //데이타 값마다 색을 다르게
        if (curSymbol.quote.sign_to_preday.equals("-")) {
            holder.arrowImg.setImageResource(R.drawable.ic_arrow_down_blue_24dp);
        }
        else {
            holder.arrowImg.setImageResource(R.drawable.ic_arrow_up_red_24dp);
        }

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

        //선택 항목 강조
        if(selectedPosition == position ){
            holder.itemView.setBackgroundResource(R.drawable.symbol_selector_button);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.symbol_selector_style);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSelectedPosition(position);
                notifyDataSetChanged();
                if (_symList.size() == 0)
                    return;
                //SmArgManager argMgr = SmArgManager.getInstance();
                selectSymbol = _symList.get(position);

                /*Object selectObj = position;
                SharedPreferences selectPrefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor prefsEditor = selectPrefs.edit();
                prefsEditor.putString("selectObject",selectObj.toString());
                prefsEditor.commit();*/

                //argMgr.registerToMap("mock_symbol_select", "symbol_code", selectSymbol.code);

                //#206CA6
            }
        });
    }

    @Override
    public int getItemCount() {
        return _symList.size();
    }


}
