package com.example.clothingwarehousemanagement.OrderACT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.clothingwarehousemanagement.Data.Order;
import com.example.clothingwarehousemanagement.Data.RecyclerViewAdapterCallBack;
import com.example.clothingwarehousemanagement.Data.User;
import com.example.clothingwarehousemanagement.Data.VolleyCallBack;
import com.example.clothingwarehousemanagement.Data.VolleyRequest;

import com.example.clothingwarehousemanagement.FunctionACT.FunctionsActivity;
import com.example.clothingwarehousemanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton refresh;
    private FloatingActionButton handle;
    private FloatingActionButton again;
    private List<Order> orders = new ArrayList<>();
    private List<Order> notHandleOrders = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private RecyclerViewAdapterCallBack callBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.recycler_view_act_order);
        refresh = findViewById(R.id.button_refresh_act_order);
        handle = findViewById(R.id.button_handle_act_order);
        again = findViewById(R.id.button_handle_again_act_order);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshOrder("refresh");
            }
        });

        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(OrderActivity.this, "handle", Toast.LENGTH_SHORT).show();
                handleOrder();
            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!notHandleOrders.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    for(Order order:notHandleOrders){
                        sb.append(order.getID()+"\n");
                    }
                    notHandleOrderDialog(sb.toString());
                }else{
                    Toast.makeText(OrderActivity.this, "没有未被处理的订单", Toast.LENGTH_SHORT).show();
                }
            }
        });

        initRecyclerView();
    }


    //初始化recyclerview
    private void initRecyclerView(){
        callBack = new RecyclerViewAdapterCallBack() {
            @Override
            public void click(int position) {
                if(!orders.isEmpty()){
                    Map<String,Integer> order = orders.get(position).getOrder();
                    //显示订单具体内容
                    orderDialog(getOrderForString(order));
                }

            }
        };
        orderAdapter = new OrderAdapter(orders,callBack);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderAdapter);

        try{
            String type = getIntent().getStringExtra("type");
            if(!type.equals("")){
                refreshOrder("import");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //将订单转化为字符串
    private String getOrderForString(Map<String,Integer> order){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : order.entrySet()){
            sb.append(entry.getKey()+"\n["+entry.getValue()+"]\n");
        }
        return sb.toString();
    }

    //获取订单
    private void refreshOrder(String opration){
        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {
                Gson gson = new Gson();
                List<Order> tempOrders = gson.fromJson(data,new TypeToken<List<Order>>(){}.getType());
                if(tempOrders.isEmpty()){
                    Toast.makeText(OrderActivity.this, "无记录", Toast.LENGTH_SHORT).show();
                }else{
                    if(orders.isEmpty()){
                        orders = tempOrders;
                        orderAdapter.dataChange(orders);
                    }else{
                        orders.addAll(tempOrders);
                        orderAdapter.dataChange(orders);
                    }
                }
            }
        };
        Map<String,String> params = new HashMap<>();
        params.put("operation",opration);
        if(opration.equals("import")){
            params.put("type",getIntent().getStringExtra("type"));
        }

        VolleyRequest.request(OrderActivity.this,params,callBack,VolleyRequest.URL_ORDER,"refreshOrder");
    }

    private void handleOrderForAnEmployee(List<Order> orderList){
        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {
                Gson gson = new Gson();
                //获取返回的没有处理的参数
                List<Order> tempOrders = gson.fromJson(data,new TypeToken<List<Order>>(){}.getType());
                if(tempOrders != null && tempOrders.size() != 0){
                    notHandleOrders.addAll(tempOrders);
                    Toast.makeText(OrderActivity.this, "有未能处理的订单", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OrderActivity.this, "处理成功", Toast.LENGTH_SHORT).show();
                }

            }
        };
        User user = User.getUser();
        Map<String,String> params = new HashMap<>();
        params.put("operation","handle");
        params.put("orders",new Gson().toJson(orderList));
        params.put("userName",user.getUserName());
        params.put("password",user.getPassword());


        VolleyRequest.request(OrderActivity.this,params,callBack,VolleyRequest.URL_ORDER,"handleOrder");
    }

    private void handleOrder(){
        if(!orders.isEmpty()){
            int size = orders.size();
            List<Order> orderList;
            int toIndex = User.handlePower;
            for(int i=0;i<orders.size();i+=User.handlePower){
                if(i+User.handlePower>size){
                    toIndex = size-i;
                }
                orderList = orders.subList(i,i+toIndex);
                handleOrderForAnEmployee(orderList);
            }

            //更新数据
            orders = new ArrayList<>();
            orderAdapter.dataChange(orders);
        }
    }



    //diolog显示
    private void orderDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
        builder.setTitle("订单详情");// 设置标题
        // builder.setIcon(R.drawable.ic_launcher);//设置图标
        builder.setMessage(content);// 为对话框设置内容

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //Toast.makeText(OrderActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();// 使用show()方法显示对话框

    }

    private void notHandleOrderDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
        builder.setTitle("未处理的订单详情");// 设置标题
        // builder.setIcon(R.drawable.ic_launcher);//设置图标
        builder.setMessage(content);// 为对话框设置内容
        builder.setNegativeButton("清除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                notHandleOrders = new ArrayList<>();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //Toast.makeText(OrderActivity.this, "确定", Toast.LENGTH_SHORT).show();
                orders.addAll(notHandleOrders);
                orderAdapter.dataChange(orders);
            }
        });
        builder.create().show();// 使用show()方法显示对话框

    }

}