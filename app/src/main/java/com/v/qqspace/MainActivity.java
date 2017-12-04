package com.v.qqspace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.v.qqspace.Adapter.GridViewAdapter;
import com.v.qqspace.Adapter.ListViewAdapter;
import com.v.qqspace.BaseActivity.BaseActivity;
import com.v.qqspace.CustomViews.DragGridView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText editText;
    private TextView tvLength;
    private LinearLayout llout;
    private int ACTIVITY_REQUEST_SELECT_PHOTO = 25252;
    private List<String> list;
    private DragGridView gv;
    private GridViewAdapter gridViewAdapter;
    private int count;
    private TextView tvLocation;
    private ListView poplv;
    private TextView tvSelect;
    private ListViewAdapter listViewAdapter;
    private PopupWindow popupWindow;
    private List<String> listString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPop();

        list = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(this);
        gridViewAdapter.setList(list);
        gv.setAdapter(gridViewAdapter);

        //条目点击去相册
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                count = list.size();
                if (position == parent.getChildCount() - 1) {
                    Album.image(MainActivity.this) // 选择图片。
                            .multipleChoice()
                            .requestCode(ACTIVITY_REQUEST_SELECT_PHOTO)
                            .camera(true)
                            .columnCount(3)
                            .selectCount(9 - count)
                            .onResult(new Action<ArrayList<AlbumFile>>() {
                                @Override
                                public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                                    if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO) {
                                        for (int i = 0; i < result.size(); i++) {
                                            AlbumFile albumFile = result.get(i);
                                            list.add(albumFile.getPath());
                                        }
                                        gridViewAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                            .onCancel(new Action<String>() {
                                @Override
                                public void onAction(int requestCode, @NonNull String result) {
                                }
                            })
                            .start();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("是否删除");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            list.remove(position);
                            gridViewAdapter.notifyDataSetChanged();
                        }
                    });
                    builder.create().show();
                }
            }
        });

        //设置条目互换
        gv.setOnChangeListener(new DragGridView.OnChanageListener() {
            @Override
            public void onChange(int form, int to) {
                if (form < list.size() && to < list.size()) {
                    String s = list.get(form);
                    //这里的处理需要注意下
                    if (form < to) {
                        for (int i = form; i < to; i++) {
                            Collections.swap(list, i, i + 1);
                        }
                    } else if (form > to) {
                        for (int i = form; i > to; i--) {
                            Collections.swap(list, i, i - 1);
                        }
                    }
                    list.set(to, s);
                    gridViewAdapter.notifyDataSetChanged();
                }

            }
        });

        //设置文本输入框
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //文字改变之后会接收
            @Override
            public void afterTextChanged(Editable s) {
                tvLength.setText(s.toString().length() + "/200");
            }
        });

        poplv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                    tvSelect.setText(listString.get(position)+":"+position);
            }
        });

    }
    private void initView() {
        editText = (EditText) findViewById(R.id.editText);
        tvLength = (TextView) findViewById(R.id.tvLength);
        llout = (LinearLayout) findViewById(R.id.llout);

        gv = (DragGridView) findViewById(R.id.gv);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLocation.setOnClickListener(this);
        tvSelect = (TextView) findViewById(R.id.tvSelect);
        tvSelect.setOnClickListener(this);
    }

    private void initPop() {

        listString = new ArrayList<>();
        listString.add("所有人可见");
        listString.add("QQ好友可见");
        listString.add("部分好友可见");
        listString.add("部分好友不可见");
        listString.add("仅自己可见");


        View poplist = View.inflate(this, R.layout.poplist, null);
        poplv = poplist.findViewById(R.id.poplv);
        listViewAdapter = new ListViewAdapter(listString, this);

        popupWindow = new PopupWindow(poplist, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置可触摸  焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(new ColorDrawable());


    }


    //动态设置 沉浸颜色
    @Override
    public void setColor(int color) {
        super.setColor(color);
    }



    //跳转高德地图  获取位置
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLocation:
                startActivityForResult(new Intent(this, MapActivity.class), 1000);
                break;
            case R.id.tvSelect:
                poplv.setAdapter(listViewAdapter);
                popupWindow.showAsDropDown(tvSelect,tvSelect.getWidth(),2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 2000) {
            String info = data.getStringExtra("info");
            tvLocation.setText(info);
        }

    }
}
