package signalmaster.com.smmobile.expert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.SmOptionFragment;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmLayoutManager;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;
import signalmaster.com.smmobile.symbol.SmSymbolSelector;

public class SmExpertFragment extends Fragment {

    public static SmExpertFragment newInstance() {
        SmExpertFragment fragment = new SmExpertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String API_KEY = "AIzaSyA5PQTrd_bVOGX3vcZouGywRPp8X1kg1TI";

    private static String VIDEO_ID = "BO4zdmkTi48";

    private SmChartFragment _prChartFragment = null;
    private SmOptionFragment _optionFragment = null;

    FrameLayout expert_container, option_container;

    YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*if(savedInstanceState == null){
            _prChartFragment = SmChartFragment.newInstance("mock_main");
            _optionFragment = new SmOptionFragment();
            verticalStackChartFragment = new VerticalStackChartFragment();
            SmLayoutManager layoutManager = SmLayoutManager.getInstance();
            layoutManager.register("mock_main", _prChartFragment);
        }*/

        if (savedInstanceState == null) {
            _prChartFragment = SmChartFragment.newInstance("expert_main");
            _optionFragment = new SmOptionFragment();
        }



        /*_prChartFragment = new SmChartFragment();
        _prChartFragment.setRetainInstance(true);*/
        /*_optionFragment = new SmOptionFragment();
        _optionFragment.setRetainInstance(true);*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sm_expert_fragment, container, false);

        /*youtubeplayer = (YouTubePlayerView)view.findViewById(R.id.youtubeplayer);
        youtubeplayer.setOnTouchListener(this);*/


        expert_container = (FrameLayout) view.findViewById(R.id.mainChartContainer);
        option_container = (FrameLayout) view.findViewById(R.id.option_container);


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainChartContainer, _prChartFragment);
        fragmentTransaction.add(R.id.option_container, _optionFragment);
        fragmentTransaction.add(R.id.youtube_container, youTubePlayerFragment);
        fragmentTransaction.commit();


        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.loadVideo(VIDEO_ID);
                    youTubePlayer.pause();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                String errorMessage = youTubeInitializationResult.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        /*getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent intent = new Intent(getContext(),SmYoutubePlayer.class);
        startActivity(intent);*/

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //insertNestedFragment();
    }

    private void insertNestedFragment() {
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        //verticalStackChartFragment = VerticalStackChartFragment.newInstance();
        _optionFragment = new SmOptionFragment();


        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //transaction.replace(R.id.mainChartContainer, verticalStackChartFragment);
        transaction.replace(R.id.option_container, _optionFragment);
        transaction.replace(R.id.youtube_container, youTubePlayerFragment);
        transaction.commit();




        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.expert_container,verticalStackChartFragment);
        fragmentTransaction.add(R.id.option_container,_optionFragment);
        fragmentTransaction.add(R.id.youtube_container,youTubePlayerFragment);
        fragmentTransaction.commit();*/
    }

    /*private String symbolCode;

    SmSymbolManager symbolManager = SmSymbolManager.getInstance();
    SmSymbol symbol = symbolManager.findSymbol(symbolCode);*/

    /*public void changeSymbol(Button symExpertSelectBtn) {
        Intent intent = new Intent(getActivity(), SmSymbolSelector.class);
        SmArgManager argManager = SmArgManager.getInstance();
        argManager.registerToMap("mock_symbol_select", "fragment", this);
        argManager.registerToMap("mock_symbol_select", "old_symbol", symbolCode);
        argManager.registerToMap("mock_symbol_select", "clicked_button", symExpertSelectBtn);
        argManager.registerToMap("mock_symbol_select", "old_market_index", marketIndex);
        argManager.registerToMap("mock_symbol_select", "old_symbol_index", selectedIndex);
        startActivity(intent);
    }*/


    /*@Override
    public boolean onTouch(View v, MotionEvent event) {
        int width = ((ViewGroup) v.getParent()).getWidth() - v.getWidth();
        int height = ((ViewGroup) v.getParent()).getHeight() - v.getHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldXvalue = event.getX();
            oldYvalue = event.getY();
            //  Log.i("Tag1", "Action Down X" + event.getX() + "," + event.getY());
            Log.i("Tag1", "Action Down rX " + event.getRawX() + "," + event.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            v.setX(event.getRawX() - oldXvalue);
            v.setY(event.getRawY() - (oldYvalue + v.getHeight()));
            //  Log.i("Tag2", "Action Down " + me.getRawX() + "," + me.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (v.getX() > width && v.getY() > height) {
                v.setX(width);
                v.setY(height);
            } else if (v.getX() < 0 && v.getY() > height) {
                v.setX(0);
                v.setY(height);
            } else if (v.getX() > width && v.getY() < 0) {
                v.setX(width);
                v.setY(0);
            } else if (v.getX() < 0 && v.getY() < 0) {
                v.setX(0);
                v.setY(0);
            } else if (v.getX() < 0 || v.getX() > width) {
                if (v.getX() < 0) {
                    v.setX(0);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight());
                } else {
                    v.setX(width);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight());
                }
            } else if (v.getY() < 0 || v.getY() > height) {
                if (v.getY() < 0) {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(0);
                } else {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(height);
                }
            }


        }
        return true;
    }*/
}
