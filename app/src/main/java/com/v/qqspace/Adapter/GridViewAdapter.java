package com.v.qqspace.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.v.qqspace.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    LayoutInflater layoutInflater;
    private ImageView mImageView;
    private boolean show=false;
    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public GridViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return list.size()+1;//注意此处
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.gridview_item, null);
        mImageView = (ImageView) convertView.findViewById(R.id.imageview);

        if (position < list.size()) {
            String s = list.get(position);
            File file = new File(s);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            mImageView.setBackgroundResource(R.mipmap.add_pic);//最后一个显示加号图片
            if(show){
                mImageView.setVisibility(View.GONE);
            }else{
                mImageView.setVisibility(View.VISIBLE);
            }
        }

        if(list.size()>=9){
            show=true;
        }else{
            show=false;
        }
        return convertView;
    }

}  