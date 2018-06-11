package com.example.alialzantot.customlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.alialzantot.R;
import com.example.alialzantot.retrofit.beans.PersonResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ali Alzantot on 07/06/2018.
 */

public class CustomAdapter extends ArrayAdapter {



    private List<PersonResult> persons;

    Context myConext;
    public CustomAdapter(@NonNull Context context, int layout, int resource, @NonNull List<PersonResult> objects) {
        super(context,layout, resource, objects);
        persons=objects;
        myConext=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        View rowView=view;
        ViewHolder viewHolder;
        if(rowView == null){

            LayoutInflater layoutInflater= (LayoutInflater) myConext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView=layoutInflater.inflate(R.layout.single_row,parent,false);
            viewHolder=new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder)rowView.getTag();
        }

        viewHolder.getName().setText(persons.get(position).getName());

        Picasso.with(myConext).load("http://image.tmdb.org/t/p/w200/"+persons.get(position).getProfilePath()).placeholder(R.drawable.loading).error(R.drawable.error).into(viewHolder.getImg());

        return rowView;
    }



}
