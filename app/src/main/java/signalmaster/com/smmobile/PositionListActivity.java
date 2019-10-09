package signalmaster.com.smmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmTotalPositionManager;

public class PositionListActivity extends AppCompatActivity {

    ImageView closeImg;
    RecyclerView balanceRecyclerView;
    RightOptionAdapter rightOptionAdapter = new RightOptionAdapter();


    SmTotalPositionManager smTotalPositionManager = SmTotalPositionManager.getInstance();

    TextView symbolNameTxt,positionTypeTxt,avgPriceTxt,openQtyTxt,openPLTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_list_layout);

        closeImg = findViewById(R.id.closeImg);

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HashMap<String, SmPosition> positionHashMap = smTotalPositionManager.getPositionHashMap();

        //잔고
        balanceRecyclerView = findViewById(R.id.balanceRecyclerView);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rightOptionAdapter = new RightOptionAdapter();
        balanceRecyclerView.setAdapter(rightOptionAdapter);

        /*rightOptionAdapter.setOnItemClickListener(new RightOptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                symbolNameTxt = v.findViewById(R.id.symbolNameTxt);
                positionTypeTxt = v.findViewById(R.id.positionTypeTxt);
                avgPriceTxt = v.findViewById(R.id.avgPriceTxt);
                openQtyTxt = v.findViewById(R.id.openQtyTxt);
                openPLTxt = v.findViewById(R.id.openPLTxt);

            }
        });*/

    }

}
