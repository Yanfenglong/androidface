package com.arcsoft.facerecogn.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcsoft.facerecogn.R;
import com.arcsoft.facerecogn.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class KaoQinActivity extends AppCompatActivity {
    private Spinner spinner;
   private CheckBox checkBox, checkBox2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kao_qin);
        spinner = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        // 1. data
        List<String> data = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select distinct stu_dormitory, stu_room from students";
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()) {
            data.add(c.getString(0) + "-" + c.getString(1));
        }
        c.close();
        db.close();
        // 2.ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, data);

        // 3.绑定
        spinner.setAdapter(adapter);

    }

    public void buttonClick(View view) {
        if(!checkBox.isChecked() && !checkBox2.isChecked()) {
            Toast.makeText(this, "请至少选择一项", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(this, KaoQinListActivity.class);
        i.putExtra("stu_room", spinner.getSelectedItem().toString());
        // 0:已签到；1: 缺考；2:全部
        if(checkBox.isChecked() && checkBox2.isChecked()) {
            i.putExtra("stu_status", "2");
        } else if (checkBox.isChecked()) {
            i.putExtra("stu_status", "0");
        } else if(checkBox2.isChecked()) {
            i.putExtra("stu_status", "1");
        }
        startActivity(i);
    }
}
