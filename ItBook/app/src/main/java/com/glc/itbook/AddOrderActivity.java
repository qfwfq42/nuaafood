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

public class AddOrderActivity extends AppCompatActivity {
    private EditText t_place;
    private EditText t_time;
    private EditText t_people;
    private Button Btn;
    private ImageView fanhui;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        fanhui=findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        t_place=findViewById(R.id.edt_place);
        t_time=findViewById(R.id.edt_time);
        t_people=findViewById(R.id.edt_people);
        Btn=findViewById(R.id.btn_fabu);
        //sharedPreferences =getSharedPreferences("data", Context.MODE_PRIVATE);
        //final String userid= sharedPreferences.getString("userid","");
        Intent intent = getIntent();
        final Integer userid =  intent.getIntExtra("userid",0);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String place1 = t_place.getText().toString().trim();
                String time1 = t_time.getText().toString().trim();
                String people1 = t_people.getText().toString().trim();
                if(place1.equals("")||time1.equals("")||people1.equals("")){
                    Toast.makeText(AddOrderActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                }else {
                    Add(userid,place1,time1,people1);
                }
            }
        });
    }
    private void Add(int userid,String place,String time,String people){
        JSONObject jsonObject=new JSONObject();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp tdate = new Timestamp(date.getTime());
        String url="http://192.168.43.211:8085/order/addOrder?userID="+userid+"&publishPlace="+place
                +"&timeLimit="+time+"&peopleLimit="+people+"&publishTime="+formatter.format(tdate)+"&orderState=waiting"+"";
        Log.d("信息",url);
        RequestQueue requestQueue= Volley.newRequestQueue(AddOrderActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String info1 = jsonObject.getString("info");
                    if(info1.equals("添加成功")){
                        Toast.makeText(AddOrderActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddOrderActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(AddOrderActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
