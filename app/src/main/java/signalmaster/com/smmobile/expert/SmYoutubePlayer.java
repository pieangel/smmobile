package signalmaster.com.smmobile.expert;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


public class SmYoutubePlayer extends YouTubePlayerSupportFragment {
    public SmYoutubePlayer() {
    }

    public static SmYoutubePlayer newInstance(String url){
        SmYoutubePlayer smYoutubePlayer = new SmYoutubePlayer();

        Bundle b = new Bundle();
        b.putString("BO4zdmkTi48",url);

        smYoutubePlayer.setArguments(b);
        smYoutubePlayer.init();

        return smYoutubePlayer;
    }

    private void init(){
        initialize("AIzaSyA5PQTrd_bVOGX3vcZouGywRPp8X1kg1TI", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b){
                    youTubePlayer.cueVideo(getArguments().getString("BO4zdmkTi48"));
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}

/*public class SmYoutubePlayer extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener listener;
    View youtubeView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_youtube);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeplayer);


        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("BO4zdmkTi48");//url의 맨 뒷부분 ID값만 넣으면 됨
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.initialize("AIzaSyA5PQTrd_bVOGX3vcZouGywRPp8X1kg1TI", listener);*/

       /* youTubePlayerView.setOnTouchListener(new View.OnTouchListener() {
            float oldXvalue;
            float oldYvalue;

            @Override
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
            }
        });*/
  /*  }







}*/
