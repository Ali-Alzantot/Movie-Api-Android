package com.example.alialzantot.movieapi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ali Alzantot on 07/06/2018.
 */

public class ViewHolder {
    View v;
    ImageView img;
    TextView name;
    ViewHolder(View v){
        this.v=v;
    }
    public TextView getName(){
        if(name==null)
            name=v.findViewById(R.id.name);
        return name;
    }

    public ImageView getImg(){
        if(img==null)
            img=v.findViewById(R.id.imageView);
        return img;
    }
}
