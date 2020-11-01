package com.glc.itbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.glc.itbook.bean.Food;
import com.glc.itbook.bean.OrderPage;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class SearchOrderActivity extends AppCompatActivity {
    private ListView mlistView;
    private BaseAdapter adapter;
    private TextView tvShangye;
    private TextView tvNext;
    private EditText edtYeMa;
    private Button btnTiaozhuan;
    private int page = 1;
    private TextView tvCurrentPage;
    private int totalPage;
    private TextView orderName;
    private Button souSuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order);
        mlistView = findViewById(R.id.menu_list);
        tvNext = findViewById(R.id.tv_next);
        tvShangye = findViewById(R.id.tv_shangye);
        tvCurrentPage = findViewById(R.id.tv_currentPage);
        edtYeMa = findViewById(R.id.edt_yema);
        btnTiaozhuan = findViewById(R.id.btn_tiaozhuan);
        orderName=findViewById(R.id.edt_orderName);
        souSuo=findViewById(R.id.btn_imgSousuo);

        String foodNameStr = orderName.getText().toString().trim();
        selectFenYe(foodNameStr,1);
        //上一页
        tvShangye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page > 1) {
                    String orderNameStr = orderName.getText().toString().trim();
                    try {
                        String encode = URLEncoder.encode(orderNameStr, "utf-8");
                        selectFenYe(encode,--page);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SearchOrderActivity.this, "当前第一页", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //下一页
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page < totalPage) {
                    String orderNameStr = orderName.getText().toString().trim();
                    try {
                        String encode = URLEncoder.encode(orderNameStr, "utf-8");
                        selectFenYe(encode,++page);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SearchOrderActivity.this, "到达尾页", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //跳转页码
        btnTiaozhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtYeMa.getText().toString().trim().equals("")){
                    Toast.makeText(SearchOrderActivity.this, "请输入页码", Toast.LENGTH_SHORT).show();
                }else {
                    page = Integer.parseInt(edtYeMa.getText().toString().trim());
                    if (page > 0 && page <= totalPage) {

                        String orderNameStr = orderName.getText().toString().trim();
                        try {
                            String encode = URLEncoder.encode(orderNameStr, "utf-8");
                            selectFenYe(encode, page);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(SearchOrderActivity.this, "超过最大页数", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //搜索按钮
        souSuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderNameStr = orderName.getText().toString().trim();
                try {
                    String encode = URLEncoder.encode(orderNameStr, "utf-8");
                    selectFenYe(encode,1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void selectFenYe(String name,int page){
        JSONObject jsonObject = new JSONObject();
        String url = "http://192.168.1.103:8085/order/orderfindByPageName?name="+name+"&currentPage="+page+"&pageSize=10";
        RequestQueue requestQueue = Volley.newRequestQueue(SearchOrderActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Gson gson = new Gson();
                final OrderPage orderPage = gson.fromJson(jsonObject.toString(), OrderPage.class);
                int currentPage = orderPage.getCurrentPage();
                tvCurrentPage.setText("第" + currentPage + "页");
                totalPage = orderPage.getTotalPage();
                adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return orderPage.getOrders().size();
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
                        view = View.inflate(SearchOrderActivity.this, R.layout.item_history_order, null);
                        TextView begintime = view.findViewById(R.id.order_begintime);
                        TextView beginplace = view.findViewById(R.id.order_beginplace);
                        TextView state = view.findViewById(R.id.order_state);
                        Button btn = view.findViewById(R.id.btn_order_detail);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//
                        String dateStr = simpleDateFormat.format(orderPage.getOrders().get(i).getpublishTime());
                        begintime.setText("开始时间:"+dateStr);
                        beginplace.setText("发起位置:"+orderPage.getOrders().get(i).getpublishPlace());
                        if(orderPage.getOrders().get(i).getorderState().equals("waiting")){
                            state.setText("订单状态:等待中");
                        }else if(orderPage.getOrders().get(i).getorderState().equals("doing")){
                            state.setText("订单状态:配送中");
                        }else if(orderPage.getOrders().get(i).getorderState().equals("finished")){
                            state.setText("订单状态:已完成");
                        }else if(orderPage.getOrders().get(i).getorderState().equals("canceled")){
                            state.setText("订单状态:被取消");
                        }else if(orderPage.getOrders().get(i).getorderState().equals("outdated")){
                            state.setText("订单状态:已过期");
                        }
                        /*
                        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent=new Intent(SearchOrderActivity.this, HistoryOrderActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putInt("orderID",orderPage.getOrders().get(i).getorderID());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        */
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(SearchOrderActivity.this, HistoryOrderActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("orderID",orderPage.getOrders().get(i).getorderID());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        return view;
                    }
                };
                mlistView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SearchOrderActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
