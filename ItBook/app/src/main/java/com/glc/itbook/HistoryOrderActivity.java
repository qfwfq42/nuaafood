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

public class HistoryOrderActivity extends AppCompatActivity {
    private BaseAdapter adapter;
    private ImageView fanhui;
    private TextView weizhi;
    private TextView zhuangtai;
    private TextView faqi;
    private TextView shengyu;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        fanhui=findViewById(R.id.img_fanhuiAdd);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        weizhi=findViewById(R.id.tv_weizhi);
        zhuangtai=findViewById(R.id.tv_zhuangtai);
        faqi=findViewById(R.id.tv_shijian1);
        shengyu=findViewById(R.id.tv_shijian2);
        mListView=findViewById(R.id.hisorder_list);
        Intent intent = getIntent();
        Integer orderid =  intent.getIntExtra("orderID",0);
        showOrder(orderid);
        showApply(orderid);
    }

    private void showOrder(int orderid){
        String url = "http://192.168.1.103:8085/order/findHisOrder?orderID="+orderid;
        RequestQueue requestQueue = Volley.newRequestQueue(HistoryOrderActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonElements) {
                Gson gson = new Gson();
                List<Order> ps = gson.fromJson(jsonElements.toString(), new TypeToken<List<Order>>(){}.getType());
                if (ps.size()>0) {
                    weizhi.setText("发布地点:"+ps.get(0).getpublishPlace());
                    if(ps.get(0).getorderState().equals("doing")){
                        zhuangtai.setText("订单状态:等待配送中");
                    }else if(ps.get(0).getorderState().equals("waiting")){
                        zhuangtai.setText("订单状态:等待拼单中");
                    }else if(ps.get(0).getorderState().equals("finished")){
                        zhuangtai.setText("订单状态:已完成");
                    }else if(ps.get(0).getorderState().equals("canceled")){
                        zhuangtai.setText("订单状态:被取消");
                    }else if(ps.get(0).getorderState().equals("outdated")){
                        zhuangtai.setText("订单状态:已过期");
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//
                    String dateStr = simpleDateFormat.format(ps.get(0).getpublishTime());
                    faqi.setText("发布时间:"+dateStr);
                    shengyu.setText("时间限制:"+ps.get(0).gettimeLimit()+"分钟");

                }
                else{
                    Toast.makeText(HistoryOrderActivity.this, "信息已过期，请刷新后重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
                Toast.makeText(HistoryOrderActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void showApply(final int orderid){
        String url = "http://192.168.1.103:8085/order/applyfindByOID?orderID="+orderid;
        RequestQueue requestQueue = Volley.newRequestQueue(HistoryOrderActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonElements) {
                Gson gson = new Gson();
                final List<Apply> ps = gson.fromJson(jsonElements.toString(), new TypeToken<List<Apply>>(){}.getType());

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
                        view = View.inflate(HistoryOrderActivity.this, R.layout.item_order_apply, null);
                        TextView weizhi = view.findViewById(R.id.apply_place);
                        TextView canpin = view.findViewById(R.id.apply_food);
                        TextView shuliang = view.findViewById(R.id.apply_num);
                        TextView zhuangtai = view.findViewById(R.id.apply_state);
                        Button tongyi = view.findViewById(R.id.btn_tongyi);
                        Button jujue = view.findViewById(R.id.btn_jujue);

                        weizhi.setText("拼单位置:"+ps.get(i).getApplyPlace());
                        canpin.setText("拼单餐品:"+ps.get(i).getFoodName());
                        shuliang.setText("餐品数量:"+ps.get(i).getApplyAmount());
                        if(ps.get(i).getApplyState().equals("noanswer")) {
                            zhuangtai.setText("未回复");
                        }else if(ps.get(i).getApplyState().equals("confirmed")){
                            zhuangtai.setText("已同意");
                        }else if(ps.get(i).getApplyState().equals("rejected")){
                            zhuangtai.setText("已拒绝");
                        }else if(ps.get(i).getApplyState().equals("finished")){
                            zhuangtai.setText("已完成");
                        }else if(ps.get(i).getApplyState().equals("canceled")){
                            zhuangtai.setText("被取消");
                        }

                        if(ps.get(i).getApplyState().equals("noanswer")) {
                            tongyi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog aldg;
                                    AlertDialog.Builder adBd=new AlertDialog.Builder(HistoryOrderActivity.this);
                                    adBd.setTitle("是否确定？");
                                    adBd.setMessage("确定要同意此拼单申请吗？");
                                    adBd.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            JSONObject jsonObject=new JSONObject();
                                            String url = "http://192.168.1.103:8085/order/agreeapply?applyID=" + ps.get(i).getApplyID() ;

                                            RequestQueue requestQueue= Volley.newRequestQueue(HistoryOrderActivity.this);
                                            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject jsonObject) {
                                                    try {
                                                        String info1 = jsonObject.getString("info");
                                                        if(info1.equals("回复成功")){
                                                            Toast.makeText(HistoryOrderActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(HistoryOrderActivity.this, "状态已过期，请刷新", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {
                                                    Log.d("错误", volleyError.toString());
                                                    Toast.makeText(HistoryOrderActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            requestQueue.add(jsonObjectRequest);
                                            showOrder(orderid);
                                            showApply(orderid);
                                        }
                                    });
                                    adBd.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    aldg=adBd.create();
                                    aldg.show();
                                }
                            });

                            jujue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog aldg;
                                    AlertDialog.Builder adBd=new AlertDialog.Builder(HistoryOrderActivity.this);
                                    adBd.setTitle("是否确定？");
                                    adBd.setMessage("确定要同意此拼单申请吗？");
                                    adBd.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            JSONObject jsonObject=new JSONObject();
                                            String url = "http://192.168.1.103:8085/order/rejectapply?applyID=" + ps.get(i).getApplyID() ;

                                            RequestQueue requestQueue= Volley.newRequestQueue(HistoryOrderActivity.this);
                                            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject jsonObject) {
                                                    try {
                                                        String info1 = jsonObject.getString("info");
                                                        if(info1.equals("拒绝成功")){
                                                            Toast.makeText(HistoryOrderActivity.this, "拒绝成功", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(HistoryOrderActivity.this, "状态已过期，请刷新", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {
                                                    Log.d("错误", volleyError.toString());
                                                    Toast.makeText(HistoryOrderActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            requestQueue.add(jsonObjectRequest);
                                            showOrder(orderid);
                                            showApply(orderid);
                                        }
                                    });
                                    adBd.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    aldg=adBd.create();
                                    aldg.show();
                                }
                            });
                        }
                        else{
                            tongyi.setVisibility(View.INVISIBLE);
                            jujue.setVisibility(View.INVISIBLE);
                        }
                        return view;
                    }
                };
                mListView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
                Toast.makeText(HistoryOrderActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);

    }
}
