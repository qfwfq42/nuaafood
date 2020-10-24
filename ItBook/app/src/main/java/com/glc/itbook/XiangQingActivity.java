package com.glc.itbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class XiangQingActivity extends AppCompatActivity {
private TextView pinming;
private TextView peiliao;
private TextView jiage;
private TextView fenliang;
private ImageView imgXiangqing;
private ImageView fanhui;
private String down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        pinming=findViewById(R.id.tv_pinming);
        peiliao=findViewById(R.id.tv_peiliao);
        jiage=findViewById(R.id.tv_jiage);
        fenliang=findViewById(R.id.tv_fenliang);
        imgXiangqing=findViewById(R.id.img_imgXiangQing);
        fanhui=findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        String name = intent.getStringExtra("name");
        int price = intent.getIntExtra("price",0);
        int weight= intent.getIntExtra("weight",0);
        String ingredient = intent.getStringExtra("ingredient");
        String imgStr = intent.getStringExtra("img");
        pinming.setText("品名："+name);
        peiliao.setText("配料："+ingredient);
        jiage.setText("价格："+price+"元");
        fenliang.setText("分量："+weight+"人份");

        Glide.with(XiangQingActivity.this).load(imgStr).into(imgXiangqing);
    }

}
