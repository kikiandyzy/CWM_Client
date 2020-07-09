package com.example.clothingwarehousemanagement.Data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequest {
    //请求的url
    public static String URL_LOGIN = "http://i31973b278.wicp.vip/ClothingWarehouse/LoginServlet";
    public static String URL_QUERY = "http://i31973b278.wicp.vip/ClothingWarehouse/QueryServlet";
    public static String URL_ORDER = "http://i31973b278.wicp.vip/ClothingWarehouse/OrderServlet";

    public static void request(Context context, final Map<String,String> params, final VolleyCallBack volleyCallBack, String url, String tag){
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //避免重复申请
        requestQueue.cancelAll(tag);




        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final Request request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                volleyCallBack.getDataFromVolley(response);

            }
        }, getErrorListener(context)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);


    }



    private static Response.ErrorListener getErrorListener(final Context context){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String m = "";
                if (error != null) {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(context, "网络请求超时，请重试！", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", error.getMessage(), error);
                        return;
                    }
                    if (error instanceof ServerError) {
                        Toast.makeText(context, "服务器异常", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", error.getMessage(), error);
                        return;
                    }
                    if (error instanceof NetworkError) {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", error.getMessage(), error);
                        return;
                    }
                    if (error instanceof ParseError) {
                        Toast.makeText(context, "数据格式错误", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", error.getMessage(), error);
                        return;
                    }

                }
            }
        };
    }
}
//http://127.0.0.0/ClothingWarehouse/LoginServlet?AccountNumber=yzy&Password=874432636