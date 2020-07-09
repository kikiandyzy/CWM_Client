package com.example.clothingwarehousemanagement.CheckACT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.clothingwarehousemanagement.Data.Order;
import com.example.clothingwarehousemanagement.Data.Shelf;

import com.example.clothingwarehousemanagement.Data.User;
import com.example.clothingwarehousemanagement.Data.VolleyCallBack;
import com.example.clothingwarehousemanagement.Data.VolleyRequest;
import com.example.clothingwarehousemanagement.FunctionACT.FunctionsActivity;
import com.example.clothingwarehousemanagement.OrderACT.OrderActivity;
import com.example.clothingwarehousemanagement.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckActivity extends AppCompatActivity {

    private Spinner shelf;
    private SpinnerAdapter shelfAdapter;


    private Spinner staff;
    private SpinnerAdapter staffAdapter;
    private List<String> staffName = new ArrayList<>();

    private Button button;
    private RecyclerView recyclerView;
    private List<Shelf> shelfList = new ArrayList<>();
    private CheckAdapter checkAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        shelf = findViewById(R.id.spinner_shelf_act_check);
        staff = findViewById(R.id.spinner_staff_act_check);
        button = findViewById(R.id.button_act_check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
        recyclerView = findViewById(R.id.recycler_view_act_check);
        initRecyclerview();
        initSpinner();

    }

    private void initRecyclerview(){
        checkAdapter = new CheckAdapter(shelfList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CheckActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(checkAdapter);
    }

    private void initSpinner(){
        staffName = User.getEmployeeNames();

        shelfAdapter = ArrayAdapter.createFromResource(this, R.array.shelves, android.R.layout.simple_spinner_item);
        staffAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, staffName);
        shelf.setAdapter(shelfAdapter);
        staff.setAdapter(staffAdapter);


       shelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               //shelf.setSelection(i,true);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       staff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               //staff.setSelection(i,true);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
    }

    private void check(){
        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {
                Gson gson = new Gson();
                List<Shelf> shelves = gson.fromJson(data,new TypeToken<List<Shelf>>(){}.getType());
                if(shelves.isEmpty()){
                    Toast.makeText(CheckActivity.this, "无库存记录", Toast.LENGTH_SHORT).show();
                }else{
                    checkAdapter.dataChange(shelves);
                }
            }
        };
        Map<String,String> params = new HashMap<>();
        params.put("operation","queryShelfID");
        params.put("shelfID",(String) shelf.getSelectedItem());
        User user = User.getUser(staff.getSelectedItemPosition());
        params.put("userName",user.getUserName());
        params.put("Password",user.getPassword());

        VolleyRequest.request(CheckActivity.this,params,callBack,VolleyRequest.URL_QUERY,"check");
    }

}
