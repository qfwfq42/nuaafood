package com.glc.itbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditPasswdActivity extends AppCompatActivity{
    private EditText edt_oldpassword;
    private EditText edt_password;
    private EditText edt_password2;
    private ImageView fanhui;
    private Button changepasswd;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpswd);

        edt_oldpassword=findViewById(R.id.edt_oldpasswd);
        edt_password=findViewById(R.id.edt_newpassword);
        edt_password2=findViewById(R.id.edt_newpassword2);
        changepasswd=findViewById(R.id.btn_changepasswd);
        fanhui=findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        changepasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String username =  intent.getStringExtra("name");
                String oldpassword = edt_oldpassword.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String password2 = edt_password2.getText().toString().trim();

                if ( password.equals("") ||password2.equals("") || oldpassword.equals("") ) {
                    Toast.makeText(EditPasswdActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(password2)){
                    Toast.makeText(EditPasswdActivity.this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
                }
                else{
                    String regex=".*[a-zA-Z]+.*";
                    Matcher m= Pattern.compile(regex).matcher(password);
                    if (password.length() < 5 || !m.matches()) {
                        Toast.makeText(EditPasswdActivity.this, "密码至少需要6位，且含有字母", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("userName", username);
                            jsonObject.put("userPassword", oldpassword);
                            jsonObject.put("newpassword", password);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String url = "http://192.168.43.211:8085/user/changePasswd";
                        RequestQueue requestQueue = Volley.newRequestQueue(EditPasswdActivity.this);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    Log.d("修改信息", jsonObject.toString());
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(EditPasswdActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    if (msg.equals("修改成功")) {
                                        Toast.makeText(EditPasswdActivity.this, "成功修改", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(EditPasswdActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(jsonObjectRequest);
                    }
                }
            }
        });
    }
}
