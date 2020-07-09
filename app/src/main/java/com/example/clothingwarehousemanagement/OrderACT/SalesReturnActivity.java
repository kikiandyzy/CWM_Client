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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesReturnActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton refresh;
    private FloatingActionButton handle;
    private List<Order> orders = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private RecyclerViewAdapterCallBack callBack;
    //每个员工每次只能处理五次拣货
    private static int employeePower = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_return);
        recyclerView = findViewById(R.id.recycler_view_act_sales_return);
        initRecyclerView();
        refresh = findViewById(R.id.button_refresh_sales_return);
        handle = findViewById(R.id.button_handle_act_sales_return);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshReturnOrder();
            }
        });
        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SalesReturnActivity.this, "yes", Toast.LENGTH_SHORT).show();
                handleOrder();
            }
        });

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(SalesReturnActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderAdapter);
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
    private void refreshReturnOrder(){
        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {
                Gson gson = new Gson();
                List<Order> tempOrders = gson.fromJson(data,new TypeToken<List<Order>>(){}.getType());
                if(tempOrders.isEmpty()){
                    Toast.makeText(SalesReturnActivity.this, "无记录", Toast.LENGTH_SHORT).show();
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
        params.put("operation","refresh");


        VolleyRequest.request(SalesReturnActivity.this,params,callBack,VolleyRequest.URL_ORDER,"refreshOrder");
    }

    private void handleOrderForAnEmployee(List<Order> orderList){
        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {
                Gson gson = new Gson();
                //获取返回的没有处理的参数
                List<String> strings = gson.fromJson(data,new TypeToken<List<String>>(){}.getType());
                if(strings.get(0).equals("true")){
                    Toast.makeText(SalesReturnActivity.this, "退货完成", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SalesReturnActivity.this, "退货失败", Toast.LENGTH_SHORT).show();
                }

            }
        };
        User user = User.getUser();

        Map<String,String> params = new HashMap<>();
        params.put("operation","salesReturn");
        params.put("orders",new Gson().toJson(orderList));
        params.put("userName",user.getUserName());
        params.put("password",user.getPassword());


        VolleyRequest.request(SalesReturnActivity.this,params,callBack,VolleyRequest.URL_ORDER,"handleOrder");
    }

    private void handleOrder(){
        if(!orders.isEmpty()){
            int size = orders.size();
            List<Order> orderList;
            int toIndex = employeePower;
            for(int i=0;i<orders.size();i+=employeePower){
                if(i+employeePower>size){
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SalesReturnActivity.this);
        builder.setTitle("退货订单详情");// 设置标题
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
}
