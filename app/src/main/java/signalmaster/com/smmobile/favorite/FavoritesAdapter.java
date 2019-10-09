package signalmaster.com.smmobile.favorite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import signalmaster.com.smmobile.ChartListActivity;
import signalmaster.com.smmobile.ChartSignalActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.SmSiseChartActivity;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmArgument;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.popup.PopUpActivity;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private ArrayList<SmSymbol> _symList = null;

    public ArrayList<SmSymbol> get_symList() {
        return _symList;
    }

    public FavoritesAdapter() {

    }

    /*SmOptionList optionList = new SmOptionList();
    private ArrayList<String> _optionList = ( optionList.get_optionList());*/
    ArrayList<SmSymbol> getFavoriteList = SmMarketManager.getInstance().getFavoriteList();


    public ArrayList<SmSymbol> getFavoriteList() {
        return getFavoriteList;
    }

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        Button chartBtn;
        Button signalBtn;

        TextView textCloseValue;
        TextView textCloseRatio;

        public FavoritesAdapter parentAdapter = null;

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
    public FavoritesAdapter(ArrayList<SmSymbol> mainList) {
        getFavoriteList = mainList;

        notifyDataSetChanged();
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.sub_list_item, parent, false);
        FavoritesAdapter.ViewHolder vh = new FavoritesAdapter.ViewHolder(view);

        return vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final FavoritesAdapter.ViewHolder holder, final int position) {
        if (getFavoriteList == null) {
            return;
        }

        final SmSymbol symbol = getFavoriteList().get(position);
        String[] splitTitle = symbol.getSplitName(symbol.seriesNmKr);
        String[] splitContent = symbol.getSplitName(symbol.seriesNmKr);
        holder.title.setText(splitTitle[0]);
        double close = symbol.quote.C;
        close = close / Math.pow(10, symbol.decimal);
        holder.textCloseValue.setText(Double.toString(close));
        double gap = symbol.quote.gapToPreday / Math.pow(10, symbol.decimal);
        String ratioText = symbol.quote.sign_to_preday;
        ratioText += Double.toString(gap);
        ratioText += "(";
        ratioText += symbol.quote.ratioToPrday;
        ratioText += "%)";
        holder.textCloseRatio.setText(ratioText);

        //데이타 값마다 색을 다르게
        if (symbol.quote.sign_to_preday.equals("-")) {
            holder.textCloseRatio.setTextColor(Color.parseColor("#468ACB"));
        } else if (symbol.quote.gapToPreday == 0) {
            holder.textCloseRatio.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.textCloseRatio.setTextColor(Color.parseColor("#DD4C69"));
        }

        holder.content.setText(splitContent[1]);
        holder.signalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ChartSignalActivity.class);
                intent.putExtra("symbolCode",symbol.code);
                context.startActivity(intent);
            }
        });
        holder.chartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ChartListActivity.class);
                intent.putExtra("symbolCode",symbol.code);
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
                FavoritesAdapter parent = holder.parentAdapter;
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
                if (_symList == null || _symList.size() == 0)
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
                ArrayList<SmSymbol> getFavoriteList = SmMarketManager.getInstance().getFavoriteList();
                //String textTitle = getFavoriteList.get(position).seriesNmKr;

                SmSymbol curSymbol = getFavoriteList.get(position);
                String textTitle = curSymbol.seriesNmKr;

                String[] splittextTitle = curSymbol.getSplitName(textTitle);
                String[] splittextContent = curSymbol.getSplitName(textTitle);

                double close = curSymbol.quote.C;
                close = close / Math.pow(10, curSymbol.decimal);

                double gap = curSymbol.quote.gapToPreday / Math.pow(10, curSymbol.decimal);
                String ratioText = curSymbol.quote.sign_to_preday;
                ratioText += Double.toString(gap);
                ratioText += "(";
                //ratioText += curSymbol.quote.sign_to_preday;
                ratioText += curSymbol.quote.ratioToPrday;
                ratioText += "%)";


                Context context = v.getContext();
                Intent intent = new Intent(context, SmSiseChartActivity.class);
                intent.putExtra("symbolCode",symbol.code);
                intent.putExtra("symbolName", splittextTitle[0]);
                intent.putExtra("textCloseValue", close);
                intent.putExtra("textCloseRatio", ratioText);
                intent.putExtra("quoteSign", curSymbol.quote.sign_to_preday);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return getFavoriteList.size();
    }


}
