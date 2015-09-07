package com.global.labs.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.global.labs.sports.R;
import com.global.labs.ui.About;
import com.global.labs.ui.Feedback;
import com.global.labs.ui.HomeFragment;


public class Drawer_Adapter extends RecyclerView.Adapter<Drawer_Adapter.LocalHolder> implements View.OnClickListener {

    String[] items;
    Context ctx;
    int[] ids;
    FragmentManager fmg;
    DrawerLayout dwlayout;

    public Drawer_Adapter(String[] items, Context ctx, int[] ids, FragmentManager fmg, DrawerLayout dwlayout) {
        this.items = items;
        this.ctx = ctx;
        this.ids = ids;
        this.fmg = fmg;
        this.dwlayout = dwlayout;

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
        return 6;
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
            case 5:
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

        dwlayout.closeDrawer(Gravity.LEFT);
        switch (viewType) {

            case 0:
                break;
            case 1:
                //     ctx.startActivity(new Intent(ctx, AdvaceSearch.class));
                changefragment(new HomeFragment());
                break;
            case 2:
                changefragment(new About());
                break;
            case 3:
                changefragment(new Feedback());
                break;
            case 4:
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
                case 5:

                    break;
                default:
                    TextView tv = (TextView) itemView.findViewById(R.id.textView);
                    tv.setText(items[type - 1]);
                    ImageView iv = (ImageView) itemView.findViewById(R.id.imageView);
                    iv.setImageResource(ids[type - 1]);
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


    void changefragment(Fragment fragment) {
        fmg.beginTransaction().replace(R.id.container, fragment).commit();
    }

}
