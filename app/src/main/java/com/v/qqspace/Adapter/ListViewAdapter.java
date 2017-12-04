package com.v.qqspace.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.v.qqspace.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class ListViewAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public ListViewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.poplist_item,null);
            holder.textView=convertView.findViewById(R.id.listview_tv_item);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }
    static class ViewHolder{
        TextView textView;
    }
}
