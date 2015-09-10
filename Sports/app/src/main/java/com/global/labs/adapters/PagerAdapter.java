package com.global.labs.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.global.labs.R;
import com.global.labs.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Mantra on 9/4/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> list;
    ImageLoader loader;

    public PagerAdapter(FragmentManager fm, ArrayList<String> list, Context ctx) {
        super(fm);
        this.list = list;
        loader = new ImageLoader(ctx, android.R.color.transparent);
    }

    @Override
    public Fragment getItem(int position) {
        return new PagerFragment(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    class PagerFragment extends Fragment {
        int position;

        public PagerFragment(int position) {
            this.position = position;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.pager_fragment, container, false);
            loader.DisplayImage(list.get(position), (ImageView) view.findViewById(R.id.imageview), 300);
            return view;
        }
    }


}
