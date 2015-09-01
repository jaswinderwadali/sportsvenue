package com.global.labs.sports;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Drawer_Adapter extends RecyclerView.Adapter<Drawer_Adapter.LocalHolder> implements View.OnClickListener {

    String[] items;
    Context ctx;

    public Drawer_Adapter(String[] items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    @Override
    public Drawer_Adapter.LocalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CreateView(parent, viewType);
    }

    @Override
    public void onBindViewHolder(Drawer_Adapter.LocalHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 7;
    }


    Drawer_Adapter.LocalHolder CreateView(ViewGroup parent, int viewType) {

        Drawer_Adapter.LocalHolder holder;
        View view;
        switch (viewType) {

            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
                view.setOnClickListener(this);
                view.setTag(viewType);
                break;
            case 6:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.end, parent, false);
                view.setOnClickListener(this);
                view.setTag(viewType);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);
                view.setOnClickListener(this);
                view.setTag(viewType);
                break;
        }
        return holder = new Drawer_Adapter.LocalHolder(view, viewType);

    }

    @Override
    public void onClick(View v) {
        int viewType = (Integer) v.getTag();


        switch (viewType) {

            case 0:
                break;
            case 1:
                ctx.startActivity(new Intent(ctx, AdvaceSearch.class));
                break;
            case 2:

//                Snackbar snack = Snackbar.make(v.findViewById(R.id.textView), "Test", Snackbar.LENGTH_SHORT);
//                View view = snack.getView();
//                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
//                params.gravity = Gravity.TOP;
//                view.setLayoutParams(params);
//                snack.show();

                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;

            default:
                break;
        }


    }


    class LocalHolder extends RecyclerView.ViewHolder {

        public LocalHolder(View itemView, int type) {
            super(itemView);

            switch (type) {

                case 0:

                    break;
                case 6:

                    break;
                default:
                    TextView tv = (TextView) itemView.findViewById(R.id.textView);
                    tv.setText(items[type - 1]);
                    break;

            }
        }
    }

    View.OnClickListener mOnlclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ctx, "Undo Done", Toast.LENGTH_SHORT).show();
        }
    };

}
