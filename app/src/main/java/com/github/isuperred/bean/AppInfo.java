package com.github.isuperred.bean;

import android.graphics.drawable.Drawable;

import com.github.isuperred.utils.Constants;

public class AppInfo {
    public String name;
    public Drawable icon;
    public boolean isRom; //true表示安装在手机内存
    public boolean isUser; //true表示用户应用
    public String versionName;
    public String packageName;
    public int versionCode = Constants.DEFAULT_ERROR_VALUE;

    @Override
    public String toString() {
        return "AppInfo{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", icon=" + icon +
                ", isRom=" + isRom +
                ", isUser=" + isUser +
                '}';
    }

}
