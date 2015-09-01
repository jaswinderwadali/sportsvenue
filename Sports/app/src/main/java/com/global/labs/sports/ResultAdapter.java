package com.global.labs.sports;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by deepa on 9/1/2015.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.Holder> implements View.OnClickListener {

    Context ctx;

    public ResultAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        view.setTag(viewType);
        view.setOnClickListener(this);


        return new Holder(view, viewType);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public void onClick(View v) {
        ctx.startActivity(new Intent(ctx, MapsActivity.class));
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView, int position) {
            super(itemView);
            TextView header = (TextView) itemView.findViewById(R.id.headertv);
            TextView dec = (TextView) itemView.findViewById(R.id.detailtv);
            ImageView image = (ImageView) itemView.findViewById(R.id.image);
        }
    }


}
