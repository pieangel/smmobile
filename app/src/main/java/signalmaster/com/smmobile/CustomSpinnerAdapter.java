package signalmaster.com.smmobile;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    int[] spinnerImages;
    Context mContext;

    public CustomSpinnerAdapter(@NonNull Context context, int[] names) {
        super(context, R.layout.color_spinner_row);
        this.spinnerImages = names;
        this.mContext = context;
    }

    @Override
    public View getDropDownView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return spinnerImages.length;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.color_spinner_row, parent, false);

            mViewHolder.mImage = (ImageView) convertView.findViewById(R.id.imageview_spinner_image);
            //mViewHolder.mName = (TextView) convertView.findViewById(R.id.textview_spinner_name);
            //이거 안할때 null 난다
            convertView.setTag(mViewHolder);

        } else {

            mViewHolder = (ViewHolder) convertView.getTag();
        }

        //mViewHolder.mImage.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //mViewHolder.mImage.setBackgroundColor(spinnerImages[position]);
        mViewHolder.mImage.setImageResource(spinnerImages[position]);
        //mViewHolder.mName.setText(spinnerNames[position]);

        return convertView;
    }

    private static class ViewHolder {
        ImageView mImage;
    }
}
