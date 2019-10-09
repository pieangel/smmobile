package signalmaster.com.smmobile.notuse;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.scichart.charting.modifiers.OnAnnotationCreatedListener;
import com.scichart.charting.visuals.annotations.IAnnotation;

public class SmEditMod extends Fragment implements OnAnnotationCreatedListener {


    @Override
    public void onAnnotationCreated(@NonNull IAnnotation iAnnotation) {

    }

    /*Button edidBtn = (Button)getActivity().findViewById(R.id.deleteAnnotation);

    public void Touch(){

        boolean a = true;
        if ( a == true){
            deleteAnnotation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }*/
}
