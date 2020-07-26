package com.arcsoft.facerecogn.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.arcsoft.facerecogn.R;
import com.arcsoft.facerecogn.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class KaoQinListActivity extends AppCompatActivity {
    private ListView lvKaoQin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kao_qin_list);
        // 【更改】
        Intent i = getIntent();
        String stu_room_full = i.getStringExtra("stu_room");//拆分
        String stu_status = i.getStringExtra("stu_status");
        String stu_dormitory = "";//公寓号
        String stu_room = "";//寝室号
        if (!TextUtils.isEmpty(stu_room_full)) {
            String[] tmp = stu_room_full.split("-");
            stu_dormitory = tmp[0];
            stu_room = tmp[1];
        }
        System.out.println("11111111111"+stu_dormitory+stu_room);
        // 1
        lvKaoQin = findViewById(R.id.lvKaoQin);

        // 2
        List<Map<String, String>> data = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(this);
        // students, kaoqin
        // 【加入条件】
        String sql = "select s.stu_no, s.stu_name, k.stu_signin " +
                "from students s, kaoqin k where s.stu_no = k.stu_no AND s.stu_dormitory = ? AND s.stu_room = ? ";
        if ("0".equals(stu_status)) { // 已签到
            sql += " AND k.stu_signin <> '缺寝'";
        } else if ("1".equals(stu_status)) { // 未签到
            sql += " AND k.stu_signin = '缺寝'";
        }
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[]{stu_dormitory, stu_room});
        while (c.moveToNext()) {
            // 把查询的结果按ListView中数据的要求进行组装
            Map<String, String> row = new HashMap<>();
            row.put("stu_no", c.getString(0));
            row.put("stu_name", c.getString(1));
            row.put("stu_signin", c.getString(2));

            data.add(row);
        }
        c.close();
        db.close();
        if (data.isEmpty()){
            Toast.makeText(this,"学生信息不存在",Toast.LENGTH_LONG).show();
        }
       // System.out.println("111111111112"+data.get(0).get("stu_name"));
        // 3
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.layout_item, new String[]{"stu_no", "stu_name", "stu_signin"}, new int[]{R.id.tvStuNo, R.id.tvStuName, R.id.tvStuSignIn});
        lvKaoQin.setAdapter(adapter);
    }
}
