package com.v.qqspace.CustomViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.v.qqspace.R;


/**
 * Created by Administrator on 2017/12/2.
 */

public class TitleView extends RelativeLayout {

    private float height;
    private String midContent;
    private String rightContent;
    private int midSize;
    private int rightSize;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        //获取自定义属性

        //中间  右边文本内容
        midContent = typedArray.getString(R.styleable.TitleView_Title_text_mid_Content);
        rightContent = typedArray.getString(R.styleable.TitleView_Title_text_right_Content);
        //中间  右边文本字体大小
        midSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_Title_text_mid_Size, 16);
        rightSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_Title_text_right_Size, 16);

        AddXMLView(context);
    }

    private void AddXMLView(Context context) {
        View inflate = View.inflate(context, R.layout.titleview, this);
        ImageView titleview_ivBack = inflate.findViewById(R.id.titleview_ivBack);
        TextView titleview_tvMiddle = inflate.findViewById(R.id.titleview_tvMiddle);
        TextView titleview_tvRight = inflate.findViewById(R.id.titleview_tvRight);

        titleview_tvMiddle.setText(midContent);
        titleview_tvRight.setText(rightContent);

        titleview_tvMiddle.setTextSize(midSize);
        titleview_tvRight.setTextSize(rightSize);

        titleview_ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "返回", Toast.LENGTH_SHORT).show();
            }
        });
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                View obj = (View) msg.obj;
                Toast.makeText(obj.getContext(), "发布", Toast.LENGTH_SHORT).show();
            }
        };
        titleview_tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            Message message = new Message();
                            message.obj=v;
                            handler.sendMessage(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();

            }
        });



    }


}
