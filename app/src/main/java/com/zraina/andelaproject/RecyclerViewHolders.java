package com.zraina.andelaproject;

/**
 * Created by Okon on 2017-03-08.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    public TextView txtTitle;
    public TextView txtDesc;
    public ImageView imgFeed;
    private Context context;
    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });
        txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        txtDesc = (TextView)itemView.findViewById(R.id.txtDesc);
        imgFeed = (ImageView)itemView.findViewById(R.id.imgFeed);
    }
}
