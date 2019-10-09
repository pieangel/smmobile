package signalmaster.com.smmobile.sise;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;

import signalmaster.com.smmobile.sise.SmCurrentFragment;

import static com.scichart.core.utility.Dispatcher.runOnUiThread;

public class SmSubbarTimerTask extends TimerTask {


    private Timer _timer = new Timer();
    private SmCurrentFragment _smCurrentFragment = null;

    public SmSubbarTimerTask(SmCurrentFragment smCurrentFragment) {
        _smCurrentFragment = smCurrentFragment;
        _timer.schedule(this, 0, 100);
    }

    public void run() {
        onTimer();
    }

    public void cancelTimer() {
        _timer.cancel();
    }

    private void onTimer() {
        if (_smCurrentFragment == null || _smCurrentFragment.get_subBarRecyclerView() == null)
            return;
        _smCurrentFragment.get_subBarRecyclerView().smoothScrollBy(10, 20);
        if (!_smCurrentFragment.get_subBarRecyclerView().canScrollHorizontally(1) == true) {
            // if(lastVisibleItemPosition == itemTotalCount ){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //  _subBarRecyclerView.setAdapter(_subBarAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((_smCurrentFragment.getActivity()), LinearLayoutManager.HORIZONTAL, false);
                    _smCurrentFragment.get_subBarRecyclerView().setLayoutManager(layoutManager);
                }
            });
        }
    }


}


