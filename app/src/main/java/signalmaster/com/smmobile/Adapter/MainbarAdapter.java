package signalmaster.com.smmobile.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.SmMainbarList;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.autoorder.SmAutoFragment;
import signalmaster.com.smmobile.crobo.SmCroboFragment;
import signalmaster.com.smmobile.expert.SmExpertFragment;
import signalmaster.com.smmobile.favorite.SmFavoriteFragment;
import signalmaster.com.smmobile.fund.SmFundFragment;
import signalmaster.com.smmobile.mock.SmPrChartFragment;
import signalmaster.com.smmobile.portfolio.SmPortfolioFragment;
import signalmaster.com.smmobile.sise.SmCurrentFragment;

import static android.content.Context.MODE_PRIVATE;

public class MainbarAdapter extends RecyclerView.Adapter<MainbarAdapter.ViewHolder> {

    public MainbarAdapter() {
    }

    SmMainbarList mainbarArrayList = new SmMainbarList();
    private ArrayList<String> _mainList = ( mainbarArrayList.get_mainList());

    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView main;
        RelativeLayout mainbarLayout;
        public final View mView;

        ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            main = itemView.findViewById(R.id.main);
            mainbarLayout = itemView.findViewById(R.id.mainbarLayout);
        }
    }


    public int selectedPosition = 0;


    //생성자에서 데이터 리스트 객체를 전달받음.
    public MainbarAdapter(ArrayList<String> mainList) {
        _mainList = mainList;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public MainbarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.mainbar_listitem,parent,false);
        MainbarAdapter.ViewHolder vh = new MainbarAdapter.ViewHolder(view);

        return  vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MainbarAdapter.ViewHolder holder, final int position) {

        String text = _mainList.get(position);
        holder.main.setText(text);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = v.getContext();

                ((MainActivity)v.getContext()).onClickItem(position);
                ((MainActivity)v.getContext()).changeOptionType(position);
                //선택항목 강조
                selectedPosition = position;
                notifyDataSetChanged();

            }
        });


        //선택항목 강조
        if(selectedPosition == position){
            holder.mainbarLayout.setBackgroundColor(Color.parseColor("#2292E6"));
            holder.main.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.mainbarLayout.setBackgroundColor(Color.parseColor("#2B3652"));
            holder.main.setTextColor(Color.parseColor("#7A849D"));
        }

    }

    @Override
    public int getItemCount() {
        return _mainList.size();
    }

    /*private Context mContext;

    public MainbarAdapter(Context context, ArrayList<String> names){
        _main = names;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainbar_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder,final int position) {

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = v.getContext();
                Toast.makeText(context,position + "", Toast.LENGTH_LONG).show();
            }
        });

        holder.main.setText(_main.get(position));

    }

    @Override
    public int getItemCount() {
        return _main.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView main;

        public final View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            main = itemView.findViewById(R.id.main);
        }
    }*/
}
