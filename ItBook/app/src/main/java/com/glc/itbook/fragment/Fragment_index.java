package com.glc.itbook.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.glc.itbook.R;
import com.glc.itbook.XiangQingActivity;
import com.glc.itbook.bean.Food;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Fragment_index extends Fragment {
    private ListView mlistView;
    private BaseAdapter adapter;
    private TextView tvShangye;
    private TextView tvNext;
    private EditText edtYeMa;
    private Button btnTiaozhuan;
    private int page = 1;
    private TextView tvCurrentPage;
    private int totalPage;
    private TextView foodName;
    private Button souSuo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_index, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mlistView = view.findViewById(R.id.menu_list);
        tvNext = view.findViewById(R.id.tv_next);
        tvShangye = view.findViewById(R.id.tv_shangye);
        tvCurrentPage = view.findViewById(R.id.tv_currentPage);
        edtYeMa = view.findViewById(R.id.edt_yema);
        btnTiaozhuan = view.findViewById(R.id.btn_tiaozhuan);
        foodName=view.findViewById(R.id.edt_foodName);
        souSuo=view.findViewById(R.id.btn_imgSousuo);




        String foodNameStr = foodName.getText().toString().trim();
        selectFenYe(foodNameStr,1);
        //上一页
        tvShangye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page > 1) {
                    String foodNameStr = foodName.getText().toString().trim();
                    try {
                        String encode = URLEncoder.encode(foodNameStr, "utf-8");
                        selectFenYe(encode,--page);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "当前第一页", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //下一页
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page < totalPage) {
                    String bookNameStr = foodName.getText().toString().trim();
                    try {
                        String encode = URLEncoder.encode(bookNameStr, "utf-8");
                        selectFenYe(encode,++page);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "到达尾页", Toast.LENGTH_SHORT).show();
                }
            }
        });
    //跳转页码
        btnTiaozhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtYeMa.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "请输入页码", Toast.LENGTH_SHORT).show();
                }else {
                    page = Integer.parseInt(edtYeMa.getText().toString().trim());
                    if (page > 0 && page <= totalPage) {

                        String bookNameStr = foodName.getText().toString().trim();
                        try {
                            String encode = URLEncoder.encode(bookNameStr, "utf-8");
                            selectFenYe(encode, page);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "超过最大页数", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //搜索按钮
        souSuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookNameStr = foodName.getText().toString().trim();
                try {
                    String encode = URLEncoder.encode(bookNameStr, "utf-8");
                    selectFenYe(encode,1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    //分页搜索显示的数据 -- 默认显示全部
    private void selectFenYe(String name,int page){
        JSONObject jsonObject = new JSONObject();
        String url = "http://192.168.1.102:8085/food/foodfindByPageName?name="+name+"&currentPage="+page+"&pageSize=10";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Gson gson = new Gson();
                final Food food = gson.fromJson(jsonObject.toString(), Food.class);
                int currentPage = food.getCurrentPage();
                tvCurrentPage.setText("第" + currentPage + "页");
                totalPage = food.getTotalPage();
                adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return food.getFoods().size();
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
                        view = View.inflate(getContext(), R.layout.item_booklist, null);
                        ImageView imageView = view.findViewById(R.id.food_image);
                        TextView name = view.findViewById(R.id.food_foodName);
                        final TextView price = view.findViewById(R.id.food_foodPrice);
                        name.setText("菜名:"+food.getFoods().get(i).getFoodName());
                        price.setText("价格:"+food.getFoods().get(i).getFoodPrice());

                        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent=new Intent(getActivity(), XiangQingActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("name",food.getFoods().get(i).getFoodName());
                                bundle.putInt("price",food.getFoods().get(i).getFoodPrice());
                                bundle.putString("img",food.getFoods().get(i).getFoodPicture());
                                bundle.putString("ingredient",food.getFoods().get(i).getFoodIngredient());
                                bundle.putInt("weight",food.getFoods().get(i).getFoodWeight());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        Glide.with(getContext()).load(food.getFoods().get(i).getFoodPicture()).into(imageView);

                        return view;
                    }
                };
                mlistView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
