package com.glc.itbook;

import android.content.Context;
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

public class EditInfoActivity extends AppCompatActivity {
    private TextView t_userid;
    private TextView t_username;
    private TextView t_registime;
    private EditText t_phone;
    private Button Btn;
    private ImageView fanhui;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinfo);
        t_userid=findViewById(R.id.info_userid);
        t_username=findViewById(R.id.info_username);
        t_registime=findViewById(R.id.info_registime);
        t_phone=findViewById(R.id.edt_phone);
        fanhui=findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Btn=findViewById(R.id.btn_ChangeInfo);

        sharedPreferences =getSharedPreferences("data", Context.MODE_PRIVATE);
        final String userstr= sharedPreferences.getString("username","");
        String phonestr= sharedPreferences.getString("phone","");
        String userid= sharedPreferences.getString("userid","");
        String registertime= sharedPreferences.getString("registertime","");
        t_userid.setText(userid);
        t_username.setText(userstr);
        t_registime.setText(registertime);
        t_phone.setText(phonestr);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone1 =t_phone.getText().toString().trim();
                if(phone1.equals("")){
                    Toast.makeText(EditInfoActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                }else {
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("userName", userstr);
                            jsonObject.put("userPhonenumber", phone1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String url = "http://192.168.43.211:8085/user/changeInfo";

                        RequestQueue requestQueue= Volley.newRequestQueue(EditInfoActivity.this);
                        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    String info1 = jsonObject.getString("msg");
                                    if(info1.equals("修改成功")){
                                        Toast.makeText(EditInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("phone",phone1);
                                        editor.commit();
                                    }else {
                                        Toast.makeText(EditInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("错误", volleyError.toString());
                                Toast.makeText(EditInfoActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(jsonObjectRequest);
                }
            }
        });
    }

}
