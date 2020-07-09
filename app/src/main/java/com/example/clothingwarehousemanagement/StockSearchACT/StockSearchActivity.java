package com.example.clothingwarehousemanagement.StockSearchACT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clothingwarehousemanagement.Data.Shelf;
import com.example.clothingwarehousemanagement.Data.User;
import com.example.clothingwarehousemanagement.Data.VolleyCallBack;
import com.example.clothingwarehousemanagement.Data.VolleyRequest;
import com.example.clothingwarehousemanagement.FunctionACT.FunctionsActivity;
import com.example.clothingwarehousemanagement.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockSearchActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private List<Shelf> shelfList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ShelvesAdapter shelvesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_search);
        button = findViewById(R.id.query_button_act_stock_search);
        editText = findViewById(R.id.query_edit_text_act_stock_search);
        recyclerView = findViewById(R.id.recycler_view_act_stock_search);
        initRecyclerView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clothingID = editText.getText().toString();
                if(!clothingID.equals("")){
                    //4a507db7-7c52-2c22-d6a6-77ade48625bd
                    query(clothingID);

                    //updateRecyclerView();


                }


            }
        });
    }

    private void initRecyclerView(){
        List<String> message = new ArrayList<>();
        message.add("null");
        message.add("null");
        message.add("null");
        shelvesAdapter = new ShelvesAdapter(message);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(shelvesAdapter);
    }




    private void query(final String clothingID){
        User user = User.getUser();
        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {
                Gson gson = new Gson();
                shelfList = gson.fromJson(data,new TypeToken<List<Shelf>>(){}.getType());
                if(shelfList.isEmpty()){
                    Toast.makeText(StockSearchActivity.this, "无记录", Toast.LENGTH_SHORT).show();
                }
                updateRecyclerView();
            }
        };

        Map<String,String> params = new HashMap<>();
        params.put("userName",user.getUserName());
        params.put("Password",user.getPassword());
        params.put("operation","queryClothingID");
        params.put("clothingID",clothingID);
        VolleyRequest.request(StockSearchActivity.this,params,callBack,VolleyRequest.URL_QUERY,"query");

    }

    private void updateRecyclerView(){
        List<String> temp = new ArrayList<>();
        for(Shelf shelf:shelfList){
            temp.add(shelf.getShelfID());
            temp.add(""+shelf.getStorageLocation());
            temp.add(""+shelf.getQuantity());
        }
        shelvesAdapter.dataChange(temp);

    }
}

