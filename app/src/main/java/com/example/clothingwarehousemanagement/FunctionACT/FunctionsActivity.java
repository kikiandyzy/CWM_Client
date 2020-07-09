package com.example.clothingwarehousemanagement.FunctionACT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.clothingwarehousemanagement.CheckACT.CheckActivity;
import com.example.clothingwarehousemanagement.Data.Order;
import com.example.clothingwarehousemanagement.Data.VolleyRequest;
import com.example.clothingwarehousemanagement.OrderACT.OrderActivity;
import com.example.clothingwarehousemanagement.OrderACT.SalesReturnActivity;
import com.example.clothingwarehousemanagement.R;
import com.example.clothingwarehousemanagement.Data.RecyclerViewAdapterCallBack;
import com.example.clothingwarehousemanagement.Data.User;
import com.example.clothingwarehousemanagement.Data.VolleyCallBack;
import com.example.clothingwarehousemanagement.StockSearchACT.StockSearchActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FunctionsActivity extends AppCompatActivity {

    private List<String> functionNames;
    private RecyclerView recyclerView;
    private FuntionsAdapter funtionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);
        initFunctionNames();
        recyclerView = findViewById(R.id.recycler_view_act_funtions);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterCallBack callBack = new RecyclerViewAdapterCallBack() {
            @Override
            public void click(int position) {
                Intent intent;
                switch (position){
                    case 0:
                        //Toast.makeText(FunctionsActivity.this, "0", Toast.LENGTH_SHORT).show();
                        intent = new Intent(FunctionsActivity.this, StockSearchActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //Toast.makeText(FunctionsActivity.this, "1", Toast.LENGTH_SHORT).show();
                        intent = new Intent(FunctionsActivity.this, OrderActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //Toast.makeText(FunctionsActivity.this, "产品入库", Toast.LENGTH_SHORT).show();
                        importShelf();
                        break;
                    case 3:
                        //Toast.makeText(FunctionsActivity.this, "订单导入", Toast.LENGTH_SHORT).show();
                        impostOrderDialog();
                        break;
                    case 4:
                        intent = new Intent(FunctionsActivity.this, SalesReturnActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        //Toast.makeText(FunctionsActivity.this, "库存查询", Toast.LENGTH_SHORT).show();
                        intent = new Intent(FunctionsActivity.this, CheckActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        funtionsAdapter = new FuntionsAdapter(functionNames,callBack);
        recyclerView.setAdapter(funtionsAdapter);
        //user = (User) getIntent().getSerializableExtra("user");

        if(User.getUser().getIdentity().equals("supervisor")){//如果主管身份登录，那么就获取普通员工的列表
            getUserList();
            //Toast.makeText(this, "hh", Toast.LENGTH_SHORT).show();
        }
    }

    private void initFunctionNames(){
        functionNames = new ArrayList<>();
        functionNames.add("服装编号");//0
        functionNames.add("订单拣货");//1
        functionNames.add("产品入库");//2
        functionNames.add("订单导入");//3
        functionNames.add("退货处理");//4
        functionNames.add("库存查询");//5

    }

    //获取普通员工的列表
    private void getUserList(){
        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {
                Gson gson = new Gson();

                User.setUserListPrams(data);

            }
        };
        User user = User.getUser();
        Map<String,String> params = new HashMap<>();
        params.put("userName",user.getUserName());
        params.put("Password",user.getPassword());
        params.put("operation","queryEmployee");

        VolleyRequest.request(FunctionsActivity.this,params,callBack,VolleyRequest.URL_QUERY,"query");
    }

    private void importShelf(){
        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {
                Toast.makeText(FunctionsActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        };
        User user = User.getUser();
        Map<String,String> params = new HashMap<>();
        params.put("operation","import");
        params.put("userName",user.getUserName());
        params.put("Password",user.getPassword());

        VolleyRequest.request(FunctionsActivity.this,params,callBack,VolleyRequest.URL_QUERY,"handleOrder");
    }








    private void impostOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FunctionsActivity.this);
        builder.setTitle("导入订单");// 设置标题
        // builder.setIcon(R.drawable.ic_launcher);//设置图标
        builder.setMessage("选择文件类型");// 为对话框设置内容
        builder.setNegativeButton("xls", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(FunctionsActivity.this,OrderActivity.class);
                intent.putExtra("type","xls");
                startActivity(intent);
            }
        });

        builder.setPositiveButton("xml", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //Toast.makeText(OrderActivity.this, "确定", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FunctionsActivity.this,OrderActivity.class);
                intent.putExtra("type","xml");
                startActivity(intent);

            }
        });

        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();// 使用show()方法显示对话框

    }


}
