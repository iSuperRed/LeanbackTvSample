package com.github.isuperred.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/18.
 * ClassName  :
 * Description  :
 */
public class LocalJsonResolutionUtil {

    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public  static <T> T JsonToObject(String json, Class<T> type) {
        Gson gson =new Gson();
        return gson.fromJson(json, type);
    }
}
