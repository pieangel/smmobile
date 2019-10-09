package signalmaster.com.smmobile.mock;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import signalmaster.com.smmobile.R;

public class SmMockActivity extends AppCompatActivity {


    //side menu add..
    private String[] navItems = {"예약매수", "예약매도", "예탁잔고", "미결제", "미체결"};
    private ListView sideListView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private FrameLayout flContainer;

    SmPrChartFragment smPrChartFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        //sideMenubar Add..
        //UI
        sideListView = (ListView) findViewById(R.id.sideListView);
        flContainer = (FrameLayout) findViewById(R.id.flContainer);

        smPrChartFragment = SmPrChartFragment.newInstance();

        //리스트의 내용을 받아서 리스너로 넘겨줌 . select 했을 시 DrawerItemClickListener 에서 처리
        sideListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, navItems));
        sideListView.setOnItemClickListener(new DrawerItemClickListener());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(toggle);

        setSupportActionBar(toolbar);

        //액션바 = 모양
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //일단 sidemenu부터
        /*FragmentManager fragmentManager = getSupportFragmentManager();
         prChartFragmentNOTUSE = new ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.,);
        fragmentTransaction.commit();*/

        //초기화면 설정
       // smPrChartFragment = new SmPrChartFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, smPrChartFragment);
        //fragmentTransaction.replace(R.id.SmSubMenu,smSubbarFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:

                    break;

                case 1:

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

            }
            drawerLayout.closeDrawer(sideListView);
        }
    }
}
