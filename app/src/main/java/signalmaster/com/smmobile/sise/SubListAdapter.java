package signalmaster.com.smmobile.sise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import signalmaster.com.smmobile.ChartListActivity;
import signalmaster.com.smmobile.ChartSignalActivity;
import signalmaster.com.smmobile.login.LoginActivity;
import signalmaster.com.smmobile.Util.SmLayoutManager;
import signalmaster.com.smmobile.popup.PopUpActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmArgument;
import signalmaster.com.smmobile.market.SmCategory;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.userinfo.SmUserManager;

public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.ViewHolder> implements Serializable {
    private Context _context;
    private static final long serialVersionUID = 1L;
    private ArrayList<SmSymbol> _symList = null;
    private HashMap<String, SmSymbol> symbolHashMap = new HashMap<>();

    public PopUpActivity popUp = null;

    public SubListAdapter(Context context) {
        this._context = context;
    }

    public ArrayList<SmSymbol> get_symList() {
        return _symList;
    }

    private int _curMarketPostion = 0;

    public int get_curMarketPostion() {
        return _curMarketPostion;
    }

    public void set_curMarketPostion(int _curMarketPostion) {
        _curMarketPostion = _curMarketPostion;
    }


    public void changeSymbol(int oldPos, int newPos) {
        if (_symList.size() == 0)
            return;

        SmSymbol curSymbol = _symList.get(oldPos);
        SmCategory cat = curSymbol.get_category();
        _symList.set(oldPos, cat.get_symbolList().get(newPos));
        notifyItemChanged(oldPos);
        //notifyDataSetChanged();
        // _symList.remove(oldPos);
        //notifyItemRemoved(oldPos);
    }

    public HashMap<String, SmSymbol> getSymbolHashMap() {
        return symbolHashMap;
    }

    public void setSymbolHashMap(HashMap<String, SmSymbol> symbolHashMap) {
        this.symbolHashMap = symbolHashMap;
    }

    public void refreshItem(SmSymbol symbol) {
        if (symbol == null)
            return;
        // 목록에 포함되어 있으면 업데이트 해준다.
        if (symbolHashMap.containsKey(symbol.code)) {
            notifyDataSetChanged();
        }
    }


    /*
    // SmMarketManager smMarketManager = SmMarketManager.getInstance();
       // private ArrayList<SmMarket> _marketList= (smMarketManager.get_marketList());

            SmMarketManager smMarketManager = SmMarketManager.getInstance();
            //SmMarket smMarket = new SmMarket();
           // ArrayList<SmMarket> _categoryList = (smMarketManager.get_marketList());
            ArrayList<SmMarket> _marketList = (smMarketManager.get_marketList());
            SmMarket mrkt = _marketList.get(0);
            ArrayList<SmCategory> catList = mrkt.get_categoryList();
            SmCategory cat = catList.get(0);
            ArrayList<SmSymbol> symList = cat.get_symbolList();

    */


    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        Button chartBtn;
        Button signalBtn;

        TextView textCloseValue;
        TextView textCloseRatio;

        public SubListAdapter parentAdapter = null;


        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);

            chartBtn = itemView.findViewById(R.id.chartBtn);
            signalBtn = itemView.findViewById(R.id.signalBtn);

            textCloseValue = itemView.findViewById(R.id.cf_close_value);
            textCloseRatio = itemView.findViewById(R.id.cf_close_ratio);
        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    public SubListAdapter(ArrayList<SmSymbol> list) {
        _symList = list;
        getSymbolHashMap().clear();

        for (int i = 0; i < _symList.size(); ++i) {
            SmSymbol sym = _symList.get(i);
            getSymbolHashMap().put(sym.code, sym);
        }
        SmLayoutManager arg = SmLayoutManager.getInstance();
        arg.register("R.id.SubListAdapter", this);
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public SubListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.sub_list_item, parent, false);
        SubListAdapter.ViewHolder vh = new SubListAdapter.ViewHolder(view);
        vh.parentAdapter = this;
        return vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final SubListAdapter.ViewHolder holder, final int position) {
        if (_symList == null)
            return;
        final SmSymbol curSymbol = _symList.get(position);
        String textTitle = curSymbol.seriesNmKr;
        // String textTitle2 = symList.get(1).seriesNmKr;
        //   System.out.println(textTitle2);
        String[] splittextTitle = curSymbol.getSplitName(textTitle);
        String[] splittextContent = curSymbol.getSplitName(textTitle);
        holder.title.setText(splittextTitle[0]);

        double close = curSymbol.quote.C;
        close = close / Math.pow(10, curSymbol.decimal);
        holder.textCloseValue.setText(String.format(curSymbol.getFormat(), close));
        double gap = curSymbol.quote.gapToPreday / Math.pow(10, curSymbol.decimal);
        String ratioText = curSymbol.quote.sign_to_preday;
        ratioText += String.format(curSymbol.getFormat(), gap);
        ratioText += "(";
        ratioText += curSymbol.quote.ratioToPrday;
        ratioText += "%)";
        holder.textCloseRatio.setText(ratioText);

        //데이타 값마다 색을 다르게
        if (curSymbol.quote.sign_to_preday.equals("-")) {
            holder.textCloseRatio.setTextColor(Color.parseColor("#468ACB"));
        }else if(curSymbol.quote.gapToPreday== 0){
            holder.textCloseRatio.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else {
            holder.textCloseRatio.setTextColor(Color.parseColor("#DD4C69"));
        }

        holder.content.setText(splittextContent[1]);

        //signalBtn , ChartSignalActivity = 프리미엄 신호 버튼
        holder.signalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                if (!SmUserManager.getInstance().isLoggedIn()) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }

                Intent intent = new Intent(context, ChartSignalActivity.class);
                intent.putExtra("symbolCode", curSymbol.code);
                context.startActivity(intent);

            }
        });
        holder.chartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ChartListActivity.class);
                intent.putExtra("symbolCode", curSymbol.code);
                context.startActivity(intent);
            }
        });

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                //_context.startActivity(,PopUpActivity.class);
                Context context = v.getContext();
                Intent intent = new Intent(context, PopUpActivity.class);
                SubListAdapter parent = holder.parentAdapter;
                //intent
                // intent.putExtra("size",curSymbol.seriesNmKr);
                // intent.putExtra("_symList",_symList);
                //  ((MainActivity)_context).RequestShow();
                /*SmSymbol curSymbol = _symList.get(position);
                SmCategory cat = curSymbol.get_category();
                _symList.set(position,cat.get_symbolList().get(1));
                notifyDataSetChanged();*/

                //parent.OnItemClick(position);
                //parent.changeSymbol(0, 1);
                if (_symList.size() == 0)
                    return;
                SmSymbol curSym = _symList.get(position);
                SmMarketManager mrktMgr = SmMarketManager.getInstance();
                intent.putExtra("sym_click", "sym_click");
                int curMarket = mrktMgr.getMarketIndex(curSym.marketType);


                SmArgManager argMgr = SmArgManager.getInstance();
                SmArgument arg = new SmArgument();
                arg.add("sym_pos", position);
                arg.add("mrkt_pos", curMarket);
                arg.add("caller", parent);
                argMgr.register("sym_click", arg);

                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SmSymbol curSymbol = _symList.get(position);
                String textTitle = curSymbol.seriesNmKr;

                String[] splittextTitle = curSymbol.getSplitName(textTitle);
                String[] splittextContent = curSymbol.getSplitName(textTitle);

                double close = curSymbol.quote.C;
                close = close / Math.pow(10, curSymbol.decimal);

                double gap = curSymbol.quote.gapToPreday / Math.pow(10, curSymbol.decimal);
                String ratioText = curSymbol.quote.sign_to_preday;
                ratioText += Double.toString(gap);
                ratioText += "(";
                ratioText += curSymbol.quote.ratioToPrday;
                ratioText += "%)";




                Context context = v.getContext();
                Intent intent = new Intent(context, SmSiseChartActivity.class);
                intent.putExtra("symbolCode", curSymbol.code);
                intent.putExtra("symbolName", splittextTitle[0]);
                intent.putExtra("textCloseValue", close);
                intent.putExtra("textCloseRatio", ratioText);
                intent.putExtra("quoteSign",curSymbol.quote.sign_to_preday);
                context.startActivity(intent);
            }
        });

        /*
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Context context = v.getContext();
                Intent intent = new Intent(context, PopUpActivity.class);
                SubListAdapter parent = holder.parentAdapter;

                if (_symList.size() == 0)
                    return;
                SmSymbol curSym = _symList.get(position);
                SmMarketManager mrktMgr = SmMarketManager.getInstance();
                intent.putExtra("sym_click", "sym_click");
                int curMarket = mrktMgr.getMarketIndex(curSym.marketType);


                SmArgManager argMgr = SmArgManager.getInstance();
                SmArgument arg = new SmArgument();
                arg.add("sym_pos", position);
                arg.add("mrkt_pos", curMarket);
                arg.add("caller", parent);
                argMgr.register("sym_click", arg);

                context.startActivity(intent);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return _symList.size();
    }

    public void OnItemClick(int position) {

        if (_symList.size() == 0)
            return;
        SmSymbol curSymbol = _symList.get(position);
        SmCategory cat = curSymbol.get_category();
        _symList.set(position, cat.get_symbolList().get(1));
        notifyDataSetChanged();
    }

    public void setSymbolList(ArrayList<SmSymbol> symList) {
        _symList.clear();
        _symList = symList;
        getSymbolHashMap().clear();
        for (int i = 0; i < _symList.size(); ++i) {
            SmSymbol sym = _symList.get(i);
            getSymbolHashMap().put(sym.code, sym);
        }
        notifyDataSetChanged();
    }

}
