package signalmaster.com.smmobile.favorite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scichart.core.common.Action1;

import java.util.ArrayList;
import java.util.Collections;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.SmOptionList;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;
import signalmaster.com.smmobile.symbol.SmSymbolSelector;

import static com.scichart.core.utility.Dispatcher.runOnUiThread;

public class SmFavoriteFragment extends Fragment {

    public static SmFavoriteFragment newInstance(){
        SmFavoriteFragment fragment = new SmFavoriteFragment();
        Bundle args =  new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private FavoritesAdapter _favoritesAdapter = null;

    SmFavoriteFragment self = this;
    ImageView addImg;

    private RecyclerView _recyclerView = null;
    private RecyclerView.Adapter _adapter = null;
    SmOptionList smOptionList = new SmOptionList();
    //ArrayList<String> optionList = (smOptionList.get_optionList());
    ArrayList<SmSymbol> getFavoriteList = SmMarketManager.getInstance().getFavoriteList();


    TextView emptyText;

    public SmFavoriteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.sm_favorites_fragment,container,false);

        _recyclerView = (RecyclerView) view.findViewById(R.id.SmFavoritesList);
        _favoritesAdapter = new FavoritesAdapter(getFavoriteList);
        _recyclerView.setAdapter(_favoritesAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        _recyclerView.setLayoutManager(layoutManager);


        /*addImg = (ImageView)view.findViewById(R.id.addImg);
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.context, SmSymbolSelector.class);
                MainActivity.context.startActivity(intent);
                SmArgManager argManager = SmArgManager.getInstance();
                argManager.registerToMap("symbol_selector_popup","source_fragment", self);
            }
        });*/

        emptyText = (TextView)view.findViewById(R.id.emptyText);

        //set EmptyScreen
        // 완전히 비어있을때 ui 가 바뀌지않음
        if(getFavoriteList.isEmpty()){
            _recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        }else if(getFavoriteList.size() > 0){
            _recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(_recyclerView);

        /*ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(_recyclerView);*/

        /*swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                _favoritesAdapter.getFavoriteList().remove(position);
                _favoritesAdapter.notifyItemRemoved(position);
                _favoritesAdapter.notifyItemRangeChanged(position, _favoritesAdapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(_recyclerView);

        _recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void onDraw(Canvas c, RecyclerView parent,
                               RecyclerView.State state){
                swipeController.onDraw(c);
            }
        });*/

        _recyclerView.getItemAnimator().setChangeDuration(0);
        SmChartDataService chartDataService = SmChartDataService.getInstance();
        chartDataService.subscribeSise(onUpdateSymbol(), this.getClass().getSimpleName());

        return view;
    }

    //swipe add..
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            Toast.makeText(getActivity(),"on Move",Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            //Toast.makeText(getActivity(),"on Swiped",Toast.LENGTH_SHORT).show();
            //Remove swiped item from list and notify the RecyclerView
            final int position = viewHolder.getAdapterPosition();

            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
            alert_confirm.setMessage("삭제 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'YES'
                            getFavoriteList.remove(position);
                            _favoritesAdapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(),"삭제 되었습니다.",Toast.LENGTH_SHORT).show();
                            //set EmptyScreen
                            if(getFavoriteList.isEmpty()){
                                _recyclerView.setVisibility(View.GONE);
                                emptyText.setVisibility(View.VISIBLE);
                            }else{
                                _recyclerView.setVisibility(View.VISIBLE);
                                emptyText.setVisibility(View.GONE);
                            }
                        }
                    }).setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'NO'
                            _favoritesAdapter.notifyDataSetChanged();
                            return;
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
        }
    };


    //swipe add..
    /*ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //삭제되는 아이템의 포지션을 가져온다
            final  int position = viewHolder.getAdapterPosition();
            //데이터의 해당 포지션을 삭제한다
            _recyclerView.getAdapter().notifyItemRemoved(position);
        }
    };*/


    //관심종목 추가
    public void onSymbolAdded(Object sym_code) {
        if (sym_code == null) {
            return;
        }
        SmSymbolManager symMgr = SmSymbolManager.getInstance();
        SmSymbol sym = symMgr.findSymbol(sym_code.toString());
        if (sym != null) {

            SmMarketManager smMarketManager = SmMarketManager.getInstance();


            FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getFavoriteList);
            //중복체크
            for(int i = 0; i<=favoritesAdapter.getFavoriteList.size()-1; i++){
                if(favoritesAdapter.getFavoriteList.get(i).code == sym_code){
                    Toast.makeText(getContext(), "이미 추가하셨습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    Toast.makeText(getContext(), "관심종목이 추가 되었습니다", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            smMarketManager.AddFavorite(sym);

            /*if(getFavoriteList.get(0).code == sym_code){
                Toast.makeText(getContext(), "이미 추가하셨습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "관심종목이 추가 되었습니다", Toast.LENGTH_SHORT).show();
            }*/

            favoritesAdapter.notifyDataSetChanged();



            //notify 후에 다시 그려준다 list를
            _recyclerView.setAdapter(_favoritesAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            _recyclerView.setLayoutManager(layoutManager);


            /*String[] splittextTitle = curSymbol.getSplitName(textTitle);
            String[] splittextContent = curSymbol.getSplitName(textTitle);*/
            /*SmArgManager argManager = SmArgManager.getInstance();
            Button btn = (Button) argManager.getVal("symbol_selector_popup", "add_list");

            btn.setText(sym.seriesNmKr);*/

            //unregister
            //argManager.unregisterFromMap("symbol_selector_popup");

        }
    }

    public void changeSymbol(){
        Intent intent = new Intent(getActivity(),SmSymbolSelector.class);
        startActivity(intent);
        SmArgManager argManager = SmArgManager.getInstance();
        argManager.registerToMap("mock_symbol_select","favorite_fragment",this);
        //argManager.registerToMap("mock_symbol_select","old_symbol",symbolCode);

    }

    //관심종목 추가
    public void onChangeSymbol(SmSymbol sym_code) {
        if (sym_code == null) {
            return;
        }
        SmSymbolManager symMgr = SmSymbolManager.getInstance();
        SmSymbol sym = symMgr.findSymbol(sym_code.code);
        if (sym != null) {

            SmMarketManager smMarketManager = SmMarketManager.getInstance();


            FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getFavoriteList);
            //중복체크
            for(int i = 0; i<=favoritesAdapter.getFavoriteList.size()-1; i++){
                if(favoritesAdapter.getFavoriteList.get(i).code == sym_code.code){
                    Toast.makeText(getContext(), "이미 추가하셨습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    Toast.makeText(getContext(), "관심종목이 추가 되었습니다", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            smMarketManager.AddFavorite(sym);

            /*if(getFavoriteList.get(0).code == sym_code){
                Toast.makeText(getContext(), "이미 추가하셨습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "관심종목이 추가 되었습니다", Toast.LENGTH_SHORT).show();
            }*/

            favoritesAdapter.notifyDataSetChanged();



            //notify 후에 다시 그려준다 list를
            _recyclerView.setAdapter(_favoritesAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            _recyclerView.setLayoutManager(layoutManager);


            /*String[] splittextTitle = curSymbol.getSplitName(textTitle);
            String[] splittextContent = curSymbol.getSplitName(textTitle);*/
            /*SmArgManager argManager = SmArgManager.getInstance();
            Button btn = (Button) argManager.getVal("symbol_selector_popup", "add_list");

            btn.setText(sym.seriesNmKr);*/

            //unregister
            //argManager.unregisterFromMap("symbol_selector_popup");

        }
    }


    @NonNull
    private synchronized Action1<SmSymbol> onUpdateSymbol() {
        return new Action1<SmSymbol>() {
            @Override
            public void execute(final SmSymbol symbol) {
                if (symbol == null)
                    return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       ValueChange(symbol);
                    }
                });
            }
        };
    }


    public void ValueChange(SmSymbol symbol) {

        //깜빡거림현상 제거
        //onCreateView -> recyclerView.getItemAnimator().setChangeDuration(0);

        /*for(int i = 0; i<=_favoritesAdapter.get_symList().size()-1;i++){
            if(_favoritesAdapter.get_symList().get(i).code == symbol.code){
                _favoritesAdapter.notifyItemChanged(i);
                break;
            }
        }*/
        for (int i = 0; i <= _favoritesAdapter.getFavoriteList.size() - 1; i++) {
            if (_favoritesAdapter.getFavoriteList().get(i).code == symbol.code) {
                _favoritesAdapter.notifyItemChanged(i);
                break;
            }
        }

    }



}
