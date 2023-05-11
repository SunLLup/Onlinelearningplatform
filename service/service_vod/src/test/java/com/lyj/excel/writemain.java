package com.lyj.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class writemain {
    public static void main(String[] args) {
        String fileurl = "D:\\软件目录\\esay.xlsx";
        EasyExcel.write(fileurl,User.class).sheet("读操作").doWrite(data());
    }

    public static List<User> data(){
        ArrayList<User> users = new ArrayList<>();
        for (int i=1;i<10;i++){
            User user = new User();
            user.setId(i);
            user.setName("你好"+i);
            users.add(user);
        }
        return users;
    }
}
