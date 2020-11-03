package com.glc.itbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddApplyActivity extends AppCompatActivity {
    private EditText t_name;
    private EditText t_amount;
    private EditText t_place;
    private Button Btn;
    private ImageView fanhui;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apply);

        fanhui=findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        t_place=findViewById(R.id.edt_place);
        t_amount=findViewById(R.id.edt_num);
        t_name=findViewById(R.id.edt_name);
        Btn=findViewById(R.id.btn_tijiao);

        Intent intent = getIntent();
        final Integer userid =  intent.getIntExtra("userid",0);
        final Integer orderID = intent.getIntExtra("orderID",0);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String place1 = t_place.getText().toString().trim();
                String amount1 = t_amount.getText().toString().trim();
                String name1 = t_name.getText().toString().trim();
                if(place1.equals("")||amount1.equals("")||name1.equals("")){
                    Toast.makeText(AddApplyActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                }else {
                    Add(userid,orderID,name1,amount1,place1);
                }
            }
        });
    }
    private void Add(int userid,int orderID,String name1,String amount1,String place1){
        JSONObject jsonObject=new JSONObject();
        Date date = new Date();
        String url="http://192.168.1.103:8085/apply/addApply?userID="+userid+"&orderID="+orderID
                +"&foodName="+name1+"&applyAmount="+amount1+"&applyPlace="+place1;
        Log.d("信息",url);
        RequestQueue requestQueue= Volley.newRequestQueue(AddApplyActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String info1 = jsonObject.getString("info");
                    if(info1.equals("已申请")){
                        Toast.makeText(AddApplyActivity.this, "已经申请过，不可重复申请！", Toast.LENGTH_SHORT).show();
                    }else if(info1.equals("申请成功")){
                        Toast.makeText(AddApplyActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddApplyActivity.this, "状态过期，请刷新重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(AddApplyActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
