package com.arcsoft.facerecogn.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.arcsoft.facerecogn.R;
import com.arcsoft.facerecogn.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckActivity extends AppCompatActivity {
    private ListView listview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        //1.listview
        listview2=findViewById(R.id.listView2);
        //2.data
        final List<Map<String,String>> data=new ArrayList();

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql3 = "select s.stu_no, s.stu_name, s.stu_room, k.stu_signin  from students s left outer join kaoqin k on (s.stu_no = k.stu_no)";
        Cursor c = db.rawQuery(sql3,null);

        while (c.moveToNext()){
            String stu_no = c.getString(0);
            String stu_name = c.getString(1);
            String stu_room = c.getString(2);
            String signIn = c.getString(3);

            Map<String,String> row = new HashMap<>();
            row.put("stu_no", stu_no);
            row.put("stu_name",stu_name);
            row.put("stu_room",stu_room);
            row.put("tvSignIn",signIn);
            data.add(row);
        }

        c.close();
        db.close();

        final SimpleAdapter adapter=new SimpleAdapter(this,
                data,
                R.layout.layout_item1,
                new String[]{"stu_no","stu_name","stu_room", "tvSignIn"},
                new int[]{R.id.stu_no,R.id.stu_name,R.id.stu_room, R.id.tvSignIn});

        listview2.setAdapter(adapter);
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final String[] reason = new String[]{"缺寝"};

                new AlertDialog.Builder(CheckActivity.this)
                        .setItems(reason, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                DatabaseHelper helper = new DatabaseHelper(CheckActivity.this);
                                SQLiteDatabase db = helper.getWritableDatabase();

                                String sql_kaoqin1 = "insert into kaoqin(stu_no,stu_signin) values(?,?)";
                                String sql_kaoqin2 = "select * from kaoqin where stu_no = ?";
                                String sql_kaoqin3 = "update kaoqin set stu_signin = ? where stu_no = ?";
                                    //查询考勤表，如果有学生信息走if进行更新，没有则插入
                                Cursor c1 = db.rawQuery(sql_kaoqin2,new String[]{data.get(i).get("stu_no")});
                                if(c1.moveToNext()){
                                    //更新考勤表，根据学号设置考勤信息
                                    db.execSQL(sql_kaoqin3,new String[]{reason[j],data.get(i).get("stu_no")});
                                }else{

                                    db.execSQL(sql_kaoqin1,new String[]{data.get(i).get("stu_no"),reason[j]});
                                }
                                c1.close();
                                db.close();

                                // 使用reason数组中的值替换掉ListView中数据
                                data.get(i).put("tvSignIn", reason[j]);
                                // 刷新ListView
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });

    }
}
