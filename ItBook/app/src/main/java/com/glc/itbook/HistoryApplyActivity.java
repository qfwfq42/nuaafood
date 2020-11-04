package com.glc.itbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.glc.itbook.bean.Apply;
import com.glc.itbook.bean.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryApplyActivity extends AppCompatActivity {
    private ImageView fanhui;
    private TextView weizhi;
    private TextView zhuangtai;
    private TextView faqi;
    private TextView danzhu;
    private TextView place;
    private TextView name;
    private TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_apply);
        fanhui = findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        weizhi = findViewById(R.id.tv_weizhi);
        zhuangtai = findViewById(R.id.tv_zhuangtai);
        faqi = findViewById(R.id.tv_shijian);
        danzhu = findViewById(R.id.tv_danzhu);
        place = findViewById(R.id.tv_apply_weizhi);
        name = findViewById(R.id.tv_foodname);
        amount  = findViewById(R.id.tv_amount);
        Intent intent = getIntent();
        Integer orderid = intent.getIntExtra("orderID", 0);
        Integer applyid = intent.getIntExtra("applyID", 0);
        System.out.println(orderid);
        System.out.println(applyid);
        showOrder(orderid);
        showApply(applyid);
    }

    private void showOrder(int orderid) {
        String url = "http://192.168.1.103:8085/order/findOrder?orderID=" + orderid;
        RequestQueue requestQueue = Volley.newRequestQueue(HistoryApplyActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonElements) {
                Gson gson = new Gson();
                List<Order> ps = gson.fromJson(jsonElements.toString(), new TypeToken<List<Order>>() {
                }.getType());
                if (ps.size() > 0) {
                    weizhi.setText("发起位置:" + ps.get(0).getpublishPlace());
                    if (ps.get(0).getorderState().equals("doing")) {
                        zhuangtai.setText("订单状态:等待配送中");
                    } else if (ps.get(0).getorderState().equals("waiting")) {
                        zhuangtai.setText("订单状态:等待拼单中");
                    } else if (ps.get(0).getorderState().equals("finished")) {
                        zhuangtai.setText("订单状态:已完成");
                    } else if (ps.get(0).getorderState().equals("canceled")) {
                        zhuangtai.setText("订单状态:被取消");
                    } else if (ps.get(0).getorderState().equals("outdated")) {
                        zhuangtai.setText("订单状态:已过期");
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//
                    String dateStr = simpleDateFormat.format(ps.get(0).getpublishTime());
                    faqi.setText("发布时间:" + dateStr);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userID", ps.get(0).getuserID());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url = "http://192.168.1.103:8085/user/getUserInfo";
                    RequestQueue requestQueue = Volley.newRequestQueue(HistoryApplyActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String msg = jsonObject.getString("msg");
                                if (msg.equals("查询成功")) {
                                    JSONObject detail = jsonObject.getJSONObject("detail");
                                    String username = detail.getString("userName");
                                    danzhu.setText("发起人:"+username);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(HistoryApplyActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);

                } else {
                    Toast.makeText(HistoryApplyActivity.this, "信息已过期，请刷新后重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
                Toast.makeText(HistoryApplyActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void showApply(int applyid) {
        String url = "http://192.168.1.103:8085/apply/applyfindByAID?applyID=" + applyid;
        RequestQueue requestQueue = Volley.newRequestQueue(HistoryApplyActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonElements) {
                Gson gson = new Gson();
                List<Apply> ps = gson.fromJson(jsonElements.toString(), new TypeToken<List<Apply>>() {
                }.getType());
                if (ps.size() > 0) {
                    place.setText("配送位置：" + ps.get(0).getApplyPlace());
                    name.setText("餐品名称："+ps.get(0).getFoodName());
                    amount.setText("餐品数量："+ps.get(0).getApplyAmount());

                } else {
                    Toast.makeText(HistoryApplyActivity.this, "信息已过期，请刷新后重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
                Toast.makeText(HistoryApplyActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }
}