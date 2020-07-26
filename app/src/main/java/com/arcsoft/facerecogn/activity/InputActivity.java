package com.arcsoft.facerecogn.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arcsoft.facerecogn.R;
import com.arcsoft.facerecogn.util.DatabaseHelper;

public class InputActivity extends AppCompatActivity {
    private EditText edtNewDormitory, edtNewRoom, edtNewClass, edtNewName, edtNewNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        edtNewDormitory = findViewById(R.id.edtNewDormitory);
        edtNewRoom = findViewById(R.id.edtNewRoom);
        edtNewClass = findViewById(R.id.edtNewClass);
        edtNewName = findViewById(R.id.edtNewName);
        edtNewNumber = findViewById(R.id.edtNewNumber);
    }

    public void btn_InputClick(View view) {
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql3 = "insert into students(stu_dormitory,stu_room,stu_class,stu_name,stu_no) values(?,?,?,?,?)";
        db.execSQL(sql3,new String[]{
                edtNewDormitory.getText().toString(),
                edtNewRoom.getText().toString(),
                edtNewClass.getText().toString(),
                edtNewName.getText().toString(),
                edtNewNumber.getText().toString()});
        db.close();
        edtNewDormitory.setText("");
        edtNewRoom.setText("");
        edtNewClass.setText("");
        edtNewName.setText("");
        edtNewNumber.setText("");
        Toast.makeText(this,
                "信息录入成功"+edtNewDormitory.getText().toString()+ edtNewRoom.getText().toString()
                        +edtNewClass.getText().toString()
                        +edtNewName.getText().toString()+edtNewNumber.getText().toString(),
                Toast.LENGTH_SHORT).show();
    }
}
