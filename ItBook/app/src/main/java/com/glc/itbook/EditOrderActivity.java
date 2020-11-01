package com.glc.itbook;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.glc.itbook.bean.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditOrderActivity extends AppCompatActivity {
    private TextView t_place;
    private EditText t_time;
    private EditText t_people;
    private Button Btn;
    private ImageView fanhui;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        Intent intent = getIntent();
        final Integer orderid =  intent.getIntExtra("orderID",0);
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
        Btn=findViewById(R.id.btn_tijiao);

        String url = "http://192.168.1.103:8085/order/orderfindByOID?orderID="+orderid;
        RequestQueue requestQueue = Volley.newRequestQueue(EditOrderActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonElements) {
                Gson gson = new Gson();
                List<Order> ps = gson.fromJson(jsonElements.toString(), new TypeToken<List<Order>>(){}.getType());
                if (ps.size()>0 && ps.get(0).getorderState().equals("waiting")){
                    t_place.setText("发布地点:"+ps.get(0).getpublishPlace());
                    t_time.setText(Integer.toString(ps.get(0).gettimeLimit()));
                    t_people.setText(Integer.toString(ps.get(0).getpeopleLimit()));
                    Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String time1 = t_time.getText().toString().trim();
                            String people1 = t_people.getText().toString().trim();
                            if(time1.equals("")||people1.equals("")||Integer.parseInt(time1)<=0||Integer.parseInt(people1)<=0){
                                Toast.makeText(EditOrderActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                            }else if(time1.equals("")&&people1.equals("")) {

                            }
                            else {
                                JSONObject jsonObject=new JSONObject();
                                String url="http://192.168.1.103:8085/order/editOrder?orderID="+orderid+"&timeLimit="+time1
                                        +"&peopleLimit="+people1;
                                RequestQueue requestQueue= Volley.newRequestQueue(EditOrderActivity.this);
                                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        try {
                                            String info1 = jsonObject.getString("info");
                                            if(info1.equals("修改成功")){
                                                Toast.makeText(EditOrderActivity.this, "订单修改成功", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(EditOrderActivity.this, "订单修改失败", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Log.d("错误", volleyError.toString());
                                        Toast.makeText(EditOrderActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                requestQueue.add(jsonObjectRequest);
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(EditOrderActivity.this, "当前订单不可更改！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
                Toast.makeText(EditOrderActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

}
