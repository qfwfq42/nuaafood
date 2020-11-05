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
import com.glc.itbook.bean.Apply;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditApplyActivity extends AppCompatActivity {
    private EditText t_num;
    private EditText t_place;
    private EditText t_name;
    private Button Btn;
    private ImageView fanhui;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_apply);
        Intent intent = getIntent();
        final Integer applyid =  intent.getIntExtra("applyID",0);
        fanhui=findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        t_place=findViewById(R.id.edt_place);
        t_name=findViewById(R.id.edt_name);
        t_num=findViewById(R.id.edt_num);
        Btn=findViewById(R.id.btn_tijiao);

        String url = "http://192.168.1.103:8085/apply/applyfindByAID?applyID="+applyid;
        RequestQueue requestQueue = Volley.newRequestQueue(EditApplyActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonElements) {
                Gson gson = new Gson();
                final List<Apply> ps = gson.fromJson(jsonElements.toString(), new TypeToken<List<Apply>>(){}.getType());
                if (ps.size()>0 && ps.get(0).getApplyState().equals("noanswer")){
                    t_place.setText(ps.get(0).getApplyPlace());
                    t_num.setText(ps.get(0).getApplyAmount()+"");
                    t_name.setText(ps.get(0).getFoodName());
                    Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String place1 = t_place.getText().toString().trim();
                            String num1 = t_num.getText().toString().trim();
                            String name1 = t_name.getText().toString().trim();
                            if(place1.equals("")||num1.equals("")||name1.equals("")){
                                Toast.makeText(EditApplyActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                            }else if(place1.equals(ps.get(0).getApplyPlace())&&num1.equals(ps.get(0).getApplyAmount()+"")&&name1.equals(ps.get(0).getFoodName())){
                                Toast.makeText(EditApplyActivity.this, "信息没有变化！", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                JSONObject jsonObject=new JSONObject();
                                String url="http://192.168.1.103:8085/apply/editApply?applyID="+applyid+"&applyPlace="+place1
                                        +"&applyAmount="+num1+"&foodName="+name1;
                                RequestQueue requestQueue= Volley.newRequestQueue(EditApplyActivity.this);
                                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        try {
                                            String info1 = jsonObject.getString("info");
                                            if(info1.equals("修改成功")){
                                                Toast.makeText(EditApplyActivity.this, "拼单修改成功", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(EditApplyActivity.this, "拼单修改失败，请检查状态", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Log.d("错误", volleyError.toString());
                                        Toast.makeText(EditApplyActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                requestQueue.add(jsonObjectRequest);
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(EditApplyActivity.this, "当前拼单不可更改！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
                Toast.makeText(EditApplyActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

}
