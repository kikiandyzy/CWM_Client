package com.example.clothingwarehousemanagement.Data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private static User user = null;

    private static List<User> userList = new ArrayList<>();

    //员工权重
    private static int weight = 0;
    //拣货能力
    public static int handlePower = 5;

    public static void setUserParams(String userName,String password,String identity){
        if(null == user){
            user = new User(userName,password,identity);
        }
    }

    public static User getUser(){
        if(!userList.isEmpty()){
            int tempWeight = weight;
            weight = (weight+1)%userList.size();
            return userList.get(tempWeight);

        }else {
            return user;
        }
    }
    public static User getUser(int index){
        if(!userList.isEmpty()){

            return userList.get(index);

        }else {
            return user;
        }
    }


    public static void setUserListPrams(String response){
        Gson gson = new Gson();
        userList = gson.fromJson(response,new TypeToken<List<User>>(){}.getType());
    }


    public static List<String> getEmployeeNames(){
        List<String> userNames = new ArrayList<>();
        if(!userList.isEmpty()){
            for(User u:userList){
                userNames.add(u.getUserName());
            }
        }else {
            userNames.add(user.getUserName());
        }

        return userNames;
    }





    //用户姓名
    private String userName;

    //用户密码
    private String password;

    private String identity;

    public User(){

    }

    private User(String userName,String password,String identity){
        this.userName = userName;
        this.password = password;
        this.identity = identity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity(){
        return identity;
    }

    public void setIdentity(String identity){
        this.identity = identity;
    }

    @Override
    public String toString(){
     return userName+"-"+password+"-"+identity;
    }
}
