package com.arcsoft.facerecogn.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;


//SQLite数据打开的帮助类
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        //显示调用父类中带参数的构造函数
        //参数1：上下文，传递Activity对象
        //参数2：数据库文件的名
        //参数3：游标工厂，忽略
        //参数4：数据库版本
        super(context,"my.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //当数据库文件不存在时，调用；数据库文件存在，忽略
        //建表
        //主键的规则：名称约定_id,类型Integer
        String sql = "create table users(_id Integer primary key autoincrement,username text,password text)";
        db.execSQL(sql);

        //假数据，相当于注册
        String sql2 = "insert into users(username,password) values (?,?)";
        db.execSQL(sql2,new String[]{"admin","123456"});
        db.execSQL(sql2,new String[]{"1","1"});

        String sql3 = "create table students(_id integer primary key autoincrement, stu_dormitory text,stu_room text,stu_class text,stu_name text, stu_no text)";
        db.execSQL(sql3);

        //假数据
//        String sql3_1 = "insert into students(stu_no, stu_name, stu_class) values(?,?,?)";
//        db.execSQL(sql3_1,new String[]{"10","632","物联171","132116","宋丽俏"});

        String sql4 = "create table kaoqin(_id integer primary key autoincrement,stu_no text,stu_signin text)";
        db.execSQL(sql4);

        Log.d("DatabaseHelper","onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //当数据库文件存在，但是版本升级，调用
        //升级表
    }
}
