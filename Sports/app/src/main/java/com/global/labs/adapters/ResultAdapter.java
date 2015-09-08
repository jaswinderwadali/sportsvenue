package com.global.labs.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.global.labs.common.Constants;
import com.global.labs.common.SeachModel;
import com.global.labs.sports.R;
import com.global.labs.ui.DetailActivity;

import java.util.List;

/**
 * Created by deepa on 9/1/2015.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.Holder> implements View.OnClickListener {

    Context ctx;
    List<SeachModel> Datalist;

    public ResultAdapter(List<SeachModel> Datalist, Context ctx) {
        this.ctx = ctx;
        this.Datalist = Datalist;
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
        return Datalist.size();
    }

    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.ADDRESS, Datalist.get((Integer) v.getTag()).getAddress());
        bundle.putString(Constants.INFO, Datalist.get((Integer) v.getTag()).getGroundInfo());
        bundle.putString(Constants.PHONENO, Datalist.get((Integer) v.getTag()).getPhoneNum());
        bundle.putString(Constants.GROUNDNAME, Datalist.get((Integer) v.getTag()).getGroundName());
        bundle.putString(Constants.AREA, Datalist.get((Integer) v.getTag()).getArea());
        bundle.putString(Constants.CITY, Datalist.get((Integer) v.getTag()).getCity());


        ctx.startActivity(new Intent(ctx, DetailActivity.class).putExtra("Lat", Datalist.get((Integer) v.getTag()).getLat()).putExtra("Long", Datalist.get((Integer) v.getTag()).getMlong()).putExtra("MARK", Datalist.get((Integer) v.getTag()).getGroundName()).putExtras(bundle));
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView, int position) {
            super(itemView);
            TextView header = (TextView) itemView.findViewById(R.id.headertv);
            header.setText(Datalist.get(position).getSport());
            TextView dec = (TextView) itemView.findViewById(R.id.detailtv);
            dec.setText(Datalist.get(position).getGroundInfo());
            ImageView image = (ImageView) itemView.findViewById(R.id.image);
            image.setImageResource(R.drawable.groundbig);

        }
    }


}
