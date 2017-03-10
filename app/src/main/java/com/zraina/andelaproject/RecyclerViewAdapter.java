package com.zraina.andelaproject;

/**
 * Created by Okon on 2017-03-08.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>{
    private ArrayList<Users> contactList;
    private Context context;

    public RecyclerViewAdapter(ArrayList<Users> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.txtTitle.setText(contactList.get(position).getLogin());
        holder.txtDesc.setText("Java Developer | Lagos");
        Picasso.with(context)
                .load(contactList.get(position).getImg())
                .placeholder(R.drawable.ic_test)
                .error(R.drawable.ic_test)
                .into(holder.imgFeed);
    }

    @Override
    public int getItemCount() {
        return this.contactList.size();
    }

    public Users getItem(int position) {
        return contactList.get(position);
    }

}