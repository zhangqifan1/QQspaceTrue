package com.v.qqspace.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.v.qqspace.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/2.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //创建视图
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.gridview_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //绑定视图的数据
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myClickListener!=null){
                    myClickListener.onItemCilckListener(v,holder.getLayoutPosition());
                }
            }
        });
        if(position<list.size()){
            String s = list.get(position);
            File file = new File(s);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                holder.imageview.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            holder.imageview.setImageResource(R.mipmap.add_pic);
        }
    }


    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageview;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
        }
    }

    public interface MyClickListener{
        void onItemCilckListener(View v,int position);
    }

    public MyClickListener myClickListener;

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
}
