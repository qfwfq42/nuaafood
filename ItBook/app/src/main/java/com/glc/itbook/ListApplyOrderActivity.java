package com.glc.itbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.text.SimpleDateFormat;
import java.util.List;

public class ListApplyOrderActivity extends AppCompatActivity {
    private ListView mlistView;
    private BaseAdapter adapter;
    private Button refresher;
    private ImageView fanhui;
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_apply_order);
        fanhui=findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mlistView = findViewById(R.id.noworder_list);
        refresher = findViewById(R.id.btn_imgrefresh);
        Intent intent = getIntent();
        final Integer userid =  intent.getIntExtra("userid",0);
        refresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrder(userid);
            }
        });
        showOrder(userid);
    }

    private void showOrder(final int userid){
        String url = "http://192.168.1.103:8085/apply/listAvailableOrder?userID="+userid;
        RequestQueue requestQueue = Volley.newRequestQueue(ListApplyOrderActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonElements) {
                Gson gson = new Gson();
                final List<Order> ps = gson.fromJson(jsonElements.toString(), new TypeToken<List<Order>>(){}.getType());

                adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return ps.size();
                    }

                    @Override
                    public Object getItem(int i) {
                        return null;
                    }

                    @Override
                    public long getItemId(int i) {
                        return 0;
                    }

                    @Override
                    public View getView(final int i, View view, ViewGroup viewGroup) {
                        view = View.inflate(ListApplyOrderActivity.this, R.layout.item_available_order, null);
                        final TextView mingzi = view.findViewById(R.id.order_name);
                        TextView dizhi = view.findViewById(R.id.order_beginplace);
                        TextView renshu = view.findViewById(R.id.order_people);
                        Button tijiao = view.findViewById(R.id.btn_add_apply);
                        int uid = ps.get(i).getuserID();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("userID", uid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String url = "http://192.168.1.103:8085/user/getUserInfo";
                        RequestQueue requestQueue = Volley.newRequestQueue(ListApplyOrderActivity.this);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    String msg = jsonObject.getString("msg");
                                    if (msg.equals("查询成功")) {
                                        JSONObject detail = jsonObject.getJSONObject("detail");
                                        String username = detail.getString("userName");
                                        mingzi.setText("发起人:"+username);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(ListApplyOrderActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(jsonObjectRequest);

                        dizhi.setText("发布地点:"+ps.get(i).getpublishPlace());
                        renshu.setText("限制人数:"+ps.get(i).getpeopleLimit());
                        tijiao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(ListApplyOrderActivity.this, AddApplyActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("orderID",ps.get(i).getorderID());
                                bundle.putInt("userid",userid);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        return view;
                    }
                };
                mlistView.setAdapter(adapter);

            }
        }
        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
                Toast.makeText(ListApplyOrderActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
