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
import com.glc.itbook.bean.ApplyPage;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class SearchApplyActivity extends AppCompatActivity {
    private ListView mlistView;
    private BaseAdapter adapter;
    private TextView tvShangye;
    private TextView tvNext;
    private EditText edtYeMa;
    private Button btnTiaozhuan;
    private int page = 1;
    private TextView tvCurrentPage;
    private int totalPage;
    private TextView applyName;
    private Button souSuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_apply);
        mlistView = findViewById(R.id.menu_list);
        tvNext = findViewById(R.id.tv_next);
        tvShangye = findViewById(R.id.tv_shangye);
        tvCurrentPage = findViewById(R.id.tv_currentPage);
        edtYeMa = findViewById(R.id.edt_yema);
        btnTiaozhuan = findViewById(R.id.btn_tiaozhuan);
        applyName=findViewById(R.id.edt_applyName);
        souSuo=findViewById(R.id.btn_imgSousuo);
        Intent intent = getIntent();
        final Integer userid =  intent.getIntExtra("userid",0);
        String foodNameStr = applyName.getText().toString().trim();
        selectFenYe(foodNameStr,userid,1);
        //上一页
        tvShangye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page > 1) {
                    String applyNameStr = applyName.getText().toString().trim();
                    try {
                        String encode = URLEncoder.encode(applyNameStr, "utf-8");
                        selectFenYe(encode,userid,--page);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SearchApplyActivity.this, "当前第一页", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //下一页
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page < totalPage) {
                    String applyNameStr = applyName.getText().toString().trim();
                    try {
                        String encode = URLEncoder.encode(applyNameStr, "utf-8");
                        selectFenYe(encode,userid,++page);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SearchApplyActivity.this, "到达尾页", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //跳转页码
        btnTiaozhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtYeMa.getText().toString().trim().equals("")){
                    Toast.makeText(SearchApplyActivity.this, "请输入页码", Toast.LENGTH_SHORT).show();
                }else {
                    page = Integer.parseInt(edtYeMa.getText().toString().trim());
                    if (page > 0 && page <= totalPage) {

                        String applyNameStr = applyName.getText().toString().trim();
                        try {
                            String encode = URLEncoder.encode(applyNameStr, "utf-8");
                            selectFenYe(encode, userid,page);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(SearchApplyActivity.this, "超过最大页数", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //搜索按钮
        souSuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String applyNameStr = applyName.getText().toString().trim();
                try {
                    String encode = URLEncoder.encode(applyNameStr, "utf-8");
                    selectFenYe(encode,userid,1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void selectFenYe(String name, final int userid, int page){
        JSONObject jsonObject = new JSONObject();
        String url = "http://192.168.43.211:8085/apply/applyfindByPageName?name="+name+"&userID="+userid+"&currentPage="+page+"&pageSize=10";
        RequestQueue requestQueue = Volley.newRequestQueue(SearchApplyActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Gson gson = new Gson();
                final ApplyPage applyPage = gson.fromJson(jsonObject.toString(), ApplyPage.class);
                int currentPage = applyPage.getCurrentPage();
                tvCurrentPage.setText("第" + currentPage + "页");
                totalPage = applyPage.getTotalPage();
                adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return applyPage.getApplys().size();
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
                        view = View.inflate(SearchApplyActivity.this, R.layout.item_history_apply, null);
                        TextView mingcheng = view.findViewById(R.id.apply_foodname);
                        TextView shuliang = view.findViewById(R.id.apply_amount);
                        TextView zhuangtai = view.findViewById(R.id.apply_state);
                        TextView dizhi = view.findViewById(R.id.apply_place);
                        Button xiangqing = view.findViewById(R.id.btn_apply_detail);
                        mingcheng.setText("餐品名称:"+applyPage.getApplys().get(i).getFoodName());
                        shuliang.setText("餐品数量:"+applyPage.getApplys().get(i).getApplyAmount());
                        dizhi.setText("配送地址:"+applyPage.getApplys().get(i).getApplyPlace());
                        if(applyPage.getApplys().get(i).getApplyState().equals("noanswer")) {
                            zhuangtai.setText("未回复");
                        }else if(applyPage.getApplys().get(i).getApplyState().equals("confirmed")){
                            zhuangtai.setText("已确认");
                        }else if(applyPage.getApplys().get(i).getApplyState().equals("rejected")){
                            zhuangtai.setText("被拒绝");
                        }else if(applyPage.getApplys().get(i).getApplyState().equals("finished")){
                            zhuangtai.setText("已完成");
                        }else if(applyPage.getApplys().get(i).getApplyState().equals("canceled")){
                            zhuangtai.setText("被取消");
                        }
                        xiangqing.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(SearchApplyActivity.this, HistoryApplyActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("applyID",applyPage.getApplys().get(i).getApplyID());
                                bundle.putInt("orderID",applyPage.getApplys().get(i).getOrderID());
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
                Toast.makeText(SearchApplyActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
