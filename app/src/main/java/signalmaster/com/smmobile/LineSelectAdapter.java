package signalmaster.com.smmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class LineSelectAdapter extends RecyclerView.Adapter<LineSelectAdapter.ViewHolder> {

    public LineSelectAdapter() {

    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    RecyclerViewList recyclerViewList = new RecyclerViewList();
    private ArrayList<String> _lineSelectList = (recyclerViewList.get_lineSelectList());

    public ArrayList<String> get_lineSelectList() {
        return _lineSelectList;
    }


    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lineName;
        ImageView lineSetting;


        //public LineSelectAdapter parentAdapter = null;

        //public final View mView;

        ViewHolder(View itemView) {
            super(itemView);
            //mView = itemView;

            // 뷰 객체에 대한 참조. (hold strong reference)
            //index item 아이템 재사용


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, position);

                        }
                    }
                }
            });

            lineName = itemView.findViewById(R.id.lineName);
            lineSetting = itemView.findViewById(R.id.lineSetting);

        }
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    public LineSelectAdapter(ArrayList<String> list) {
        _lineSelectList = list;
    }

    //onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public LineSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //index item 재사용
        View view = inflater.inflate(R.layout.line_new_item, parent, false);
        LineSelectAdapter.ViewHolder vh = new LineSelectAdapter.ViewHolder(view);
        //vh.parentAdapter = this;

        return vh;
    }

    //onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(LineSelectAdapter.ViewHolder holder, final int position) {

        String textName = _lineSelectList.get(position);
        holder.lineName.setText(textName);

       /* if (_symList == null)
            return;
        SmSymbol curSymbol = _symList.get(position);
        String textTitle = curSymbol.seriesNmKr;
        // String textTitle2 = symList.get(1).seriesNmKr;
        //   System.out.println(textTitle2);
        String[] splittextTitle = curSymbol.getSplitName(textTitle);
        String[] splittextContent = curSymbol.getSplitName(textTitle);
        holder.f_title.setText(splittextTitle[0]);
        holder.f_content.setText(splittextContent[1]);*/


        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //#206CA6
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return _lineSelectList.size();
    }


}
