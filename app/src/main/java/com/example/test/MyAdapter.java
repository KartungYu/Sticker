package com.example.test;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

class MyAdapter extends BaseAdapter {
    private List<String> mData = new ArrayList<>();
    private Context mContext;
    public MyAdapter(List<String> data,Context context){
        mData = data;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_gridview, null);
        ImageView iv =inflate.findViewById(R.id.img);
        iv.setImageBitmap(BitmapFactory.decodeFile(mData.get(i)));
        return inflate;
    }
}
