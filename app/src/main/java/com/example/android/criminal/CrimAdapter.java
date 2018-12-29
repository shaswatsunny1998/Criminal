package com.example.android.criminal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList ;

public class CrimAdapter extends ArrayAdapter<Crim>
{
    private Context context;
    private ArrayList<Crim> mcrimes;


    public CrimAdapter(@NonNull Context context, int resource, ArrayList<Crim> mcrimes) {
        super(context, resource, mcrimes);
        this.context=context;
        this.mcrimes=mcrimes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.adapter,parent,false);
        CheckBox cb=(CheckBox) convertView.findViewById(R.id.checkBox);
        TextView title=(TextView) convertView.findViewById(R.id.textView);
        TextView uuid=(TextView) convertView.findViewById(R.id.textView2);
        title.setText(mcrimes.get(position).getName().toString());
        cb.setChecked(mcrimes.get(position).isSolved());
        uuid.setText(mcrimes.get(position).getMid().toString());
        return convertView;
    }
}
