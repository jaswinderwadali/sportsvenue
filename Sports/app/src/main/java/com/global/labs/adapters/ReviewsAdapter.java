package com.global.labs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.global.labs.R;
import com.global.labs.common.ReviewModel;

import java.util.List;

/**
 * Created by Mantra on 9/15/2015.
 */
public class ReviewsAdapter extends BaseAdapter {
    List<ReviewModel> datalist;

    public ReviewsAdapter(List<ReviewModel> datalist) {
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spiner_item, viewGroup,false);
    }
}
