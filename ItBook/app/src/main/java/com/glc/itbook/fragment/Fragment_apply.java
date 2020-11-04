package com.glc.itbook.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.glc.itbook.EditApplyActivity;
import com.glc.itbook.EditOrderActivity;
import com.glc.itbook.ListApplyOrderActivity;
import com.glc.itbook.NowApplyActivity;
import com.glc.itbook.R;
import com.glc.itbook.NowOrderActivity;
import com.glc.itbook.SearchApplyActivity;
import com.glc.itbook.bean.Apply;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Fragment_apply extends Fragment {

    private LinearLayout newapply;
    private LinearLayout historyapply;
    private SharedPreferences sharedPreferences;
    private ListView mlistView;
    private BaseAdapter adapter;
    private Button refresher;
    private int userid2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apply, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newapply = view.findViewById(R.id.ly_newapply);
        historyapply = view.findViewById(R.id.ly_historyapply);
        mlistView = view.findViewById(R.id.nowapply_list);
        refresher = view.findViewById(R.id.btn_imgrefresh);
        newapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getArguments().getString("username");
                Integer userid = getArguments().getInt("userid");
                Intent intent=new Intent(getActivity(), ListApplyOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",username);
                bundle.putInt("userid",userid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        historyapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getArguments().getString("username");
                Integer userid = getArguments().getInt("userid");
                Intent intent=new Intent(getActivity(), SearchApplyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",username);
                bundle.putInt("userid",userid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        refresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userid= getArguments().getInt("userid");
                showApply(userid);
            }
        });
        int userid= getArguments().getInt("userid");
        userid2 = userid;
        showApply(userid);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            showApply(userid2);
        }}

    private void showApply(final int userid){
        String url = "http://192.168.1.103:8085/apply/applyfindByID?userID="+userid;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                        view = View.inflate(getContext(), R.layout.item_now_apply, null);
                        TextView mingcheng = view.findViewById(R.id.apply_foodname);
                        TextView shuliang = view.findViewById(R.id.apply_amount);
                        TextView zhuangtai = view.findViewById(R.id.apply_state);
                        TextView dizhi = view.findViewById(R.id.apply_place);
                        Button xiangqing = view.findViewById(R.id.btn_apply_detail);
                        Button xiugai = view.findViewById(R.id.btn_edit_apply);
                        Button shanchu = view.findViewById(R.id.btn_delete_apply);
                        Button queren = view.findViewById(R.id.btn_finish_apply);
                        mingcheng.setText("餐品名称:"+ps.get(i).getFoodName());
                        shuliang.setText("餐品数量:"+ps.get(i).getApplyAmount());
                        dizhi.setText("配送地址:"+ps.get(i).getApplyPlace());
                        if(ps.get(i).getApplyState().equals("noanswer")) {
                            queren.setVisibility(View.INVISIBLE);
                            zhuangtai.setText("未回复");
                        }else if(ps.get(i).getApplyState().equals("confirmed")){
                            xiugai.setVisibility(View.INVISIBLE);
                            shanchu.setVisibility(View.INVISIBLE);
                            zhuangtai.setText("已确认");
                        }else if(ps.get(i).getApplyState().equals("rejected")){
                            xiugai.setVisibility(View.INVISIBLE);
                            shanchu.setVisibility(View.INVISIBLE);
                            queren.setVisibility(View.INVISIBLE);
                            zhuangtai.setText("被拒绝");
                        }else if(ps.get(i).getApplyState().equals("finished")){
                            xiugai.setVisibility(View.INVISIBLE);
                            shanchu.setVisibility(View.INVISIBLE);
                            queren.setVisibility(View.INVISIBLE);
                            zhuangtai.setText("已完成");
                        }else if(ps.get(i).getApplyState().equals("canceled")){
                            xiugai.setVisibility(View.INVISIBLE);
                            shanchu.setVisibility(View.INVISIBLE);
                            queren.setVisibility(View.INVISIBLE);
                            zhuangtai.setText("被取消");
                        }

                        xiangqing.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(getActivity(), NowApplyActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("applyID",ps.get(i).getApplyID());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        xiugai.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(getActivity(), EditApplyActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("applyID",ps.get(i).getApplyID());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        shanchu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog aldg;
                                AlertDialog.Builder adBd=new AlertDialog.Builder(getActivity());
                                adBd.setTitle("是否取消？");
                                adBd.setMessage("确定要取消此拼单申请吗？");
                                adBd.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        JSONObject jsonObject=new JSONObject();
                                        String url = "http://192.168.1.103:8085/apply/cancelApply?applyID=" + ps.get(i).getApplyID() ;

                                        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
                                        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject jsonObject) {
                                                try {
                                                    String info1 = jsonObject.getString("info");
                                                    if(info1.equals("取消成功")){
                                                        Toast.makeText(getActivity(), "取消成功", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        Toast.makeText(getActivity(), "状态已过期，请刷新", Toast.LENGTH_SHORT).show();
                                                    }
                                                    showApply(userid);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                Log.d("错误", volleyError.toString());
                                                Toast.makeText(getActivity(), "网络失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        requestQueue.add(jsonObjectRequest);

                                    }
                                });
                                adBd.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        showApply(userid);
                                    }
                                });
                                aldg=adBd.create();
                                aldg.show();
                            }
                        });
                        /*
                        JSONObject jsonObject=new JSONObject();
                        String url="http://192.168.1.103:8085/order/countnoapply?orderID="+ps.get(i).getorderID();
                        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
                        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    String info1 = jsonObject.getString("info");
                                    if(info1.equals("存在")){
                                        huifu.setText("您有新的拼单申请，点击进行回复 >>>");
                                        huifu.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent=new Intent(getActivity(), NowOrderActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("orderID",ps.get(i).getorderID());
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("错误", volleyError.toString());
                                Toast.makeText(getActivity(), "网络失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(jsonObjectRequest);
                        */
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
                Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }
}


