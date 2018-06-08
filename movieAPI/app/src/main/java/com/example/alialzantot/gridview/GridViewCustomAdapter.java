package com.example.alialzantot.gridview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.Toast;

import com.example.alialzantot.R;

import com.example.alialzantot.details.SingeImageView;
import com.example.alialzantot.retrofit.beans.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ali Alzantot on 08/06/2018.
 */

public class GridViewCustomAdapter extends BaseAdapter {


    Context context;
    private List<Profile> personImages;
    LayoutInflater layoutInflater;

    public GridViewCustomAdapter(Context context,List<Profile> personImages) {
        this.context = context;
        this.personImages = personImages;

    }

    @Override
    public int getCount() {
        return personImages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Holder holder = new Holder();
        View rowView;

        rowView = layoutInflater.inflate(R.layout.sample_gridlayout, null);

        holder.img = (ImageView) rowView.findViewById(R.id.gridPersonImg);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/"+personImages.get(position).getFilePath()).into(holder.img);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent singleImageIntent=new Intent(context, SingeImageView.class);
                singleImageIntent.putExtra("imgPath",personImages.get(position).getFilePath());
                context.startActivity(singleImageIntent);


            }
        });

        return rowView;
    }

    public class Holder
    {
        ImageView img;
    }



}

