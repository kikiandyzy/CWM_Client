package com.example.clothingwarehousemanagement.MainACT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clothingwarehousemanagement.Data.VolleyRequest;
import com.example.clothingwarehousemanagement.R;
import com.example.clothingwarehousemanagement.Data.User;
import com.example.clothingwarehousemanagement.Data.VolleyCallBack;
import com.example.clothingwarehousemanagement.FunctionACT.FunctionsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextPassword;
    Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUserName = findViewById(R.id.editText_user_name_act_main);
        editTextPassword = findViewById(R.id.editText_password_act_main);
        buttonLogin = findViewById(R.id.button_login_act_main);

        //尝试获取之前登录过的代码
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        editTextUserName.setText(preferences.getString("userName",""));
        editTextPassword.setText(preferences.getString("password",""));

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editTextUserName.getText().toString();
                String  password = editTextPassword.getText().toString();
                //如果不为空
                if(!userName.equals("") && !password.equals("")){

                    login(userName,password);
                }
            }
        });
    }

    private void login(final String userName,final String password){

        VolleyCallBack<String> callBack = new VolleyCallBack<String>() {
            @Override
            public void getDataFromVolley(String data) {

                try {
                    JSONObject jsonObject = (JSONObject) new JSONObject(data).get("params");
                    String result = jsonObject.getString("Result");

                    if(result.equals("success")){

                        //保存上次登录的代码
                        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                        editor.putString("userName",userName);
                        editor.putString("password",password);
                        editor.apply();

                        User.setUserParams(userName,password,jsonObject.getString("identity"));
                        //启动活动

                        Intent intent = new Intent(MainActivity.this,FunctionsActivity.class);

                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "账号或密码错误，请重新登录", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        };

        Map<String,String> params = new HashMap<>();
        params.put("AccountNumber",userName);
        params.put("Password",password);

        VolleyRequest.request(MainActivity.this,params,callBack,VolleyRequest.URL_LOGIN,"login");

    }


}
